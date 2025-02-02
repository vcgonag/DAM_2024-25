package modelo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Clase que gestiona la ejecución de consultas SQL y la exportación de
 * resultados a CSV para los clientes.
 */
public class Client {

	private static List<String> nombresColumnas = new ArrayList<>();
	private static List<List<Object>> filas = new ArrayList<>();
	private static int columnas;

	/**
	 * Ejecuta una consulta SQL y almacena los resultados.
	 * 
	 * @param sentencia Consulta SQL a ejecutar.
	 */
	public static void ejecutarConsulta(String sentencia) {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/population", Admin.getLogin(),
					Admin.getPassword());

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sentencia);

			ResultSetMetaData metaData = rs.getMetaData();
			columnas = metaData.getColumnCount();
			// Limpiar listas anteriores para nueva consulta
			nombresColumnas.clear();
			filas.clear();
			// Obtener nombres de las columnas
			for (int i = 1; i <= columnas; i++) {
				nombresColumnas.add(metaData.getColumnName(i));
			}
			// Obtener datos de las filas
			while (rs.next()) {
				List<Object> fila = new ArrayList<>();
				for (int i = 1; i <= columnas; i++) {
					fila.add(rs.getObject(i));
				}
				filas.add(fila);
			}

			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Exporta los resultados de la consulta a un archivo CSV.
	 * 
	 * @param consulta Consulta SQL cuyos resultados serán exportados.
	 */
	public static void exportarResultadosCSV(String consulta) {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/population", Admin.getLogin(),
					Admin.getPassword());
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(consulta);
			ResultSetMetaData rsmd = rs.getMetaData();
			// Construcción de la cabecera
			StringBuilder cabecera = new StringBuilder();
			int numCampos = rsmd.getColumnCount();
			for (int i = 1; i <= numCampos; i++) {
				cabecera.append(rsmd.getColumnName(i));
				if (i < columnas) {
					cabecera.append(";");
				}
			}
			System.out.println(cabecera);
		
			File archivoCSV = new File("archivoCSV" + Admin.getLogin() + ".csv");
			FileWriter fw = new FileWriter(archivoCSV);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write(cabecera.toString());
			bw.newLine();

			while (rs.next()) {
				for (int i = 1; i <= columnas; i++) {
					bw.write(rs.getString(i));
					if (i < columnas) {
						bw.write(";");
					}
				}
				bw.newLine();
			}

			bw.close();
			fw.close();
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Obtiene el número de columnas de la última consulta ejecutada.
	 * 
	 * @return Número de columnas en los resultados.
	 */
	public static int getColumnas() {
		return columnas;
	}

	/**
	 * Obtiene la lista de nombres de las columnas de la última consulta ejecutada.
	 * 
	 * @return Lista con los nombres de las columnas.
	 */
	public static List<String> getNombresColumnas() {
		return nombresColumnas;
	}

	/**
	 * Obtiene las filas de los resultados de la última consulta ejecutada.
	 * 
	 * @return Lista de listas con los datos de cada fila.
	 */
	public static List<List<Object>> getFilas() {
		return filas;
	}
}
