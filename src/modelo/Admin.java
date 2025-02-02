package modelo;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

/**
 * Clase que gestiona la administración de usuarios en la base de datos. Permite
 * la autenticación, el registro de usuarios y la gestión de permisos.
 */
public class Admin {

	private static String login;
	private static String password;
	private static String type;

	/**
	 * Obtiene el nombre de usuario actualmente autenticado.
	 * 
	 * @return Nombre de usuario.
	 */
	public static String getLogin() {
		return login;
	}

	/**
	 * Obtiene la contraseña del usuario autenticado.
	 * 
	 * @return Contraseña en formato encriptado.
	 */
	public static String getPassword() {
		return password;
	}

	/**
	 * Genera un hash MD5 para una contraseña dada.
	 * 
	 * @param input La contraseña en texto plano.
	 * @return La contraseña en formato MD5.
	 */
	public static String generarMD5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());

			BigInteger no = new BigInteger(1, messageDigest);
			String hashText = no.toString(16);

			while (hashText.length() < 32) {
				hashText = "0" + hashText;
			}
			return hashText;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Verifica si las credenciales de un usuario son correctas.
	 * 
	 * @param user Nombre de usuario.
	 * @param pass Contraseña en texto plano.
	 * @return `true` si las credenciales son correctas, `false` en caso contrario.
	 */
	public static boolean verificarUsuario(String user, String pass) {
		String passwordMD5 = generarMD5(pass);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/population", "root", "");

			String sql = "SELECT login, password, type FROM users WHERE login = ? AND password = ?";
			PreparedStatement psConsultaCredenciales = con.prepareStatement(sql);
			psConsultaCredenciales.setString(1, user);
			psConsultaCredenciales.setString(2, passwordMD5);
			ResultSet rs = psConsultaCredenciales.executeQuery();

			boolean autenticado = false;
			if (rs.next()) {
				login = rs.getString("login");
				password = rs.getString("password");
				type = rs.getString("type");
				autenticado = true;
			}
			rs.close();
			psConsultaCredenciales.close();
			con.close();
			return autenticado;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Comprueba si el usuario autenticado es un administrador.
	 * 
	 * @return `true` si es administrador, `false` si es cliente.
	 */
	public static boolean comprobarType() {
		return "admin".equals(type);
	}

	/**
	 * Comprueba si la tabla "population" existe en la base de datos.
	 * 
	 * @return `true` si la tabla existe, `false` en caso contrario.
	 */
	public static boolean comprobarTablaExiste() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/population", login, password);
			PreparedStatement pstmt = conn.prepareStatement("SHOW TABLES LIKE ?");
			pstmt.setString(1, "population");

			ResultSet rs = pstmt.executeQuery();
			return rs.next();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Registra un nuevo usuario en la base de datos y le asigna permisos.
	 * 
	 * @param username    Nombre de usuario a registrar.
	 * @param passwordMD5 Contraseña encriptada en formato MD5.
	 * @return `true` si el registro fue exitoso, `false` si ocurrió un error.
	 */
	public static boolean registrarUsuario(String username, String passwordMD5) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/population", login, password);

			// Verificar si el usuario ya existe
			String checkUserSql = "SELECT login FROM users WHERE login = ?";
			PreparedStatement checkStmt = conn.prepareStatement(checkUserSql);
			checkStmt.setString(1, username);
			ResultSet rs = checkStmt.executeQuery();
			if (rs.next()) {
				System.out.println("El usuario ya existe.");
				return false;
			}

			// Insertar nuevo usuario en la base de datos
			String sql = "INSERT INTO users (login, password, type) VALUES (?, ?, 'client')";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, passwordMD5);
			pstmt.executeUpdate();
			pstmt.close();

			// Crear usuario en MySQL
			String sqlCreateUser = "CREATE USER ?@'localhost' IDENTIFIED BY ?";
			PreparedStatement pstmtUser = conn.prepareStatement(sqlCreateUser);
			pstmtUser.setString(1, username);
			pstmtUser.setString(2, passwordMD5);
			pstmtUser.executeUpdate();
			pstmtUser.close();

			// Asignar permisos al usuario
			String sqlGrant = "GRANT SELECT ON population.population TO ?@'localhost'";
			PreparedStatement pstmtGrant = conn.prepareStatement(sqlGrant);
			pstmtGrant.setString(1, username);
			pstmtGrant.executeUpdate();
			pstmtGrant.close();

			return true;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
}
