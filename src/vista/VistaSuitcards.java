package vista;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Clase que representa la interfaz gráfica para seleccionar el tipo de baraja de cartas en el juego de Blackjack.
 */
public class VistaSuitcards extends JFrame {

    private JButton cardsESButton;
    private JButton cardsFRButton;

    /**
     * Constructor de la clase. Inicializa la interfaz gráfica de selección de baraja.
     */
    public VistaSuitcards() {
        initialize();
    }

    /**
     * Configura los componentes gráficos de la ventana de selección de baraja.
     */
    private void initialize() {
        setTitle("Suit Cards");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLayout(null);

        cardsESButton = new JButton("ES");
        cardsESButton.setBounds(60, 40, 180, 30);
        add(cardsESButton);

        cardsFRButton = new JButton("FR");
        cardsFRButton.setBounds(60, 90, 180, 30);
        add(cardsFRButton);

        setVisible(true);
    }

    /**
     * Obtiene el botón que permite seleccionar la baraja española.
     *
     * @return El botón de selección de la baraja española.
     */
    public JButton getCardsESButton1() {
        return cardsESButton;
    }

    /**
     * Obtiene el botón que permite seleccionar la baraja francesa.
     *
     * @return El botón de selección de la baraja francesa.
     */
    public JButton getCardsFRButton() {
        return cardsFRButton;
    }
}
