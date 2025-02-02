package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import modelo.Admin;
import modelo.Population;
import modelo.Client;
import vista.VistaRegistroUsuario;
import vista.VistaLogin;
import vista.VistaImportarCSV;
import vista.VistaCliente;
import vista.VistaMenuAdmin;

/**
 * Clase Controlador que maneja la lógica de la aplicación y la interacción
 * entre la vista y el modelo.
 */
public class Controlador {
	private VistaLogin vistaLogin;
	private VistaImportarCSV vistaImportarCSV;
	private VistaRegistroUsuario vistaRegistroUsuario;
	private VistaCliente vistaCliente;
	private VistaMenuAdmin vistaMenuAdmin;
	private static String consulta;

	/**
	 * Constructor de la clase Controlador. Inicia la ventana de login al iniciar el
	 * programa.
	 */
	public Controlador() {
		iniciarLogin();
	}

	/**
	 * Inicia la vista de inicio de sesión y maneja la autenticación del usuario.
	 */
	private void iniciarLogin() {
		vistaLogin = new VistaLogin();
		vistaLogin.getLoginButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = vistaLogin.userText.getText();
				String password = new String(vistaLogin.passwordText.getPassword());

				if (Admin.verificarUsuario(username, password)) {
					JOptionPane.showMessageDialog(vistaLogin, "¡Login exitoso!");
					vistaLogin.dispose();
					if (Admin.comprobarType()) {
						if (Admin.comprobarTablaExiste()) {
							iniciarMenuAdmin();
						} else {
							iniciarImportacionCSV();
						}
					} else {
						iniciarConsultaCliente();
					}
				} else {
					JOptionPane.showMessageDialog(vistaLogin, "Usuario o contraseña incorrectos.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	/**
	 * Inicia la vista para importar archivos CSV y maneja el proceso de
	 * importación.
	 */
	private void iniciarImportacionCSV() {
		vistaImportarCSV = new VistaImportarCSV();
		vistaImportarCSV.getImportarButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Population.createTable();
					Population.listarArchivos();
					Population.insentarDatosSQL();
					JOptionPane.showMessageDialog(vistaImportarCSV, "Importación completada con éxito.");
					vistaImportarCSV.dispose();
					iniciarRegistroUsuario();
				} catch (ClassNotFoundException | SQLException | IOException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	/**
	 * Inicia el menú de administración, permitiendo al usuario elegir entre
	 * registrar usuarios o realizar consultas SQL.
	 */
	private void iniciarMenuAdmin() {
		vistaMenuAdmin = new VistaMenuAdmin();
		vistaMenuAdmin.getRegistroUsuarioButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vistaMenuAdmin.dispose();
				iniciarRegistroUsuario();
			}
		});

		vistaMenuAdmin.getConsultasSQLButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vistaMenuAdmin.dispose();
				iniciarConsultaCliente();
			}
		});
	}

	/**
	 * Inicia la vista de registro de usuario y maneja el proceso de registro.
	 */
	private void iniciarRegistroUsuario() {
		vistaRegistroUsuario = new VistaRegistroUsuario();
		vistaRegistroUsuario.getRegisterButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				String username = vistaRegistroUsuario.userText.getText();
				String password = new String(vistaRegistroUsuario.passwordText.getPassword());
				String confirmPassword = new String(vistaRegistroUsuario.confirmPasswordText.getPassword());

				if (!password.equals(confirmPassword)) {
					JOptionPane.showMessageDialog(vistaRegistroUsuario, "Las contraseñas no coinciden.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					String passwordMD5 = Admin.generarMD5(password);
					if (Admin.registrarUsuario(username, passwordMD5)) {
						JOptionPane.showMessageDialog(vistaRegistroUsuario, "Usuario registrado con éxito.");
						vistaRegistroUsuario.dispose();
						iniciarLogin();
					} else {
						JOptionPane.showMessageDialog(vistaRegistroUsuario, "Error al registrar el usuario.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}

	/**
	 * Inicia la vista de consulta para clientes, permitiendo ejecutar consultas
	 * SQL.
	 */
	private void iniciarConsultaCliente() {
		vistaCliente = new VistaCliente();
		vistaCliente.getEjecutarConsultaBtn().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consulta = vistaCliente.getConsultaTextField().getText();
				if (consulta.isEmpty()) {
					JOptionPane.showMessageDialog(vistaCliente, "Por favor, ingrese una consulta SQL.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				Client.ejecutarConsulta(consulta);
				try {
					DefaultTableModel modelo = (DefaultTableModel) vistaCliente.getResultTable().getModel();
					// Limpiar la tabla
					modelo.setRowCount(0);
					// Asignar nombres de las columnas
					modelo.setColumnIdentifiers(Client.getNombresColumnas().toArray());
					// Obtener y agregar las filas
					for (List<Object> fila : Client.getFilas()) {
						modelo.addRow(fila.toArray());
					}

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(vistaCliente, "Error al ejecutar la consulta: " + ex.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		vistaCliente.getImportarCsvBtn().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Client.exportarResultadosCSV(consulta);
				JOptionPane.showMessageDialog(vistaCliente, "Datos exportados correctamente a CSV.");
			}
		});

		vistaCliente.getLogoutBtn().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vistaCliente.dispose();
				iniciarLogin();
			}
		});
	}

}
