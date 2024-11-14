Base de données SQLite

Étape 1 :
Téléchargement de la Bibliothèque JDBC pour SQLite

Tout d'abord, téléchargez la bibliothèque JDBC pour SQLite.

Rendez-vous dans le maven repository pour trouver (la bonne version de) la bibliothèque ajouter à votre projet Maven dans le fichier pom.xml


Étape 2 :
Configuration de la Base de Données SQLite

Créez une nouvelle base de données SQLite en ajoutant un fichier nommé taches.db à l'intérieur du répertoire src/main/resources de votre projet.
Ce fichier servira de stockage pour vos données.

Vous pouvez préparer votre base de données SQLite avec la structure de table requise.
Par exemple, pour une application de gestion des tâches, vous pourriez avoir une table nommée taches.
Utilisez un outil comme DB Browser for SQLite pour créer votre table, ou exécutez une commande SQL dans votre programme Java pour créer la table lors de l'initialisation.

CREATE TABLE Tache (
id INTEGER PRIMARY KEY AUTOINCREMENT,
nom TEXT NOT NULL,
description TEXT,
a_faire BOOLEAN DEFAULT TRUE;
date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
Insersion de données de test simples :

INSERT INTO TEST (titre, nom) VALUES 
('Test 1', 'Description du Test 1'),
('Test 2', 'Description du Test 2'),
('Test 3', 'Description du Test 3');    

Étape 3 :
Connexion à SQLite depuis Java

Créez une classe Java pour gérer la connexion à la base de données.
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GestionnaireConnexionSQLite {

    //private static final String URL = "jdbc:sqlite:src/main/resources/taches.db";
    private static final String URL = "jdbc:nom_sgbd:chemin_vers_la_bd_taches.db";


    public static Connection obtenirConnexion() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}

Le nom du sgbd : sqlite 

Sous Linux, le chemin vers la bd : src/main/resources/taches.db
Note :
Pour garantir que le chemin vers taches.db soit valide à la fois sous Linux et Windows :

import java.nio.file.Path;
import java.nio.file.Paths;

public class GestionnaireConnexionSQLite {
    private static final String URL;

    static {
        Path cheminVersBase = Paths.get("src", "main", "resources", "le_fichier.db");
        URL = "jdbc:nom_sgbd:" + cheminVersBase.toString();
    }

    public static Connection obtenirConnexion() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}

Étape 4 :
Écrire une Requête Simple :

Objectif :
Exécutez une requête SQL simple pour vérifier si la connexion est établie correctement.

Consigne :
Dans la classe ConnexionBaseDeDonnees, après avoir testé la connexion, ajoutez une méthode executerUneRequeteSimple.

Cette méthode doit exécuter une requête SELECT * FROM [NomTable] pour une table que vous avez créée dans la base de données.

Affichez le résultat de la requête dans la console pour vérifier que la requête fonctionne correctement.

Compléter le Message d'Erreur :

Objectif :
Améliorer la gestion des exceptions pour fournir des informations utiles en cas de problème de connexion.

Consigne :
Dans la méthode testerConnexion, ajoutez un bloc try-catch pour attraper les exceptions potentielles lors de la connexion à la base de données.

Dans le bloc catch, utilisez System.err.println pour afficher un message d'erreur détaillé en incluant le message et la pile d'exception (Throwable).

Assurez-vous que le message d'erreur explique clairement la cause potentielle de l'erreur (par exemple, problème d'authentification, URL incorrecte, etc.).
