package cal335.Service;

import cal335.GestionnaireConnection;
import cal335.Modele.Tache;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;

public class TacheDAO {

    public static void insererTache(Tache tache) {
        String requeteSQL = "INSERT INTO Tache (nom, description,a_faire) VALUES (?, ?, ?)";

        try (
                Connection connexion = GestionnaireConnection.getInstance().obtenirConnexion();
                PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {

            preparedStatement.setString(1, tache.getNom());
           preparedStatement.setString(2, tache.getDescription());
          preparedStatement.setBoolean(3, tache.isaFaire());

            int result = preparedStatement.executeUpdate();
            System.out.println("Nombre de lignes insérées : " + result);
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de la tâche : " + e.getMessage());
        }
    }

    public static void obtenirTaches() {
        String requeteSQL = "SELECT * FROM Tache";

        try (Connection connexion = GestionnaireConnection.getInstance().obtenirConnexion();
             PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            System.out.println("---------------------------");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String description = resultSet.getString("description");
                String a_faire = resultSet.getString("a_faire");
                System.out.println("ID: " + id + ", Nom: " + nom + ", Description: " + description + " A faire " + a_faire );
            }
            System.out.println("---------------------------");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la lecture des tâches : " + e.getMessage());
        }
    }

    public static void mettreAJourNomTache(int id, String nouveauNom) {
        String requeteSQL = "UPDATE Tache SET nom = ? WHERE id = ?";

        try (Connection connexion = GestionnaireConnection.getInstance().obtenirConnexion();
             PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {

            preparedStatement.setString(1, nouveauNom);
            preparedStatement.setInt(2, id);

            int result = preparedStatement.executeUpdate();
            System.out.println("Nombre de lignes mises à jour : " + result);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de la tâche : " + e.getMessage());
        }
    }

    public static void supprimerTache(int id) {
        String requeteSQL = "DELETE FROM Tache WHERE id = ?";

        try (Connection connexion = GestionnaireConnection.getInstance().obtenirConnexion();
             PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {

            preparedStatement.setInt(1, id);

            int result = preparedStatement.executeUpdate();
            System.out.println("Nombre de lignes supprimées : " + result);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la tâche : " + e.getMessage());
        }
    }

}
