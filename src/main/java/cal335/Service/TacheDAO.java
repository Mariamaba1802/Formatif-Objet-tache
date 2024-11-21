package cal335.Service;

import cal335.GestionnaireConnection;
import cal335.Modele.Tache;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TacheDAO {


    public static void insererTache(Tache tache) {
        String requeteSQL = "INSERT INTO Tache (nom, description,a_faire) VALUES (?, ?, ?)";
        Connection connexion = GestionnaireConnection.getInstance().obtenirConnexion();

        try (

                PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL  )) {

            preparedStatement.setString(1, tache.getNom());
           preparedStatement.setString(2, tache.getDescription());
          preparedStatement.setBoolean(3, tache.isaFaire());

            int result = preparedStatement.executeUpdate();
            System.out.println("Nombre de lignes insérées : " + result);
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de la tâche : " + e.getMessage());
        }
    }

    public static List<Tache> obtenirTaches() {
        String requeteSQL = "SELECT * FROM Tache";
        List<Tache> listeTaches = new ArrayList<>();

        try (Connection connexion = GestionnaireConnection.getInstance().obtenirConnexion();
             PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                // Récupérer les données depuis le ResultSet
                String nom = resultSet.getString("nom");
                String description = resultSet.getString("description");
                boolean aFaire = resultSet.getBoolean("a_faire");

                // Créer une instance de Tache
                Tache tache = new Tache(nom, description, aFaire);
                listeTaches.add(tache);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la lecture des tâches : " + e.getMessage());
        }

        return listeTaches;
    }


    public static void mettreAJourNomTache(int id, String nouveauNom) {
        String requeteSQL = "UPDATE Tache SET nom = ? WHERE id = ?";
        Connection connexion = GestionnaireConnection.getInstance().obtenirConnexion();

        try (
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
        Connection connexion = GestionnaireConnection.getInstance().obtenirConnexion();

        try (
             PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {

            preparedStatement.setInt(1, id);

            int result = preparedStatement.executeUpdate();
            System.out.println("Nombre de lignes supprimées : " + result);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la tâche : " + e.getMessage());
        }
    }

}
