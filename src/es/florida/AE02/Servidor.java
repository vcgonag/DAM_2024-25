package es.florida.AE02;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase principal que implementa un servidor para gestionar conexiones de clientes.
 * El servidor escucha en un puerto específico y crea un hilo para cada cliente que se conecta.
 */
public class Servidor {

    /**
     * Lista sincronizada de los clientes conectados al servidor.
     */
    public static List<ClienteHilo> listaClienteHilo = Collections.synchronizedList(new ArrayList<>());

    /**
     * Método principal que inicia el servidor.
     * Escucha conexiones entrantes en el puerto 9007 y lanza un hilo para cada cliente conectado.
     *
     * @param args argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        System.err.println("SERVIDOR >>> Arranca el servidor, espera petición...");
        ServerSocket socketEscucha = null;

        try {            
            socketEscucha = new ServerSocket(9007);
        } catch (IOException e) {
            System.err.println("SERVIDOR >>> Error al iniciar el servidor");
            e.printStackTrace();
            return;
        }

        while (true) {
            Socket conexion;
            try {
                conexion = socketEscucha.accept();
                System.err.println("SERVIDOR >>> Conexion recibida ---> Lanza nuevo hilo");

                HiloServidor h = new HiloServidor(conexion);
                Thread hilo = new Thread(h);
                hilo.start();
            } catch (IOException e) {
                System.err.println("SERVIDOR >>> Error al aceptar una conexión");
                e.printStackTrace();
            }
        }
    }
}
