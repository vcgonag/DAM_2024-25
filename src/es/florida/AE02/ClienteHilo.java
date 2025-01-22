package es.florida.AE02;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;

/**
 * Representa un cliente conectado al servidor en un canal específico. Esta
 * clase encapsula la información del cliente, incluyendo su hilo, socket,
 * usuario y canal.
 */
public class ClienteHilo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Thread hilo;
	private Socket socket;
	private String usuario;
	private int canal;

	/**
	 * Constructor que inicializa un cliente con su hilo, socket, nombre de usuario
	 * y canal.
	 *
	 * @param hilo     el hilo asociado al cliente.
	 * @param socket   el socket conectado al cliente.
	 * @param nameUser el nombre de usuario del cliente.
	 * @param canal    el canal al que pertenece el cliente.
	 */
	public ClienteHilo(Thread hilo, Socket socket, String nameUser, int canal) {
		this.hilo = hilo;
		this.socket = socket;
		this.usuario = nameUser;
		this.canal = canal;
	}

	/**
	 * Obtiene el hilo asociado al cliente.
	 *
	 * @return el hilo del cliente.
	 */
	public Thread getHilo() {
		return hilo;
	}

	/**
	 * Establece el hilo asociado al cliente.
	 *
	 * @param hilo el nuevo hilo del cliente.
	 */
	public void setHilo(Thread hilo) {
		this.hilo = hilo;
	}

	/**
	 * Obtiene el socket del cliente.
	 *
	 * @return el socket conectado al cliente.
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * Establece el socket del cliente.
	 *
	 * @param socket el nuevo socket del cliente.
	 */
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	/**
	 * Obtiene el nombre de usuario del cliente.
	 *
	 * @return el nombre de usuario del cliente.
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * Establece el nombre de usuario del cliente.
	 *
	 * @param usuario el nuevo nombre de usuario del cliente.
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * Obtiene el canal al que pertenece el cliente.
	 *
	 * @return el canal del cliente.
	 */
	public int getCanal() {
		return canal;
	}

	/**
	 * Establece el canal al que pertenece el cliente.
	 *
	 * @param canal el nuevo canal del cliente.
	 */
	public void setCanal(int canal) {
		this.canal = canal;
	}

	/**
	 * Devuelve una representación en forma de cadena de los datos del cliente.
	 *
	 * @return una cadena que representa los datos del cliente.
	 */
	@Override
	public String toString() {
		return "ClienteHilo [hilo=" + hilo + ", socket=" + socket + ", usuario=" + usuario + ", canal=" + canal + "]";
	}
}
