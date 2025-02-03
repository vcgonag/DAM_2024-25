package vista;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Clase que representa la interfaz gráfica para elegir quién comienza la partida de Blackjack.
 */
public class VistaElegirTurno extends JFrame {

    private JButton playerButton;
    private JButton crupierButton;

    /**
     * Constructor de la clase. Inicializa la interfaz gráfica.
     */
    public VistaElegirTurno() {
        initialize();
    }

    /**
     * Método privado que configura los componentes de la ventana.
     */
    private void initialize() {
        setTitle("Elegir primer jugador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLayout(null);

        crupierButton = new JButton("Crupier");
        crupierButton.setBounds(60, 40, 180, 30);
        add(crupierButton);

        playerButton = new JButton("Player");
        playerButton.setBounds(60, 90, 180, 30);
        add(playerButton);

        setVisible(true);
    }

    /**
     * Obtiene el botón que permite seleccionar al jugador como primer turno.
     *
     * @return El botón correspondiente al jugador.
     */
    public JButton getPlayerButton() {
        return playerButton;
    }

    /**
     * Obtiene el botón que permite seleccionar al crupier como primer turno.
     *
     * @return El botón correspondiente al crupier.
     */
    public JButton getCrupierButton() {
        return crupierButton;
    }
}
