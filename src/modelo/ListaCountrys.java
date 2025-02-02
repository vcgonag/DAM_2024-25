package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona una lista de objetos {@link Countrys}.
 */
public class ListaCountrys {

	private List<Countrys> lista = new ArrayList<>();

	/**
	 * Constructor de la clase. Inicializa una lista vacía de países.
	 */
	public ListaCountrys() {
	}

	/**
	 * Añade un país a la lista.
	 * 
	 * @param coun Objeto {@link Countrys} que representa un país.
	 */
	public void anyadirCountry(Countrys coun) {
		lista.add(coun);
	}

	/**
	 * Obtiene la lista de países almacenada.
	 * 
	 * @return Lista de objetos {@link Countrys}.
	 */
	public List<Countrys> getListaCountrys() {
		return lista;
	}
}
