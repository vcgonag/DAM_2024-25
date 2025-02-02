package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Clase que gestiona la información de población, la creación de tablas en la
 * base de datos, la importación de datos desde archivos CSV y la exportación a
 * formato XML.
 */
public class Population {

	private static String cabecera = "";
	private static List<String> arrayDatos = new ArrayList<String>();

	/**
	 * Crea una tabla en la base de datos para almacenar información de población.
	 * 
	 * @throws ClassNotFoundException Si no se encuentra el controlador de la base
	 *                                de datos.
	 * @throws SQLException           Si ocurre un error de SQL.
	 * @throws IOException            Si ocurre un error al leer el archivo CSV.
	 */
	public static void createTable() throws ClassNotFoundException, SQLException, IOException {

		File fichero = new File("AE02_population.csv");
		FileReader fr = new FileReader(fichero, StandardCharsets.ISO_8859_1);
		BufferedReader br = new BufferedReader(fr);
		String linea = br.readLine();
		int contadorLinea = 0;
		ListaCountrys listaCountrys = new ListaCountrys();
		while (linea != null) {
			if (contadorLinea == 0) {
				cabecera = linea;
			} else {
				Countrys country = new Countrys();
				String[] values = linea.split(";");

				country.setNameCountry(values[0]);
				country.setPopulation(values[1]);
				country.setDenalty(values[2]);
				country.setArea(values[3]);
				country.setFertility(values[4]);
				country.setAge(values[5]);
				country.setUrban(values[6]);
				country.setShare(values[7]);

				listaCountrys.anyadirCountry(country);
				System.out.println(country.toString());
			}

			linea = br.readLine();
			contadorLinea++;
		}
		br.close();
		fr.close();

		try {
			String[] arrayCabecera = cabecera.split(";");
			String columna = "";
			for (String val : arrayCabecera) {
				columna = columna + ", " + val + " VARCHAR(30) NOT NULL";
			}
			String consultaSQL = "CREATE TABLE population.population (idCountry INT(4) NOT NULL AUTO_INCREMENT "
					+ columna + ", PRIMARY KEY (idCountry))";

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/population", Admin.getLogin(),
					Admin.getPassword());

			PreparedStatement psCreateTable = con.prepareStatement(consultaSQL);
			System.out.println("Creando tabla...");

			int resultadoCrearTabla = psCreateTable.executeUpdate();
			if (resultadoCrearTabla == 0) {
				System.out.println("Tabla creada en la base de datos");
			} else {
				System.err.println("Error en la inserción");
			}

			psCreateTable.close();
			con.close();

			System.out.println("\n");
		} catch (Exception e) {
			System.out.println(e);
		}

		createSubfolderXML();
		writeXmlFile(listaCountrys);

	}

	/**
	 * Crea una carpeta "xml" si no existe para almacenar archivos XML generados.
	 */
	public static void createSubfolderXML() {
		String ruta = "./xml";
		File carpeta = new File(ruta);
		if (!carpeta.exists()) {
			if (carpeta.mkdir()) {
				System.out.println("Carpeta 'xml' creada exitosamente.");
			} else {
				System.out.println("No se pudo crear la carpeta 'xml'.");
			}
		} else {
			System.out.println("La carpeta 'xml' ya existe.");
		}
	}

	/**
	 * Genera archivos XML con la información de la lista de países.
	 * 
	 * @param lista Lista de objetos Countrys a exportar en XML.
	 * @throws UnsupportedEncodingException Si la codificación no es compatible.
	 */
	public static void writeXmlFile(ListaCountrys lista) throws UnsupportedEncodingException {

		try {
			for (Countrys coun : lista.getListaCountrys()) {

				DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
				DocumentBuilder build = dFact.newDocumentBuilder();
				Document doc = build.newDocument();

				Element raiz = doc.createElement("countrys");
				doc.appendChild(raiz);

				Element country = doc.createElement("country");
				raiz.appendChild(country);

				Element nameCountry = doc.createElement("country");
				nameCountry.appendChild(doc.createTextNode(coun.getNameCountry()));
				country.appendChild(nameCountry);

				Element population = doc.createElement("population");
				population.appendChild(doc.createTextNode(String.valueOf(coun.getPopulation())));
				country.appendChild(population);

				Element denalty = doc.createElement("density");
				denalty.appendChild(doc.createTextNode(String.valueOf(coun.getDenalty())));
				country.appendChild(denalty);

				Element area = doc.createElement("area");
				area.appendChild(doc.createTextNode(String.valueOf(coun.getArea())));
				country.appendChild(area);

				Element fertility = doc.createElement("fertility");
				fertility.appendChild(doc.createTextNode(String.valueOf(coun.getFertility())));
				country.appendChild(fertility);

				Element age = doc.createElement("age");
				age.appendChild(doc.createTextNode(String.valueOf(coun.getAge())));
				country.appendChild(age);

				Element urban = doc.createElement("urban");
				urban.appendChild(doc.createTextNode(String.valueOf(coun.getUrban())));
				country.appendChild(urban);

				Element share = doc.createElement("share");
				share.appendChild(doc.createTextNode(String.valueOf(coun.getShare())));
				country.appendChild(share);

				TransformerFactory tranFactory = TransformerFactory.newInstance();
				Transformer aTransformer = tranFactory.newTransformer();

				aTransformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				aTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
				aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");

				DOMSource source = new DOMSource(doc);

				// Reemplaza caracteres especiales con "_"
				String nombreArchivoSeguro = coun.getNameCountry().replaceAll("[^a-zA-Z0-9\\.\\-]", "_");

				try {
					FileWriter fw = new FileWriter("./xml/" + nombreArchivoSeguro + ".xml");
					StreamResult result = new StreamResult(fw);
					aTransformer.transform(source, result);
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		} catch (TransformerException ex) {
			System.out.println("Error escribiendo el documento");
		} catch (ParserConfigurationException ex) {
			System.out.println("Error construyendo el documento");
		}
	}

	/**
	 * Lista los archivos XML generados y extrae sus datos.
	 */
	public static void listarArchivos() {
		String rutaCarpeta = "./xml";
		File carpeta = new File(rutaCarpeta);
		String[] listArchivos = carpeta.list();
		if (listArchivos != null) {
			System.out.println("Archivos en la carpeta '" + rutaCarpeta + "':");
		} else {
			System.out.println("La carpeta no existe o está vacía.");
		}

		for (String archivo : listArchivos) {
			extraerDatosXML(archivo);
			System.out.println("archivo:" + archivo);
		}
	}

	/**
	 * Extrae datos de un archivo XML y los almacena en una lista para posterior
	 * inserción en SQL.
	 * 
	 * @param archivo Nombre del archivo XML a leer.
	 */
	public static void extraerDatosXML(String archivo) {
		String[] arrayCabecera = cabecera.split(";");

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse("./xml/" + archivo);

			Element raiz = document.getDocumentElement();
			NodeList nodeList = document.getElementsByTagName(arrayCabecera[0]);
			int numeroNodos = nodeList.getLength();

			int idUltimo = 0;
			System.out.println("Número total de nodos (countrys): " + nodeList.getLength());

			String datosConsulta = "";
			for (int i = 0; i < nodeList.getLength(); i++) {

				Node node = nodeList.item(i);
				Element eElement = (Element) node;

				for (int j = 0; j < arrayCabecera.length; j++) {

					String elemento = eElement.getElementsByTagName(arrayCabecera[j]).item(0).getTextContent();

					if (j == 0) {
						datosConsulta = "(\"" + elemento + "\", ";
						System.out.println(datosConsulta);
					} else if (j == arrayCabecera.length - 1) {
						datosConsulta = datosConsulta + "\"" + elemento + "\")";
						System.out.println(datosConsulta);
					} else {
						datosConsulta = datosConsulta + "\"" + elemento + "\", ";
						System.out.println(datosConsulta);
					}
				}
				arrayDatos.add(datosConsulta);
			}
			System.out.println("datosConsulta: " + datosConsulta);

		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println();
		}

	}

	/**
	 * Inserta los datos de la población en la base de datos MySQL.
	 * 
	 * @throws ClassNotFoundException Si el controlador de la base de datos no se
	 *                                encuentra.
	 * @throws SQLException           Si ocurre un error al ejecutar la consulta
	 *                                SQL.
	 */
	public static void insentarDatosSQL() throws ClassNotFoundException, SQLException {
		System.out.println("arrayDatos.size()" + arrayDatos.size());
		String datosCabecera = cabecera.replace(";", ", ");
		String consulta = "INSERT INTO population.population (" + datosCabecera + ") VALUES ";

		for (int i = 0; i < arrayDatos.size(); i++) {

			if (i == arrayDatos.size() - 1) {
				consulta = consulta + arrayDatos.get(i);
			} else {
				consulta = consulta + arrayDatos.get(i) + ", ";
			}
		}
		System.out.println(consulta);

		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bibliotecaAD", Admin.getLogin(),
				Admin.getPassword());
		PreparedStatement psInsertar = con.prepareStatement(consulta);
		int resultadoInsertar = psInsertar.executeUpdate();
		if (resultadoInsertar > 0) {
			System.out.println("PAIS guardado en base de datos (fila " + resultadoInsertar + ")");
		} else {
			System.err.println("Error en la inserción");
		}

		psInsertar.close();
		con.close();
	}
}
