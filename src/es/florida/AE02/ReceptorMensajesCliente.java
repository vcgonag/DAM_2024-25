package es.florida.AE02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Clase que implementa un hilo para recibir y procesar mensajes del servidor.
 * Filtra mensajes para evitar que los enviados por el propio cliente se muestren nuevamente.
 */
public class ReceptorMensajesCliente implements Runnable {

    private Socket cliente;
    private String nombreUsuario;

    /**
     * Constructor que inicializa el receptor de mensajes con el socket del cliente y su nombre de usuario.
     *
     * @param cliente el socket conectado al servidor.
     * @param nombreUsuario el nombre del cliente que está recibiendo los mensajes.
     */
    public ReceptorMensajesCliente(Socket cliente, String nombreUsuario) {
        this.cliente = cliente;
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Método principal que ejecuta el hilo.
     * Lee los mensajes del servidor y los muestra en la consola, filtrando los mensajes enviados por el propio cliente.
     */
    @Override
    public void run() {
        InputStream is;
        try {
            is = cliente.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String mensaje;
            
            while ((mensaje = br.readLine()) != null) {       
                if (!mensaje.contains(nombreUsuario + " >>> ")) {
                    System.err.println(mensaje);
                }
            }

        } catch (IOException e) {
            if (e instanceof java.net.SocketException && "Socket closed".equals(e.getMessage())) {
                System.out.println("La conexión fue cerrada. Finalizando el hilo...");
            } else {
                e.printStackTrace();
            }
        }
    }
}
