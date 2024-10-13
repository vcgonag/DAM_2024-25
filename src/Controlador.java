import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.io.*;
import java.text.Normalizer;
import java.text.SimpleDateFormat;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 * Controlador que gestiona la lógica de la aplicación, incluyendo la apertura de directorios,
 * búsqueda y reemplazo de cadenas en archivos.
 */
public class Controlador {

	private Vista vista;
	
	private ActionListener actionListenerAbrir;
	private ActionListener actionListenerBuscar;
	private ActionListener actionListenerReemplazar;
	
	private ArrayList<String> listadoInformacionFichero = new ArrayList<String>();
	private ArrayList<String> listadoInformacionCoincidencias = new ArrayList<String>();
	private ArrayList<String> listadoInformacionReemplazos = new ArrayList<String>();
	private String url="";
	private int opcionMayMin = 0;
	private int opcionAcentos = 0;
	private String cadenaBuscar = "";
	private String cadenaReemplazar = "";
	private ArrayList<String> extensionesNoLegibles = new ArrayList<>(
			Arrays.asList(".bin", ".exe", ".jpg", ".jpeg", ".png", ".gif", ".mp3", ".mp4", ".wav", ".zip", ".rar",
					".pdf", ".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx", ".sql", ".yaml", ".ai", ".psd", ".indd",
					".class", ".jar", ".sys", ".dll", ".properties", ".ini", ".tar", ".gz", ".bz2", ".DS_Store"));

	
	/**
	 * Constructor
	 */
	public Controlador(Vista vista) {
		this.vista = vista;
		control();
	}

	/**
	 * Configura los ActionListeners para los botones de la interfaz.
	 */
	public void control() {

		actionListenerAbrir = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				url = vista.getTextFieldAbrir().getText();
				File directorio = new File(url);
				listadoInformacionFichero.clear();
				listarArchivos(directorio);
				imprimirPantalla(1);
			}
		};
		vista.getBtnAbrir().addActionListener(actionListenerAbrir);

		actionListenerBuscar = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				cadenaBuscar = vista.getTextFieldBuscar().getText();
				File directorio = new File(url);
				opcionMayMin = 0;
				opcionAcentos = 0;
				listadoInformacionCoincidencias.clear();
				if (vista.getRdbtnNewRadioButton_2().isSelected()) {
					if (vista.getRdbtnNewRadioButton().isSelected()) {
						opcionMayMin = 1;
						opcionAcentos = 1;
						desgloseFicheros_Buscar(directorio);
						imprimirPantalla(2);
					} else {
						opcionMayMin = 2;
						opcionAcentos = 1;
						desgloseFicheros_Buscar(directorio);
						imprimirPantalla(2);
					}
				} else {
					if (vista.getRdbtnNewRadioButton().isSelected()) {
						opcionMayMin = 1;
						opcionAcentos = 2;
						desgloseFicheros_Buscar(directorio);
						imprimirPantalla(2);
					} else {
						opcionMayMin = 2;
						opcionAcentos = 2;
						desgloseFicheros_Buscar(directorio);
						imprimirPantalla(2);
					}
				}

			}
		};
		vista.getBtnBuscar().addActionListener(actionListenerBuscar);

		actionListenerReemplazar = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				cadenaBuscar = vista.getTextFieldBuscar().getText();
				cadenaReemplazar = vista.getTextFieldReemplazar().getText();
				File directorio = new File(url);
				
				opcionMayMin = 0;
				opcionAcentos = 0;
				listadoInformacionReemplazos.clear();
				if (vista.getRdbtnNewRadioButton_2().isSelected()) {
					if (vista.getRdbtnNewRadioButton().isSelected()) {
						opcionMayMin = 1;
						opcionAcentos = 1;
						desgloseFicheros_Reemplazar(directorio);
						imprimirPantalla(3);
					} else {
						opcionMayMin = 2;
						opcionAcentos = 1;
						desgloseFicheros_Reemplazar(directorio);
						imprimirPantalla(3);
					}
				} else {
					if (vista.getRdbtnNewRadioButton().isSelected()) {
						opcionMayMin = 1;
						opcionAcentos = 2;
						desgloseFicheros_Reemplazar(directorio);
						imprimirPantalla(3);
					} else {
						opcionMayMin = 2;
						opcionAcentos = 2;
						desgloseFicheros_Reemplazar(directorio);
						imprimirPantalla(3);
					}
				}
			}
		};
		vista.getBtnReemplazar().addActionListener(actionListenerReemplazar);

	}

	/**
	 * Metodo que recorre recursivamente un directorio y sus subdirectorios, invocando 
	 * `montarInformacion` para cada archivo encontrado.
	 *
	 * @param directorio El directorio a listar.
	 */
	public void listarArchivos(File directorio) {
		
		if (directorio.exists() != false) {
			File[] ficheros = directorio.listFiles();
			if (ficheros != null) {
				for (int i = 0; i < ficheros.length; i++) {
					if (ficheros[i].isDirectory()) {
						listarArchivos(ficheros[i]);
					} else {
						montarInformacion(ficheros[i], 0, 1);
					}
				}
			}
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "El directorio no EXISTE");
		}
	}

	/**
	 * Metodo que ecorre recursivamente un directorio y sus subdirectorios, buscando coincidencias 
	 * de una cadena en cada archivo legible. Para archivos PDF, se utiliza un método específico 
	 * para leer su contenido; para otros archivos legibles, se aplica un método general. 
	 * Registra información sobre los archivos procesados, incluyendo el número de coincidencias 
	 * encontradas o un indicador para archivos no legibles.
	 * 
	 * @param directorio El directorio raíz desde el cual iniciar la búsqueda. 
	 *                   Si el directorio no contiene archivos, no se realiza ninguna acción.
	 */
	public void desgloseFicheros_Buscar(File directorio) {

		File[] archivos = directorio.listFiles();

		if (archivos != null) {
			for (int i = 0; i < archivos.length; i++) {
				if (archivos[i].isDirectory()) {
					desgloseFicheros_Buscar(archivos[i]);
				} else {
					// FILTRO EXTENSION
					String extensionFichero = buscarExtension(archivos[i]);
					if (extensionFichero.equals(".pdf")) {
						int numeroCoincidencias = lecturaFicheroPdf_Buscar(archivos[i]);
						montarInformacion(archivos[i], numeroCoincidencias, 2);
					} else if (extensionesNoLegibles.contains(extensionFichero) == false) {
						int numeroCoincidencias = lecturaFichero_Buscar(archivos[i]);
						montarInformacion(archivos[i], numeroCoincidencias, 2);
					} else {
						montarInformacion(archivos[i], -1, 2);
					}
				}
			}
		}
	}

	/**
	 * Metodo que lee un archivo y cuenta las coincidencias de una cadena, considerando 
	 * opciones de mayúsculas/minúsculas y acentos.
	 *
	 * @param fichero          El archivo a leer.
	 * @return El número de coincidencias encontradas.
	 */
	public int lecturaFichero_Buscar(File fichero) {

		try {
			FileReader fr = new FileReader(fichero);
			BufferedReader br = new BufferedReader(fr);

			String linea = br.readLine();
			int contador = 0;

			if (opcionAcentos == 2) { /* Sin acentos */
				cadenaBuscar = Normalizer.normalize(cadenaBuscar, Normalizer.Form.NFD);
				cadenaBuscar = cadenaBuscar.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

				linea = Normalizer.normalize(linea, Normalizer.Form.NFD);
				linea = linea.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
			}
			while (linea != null) {
				if (opcionMayMin == 1) {
					int coincidencias = buscar(linea, cadenaBuscar);
					contador = contador + coincidencias;
				} else if (opcionMayMin == 2) {
					int coincidencias = buscar(linea.toLowerCase(), cadenaBuscar.toLowerCase());
					contador = contador + coincidencias;
				}
				linea = br.readLine();
				if (opcionAcentos == 2) {
					if (linea != null) {
						linea = Normalizer.normalize(linea, Normalizer.Form.NFD);
						linea = linea.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
					}
				}
			}

			br.close();
			fr.close();

			return contador;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		return -1;
	}

	/**
	 * Metodo que lee un archivo PDF y cuenta las coincidencias de una cadena, considerando 
	 * opciones de mayúsculas/minúsculas y acentos.
	 *
	 * @param fichero          El archivo PDF a leer.
	 * @return El número de coincidencias encontradas.
	 */
	public int lecturaFicheroPdf_Buscar(File fichero) {
		// https://adictosaltrabajo.com/2020/12/28/como-leer-un-pdf-linea-por-linea-utilizando-pdfbox/#extraer
		PDDocument pdDocument = null;
		int contador = 0;
		try {
			pdDocument = PDDocument.load(fichero);
			PDFTextStripper pdfText = new PDFTextStripper();
			int pag = pdDocument.getNumberOfPages();
			pdfText.setStartPage(1);
			pdfText.setEndPage(pag);
			String texto = pdfText.getText(pdDocument); // Extraccion texto
			if (opcionAcentos == 1) {
				if (opcionMayMin == 1) {
					contador = buscar(texto, cadenaBuscar);
				} else if (opcionMayMin == 2) {
					contador = buscar(texto.toLowerCase(), cadenaBuscar.toLowerCase());
				}
			} else if (opcionAcentos == 2) {
				cadenaBuscar = Normalizer.normalize(cadenaBuscar, Normalizer.Form.NFD);
				cadenaBuscar = cadenaBuscar.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

				texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
				texto = texto.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
				if (opcionMayMin == 1) {
					contador = buscar(texto, cadenaBuscar);
				} else if (opcionMayMin == 2) {
					contador = buscar(texto.toLowerCase(), cadenaBuscar.toLowerCase());
				}
			}
			return contador;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pdDocument != null) {
				try {
					pdDocument.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}

	/**
	 * Metodo que Cuenta las coincidencias de una cadena dentro de un texto.
	 *
	 * @param texto  El texto en el que buscar.
	 * @param cadena La cadena a buscar.
	 * @return El número de coincidencias encontradas.
	 */
	public int buscar(String texto, String cadenaBuscar) {
		int contador = 0;
		int index = texto.indexOf(cadenaBuscar);
		while (index != -1) {
			contador++;
			index = texto.indexOf(cadenaBuscar, index + 1);
		}
		return contador;
	}
	
	/**
	 * Metodo que recorre recursivamente un directorio y sus subdirectorios, procesando cada archivo 
	 * que no sea de una extensión no legible. Para cada archivo legible, invoca un método 
	 * para reemplazar una cadena en su contenido y registra la información relevante.
	 * 
	 * @param directorio El directorio raíz desde el cual iniciar el desglose.
	 *                   Si el directorio no existe, se muestra un mensaje de error.
	 */
	private void desgloseFicheros_Reemplazar(File directorio) {

		if (directorio.exists() != false) {
			File[] archivos = directorio.listFiles();
			if (archivos != null) {
				for (int i = 0; i < archivos.length; i++) {
					if (archivos[i].isDirectory()) {
						desgloseFicheros_Reemplazar(archivos[i]);
					} else {
						String extensionFichero = buscarExtension(archivos[i]);
						if (extensionesNoLegibles.contains(extensionFichero) == false) {
							int numeroReemplazos = lecturaEscrituraFichero_ReemplazarCadena(archivos[i]);
							montarInformacion(archivos[i], numeroReemplazos, 3);
						} else {
							montarInformacion(archivos[i], -1, 3);
						}
					}
				}
			}

		} else {
			JOptionPane.showMessageDialog(new JFrame(), "El directorio no EXISTE");
		}

	}

	/**
	 * Metodo que reemplaza todas las ocurrencias de una cadena en un archivo, creando una copia del archivo
	 * con las modificaciones y devolviendo el número de reemplazos realizados.
	 *
	 * @param ficheroOriginal   El archivo original donde se realizará el reemplazo.
	 * @return El número de reemplazos realizados en el archivo.
	 */
	private int lecturaEscrituraFichero_ReemplazarCadena(File ficheroOriginal) {

		String rutaFicheroCopia = rutaficheroCopia(ficheroOriginal); 
																	
		int contadorReemplazos = 0;
		try {
			File ficheroCopia = new File(rutaFicheroCopia);

			FileReader fr = new FileReader(ficheroOriginal);
			BufferedReader br = new BufferedReader(fr);

			FileWriter fw = new FileWriter(ficheroCopia);
			BufferedWriter bw = new BufferedWriter(fw);

			if (opcionAcentos == 1) {
				String nuevaLinea = "";
				String linea = br.readLine();
				contadorReemplazos = lecturaFichero_Buscar(ficheroOriginal);
				while (linea != null) {
					if (opcionMayMin == 1) {
						nuevaLinea = linea.replace(cadenaBuscar, cadenaReemplazar);
					} else if (opcionMayMin == 2) {
						String cadenaBuscarNuevo = "(?i)" + cadenaBuscar;
						nuevaLinea = linea.replaceAll(cadenaBuscarNuevo, cadenaReemplazar);
					}
					linea = br.readLine();
					bw.write(nuevaLinea);
					bw.newLine();
				}
				
			} else if (opcionAcentos == 2) {
				String cadenaSinAcento = cadenaBuscar;
				cadenaSinAcento = Normalizer.normalize(cadenaSinAcento, Normalizer.Form.NFD);
				cadenaSinAcento = cadenaSinAcento.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

				String nuevaLinea = "";
				String linea = br.readLine();
				linea = Normalizer.normalize(linea, Normalizer.Form.NFD);
				linea = linea.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

				System.out.println("linea sin acento: " + linea);
				while (linea != null) {
					if (opcionMayMin == 1) {
						nuevaLinea = linea.replace(cadenaSinAcento, cadenaReemplazar);
					} else if (opcionMayMin == 2) {
						cadenaSinAcento = "(?i)" + cadenaSinAcento; 
						/* "(?i)" Hace que la búsqueda sea insensible mayúsculas y minúsculas para todo lo que sigue al
						modificador.*/
						nuevaLinea = linea.replaceAll(cadenaSinAcento, cadenaReemplazar);
					}
					linea = br.readLine();
					if (linea != null) {
						linea = Normalizer.normalize(linea, Normalizer.Form.NFD);
						linea = linea.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
					}
					bw.write(nuevaLinea);
					bw.newLine();
				}
			}

			bw.close();
			fw.close();
			br.close();
			fr.close();

			contadorReemplazos = lecturaFichero_Buscar(ficheroOriginal/*, cadena/*, opcionMayMin, opcionAcentos*/);

			return contadorReemplazos;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * Metodo que genera la ruta de un archivo copia a partir del archivo original,
	 * prefijando el nombre con "MOD_" y conservando su extensión.
	 *
	 * @param fichero El archivo original del cual se creará la copia.
	 * @return La ruta completa del archivo copia generado.
	 */
	private String rutaficheroCopia(File fichero) {
		String nombreFichero = fichero.getName();
		String rutaFichero = fichero.getAbsolutePath();

		int posicionPunto = nombreFichero.indexOf(".");
		String nombreFicheroSinExtension = nombreFichero.substring(0, posicionPunto); // NOMBRE SIN EXTENSION
		String extension = nombreFichero.substring(posicionPunto);

		int posicionNombreFichero = rutaFichero.indexOf(nombreFichero);
		String rutaSinNombre = rutaFichero.substring(0, posicionNombreFichero);

		String rutaFicheroCopia = rutaSinNombre + "MOD_" + nombreFicheroSinExtension + extension;																					

		return rutaFicheroCopia;
	}

	/**
	 * Metodo que construye información sobre un archivo y la añade a una lista, 
	 * incluyendo la ruta, tamaño y estadísticas de coincidencias.
	 *
	 * @param fichero  El archivo del que se obtiene información.
	 * @param contador Número de coincidencias o reemplazos.
	 * @param opcion   Opción para el tipo de información a montar.
	 */
	private void montarInformacion(File fichero, int contador, int opcion) {
		// RUTA
		int x = url.lastIndexOf("/");
		String nombreCarpeta = url.substring(x + 1);
		String rutaAbsoluta = fichero.getAbsolutePath();
		int y = rutaAbsoluta.lastIndexOf(nombreCarpeta);
		String rutaNoAbsoluta = rutaAbsoluta.substring(y);

		String infoArchivo = "";
		if (opcion == 1) {
			// FORMATO AÑO HORA PEIDO EN EL EJERCICIO
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date fecha = new Date(fichero.lastModified());
			String fechaStr = sdf.format(fecha);

			// TAMAÑO DEL ARCHIVO
			double tamaño = fichero.length() / 1024;
			infoArchivo = rutaNoAbsoluta + " (" + tamaño + " KB – " + fechaStr + ")";

			listadoInformacionFichero.add(infoArchivo);

		} else if (opcion == 2) {
			if (contador == -1) {
				infoArchivo = rutaNoAbsoluta + " (" + 0 + " coincidencias)";
				listadoInformacionCoincidencias.add(infoArchivo);
			} else {
				infoArchivo = rutaNoAbsoluta + " (" + contador + " coincidencias)";
				listadoInformacionCoincidencias.add(infoArchivo);
			}

		} else if (opcion == 3) {
			if (contador == -1) {
				
				infoArchivo = rutaNoAbsoluta + " (No Accesible)";
				listadoInformacionReemplazos.add(infoArchivo);
			} else {
				infoArchivo = rutaNoAbsoluta + " (" + contador + " reemplazados en MOD_" + fichero.getName() + ")";
				listadoInformacionReemplazos.add(infoArchivo);
			}

		}
	}

	/**
	 * Metodo que imprime por pantalla la información acumulada en función de la opción proporcionada.
	 * @param opcion Opción que determina qué información mostrar.
	 */
	public void imprimirPantalla(int opcion) {
		if (opcion == 1) {
			vista.getTextAreaTexto().setText("");
			for (String index : listadoInformacionFichero) {
				vista.getTextAreaTexto().append(index + "\n");
			}
		} else if (opcion == 2) {
			vista.getTextAreaModificado().setText("");
			for (String index : listadoInformacionCoincidencias) {
				vista.getTextAreaModificado().append(index + "\n");
			}
		} else if (opcion == 3) {
			vista.getTextAreaModificado().setText("");
			for (String index : listadoInformacionReemplazos) {
				vista.getTextAreaModificado().append(index + "\n");
			}
		}
	}

	/**
	 * Metodo que obtiene la extensión de un archivo dado.
	 * @param fichero El archivo del cual se desea obtener la extensión.
	 * @return La extensión del archivo.
	 */
	public String buscarExtension(File fichero) {
		String abs = fichero.getAbsolutePath();
		int indice = abs.indexOf(".");
		String extension = abs.substring(indice);
		return extension;
	}

}
