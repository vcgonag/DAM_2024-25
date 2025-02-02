package vista;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Clase que representa la interfaz gráfica para importar archivos CSV.
 */
public class VistaImportarCSV extends JFrame {

	private JButton importarButton;

	/**
	 * Constructor de la clase. Inicializa la interfaz de importación de CSV.
	 */
	public VistaImportarCSV() {
		initialize();
	}

	/**
	 * Configura la interfaz gráfica de la ventana de importación de archivos CSV.
	 */
	private void initialize() {
		// Crear la ventana
		setTitle("Importar CSV");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 150);
		setLayout(null);

		// Botón de Importar CSV
		importarButton = new JButton("Importar CSV");
		importarButton.setBounds(80, 50, 140, 30);
		add(importarButton);

		// Mostrar ventana
		setVisible(true);
	}

	/**
	 * Obtiene el botón de importación de CSV.
	 * 
	 * @return JButton para importar el archivo CSV.
	 */
	public JButton getImportarButton() {
		return importarButton;
	}
}
