package vista;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Clase que representa la interfaz gráfica para importar archivos CSV.
 */
public class VistaLoadCards extends JFrame {

    private JButton loadCardsButton;

    /**
     * Constructor que inicializa la interfaz gráfica.
     */
    public VistaLoadCards() {
        initialize();
    }

    /**
     * Configura los componentes de la ventana.
     */
    private void initialize() {
        setTitle("Load Cards");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLayout(null);

        loadCardsButton = new JButton("Load Cards");
        loadCardsButton.setBounds(80, 50, 140, 30);
        add(loadCardsButton);

        setVisible(true);
    }

    /**
     * Obtiene el botón de carga de cartas.
     * @return Botón de carga de cartas.
     */
    public JButton getLoadCardsButton() {
        return loadCardsButton;
    }
}
