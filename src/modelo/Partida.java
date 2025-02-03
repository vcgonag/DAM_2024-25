package modelo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import org.apache.commons.codec.binary.Base64;
import org.bson.Document;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Clase que gestiona la partida del juego de Blackjack.
 * Permite obtener cartas desde MongoDB, barajar el mazo y procesar imágenes de cartas.
 */
public class Partida {

    /** Lista que representa el mazo de cartas disponible en la partida. */
    private static List<String> mazo = new ArrayList<>();

    /**
     * Obtiene las cartas almacenadas en la base de datos MongoDB y las almacena en la lista mazo.
     *
     * @param cartasSuit Nombre de la colección de cartas (puede ser "cartas_es" o "cartas_fr").
     */
    public static void obtenerCartasDeMongo(String cartasSuit) {
        MongoClientURI uri = new MongoClientURI(User.conexion);
        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase(User.databaseName);
        MongoCollection<Document> coleccion = database.getCollection(cartasSuit);
        MongoCursor<Document> cursor = coleccion.find().iterator();

        while (cursor.hasNext()) {
            mazo.add(cursor.next().toJson()); // Convierte cada documento a JSON y lo agrega al mazo
        }
        mongoClient.close();
    }

    /**
     * Baraja el mazo de cartas de manera aleatoria.
     */
    public static void barajarMazo() {
        Collections.shuffle(mazo, new Random());
        System.out.println("El mazo ha sido barajado.");
    }

    /**
     * Transforma una cadena en formato Base64 a una imagen JPG y la guarda en el sistema de archivos.
     *
     * @param string64 Cadena en formato Base64 que representa una imagen de carta.
     */
    public static void transformarBytesJpg(String string64) {
        try {
            byte[] btDataFile = Base64.decodeBase64(string64);
            BufferedImage imagenOriginal = ImageIO.read(new ByteArrayInputStream(btDataFile));

            Image imagenEscalada = imagenOriginal.getScaledInstance(-1, 400, Image.SCALE_SMOOTH);
            BufferedImage bufferedEscalada = new BufferedImage(imagenEscalada.getWidth(null),
                    imagenEscalada.getHeight(null), BufferedImage.TYPE_INT_RGB);

            bufferedEscalada.getGraphics().drawImage(imagenEscalada, 0, 0, null);

            ImageIO.write(bufferedEscalada, "jpg", new File("imagen.jpg"));
            System.out.println("Imagen generada y guardada como 'imagen.jpg'.");

        } catch (IOException e) {
            System.err.println("Error al procesar la imagen: " + e.getMessage());
        }
    }
}
