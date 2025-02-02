package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Clase que representa la interfaz gráfica para que los clientes realicen
 * consultas SQL.
 */
public class VistaCliente extends JFrame {

	private JTextField consultaTextField;
	private JButton ejecutarConsultaBtn;
	private JButton importarCsvBtn;
	private JButton logoutBtn;
	private JTable resultTable;
	private DefaultTableModel tableModel;

	/**
	 * Constructor de la clase. Configura la interfaz gráfica de la ventana de
	 * cliente.
	 */
	public VistaCliente() {
		setTitle("Cliente - Consultas SQL");
		setSize(700, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		getContentPane().setBackground(new Color(210, 210, 210)); // Fondo más oscuro

		// Panel superior con campo de consulta y botón de ejecución
		JPanel topPanel = new JPanel(new BorderLayout(5, 5));
		consultaTextField = new JTextField(50);
		ejecutarConsultaBtn = new JButton("Ejecutar");

		topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding 1cm
		topPanel.add(consultaTextField, BorderLayout.CENTER);
		topPanel.add(ejecutarConsultaBtn, BorderLayout.EAST);
		add(topPanel, BorderLayout.NORTH);

		// Panel central con tabla para mostrar resultados
		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		tableModel = new DefaultTableModel();
		resultTable = new JTable(tableModel);
		resultTable.setPreferredScrollableViewportSize(new Dimension(600, 150));

		JScrollPane scrollPane = new JScrollPane(resultTable);
		scrollPane.setPreferredSize(new Dimension(600, 180));
		centerPanel.add(scrollPane, BorderLayout.CENTER);
		add(centerPanel, BorderLayout.CENTER);

		// Panel inferior con botones de exportación y cierre de sesión
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		importarCsvBtn = new JButton("Importar CSV");
		logoutBtn = new JButton("Logout");

		bottomPanel.add(importarCsvBtn);
		bottomPanel.add(logoutBtn);
		add(bottomPanel, BorderLayout.SOUTH);

		setLocationRelativeTo(null); // Centrar la ventana en pantalla
		setVisible(true);
	}

	/**
	 * Obtiene el campo de texto donde se ingresa la consulta SQL.
	 * 
	 * @return JTextField con la consulta SQL.
	 */
	public JTextField getConsultaTextField() {
		return consultaTextField;
	}

	/**
	 * Obtiene el botón para ejecutar la consulta SQL.
	 * 
	 * @return JButton de ejecución de consulta.
	 */
	public JButton getEjecutarConsultaBtn() {
		return ejecutarConsultaBtn;
	}

	/**
	 * Obtiene el botón para importar los resultados a un archivo CSV.
	 * 
	 * @return JButton para la importación de CSV.
	 */
	public JButton getImportarCsvBtn() {
		return importarCsvBtn;
	}

	/**
	 * Obtiene el botón para cerrar sesión.
	 * 
	 * @return JButton de cierre de sesión.
	 */
	public JButton getLogoutBtn() {
		return logoutBtn;
	}

	/**
	 * Obtiene la tabla que muestra los resultados de la consulta SQL.
	 * 
	 * @return JTable con los resultados.
	 */
	public JTable getResultTable() {
		return resultTable;
	}

	/**
	 * Obtiene el modelo de la tabla.
	 * 
	 * @return DefaultTableModel de la tabla.
	 */
	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	/**
	 * Método no implementado que parece estar incompleto.
	 * 
	 * @return AbstractButton (actualmente devuelve null).
	 */
	public AbstractButton getTablaResultados() {
		return null;
	}
}
