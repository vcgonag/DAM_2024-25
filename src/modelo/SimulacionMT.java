package modelo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Clase que implementa la simulación multihilo (MT) de un proceso de cálculo
 * basado en el tipo y el orden de una proteína.
 */
public class SimulacionMT implements Runnable {

	private int tipoProteina;
	private int ordenProteina;

	/**
	 * Constructor que inicializa una simulación multihilo con el tipo y el orden de
	 * la proteína.
	 *
	 * @param tipoProteina  el tipo de proteína a simular.
	 * @param ordenProteina el orden de la proteína en la simulación.
	 */
	public SimulacionMT(int tipoProteina, int ordenProteina) {
		this.tipoProteina = tipoProteina;
		this.ordenProteina = ordenProteina;
	}

	/**
	 * Método que ejecuta la simulación multihilo. Realiza cálculos basados en el
	 * tipo de proteína y guarda los resultados en un archivo.
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		long startTime = System.currentTimeMillis();
		String dateStart = SimulacionMP.formatoFecha(startTime, 1);
		long nanoStart = System.nanoTime();

		double resultado = SimulacionBase.simulation(tipoProteina);

		long nanoEnd = System.nanoTime();
		long elapsedNanos = nanoEnd - nanoStart;
		long elapsedMillis = elapsedNanos / 1000000;
		String dateTotal = SimulacionMP.formatoFecha(elapsedMillis, 2);

		long endTime = startTime + elapsedMillis;// SimulacionBase.getEndTimeReal(); System.currentTimeMillis();
		String dateEnd = SimulacionMP.formatoFecha(endTime, 1);

		String nombreArchivo = "PROT_MT_" + tipoProteina + "_n" + ordenProteina + "_" + dateStart + ".sim";
		String[] contenidoArchivo = new String[4];
		contenidoArchivo[0] = dateStart;
		contenidoArchivo[1] = dateEnd;
		contenidoArchivo[2] = dateTotal;
		contenidoArchivo[3] = String.valueOf(resultado);

		crearArchivo(nombreArchivo, contenidoArchivo);
	}

	/**
	 * Crea un archivo para almacenar los resultados de la simulación.
	 *
	 * @param nombreArchivo    el nombre del archivo a crear.
	 * @param contenidoArchivo los datos que se escribirán en el archivo.
	 */
	private void crearArchivo(String nombreArchivo, String[] contenidoArchivo) {

		File fichResultado = new File(nombreArchivo);
		try {
			FileWriter fw = new FileWriter(fichResultado);
			BufferedWriter bw = new BufferedWriter(fw);

			for (int i = 0; i < contenidoArchivo.length; i++) {
				String linea = contenidoArchivo[i];
				bw.write(linea);
				bw.newLine();
			}
			bw.close();
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Ejecuta una simulación multihilo con el tipo y la cantidad de proteínas
	 * especificadas.
	 *
	 * @param tipoProteina   el tipo de proteína a simular.
	 * @param totalProteinas el número total de proteínas a simular.
	 * @return una cadena que indica el tiempo total de ejecución en segundos y
	 *         centésimas.
	 */
	public String ejecutarSimulacionMultihilo(int tipoProteina, int totalProteinas) {
		ArrayList<Thread> hilos = new ArrayList<>();
		long tiempoInicio = System.nanoTime();

		for (int i = 1; i <= totalProteinas; i++) {
			SimulacionMT simulacion = new SimulacionMT(tipoProteina, i); // Tipo de proteína y orden de
																			// simulación
			Thread hilo = new Thread(simulacion);
			hilos.add(hilo);
			hilo.start();
		}

		for (Thread hilo : hilos) {
			try {
				hilo.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		long tiempoFin = System.nanoTime();
		long duracionMilisegundos = (tiempoFin - tiempoInicio) / 10000000;
		long segundos = duracionMilisegundos / 1000;
		long centesimas = (duracionMilisegundos % 1000) / 10;

		String tiempoTotal = "FIN - Tiempo ejecucion TOTAL: " + segundos + " seg " + centesimas + " centesimas";
		System.out.println("FIN - Tiempo ejecucion TOTAL: " + segundos + " seg " + centesimas + " centesimas");
		return tiempoTotal;
	}

}
