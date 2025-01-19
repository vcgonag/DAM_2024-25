package modelo;

/**
 * Clase que realiza simulaciones de base para modelos multiproceso y multihilo.
 */
public class SimulacionBase {
	
	/**
     * Realiza una simulación basada en un tipo de proteína, calculando un valor
     * aleatorio en un intervalo de tiempo.
     * 
     * @param type el tipo de proteína (determina la duración de la simulación).
     * @return el resultado del cálculo de la simulación.
     */
	public static double simulation(int type) {
		double calc = 0.0;
		double simulationTime = Math.pow(5, type);
		double startTime = System.currentTimeMillis();
		double endTime = startTime + simulationTime;
		while (System.currentTimeMillis() < endTime) {
			calc = Math.sin(Math.pow(Math.random(), 2));
		}
		return calc;

	}

	  /**
     * Método principal que ejecuta la simulación, tomando parámetros desde la
     * línea de comandos y mostrando resultados en la salida estándar.
     * 
     * @param args los argumentos de línea de comandos:
     *             <ul>
     *             <li><b>args[0]</b>: tipo de proteína (entero).</li>
     *             <li><b>args[1]</b>: tiempo de inicio en milisegundos (long).</li>
     *             <li><b>args[2]</b>: tiempo de inicio en nanosegundos (long).</li>
     *             </ul>
     */
		public static void main(String[] args) {
		long startTime = Long.parseLong(args[1]);
		String dateStart = SimulacionMP.formatoFecha(startTime, 1);
		long nanoStart = Long.parseLong(args[2]);

		//SimulacionBase s = new SimulacionBase();
		int tipoProteina = Integer.parseInt(args[0]);

		double resultado = simulation(tipoProteina);
		
		long nanoEnd = System.nanoTime();
		long elapsedNanos = nanoEnd - nanoStart;
		long elapsedMillis = elapsedNanos / 1_000_000;
		String dateTotal = SimulacionMP.formatoFecha(elapsedMillis,2);
		
		long endTime =  startTime + elapsedMillis;//SimulacionBase.getEndTimeReal(); System.currentTimeMillis();
		String dateEnd = SimulacionMP.formatoFecha(endTime, 1);

		System.out.println(dateStart);
		System.out.println(dateEnd);
		System.out.println(dateTotal);
		System.out.println(resultado);
	}
	
	
}
