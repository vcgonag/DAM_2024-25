package vista;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Clase que representa la interfaz gráfica del menú principal para
 * administradores.
 */
public class VistaMenuAdmin extends JFrame {

	private JButton registroUsuarioButton;
	private JButton consultasSQLButton;

	/**
	 * Constructor de la clase. Inicializa la interfaz del menú de administrador.
	 */
	public VistaMenuAdmin() {
		initialize();
	}

	/**
	 * Configura la interfaz gráfica del menú de administración.
	 */
	private void initialize() {
		// Configurar la ventana
		setTitle("Menú Principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 200);
		setLayout(null);

		// Botón de Registro de Usuario
		registroUsuarioButton = new JButton("Registro de Usuario");
		registroUsuarioButton.setBounds(60, 40, 180, 30);
		add(registroUsuarioButton);

		// Botón de Iniciar Consultas SQL
		consultasSQLButton = new JButton("Iniciar Consultas SQL");
		consultasSQLButton.setBounds(60, 90, 180, 30);
		add(consultasSQLButton);

		// Mostrar ventana
		setVisible(true);
	}

	/**
	 * Obtiene el botón para acceder al registro de usuarios.
	 * 
	 * @return JButton de registro de usuario.
	 */
	public JButton getRegistroUsuarioButton() {
		return registroUsuarioButton;
	}

	/**
	 * Obtiene el botón para iniciar consultas SQL.
	 * 
	 * @return JButton de consultas SQL.
	 */
	public JButton getConsultasSQLButton() {
		return consultasSQLButton;
	}
}
