package vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Clase que representa la interfaz gráfica para el inicio de sesión de los usuarios.
 */
public class VistaLogin extends JFrame {

    public JTextField userText;
    public JPasswordField passwordText;
    public JPasswordField confirmPasswordText;
    private JButton loginButton;

    /**
     * Constructor de la clase. Inicializa la interfaz gráfica de inicio de sesión.
     */
    public VistaLogin() {
        initialize();
    }

    /**
     * Configura los componentes gráficos de la ventana de inicio de sesión.
     */
    private void initialize() {
        setTitle("Login de Usuario");
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

        JLabel confirmPasswordLabel = new JLabel("Confirmar:");
        confirmPasswordLabel.setBounds(20, 100, 100, 25);
        add(confirmPasswordLabel);

        confirmPasswordText = new JPasswordField();
        confirmPasswordText.setBounds(150, 100, 160, 25);
        add(confirmPasswordText);

        loginButton = new JButton("Login");
        loginButton.setBounds(150, 140, 100, 25);
        add(loginButton);

        setVisible(true);
    }

    /**
     * Obtiene el campo de texto donde el usuario ingresa su nombre de usuario.
     *
     * @return El campo de texto para el nombre de usuario.
     */
    public JTextField getUserText() {
        return userText;
    }

    /**
     * Obtiene el campo de texto donde el usuario ingresa su contraseña.
     *
     * @return El campo de texto para la contraseña.
     */
    public JPasswordField getPasswordText() {
        return passwordText;
    }

    /**
     * Obtiene el campo de texto donde el usuario confirma su contraseña.
     *
     * @return El campo de texto para confirmar la contraseña.
     */
    public JPasswordField getConfirmPasswordText() {
        return confirmPasswordText;
    }

    /**
     * Obtiene el botón de inicio de sesión.
     *
     * @return El botón de inicio de sesión.
     */
    public JButton getLoginButton() {
        return loginButton;
    }
}
