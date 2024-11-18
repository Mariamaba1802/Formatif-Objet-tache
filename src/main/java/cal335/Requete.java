package cal335;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Requete {
    public static void main(String[] args) {
        String requete = "SELECT * FROM Tache";

        try (Connection connexion = ConnexionA_SQLite.obtenirConnexion();
             Statement statement = connexion.createStatement();
             ResultSet resultSet = statement.executeQuery(requete)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String description = resultSet.getString("description");
                boolean aFaire = resultSet.getBoolean("a_faire");

                System.out.println("ID: " + id);
                System.out.println("Nom: " + nom);
                System.out.println("Description: " + description);
                System.out.println("À faire: " + aFaire);
                System.out.println("---------------------------");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'exécution de la requête : " + e.getMessage());
        }
    }
}
