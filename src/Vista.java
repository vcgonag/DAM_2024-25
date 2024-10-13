import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class Vista {

	private JFrame frame;
	private JTextField txtIntroduzcaLaRuta;
	private JTextField textField_Buscar;
	private JTextField textField_Reemplazar;
	private JButton btnAbrir;
	private JButton btnReemplazar;
	private JButton btnBuscar; 
	private JTextArea textArea_Texto;
	private JTextArea textArea_Modificado;
	private JRadioButton rdbtnNewRadioButton;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbtnNewRadioButton_2;
	private JRadioButton rdbtnNewRadioButton_3;
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	

	public Vista() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 799, 581);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		txtIntroduzcaLaRuta = new JTextField();
		txtIntroduzcaLaRuta.setBounds(22, 17, 611, 27);
		txtIntroduzcaLaRuta.setFont(new Font("Lucida Grande", Font.ITALIC, 13));
		frame.getContentPane().add(txtIntroduzcaLaRuta);
		txtIntroduzcaLaRuta.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 4, 4);
		frame.getContentPane().add(scrollPane);

		textField_Buscar = new JTextField();
		textField_Buscar.setBounds(22, 253, 177, 27);
		textField_Buscar.setColumns(10);
		frame.getContentPane().add(textField_Buscar);
		
		textField_Reemplazar = new JTextField();
		textField_Reemplazar.setBounds(398, 252, 177, 27);
		textField_Reemplazar.setColumns(10);
		frame.getContentPane().add(textField_Reemplazar);

		btnAbrir = new JButton("Abrir");
		btnAbrir.setBounds(645, 18, 120, 27);
		btnAbrir.setFont(new Font("Tahoma", Font.PLAIN, 12));
		frame.getContentPane().add(btnAbrir);

		btnReemplazar = new JButton("Reemplazar");
		btnReemplazar.setBounds(588, 253, 177, 27);
		btnReemplazar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		frame.getContentPane().add(btnReemplazar);
		
		btnBuscar = new JButton("Buscar Coincidencias");
		btnBuscar.setBounds(212, 254, 177, 27);
		btnBuscar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		frame.getContentPane().add(btnBuscar);
		
		JScrollPane scrollPane_Texto = new JScrollPane();
		scrollPane_Texto.setBounds(22, 56, 743, 185);
		frame.getContentPane().add(scrollPane_Texto);

		textArea_Texto = new JTextArea();
		textArea_Texto.setLineWrap(true);
		textArea_Texto.setRows(12);
		scrollPane_Texto.setColumnHeaderView(textArea_Texto);
		scrollPane_Texto.getViewport().setView(textArea_Texto);
		
		JScrollPane scrollPane_Modificado = new JScrollPane();
		scrollPane_Modificado.setBounds(22, 372, 743, 150);
		frame.getContentPane().add(scrollPane_Modificado);

		textArea_Modificado = new JTextArea();
		textArea_Modificado.setLineWrap(true);
		textArea_Modificado.setRows(12);
		scrollPane_Modificado.setColumnHeaderView(textArea_Modificado);
		scrollPane_Modificado.getViewport().setView(textArea_Modificado);
		
		rdbtnNewRadioButton = new JRadioButton("Respetar Mayusculas/Minusculas");
		rdbtnNewRadioButton.setSelected(true);
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setBounds(22, 292, 357, 23);
		frame.getContentPane().add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("No Respetar Mayusculas/Minusculas");
		buttonGroup.add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton_1.setBounds(22, 326, 357, 23);
		frame.getContentPane().add(rdbtnNewRadioButton_1);
		
		rdbtnNewRadioButton_2 = new JRadioButton("Respetar Acentos");
		buttonGroup_1.add(rdbtnNewRadioButton_2);
		rdbtnNewRadioButton_2.setSelected(true);
		rdbtnNewRadioButton_2.setBounds(408, 291, 357, 23);
		frame.getContentPane().add(rdbtnNewRadioButton_2);
		
		rdbtnNewRadioButton_3 = new JRadioButton("No Respetar Acentos");
		buttonGroup_1.add(rdbtnNewRadioButton_3);
		rdbtnNewRadioButton_3.setBounds(408, 326, 357, 23);
		frame.getContentPane().add(rdbtnNewRadioButton_3);


		this.frame.setVisible(true);
	}

	public JButton getBtnAbrir() {
		return btnAbrir;
	}
	
	public JButton getBtnBuscar() {
		return btnBuscar;
	}
	
	public JButton getBtnReemplazar() {
		return btnReemplazar;
	}

	public JTextField getTextFieldAbrir() {
		return txtIntroduzcaLaRuta;
	}
	
	public JTextField getTextFieldBuscar() {
		return textField_Buscar;
	}

	public JTextField getTextFieldReemplazar() {
		return textField_Reemplazar;
	}

	public JTextArea getTextAreaTexto() {
		return textArea_Texto;
	}
	
	public JTextArea getTextAreaModificado() {
		return textArea_Modificado;
	}
	
	public JRadioButton getRdbtnNewRadioButton() {
		return rdbtnNewRadioButton;
	}
	
	public JRadioButton getRdbtnNewRadioButton_2() {
		return rdbtnNewRadioButton_2;
	}
	
	
	
//	public JRadioButton getRdbtnNewRadioButton_1() {
//		return rdbtnNewRadioButton_1;
//	}
	
}