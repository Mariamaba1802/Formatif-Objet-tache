package cal335;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;


public class GestionnaireConnection {
    private static Connection connexion;
    private static GestionnaireConnection instance;
    private static final String URL = "jdbc:sqlite:" + Paths.get("src", "main", "resources", "tache.db").toAbsolutePath();



    private GestionnaireConnection() {
        try {
            connexion = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'initialisation de la connexion : " + e.getMessage());
        }
    }
    public static synchronized GestionnaireConnection getInstance() {
        if (instance == null) {
            instance = new GestionnaireConnection();
        }
        return instance;
    }
    public  Connection obtenirConnexion() {
        if (connexion == null) {
            new GestionnaireConnection();
        }
        return connexion;
    }

}