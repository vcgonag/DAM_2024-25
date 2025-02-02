package vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Clase que representa la interfaz gráfica para el inicio de sesión de los
 * usuarios.
 */
public class VistaLogin extends JFrame {

	private JFrame frame;
	public JTextField userText;
	public JPasswordField passwordText;
	private JButton loginButton;

	/**
	 * Constructor de la clase. Inicializa la interfaz de login.
	 */
	public VistaLogin() {
		initialize();
	}

	/**
	 * Configura la interfaz gráfica de la ventana de inicio de sesión.
	 */
	private void initialize() {
		setTitle("Login de Usuario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350, 200);
		setLayout(null);

		JLabel userLabel = new JLabel("Usuario:");
		userLabel.setBounds(20, 20, 100, 25);
		add(userLabel);

		userText = new JTextField();
		userText.setBounds(150, 20, 160, 25);
		add(userText);

		JLabel passwordLabel = new JLabel("Contraseña:");
		passwordLabel.setBounds(20, 60, 100, 25);
		add(passwordLabel);

		passwordText = new JPasswordField();
		passwordText.setBounds(150, 60, 160, 25);
		add(passwordText);

		// Botón de Login
		loginButton = new JButton("Login");
		loginButton.setBounds(150, 100, 100, 25);
		add(loginButton);

		setVisible(true);
	}

	/**
	 * Obtiene el campo de texto donde el usuario ingresa su nombre de usuario.
	 * 
	 * @return JTextField con el nombre de usuario.
	 */
	public JTextField getUserText() {
		return userText;
	}

	/**
	 * Obtiene el campo de texto donde el usuario ingresa su contraseña.
	 * 
	 * @return JPasswordField con la contraseña del usuario.
	 */
	public JPasswordField getPasswordText() {
		return passwordText;
	}

	/**
	 * Obtiene el botón de inicio de sesión.
	 * 
	 * @return JButton para iniciar sesión.
	 */
	public JButton getLoginButton() {
		return loginButton;
	}
}
