package controlador;

import modelo.SimulacionMP;
import modelo.SimulacionMT;
import vista.Simulador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    private static List<String> archivosMP = new ArrayList<>();
    private static List<String> archivosMT = new ArrayList<>();

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
     * Método que asocia los eventos a los componentes de la vista.
     * Configura el listener del botón de simulación para ejecutar simulaciones.
     */
    public void control() {
        actionListenerSimular = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String tiempoTotalMP = "";
                String tiempoTotalMT = "";
                boolean entradaValida = false;
                int totalProteinas = 0;

                try {                   
                    totalProteinas = Integer.parseInt(simulador.getTextFieldNumeroProteinas().getText());
                    entradaValida = true;
                } catch (NumberFormatException e) {
                    System.out.println("Error al convertir el número de proteínas.");
                    JOptionPane.showMessageDialog(null, "Introduzca un número", "Error", JOptionPane.ERROR_MESSAGE);
                }
                if (entradaValida) {                  
                    int tipoProteina = selecciontipoProteina();
                    tiempoTotalMP = simulacionMP.ejecutarSimulacionMultiproceso(tipoProteina, totalProteinas);
                    tiempoTotalMT = simulacionMT.ejecutarSimulacionMultihilo(tipoProteina, totalProteinas);

                    imprimirPantalla(tiempoTotalMP, tiempoTotalMT, tipoProteina, totalProteinas);
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
     * Filtra, ordena y muestra las simulaciones más recientes en el área de texto.
     *
     * @param tiempoTotalMP    el tiempo total de la simulación multiproceso.
     * @param tiempoTotalMT    el tiempo total de la simulación multihilo.
     * @param tipoProteina     el tipo de proteína seleccionada.
     * @param totalProteinas   el número de simulaciones realizadas.
     */
    public void imprimirPantalla(String tiempoTotalMP, String tiempoTotalMT, int tipoProteina, int totalProteinas) {
        String URL = ".";

        simulador.getTextAreaSimulacionMP().setText("--------------------------------------------------------------\n");
        simulador.getTextAreaSimulacionMT().setText("--------------------------------------------------------------\n");
        File directorio = new File(URL);
        File[] ficheros = directorio.listFiles();

        archivosMP.clear();
        archivosMT.clear();

        String filtroProteinaMP = "PROT_MP_" + tipoProteina + "_";
        String filtroProteinaMT = "PROT_MT_" + tipoProteina + "_";
        for (File fichero : ficheros) {
            String nombreArchivo = fichero.getName();
            if (nombreArchivo.contains(filtroProteinaMP)) {
                archivosMP.add(nombreArchivo);
            } else if (nombreArchivo.contains(filtroProteinaMT)) {
                archivosMT.add(nombreArchivo);
            }
        }

        ordenarArchivosPorSimulacion(archivosMP);
        ordenarArchivosPorSimulacion(archivosMT);

        int inicioMP = Math.max(0, archivosMP.size() - totalProteinas);
        int inicioMT = Math.max(0, archivosMT.size() - totalProteinas);

        for (int i = inicioMP; i < archivosMP.size(); i++) {
            simulador.getTextAreaSimulacionMP().append(archivosMP.get(i) + "\n");
        }
        simulador.getTextAreaSimulacionMP()
                .append(tiempoTotalMP + "\n--------------------------------------------------------------");

        for (int i = inicioMT; i < archivosMT.size(); i++) {
            simulador.getTextAreaSimulacionMT().append(archivosMT.get(i) + "\n");
        }
        simulador.getTextAreaSimulacionMT()
                .append(tiempoTotalMT + "\n--------------------------------------------------------------");
    }

    /**
     * Ordena una lista de nombres de archivos según su marca de tiempo.
     * Las simulaciones más recientes aparecerán al principio.
     *
     * @param archivos la lista de nombres de archivos a ordenar.
     */
    private void ordenarArchivosPorSimulacion(List<String> archivos) {
        for (int i = 0; i < archivos.size() - 1; i++) {
            for (int j = 0; j < archivos.size() - i - 1; j++) {
 
                String timestamp1 = extraerTimestamp(archivos.get(j));
                String timestamp2 = extraerTimestamp(archivos.get(j + 1));

                if (timestamp1.compareTo(timestamp2) < 0) {
                    String temp = archivos.get(j);
                    archivos.set(j, archivos.get(j + 1));
                    archivos.set(j + 1, temp);
                }
            }
        }
    }

    /**
     * Extrae la marca de tiempo de un nombre de archivo.
     *
     * @param nombreArchivo el nombre del archivo del cual extraer la marca de tiempo.
     * @return la marca de tiempo como cadena.
     */
    private String extraerTimestamp(String nombreArchivo) {
        try {
            int inicio = nombreArchivo.lastIndexOf("_") - 15; 
            int fin = nombreArchivo.lastIndexOf("_");
            return nombreArchivo.substring(inicio, fin);
        } catch (Exception e) {
            return "00000000_000000_000"; 
        }
    }
}
