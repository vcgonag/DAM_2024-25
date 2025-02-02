package vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Clase que representa la interfaz gráfica para el registro de usuarios.
 */
public class VistaRegistroUsuario extends JFrame {

	public JTextField userText;
	public JPasswordField passwordText;
	public JPasswordField confirmPasswordText;
	private JButton registerButton;

	/**
	 * Constructor de la clase. Inicializa la interfaz de registro de usuarios.
	 */
	public VistaRegistroUsuario() {
		initialize();
	}

	/**
	 * Configura la interfaz gráfica de la ventana de registro de usuarios.
	 */
	private void initialize() {

		setTitle("Registro de Usuario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350, 250);
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

		JLabel confirmLabel = new JLabel("Confirmar:");
		confirmLabel.setBounds(20, 100, 100, 25);
		add(confirmLabel);

		confirmPasswordText = new JPasswordField();
		confirmPasswordText.setBounds(150, 100, 160, 25);
		add(confirmPasswordText);

		registerButton = new JButton("Registrar");
		registerButton.setBounds(150, 140, 100, 25);
		add(registerButton);

		setVisible(true);
	}

	/**
	 * Obtiene el campo de texto donde el usuario ingresa su nombre.
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
	 * Obtiene el campo de texto donde el usuario confirma su contraseña.
	 * 
	 * @return JPasswordField con la confirmación de la contraseña.
	 */
	public JPasswordField getConfirmPasswordText() {
		return confirmPasswordText;
	}

	/**
	 * Obtiene el botón para registrar un usuario.
	 * 
	 * @return JButton para registrar al usuario.
	 */
	public JButton getRegisterButton() {
		return registerButton;
	}
}
