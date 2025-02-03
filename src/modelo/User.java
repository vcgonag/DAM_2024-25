package modelo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.JOptionPane;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.*;

/**
 * Clase que gestiona la autenticación y almacenamiento de usuarios en la base
 * de datos MongoDB.
 */
public class User {

	static JSONObject config;
	static String usuario;
	static String password;
	static String ip;
	static int puerto;
	static String databaseName;
	static String conexion;
	static String usersDB;
	static String puntuacionDB;
	static String cardsESDB;

	/**
	 * Genera un hash SHA-256 a partir de una contraseña.
	 *
	 * @param password La contraseña a encriptar.
	 * @return Una cadena hexadecimal que representa el hash de la contraseña.
	 */
	public static String generarHashSHA256(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(password.getBytes());

			// Convertir el hash en una cadena hexadecimal
			StringBuilder hexString = new StringBuilder();
			for (byte b : hash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Error al generar el hash SHA-256", e);
		}
	}

	/**
	 * Realiza la primera conexión a la base de datos MongoDB y carga la
	 * configuración desde un archivo JSON.
	 *
	 * @throws JSONException Si ocurre un error al procesar el JSON.
	 */
	public static void primeraConexion() throws JSONException {
		try {
			FileReader reader = new FileReader("config.json");
			JSONTokener tokener = new JSONTokener(reader);
			config = new JSONObject(tokener);

			usuario = config.getString("usuario");
			password = config.getString("password");
			ip = config.getString("ip");
			puerto = config.getInt("puerto");
			databaseName = config.getString("database");
			conexion = "mongodb://" + usuario + ":" + password + "@" + ip + ":" + puerto + "/";

			JSONObject colecciones = config.getJSONObject("colecciones");
			usersDB = colecciones.getString("usuarios");
			puntuacionDB = colecciones.getString("puntuaciones");

			MongoClientURI uri = new MongoClientURI(conexion);
			MongoClient mongoClient = new MongoClient(uri);
			MongoDatabase database = mongoClient.getDatabase(databaseName);
			Carta.borrarColecciones(database);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Inserta el primer usuario en la base de datos utilizando la configuración del
	 * archivo JSON.
	 *
	 * @throws JSONException Si ocurre un error al procesar el JSON.
	 */
	public static void primerUsuario() throws JSONException {
		try {
			MongoClientURI uri = new MongoClientURI(conexion);
			MongoClient mongoClient = new MongoClient(uri);
			MongoDatabase database = mongoClient.getDatabase(databaseName);
			MongoCollection<Document> coleccionUsers = database.getCollection(usersDB);
			MongoCollection<Document> coleccionPunt = database.getCollection(puntuacionDB);

			JSONObject primerUsuario = config.getJSONObject("primer_usuario");

			String username = primerUsuario.getString("user");
			String passwordHash = primerUsuario.getString("pass");

			Document doc = new Document();
			doc.append("user", username);
			doc.append("pass", passwordHash);
			coleccionUsers.insertOne(doc);

			JSONObject primerPunt = config.getJSONObject("primera_puntuacion");

			String username1 = primerPunt.getString("user");
			String suit = primerPunt.getString("suit");
			String points = primerPunt.getString("points");
			String fecha = primerPunt.getString("timestamp");

			Document doc1 = new Document();
			doc1 = new Document();
			doc1.append("user", username);
			doc1.append("pass", passwordHash);
			coleccionPunt.insertOne(doc1);

			mongoClient.close();
			System.out.println("Conexión con MongoDB cerrada.");

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Verifica si un usuario existe en la base de datos y si su contraseña es
	 * correcta.
	 *
	 * @param usuario Nombre del usuario a verificar.
	 * @param pass    Contraseña del usuario.
	 * @return {@code true} si el usuario existe y la contraseña es correcta,
	 *         {@code false} en caso contrario.
	 */
	public static boolean verificarUsuario(String usuario, String pass) {
		String passwordSHA256 = generarHashSHA256(pass);
		System.out.println("passwordSHA256 generado: " + passwordSHA256);

		MongoClientURI uri = new MongoClientURI(conexion);
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase database = mongoClient.getDatabase(databaseName);
		MongoCollection<Document> coleccionUsers = database.getCollection(usersDB);

		System.out.println("\nUsuarios con user igual a: " + usuario);
		Bson query = eq("user", usuario);
		Document usuarioEncontrado = coleccionUsers.find(query).first();

		mongoClient.close();

		if (usuarioEncontrado != null) {
			System.out.println("Usuario encontrado: " + usuario);
			return true;
		} else {
			System.out.println("Usuario no encontrado.");
			return false;
		}
	}

	/**
	 * Registra un nuevo usuario en la base de datos MongoDB.
	 *
	 * @param username Nombre de usuario a registrar.
	 * @param pass     Contraseña del usuario.
	 * @return {@code true} si el usuario fue registrado correctamente,
	 *         {@code false} si el usuario ya existe.
	 */
	public static boolean registrarUsuario(String username, String pass) {
		MongoClientURI uri = new MongoClientURI(conexion);
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase database = mongoClient.getDatabase(databaseName);
		MongoCollection<Document> coleccionUsers = database.getCollection(usersDB);

		// Verificar si el usuario ya existe
		boolean verificacion = verificarUsuario(username, pass);
		if (verificacion) {
			System.out.println("Usuario coincidente: " + username);
			JOptionPane.showMessageDialog(null, "El usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
			mongoClient.close();
			return false;
		} else {
			String passwordSHA256 = generarHashSHA256(pass);
			System.out.println("Usuario no coincidente.");
			System.out.println("Insertar usuario...\n");

			Document doc = new Document();
			doc.append("user", username);
			doc.append("pass", passwordSHA256);
			coleccionUsers.insertOne(doc);

			mongoClient.close();
			return true;
		}
	}
}
