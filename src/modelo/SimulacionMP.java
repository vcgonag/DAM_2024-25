package modelo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Clase que implementa la simulación multiproceso (MP) utilizando instancias de
 * procesos para ejecutar simulaciones basadas en el tipo y el orden de
 * proteínas.
 */
public class SimulacionMP {

	/**
	 * Lanza una simulación multiproceso para un tipo y orden de proteína
	 * especificados.
	 *
	 * @param tipoProteina  el tipo de proteína a simular.
	 * @param ordenProteina el orden de la proteína en la simulación.
	 */
	public static void lanzarSimulacion(int tipoProteina, int ordenProteina) {
		String clase = "modelo.SimulacionBase";
		long startTime = System.currentTimeMillis();
		long nanoStart = System.nanoTime();
		String dateStart = formatoFecha(startTime, 1);

		String nombreArchivo = "PROT_MP_" + tipoProteina + "_n" + ordenProteina + "_" + dateStart + ".sim";
		System.out.println("PROT_MP_" + tipoProteina + "_n" + ordenProteina + "_" + dateStart + ".sim");

		File fichResultado = new File(nombreArchivo);

		try {
			String javaHome = System.getProperty("java.home");
			String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
			String classpath = System.getProperty("java.class.path");
			String className = clase;

			ArrayList<String> command = new ArrayList<>();
			command.add(javaBin);
			command.add("-cp");
			command.add(classpath);
			command.add(className);
			command.add(String.valueOf(tipoProteina));
			command.add(String.valueOf(startTime));
			command.add(String.valueOf(nanoStart));

			ProcessBuilder builder = new ProcessBuilder(command);
			// builder.inheritIO().start();
			builder.redirectOutput(fichResultado);
			builder.start();
			Process process = builder.start();

			// Esperar a que este proceso termine
			process.waitFor();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Formatea una marca de tiempo en diferentes formatos según el tipo
	 * especificado.
	 *
	 * @param time     el tiempo en milisegundos a formatear.
	 * @param tipoDate el tipo de formato (1 para fecha completa, 2 para segundos y
	 *                 centésimas).
	 * @return una cadena que representa el tiempo formateado.
	 */
	public static String formatoFecha(long time, int tipoDate) {
		String fecha = "";
		if (tipoDate == 1) {
			Date date = new Date(time);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");
			fecha = dateFormat.format(date);
			return fecha;
		} else if (tipoDate == 2) {
			Date date = new Date(time);
			SimpleDateFormat dateFormat = new SimpleDateFormat("s_SS");
			fecha = dateFormat.format(date);
			return fecha;
		}
		return fecha;
	}

	/**
	 * Ejecuta una simulación multiproceso para un tipo de proteína y una cantidad
	 * total.
	 *
	 * @param tipoProteina   el tipo de proteína a simular.
	 * @param totalProteinas el número total de proteínas a simular.
	 * @return una cadena que indica el tiempo total de ejecución en segundos y
	 *         centésimas.
	 */
	public String ejecutarSimulacionMultiproceso(int tipoProteina, int totalProteinas) {
		long tiempoInicio = System.nanoTime();

		for (int i = 1; i <= totalProteinas; i++) {
			lanzarSimulacion(tipoProteina, i);
		}

		long tiempoFin = System.nanoTime();
		long duracionNanosegundos = tiempoFin - tiempoInicio;

		// Convertir la duración a SEGUNDOS_CENTÉSIMAS
		long duracionMilisegundos = duracionNanosegundos / 1_000_000;
		long segundos = duracionMilisegundos / 1000;
		long centesimas = (duracionMilisegundos % 1000) / 10;

		String tiempoTotal = "FIN - Tiempo ejecucion TOTAL: " + segundos + " seg " + centesimas + " centesimas";
		System.out.println("FIN - Tiempo ejecucion TOTAL: " + segundos + " seg " + centesimas + " centesimas");
		return tiempoTotal;
	}

}
