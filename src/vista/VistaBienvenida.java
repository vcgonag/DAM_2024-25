package vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Clase que representa la pantalla de bienvenida del juego Blackjack.
 */
public class VistaBienvenida extends JFrame {

    private JButton registroButton;
    private JButton loginButton;

    /**
     * Constructor que inicializa la interfaz gráfica.
     */
    public VistaBienvenida() {
        initialize();
    }

    /**
     * Configura los componentes de la ventana.
     */
    private void initialize() {
        setTitle("Bienvenido a Blackjack");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 200); 
        setLayout(null);

        JLabel bienvenidaLabel = new JLabel("¡Bienvenido a Blackjack!");
        bienvenidaLabel.setBounds(90, 30, 200, 25);
        add(bienvenidaLabel);

        registroButton = new JButton("Registro");
        registroButton.setBounds(50, 80, 100, 30);
        add(registroButton);

        loginButton = new JButton("Login");
        loginButton.setBounds(190, 80, 100, 30);
        add(loginButton);

        setVisible(true);
    }

    /**
     * Obtiene el botón de registro de usuario.
     * @return Botón de registro.
     */
    public JButton getRegistroButton() {
        return registroButton;
    }

    /**
     * Obtiene el botón de inicio de sesión.
     * @return Botón de login.
     */
    public JButton getLoginButton() {
        return loginButton;
    }
}
