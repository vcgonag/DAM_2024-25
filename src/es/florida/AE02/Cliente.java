package es.florida.AE02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * Cliente que se conecta a un servidor y permite el intercambio de mensajes.
 * Proporciona opciones para seleccionar un canal, establecer un nombre de usuario,
 * y enviar/recibir mensajes en tiempo real.
 */
public class Cliente {

    /**
     * Punto de entrada del programa cliente.
     * 
     * @param args argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);
        
        String host;
        int puerto;
        Socket cliente;

        while (true) {
            try {
                System.out.print("Introducir IP: ");
                host = teclado.next();
                System.out.print("Introducir PUERTO: ");
                puerto = Integer.parseInt(teclado.next());
                cliente = new Socket(host, puerto);
                break; 
            } catch (NumberFormatException e) {
                System.out.println("Error: El puerto debe ser un número. Inténtelo de nuevo.");
            } catch (IOException e) {
                System.out.println("Error: Host o puerto incorrectos. Inténtelo de nuevo.");
                System.out.println("\n");
            }
        }

        try {
            InputStream is = cliente.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String opcionCanal = br.readLine(); 
            System.out.print(opcionCanal + ":");
            String seleccionCanal = teclado.next();

            OutputStream os = cliente.getOutputStream();
            PrintWriter pw = new PrintWriter(os);

            boolean whois = false;
            String nameUser;
            do {
                System.out.print("Indica nombre de usuario (sin espacios): ");
                nameUser = teclado.next();
                pw.println(seleccionCanal);
                pw.flush(); 
                whois = Boolean.parseBoolean(br.readLine()); 
                if (!whois) {
                    if (nameUser == null) {
                        System.out.print("Error: rellena el nombre. ");
                    } else if (nameUser.contains(" ")) {
                        System.out.print("Error: El nombre de usuario no puede contener espacios. ");
                    } else {
                        System.out.println("El nombre ya existe. Intente otro: ");
                    }
                }
            } while (!whois);

            System.out.println("Nombre aceptado: " + nameUser);

            ReceptorMensajesCliente rmc = new ReceptorMensajesCliente(cliente, nameUser);
            Thread hilo = new Thread(rmc);
            hilo.start();

            System.out.println("Presiona enter para enviar mensajes");
            teclado.nextLine(); 

            while (true) {
                teclado.nextLine();

                String input = JOptionPane.showInputDialog(null, "Introduce 'exit' para cerrar", nameUser,
                        JOptionPane.QUESTION_MESSAGE);

                System.out.println(HiloServidor.FechaHora() + ": " + input);
                pw.println(input);
                pw.flush();

                if (input.equals("exit")) {
                    System.out.println("Desconectando...");
                    break;
                }
            }

            pw.close();
            os.close();
            is.close();
            isr.close();
            cliente.close();
            teclado.close();

        } catch (IOException e) {
            System.out.println("CLIENTE >> Error");
            e.printStackTrace();
        }
        teclado.close();
    }
}
