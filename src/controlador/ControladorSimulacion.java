package controlador;

import modelo.SimulacionMP;
import modelo.SimulacionMT;
import vista.Simulador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JOptionPane;

/**
 * Clase que controla las simulaciones multiproceso y multihilo y gestiona la
 * comunicación entre la vista y los modelos.
 */
public class ControladorSimulacion {

	private Simulador simulador;
	private SimulacionMP simulacionMP;
	private SimulacionMT simulacionMT;
	private ActionListener actionListenerSimular;

	/**
	 * Constructor que inicializa el controlador con las dependencias necesarias.
	 * 
	 * @param simulador    la vista de la aplicación.
	 * @param simulacionMP el modelo para las simulaciones multiproceso.
	 * @param simulacionMT el modelo para las simulaciones multihilo.
	 */
	public ControladorSimulacion(Simulador simulador, SimulacionMP simulacionMP, SimulacionMT simulacionMT) {
		this.simulador = simulador;
		this.simulacionMP = simulacionMP;
		this.simulacionMT = simulacionMT;
	}

	/**
	 * Método que asocia los eventos a los componentes de la vista. Configura el
	 * listener del botón de simulación.
	 */
	public void control() {

		actionListenerSimular = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				String tiempoTotalMP = "";
				String tiempoTotalMT = "";
				boolean entradaValida = false;
				int totalProteinas = 0;

				try {
					// Validar y convertir la entrada
					totalProteinas = Integer.parseInt(simulador.getTextFieldNumeroProteinas().getText());
					entradaValida = true;
				} catch (NumberFormatException e) {
					System.out.println("Error al convertir el número de proteínas.");
					JOptionPane.showMessageDialog(null, "Introduzca un numero", "Error", JOptionPane.ERROR_MESSAGE);
				}
				if (entradaValida) {
					// Ejecutar simulaciones si la entrada es válida
					int tipoProteina = selecciontipoProteina();
					tiempoTotalMP = simulacionMP.ejecutarSimulacionMultiproceso(tipoProteina, totalProteinas);
					tiempoTotalMT = simulacionMT.ejecutarSimulacionMultihilo(tipoProteina, totalProteinas);

					imprimirPantalla(tiempoTotalMP, tiempoTotalMT);
				}
			}
		};
		simulador.getBtnSimular().addActionListener(actionListenerSimular);
	}

	/**
	 * Determina el tipo de proteína seleccionado en la vista.
	 * 
	 * @return un entero que representa el tipo de proteína seleccionado.
	 */
	public int selecciontipoProteina() {
		int tipoProteina = 0;
		if (simulador.getRadioButton1().isSelected()) {
			tipoProteina = 1;
		} else if (simulador.getRadioButton2().isSelected()) {
			tipoProteina = 2;
		} else if (simulador.getRadioButton3().isSelected()) {
			tipoProteina = 3;
		} else if (simulador.getRadioButton4().isSelected()) {
			tipoProteina = 4;
		}
		return tipoProteina;
	}

	/**
	 * Muestra los resultados de las simulaciones en la vista.
	 * 
	 * @param tiempoTotalMP el tiempo total de la simulación multiproceso.
	 * @param tiempoTotalMT el tiempo total de la simulación multihilo.
	 */
	public void imprimirPantalla(String tiempoTotalMP, String tiempoTotalMT) {
		String URL = ".";

		simulador.getTextAreaSimulacionMP().setText("--------------------------------------------------------------\n");
		simulador.getTextAreaSimulacionMT().setText("--------------------------------------------------------------\n");
		File directorio = new File(URL);
		File[] ficheros = directorio.listFiles();
		for (int i = 0; i < ficheros.length; i++) {
			if (ficheros[i].getName().contains("PROT_MP_")) {
				simulador.getTextAreaSimulacionMP().append(ficheros[i] + "\n");
			} else if (ficheros[i].getName().contains("PROT_MT_")) {
				simulador.getTextAreaSimulacionMT().append(ficheros[i] + "\n");
			}

		}
		simulador.getTextAreaSimulacionMP()
				.append(tiempoTotalMP + "\n--------------------------------------------------------------");
		simulador.getTextAreaSimulacionMT()
				.append(tiempoTotalMT + "\n--------------------------------------------------------------");
		
		
	}

}
