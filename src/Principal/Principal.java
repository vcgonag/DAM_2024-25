package Principal;

import modelo.SimulacionMP;
import modelo.SimulacionMT;
import vista.Simulador;
import controlador.ControladorSimulacion;

/**
 * Clase principal que inicializa la aplicación, configurando la vista, los modelos
 * y el controlador para gestionar la simulación de proteínas.
 */
public class Principal {

	 /**
     * Método principal que actúa como punto de entrada de la aplicación.
     *
     * @param args argumentos de línea de comandos (no utilizados).
     */
	public static void main(String[] args) {
		Simulador simulador = new Simulador();
		SimulacionMP simulacionMP = new SimulacionMP();
		SimulacionMT simulacionMT = new SimulacionMT(0, 0);
		ControladorSimulacion cs = new ControladorSimulacion(simulador, simulacionMP, simulacionMT);
		cs.control();
	}

}
