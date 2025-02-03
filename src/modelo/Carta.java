package modelo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.commons.codec.binary.Base64;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Clase que gestiona la carga y almacenamiento de imágenes de cartas en una base de datos MongoDB.
 */
public class Carta {

    /** Ruta del directorio que contiene las cartas en español. */
    private static final String Dir_ES = "./img/cards_es"; 

    /** Ruta del directorio que contiene las cartas en francés. */
    private static final String Dir_FR = "./img/cards_fr"; 

    /**
     * Convierte una imagen en un string codificado en Base64.
     *
     * @param file Archivo de imagen a convertir.
     * @return Cadena de texto en formato Base64 representando la imagen.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public static String convertirImagenABase64(File file) throws IOException {
        byte[] fileContent = Files.readAllBytes(file.toPath());
        return Base64.encodeBase64String(fileContent); 
    }

    /**
     * Elimina las colecciones de cartas antiguas en la base de datos.
     *
     * @param database Instancia de la base de datos MongoDB.
     */
    static void borrarColecciones(MongoDatabase database) {
        System.out.println("Eliminando colecciones antiguas...");
        database.getCollection("cards_es").drop();
        database.getCollection("cards_fr").drop();
    }

    /**
     * Carga las imágenes de cartas en la base de datos MongoDB en formato Base64.
     * Se almacenan en dos colecciones: una para las cartas en español y otra para las cartas en francés.
     */
    public static void cargarImagenesEnMongo() {
        MongoClientURI uri = new MongoClientURI(User.conexion);
        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase(User.databaseName);

        // Eliminar colecciones antiguas antes de cargar nuevas imágenes
        borrarColecciones(database);

        // Cargar cartas en español
        MongoCollection<Document> collectionEs = database.getCollection("cartas_es");
        File folderEs = new File(Dir_ES);
        if (folderEs.exists() && folderEs.isDirectory()) {
            for (File file : folderEs.listFiles()) {
                try {
                    String[] partes = file.getName().split("_");
                    String num = partes[1].split("\\.")[0];
                    int numCartaES = Integer.parseInt(num);
                    String base64 = convertirImagenABase64(file);
                    Document doc = new Document();
                
                    doc.append("suit", partes[0]); // Palo de la carta
                    if (file.getName().contains("10") || file.getName().contains("11") || file.getName().contains("12")) {
                        doc.append("points", 10);
                    } else {
                        doc.append("points", numCartaES);
                    }
                    doc.append("base64", base64);
                    collectionEs.insertOne(doc);
                    System.out.println("Cargada imagen ES: " + file.getName());
                } catch (IOException e) {
                    System.err.println("Error al leer imagen ES: " + file.getName());
                }
            }
        }

        // Cargar cartas en francés
        MongoCollection<Document> collectionFr = database.getCollection("cards_fr");
        File folderFr = new File(Dir_FR);
        if (folderFr.exists() && folderFr.isDirectory()) {
            for (File file : folderFr.listFiles()) {
                try {
                    String base64 = convertirImagenABase64(file);
                    String[] partesFr = file.getName().split("_");
                    String numFr = partesFr[1].split("\\.")[0];
                    int numCartaFr = Integer.parseInt(numFr);
                    Document doc = new Document();
                    
                    doc.append("suit", partesFr[0]); // Palo de la carta
                    if (file.getName().contains("10") || file.getName().contains("11") || file.getName().contains("12")) {
                        doc.append("points", 10);
                    } else {
                        doc.append("points", numCartaFr);
                    }
                    doc.append("base64", base64);
                    collectionFr.insertOne(doc);
                    System.out.println("Cargada imagen FR: " + file.getName());
                } catch (IOException e) {
                    System.err.println("Error al leer imagen FR: " + file.getName());
                }
            }
        }
        System.out.println("Todas las imágenes han sido cargadas en MongoDB.");
    }
}
