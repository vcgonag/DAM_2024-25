package vista;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Clase que representa la interfaz gráfica del juego de Blackjack.
 */
public class VistaBlackjack extends JFrame {
    private JButton btnStart, btnSave, btnHallOfFame, btnLogout, btnJugar, btnStand;
    private JPanel panelCartasJugador, panelCartasCrupier;
    private JLabel lblPuntosCrupier, lblPuntosJugador;
    private ImageIcon cartaBocaAbajo, cartaBastos1;
    private int turno = 0;

    /**
     * Constructor de la clase VistaBlackjack.
     * Inicializa la ventana, los botones y los paneles del juego.
     */
    public VistaBlackjack() {
        setTitle("BlackJack - Casino Edition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        File fileTablero = new File("img/itablero.jpg");
        File fileBocaAbajo = new File("img/carta_reverso.jpg");
        File fileCartaEjemplo = new File("img/cards_es/bastos_02.jpg");

        if (!fileTablero.exists()) System.out.println("Imagen de fondo NO encontrada: " + fileTablero.getAbsolutePath());
        if (!fileBocaAbajo.exists()) System.out.println("Imagen boca abajo NO encontrada: " + fileBocaAbajo.getAbsolutePath());
        if (!fileCartaEjemplo.exists()) System.out.println("Imagen de carta NO encontrada: " + fileCartaEjemplo.getAbsolutePath());

        JPanel fondoPanel = new JPanel() {
            private Image fondo;
            {
                try {
                    fondo = ImageIO.read(fileTablero);
                } catch (IOException e) {
                    System.err.println("Error al cargar la imagen: " + e.getMessage());
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (fondo != null) {
                    g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        fondoPanel.setLayout(null);
        setContentPane(fondoPanel);

        cartaBocaAbajo = ajustarImagen(fileBocaAbajo, 90, 140);
        cartaBastos1 = ajustarImagen(fileCartaEjemplo, 90, 140);

        panelCartasCrupier = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelCartasCrupier.setBounds(150, 117, 600, 150);
        panelCartasCrupier.setOpaque(false);
        fondoPanel.add(panelCartasCrupier);

        panelCartasJugador = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelCartasJugador.setBounds(150, 300, 600, 150);
        panelCartasJugador.setOpaque(false);
        fondoPanel.add(panelCartasJugador);

        for (int i = 0; i < 5; i++) {
            panelCartasCrupier.add(new JLabel(cartaBocaAbajo));
            panelCartasJugador.add(new JLabel(cartaBocaAbajo));
        }

        lblPuntosCrupier = new JLabel("CRUPIER");
        lblPuntosCrupier.setFont(new Font("Impact", Font.PLAIN, 36));
        lblPuntosCrupier.setForeground(Color.WHITE);
        lblPuntosCrupier.setBounds(45, 117, 136, 30);

        lblPuntosJugador = new JLabel("PLAYER");
        lblPuntosJugador.setFont(new Font("Impact", Font.PLAIN, 34));
        lblPuntosJugador.setForeground(Color.WHITE);
        lblPuntosJugador.setBounds(45, 295, 136, 30);

        btnStart = new JButton("Start");
        btnStart.setBounds(248, 30, 120, 40);

        btnSave = new JButton("SAVE");
        btnSave.setBounds(380, 30, 120, 40);

        btnHallOfFame = new JButton("Hall Of Fame");
        btnHallOfFame.setBounds(512, 30, 160, 40);

        btnJugar = new JButton("NEW CARD");
        btnJugar.setBounds(762, 300, 120, 40);
        btnJugar.addActionListener(e -> levantarCarta());

        btnStand = new JButton("STAND");
        btnStand.setBounds(762, 353, 120, 40);
        btnStand.addActionListener(e -> reiniciarJuego());

        btnLogout = new JButton("Logout");
        btnLogout.setBounds(762, 501, 120, 40);

        fondoPanel.add(lblPuntosCrupier);
        fondoPanel.add(lblPuntosJugador);
        fondoPanel.add(btnStart);
        fondoPanel.add(btnSave);
        fondoPanel.add(btnHallOfFame);
        fondoPanel.add(btnJugar);
        fondoPanel.add(btnStand);
        fondoPanel.add(btnLogout);

        setVisible(true);
    }

    /**
     * Ajusta el tamaño de una imagen y la devuelve como ImageIcon.
     *
     * @param file  Archivo de imagen a escalar.
     * @param ancho Ancho deseado de la imagen.
     * @param alto  Alto deseado de la imagen.
     * @return ImageIcon con la imagen escalada.
     */
    private ImageIcon ajustarImagen(File file, int ancho, int alto) {
        try {
            Image imagen = ImageIO.read(file);
            Image imagenEscalada = imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(imagenEscalada);
        } catch (IOException e) {
            System.err.println("Error al cargar la imagen: " + e.getMessage());
            return null;
        }
    }

    /**
     * Simula la acción de levantar una carta y mostrarla en la interfaz.
     */
    private void levantarCarta() {
        if (turno < 5) {
            panelCartasJugador.remove(turno);
            panelCartasJugador.add(new JLabel(cartaBastos1), turno);
            panelCartasJugador.revalidate();
            panelCartasJugador.repaint();

            try { Thread.sleep(500); } catch (InterruptedException ignored) {}

            panelCartasCrupier.remove(turno);
            panelCartasCrupier.add(new JLabel(cartaBastos1), turno);
            panelCartasCrupier.revalidate();
            panelCartasCrupier.repaint();

            turno++;
        }
    }

    /**
     * Reinicia el juego colocando nuevamente las cartas boca abajo.
     */
    private void reiniciarJuego() {
        turno = 0;
        panelCartasJugador.removeAll();
        panelCartasCrupier.removeAll();

        for (int i = 0; i < 5; i++) {
            panelCartasJugador.add(new JLabel(cartaBocaAbajo));
            panelCartasCrupier.add(new JLabel(cartaBocaAbajo));
        }

        panelCartasJugador.revalidate();
        panelCartasJugador.repaint();
        panelCartasCrupier.revalidate();
        panelCartasCrupier.repaint();
    }

    /**
     * Obtiene el botón de inicio del juego.
     *
     * @return Botón de inicio.
     */
    public JButton getBtnStart() {
        return btnStart;
    }

    /**
     * Obtiene el botón de cerrar sesión.
     *
     * @return Botón de logout.
     */
    public JButton getBtnLogout() {
        return btnLogout;
    }

    /**
     * Obtiene el botón para solicitar una nueva carta.
     *
     * @return Botón de pedir carta.
     */
    public JButton getBtnJugar() {
        return btnJugar;
    }

    /**
     * Método principal para ejecutar la interfaz de Blackjack.
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        new VistaBlackjack();
    }
}
