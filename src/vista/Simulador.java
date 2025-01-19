package vista;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.Color;
import javax.swing.JTextPane;

/**
 * Clase que representa la interfaz gráfica de usuario (GUI) para la simulación
 * de proteínas utilizando multiproceso (MP) y multihilo (MT).
 */
public class Simulador {

    private JFrame frame;
    private JTextField txtNumeroProteinas;
    private JButton botonSimular;
    private JTextArea textArea_SimulacionMP;
    private JTextArea textArea_SimulacionMT;
    private JTextArea textAreaTiempoTotalMt;
    private JRadioButton rdbtnProteina1;
    private JRadioButton rdbtnProteina2;
    private JRadioButton rdbtnProteina3;
    private JRadioButton rdbtnProteina4;
    private final ButtonGroup buttonGroup = new ButtonGroup();

    /**
     * Constructor que inicializa la interfaz gráfica.
     */
    public Simulador() {
        initialize();
    }

    /**
     * Configura y organiza los componentes de la interfaz gráfica.
     */
    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(186, 184, 179));
        frame.setBounds(100, 100, 774, 425);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JScrollPane scrollPane_SimulacionMP = new JScrollPane();
        scrollPane_SimulacionMP.setBounds(15, 65, 745, 135);
        frame.getContentPane().add(scrollPane_SimulacionMP);

        textArea_SimulacionMP = new JTextArea();
        textArea_SimulacionMP.setLineWrap(true);
        textArea_SimulacionMP.setRows(12);
        scrollPane_SimulacionMP.setColumnHeaderView(textArea_SimulacionMP);
        scrollPane_SimulacionMP.getViewport().setView(textArea_SimulacionMP);

        txtNumeroProteinas = new JTextField();
        txtNumeroProteinas.setFont(new Font("Lucida Grande", Font.ITALIC, 13));
        txtNumeroProteinas.setText("Numero Proteinas...");
        txtNumeroProteinas.setToolTipText(" ");
        txtNumeroProteinas.setBounds(15, 10, 198, 27);
        frame.getContentPane().add(txtNumeroProteinas);
        txtNumeroProteinas.setColumns(10);

        botonSimular = new JButton("Simulación");
        botonSimular.setBounds(640, 11, 120, 27);
        botonSimular.setFont(new Font("Tahoma", Font.PLAIN, 12));
        frame.getContentPane().add(botonSimular);

        JScrollPane scrollPane_SimulacionMT = new JScrollPane();
        scrollPane_SimulacionMT.setBounds(15, 227, 745, 150);
        frame.getContentPane().add(scrollPane_SimulacionMT);

        textArea_SimulacionMT = new JTextArea();
        textArea_SimulacionMT.setLineWrap(true);
        textArea_SimulacionMT.setRows(12);
        scrollPane_SimulacionMT.setColumnHeaderView(textArea_SimulacionMT);
        scrollPane_SimulacionMT.getViewport().setView(textArea_SimulacionMT);

        rdbtnProteina1 = new JRadioButton("Proteina 1");
        rdbtnProteina1.setSelected(true);
        rdbtnProteina1.setBackground(new Color(255, 252, 245));
        buttonGroup.add(rdbtnProteina1);
        rdbtnProteina1.setBounds(223, 11, 100, 23);
        frame.getContentPane().add(rdbtnProteina1);

        rdbtnProteina2 = new JRadioButton("Proteina 2");
        rdbtnProteina2.setBackground(new Color(255, 252, 245));
        buttonGroup.add(rdbtnProteina2);
        rdbtnProteina2.setBounds(333, 11, 100, 23);
        frame.getContentPane().add(rdbtnProteina2);

        rdbtnProteina3 = new JRadioButton("Proteina 3");
        buttonGroup.add(rdbtnProteina3);
        rdbtnProteina3.setBounds(443, 11, 100, 23);
        frame.getContentPane().add(rdbtnProteina3);

        rdbtnProteina4 = new JRadioButton("Proteina 4");
        buttonGroup.add(rdbtnProteina4);
        rdbtnProteina4.setBounds(543, 11, 100, 23);
        frame.getContentPane().add(rdbtnProteina4);

        JTextPane txtpnSimulacionMp = new JTextPane();
        txtpnSimulacionMp.setBackground(new Color(186, 184, 179));
        txtpnSimulacionMp.setText("SIMULACION MP");
        txtpnSimulacionMp.setBounds(15, 45, 146, 16);
        frame.getContentPane().add(txtpnSimulacionMp);

        JTextPane txtpnSimulacionMt = new JTextPane();
        txtpnSimulacionMt.setText("SIMULACION MT");
        txtpnSimulacionMt.setBackground(new Color(186, 184, 179));
        txtpnSimulacionMt.setBounds(15, 205, 146, 16);
        frame.getContentPane().add(txtpnSimulacionMt);

        this.frame.setVisible(true);
    }

    /**
     * Obtiene el botón para iniciar la simulación.
     * 
     * @return el botón de simulación.
     */
    public JButton getBtnSimular() {
        return botonSimular;
    }

    /**
     * Obtiene el campo de texto para ingresar el número de proteínas.
     * 
     * @return el campo de texto para el número de proteínas.
     */
    public JTextField getTextFieldNumeroProteinas() {
        return txtNumeroProteinas;
    }

    /**
     * Obtiene el área de texto para mostrar los resultados de la simulación MP.
     * 
     * @return el área de texto para la simulación MP.
     */
    public JTextArea getTextAreaSimulacionMP() {
        return textArea_SimulacionMP;
    }

    /**
     * Obtiene el área de texto para mostrar los resultados de la simulación MT.
     * 
     * @return el área de texto para la simulación MT.
     */
    public JTextArea getTextAreaSimulacionMT() {
        return textArea_SimulacionMT;
    }

    /**
     * Obtiene el área de texto para mostrar el tiempo total de la simulación MT.
     * 
     * @return el área de texto para el tiempo total de la simulación MT.
     */
    public JTextArea getTextAreaTiempoTotalMT() {
        return textAreaTiempoTotalMt;
    }

    /**
     * Obtiene el botón de selección para la proteína 1.
     * 
     * @return el botón de selección de la proteína 1.
     */
    public JRadioButton getRadioButton1() {
        return rdbtnProteina1;
    }

    /**
     * Obtiene el botón de selección para la proteína 2.
     * 
     * @return el botón de selección de la proteína 2.
     */
    public JRadioButton getRadioButton2() {
        return rdbtnProteina2;
    }

    /**
     * Obtiene el botón de selección para la proteína 3.
     * 
     * @return el botón de selección de la proteína 3.
     */
    public JRadioButton getRadioButton3() {
        return rdbtnProteina3;
    }

    /**
     * Obtiene el botón de selección para la proteína 4.
     * 
     * @return el botón de selección de la proteína 4.
     */
    public JRadioButton getRadioButton4() {
        return rdbtnProteina4;
    }
}
