package es.florida.AE02;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase que implementa un hilo para gestionar la comunicación entre el servidor
 * y un cliente. Proporciona funcionalidades para recibir mensajes, gestionar
 * canales y reenviar mensajes a otros clientes.
 */
public class HiloServidor implements Runnable {

    private Socket cliente;

    /**
     * Constructor que inicializa el hilo con el socket del cliente.
     *
     * @param cliente el socket conectado al cliente.
     */
    public HiloServidor(Socket cliente) {
        this.cliente = cliente;
    }

    /**
     * Método sincronizado para verificar si un nombre de usuario ya está en uso en un canal.
     *
     * @param nameUser el nombre de usuario a verificar.
     * @param canal el canal donde se busca el usuario.
     * @return true si el nombre de usuario está disponible; false en caso contrario.
     */
    public static synchronized boolean revisarNameUser(String nameUser, int canal) {
        if (nameUser.contains(" ") || nameUser == null) {
            return false;
        }
        if (Servidor.listaClienteHilo.isEmpty()) {
            return true;
        }
        for (ClienteHilo clienteHilo : Servidor.listaClienteHilo) {
            if (clienteHilo.getCanal() == canal && clienteHilo.getUsuario().equals(nameUser)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Obtiene la fecha y hora actual en formato dd/MM/yyyy HH:mm:ss.
     *
     * @return una cadena que representa la fecha y hora actual.
     */
    public static synchronized String FechaHora() {
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return fechaHoraActual.format(formato);
    }

    /**
     * Descarga un archivo desde una URL y guarda su contenido en un archivo local.
     *
     * @return una cadena que representa el contenido del archivo descargado.
     * @throws IOException si ocurre un error al acceder o guardar el archivo.
     */
    public static synchronized String getArchivoTxt() throws IOException {
        String url = "http://localhost:80/canales.txt";
        URL laUrl = new URL(url);
        InputStream is = laUrl.openStream();
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bReader = new BufferedReader(reader);
        FileWriter escritorFichero = new FileWriter("canalesDescarga.txt");
        String linea;
        StringBuilder canales = new StringBuilder();

        while ((linea = bReader.readLine()) != null) {
            escritorFichero.write(linea + "\n");
            canales.append(" ").append(linea);
        }

        escritorFichero.close();
        bReader.close();
        reader.close();
        is.close();

        return canales.toString();
    }

    /**
     * Método que ejecuta la lógica del hilo para manejar la comunicación con el cliente.
     * Lee mensajes, verifica nombres de usuario, gestiona canales y reenvía mensajes.
     */
    @Override
    public void run() {
        try {
            InputStream is = cliente.getInputStream();
            OutputStream os = cliente.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            pw.write(FechaHora() + ": Canales disponibles: [" + getArchivoTxt() + " ]\n");
            pw.flush();

            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String nameUser;
            int canal;
            boolean nameAccepted;
            do {
                System.err.println("SERVIDOR >>> Esperando nombre de usuario y canal...");
                nameUser = br.readLine();
                canal = Integer.parseInt(br.readLine());

                nameAccepted = revisarNameUser(nameUser, canal);
                pw.println(nameAccepted);
                pw.flush();
                System.err.println("SERVIDOR >>> Usuario " + nameUser + " ha selecionado Canal: " + canal);
            } while (!nameAccepted);

            ClienteHilo userInfo = new ClienteHilo(Thread.currentThread(), cliente, nameUser, canal);
            Servidor.listaClienteHilo.add(userInfo);

            System.err.println("SERVIDOR >>> Nombre final aceptado: " + nameUser);

            String mensaje;
            while ((mensaje = br.readLine()) != null) {
                System.err.println("SERVIDOR >>> " + nameUser + " (CANAL " + canal + ") >>> " + mensaje);

                if (mensaje.equals("whois")) {
                    StringBuilder usersCanal = new StringBuilder();
                    for (ClienteHilo clienteHilo : Servidor.listaClienteHilo) {
                        if (canal == clienteHilo.getCanal()) {
                            usersCanal.append(" ").append(clienteHilo.getUsuario());
                        }
                    }
                    String contenido = FechaHora() + ": Usuarios en el canal:" + usersCanal;
                    pw.write(contenido + "\n");
                    pw.flush();
                } else if (mensaje.equals("channels")) {
                    pw.write(FechaHora() + ": Canales disponibles: [" + getArchivoTxt() + " ]\n");
                    pw.flush();
                } else if (mensaje.contains("@canal")) {
                    int canalDestino = Integer.parseInt(mensaje.substring(6, 7));
                    String contenido = mensaje.substring(8);
                    for (ClienteHilo clienteHilo : Servidor.listaClienteHilo) {
                        if (canalDestino == clienteHilo.getCanal()) {
                            OutputStream os1 = clienteHilo.getSocket().getOutputStream();
                            PrintWriter pw1 = new PrintWriter(os1);
                            pw1.write(FechaHora() + ": (CANAL" + canalDestino + ", " + userInfo.getUsuario() + ") >>> "
                                    + contenido + "\n");
                            pw1.flush();
                        }
                    }
                } else if (mensaje.equals("exit")) {
                    for (ClienteHilo clienteHilo : Servidor.listaClienteHilo) {
                        if (clienteHilo.getUsuario().equals(nameUser)) {
                            Servidor.listaClienteHilo.remove(clienteHilo);
                            break;
                        }
                    }
                    break;
                } else if (mensaje != null) {
                    if (Servidor.listaClienteHilo.isEmpty()) {
                        System.out.println("No hay clientes en la lista.");
                    } else {
                        OutputStream os1;
                        PrintWriter pw1;
                        for (ClienteHilo clienteHilo : Servidor.listaClienteHilo) {
                            if (userInfo.getCanal() == clienteHilo.getCanal()) {
                                os1 = clienteHilo.getSocket().getOutputStream();
                                pw1 = new PrintWriter(os1);
                                pw1.write(FechaHora() + ": " + userInfo.getUsuario() + " >>> " + mensaje + "\n");
                                pw1.flush();
                            }
                        }
                    }
                }
            }

            pw.close();
            is.close();
            isr.close();
            os.close();
            br.close();
            cliente.close();

        } catch (IOException e) {
            System.err.println("SERVIDOR Hilo " + Thread.currentThread().getName() + " >>> ERROR");
            e.printStackTrace();
        }

    }

}
