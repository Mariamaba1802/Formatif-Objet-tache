package cal335;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;


public class ConnexionA_SQLite {
    private static Connection connexion;
    private static final String URL = "jdbc:sqlite:" + Paths.get("src", "main", "resources", "tache.db").toAbsolutePath();



    private ConnexionA_SQLite() {
        try {
            connexion = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'initialisation de la connexion : " + e.getMessage());
        }
    }

    public static Connection obtenirConnexion() {
        if (connexion == null) {
            new ConnexionA_SQLite();
        }
        return connexion;
    }

}