package cal335.Controleur;


import cal335.Service.TacheService;
import cal335.Modele.Tache;
import cal335.Modele.TacheDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.util.List;

public class TacheControleur implements HttpHandler{
 private List<Tache> TachesSauvegardes;
    private final TacheService tacheService;
    private final ObjectMapper objectMapper;

    public TacheControleur(TacheService tacheService) {
        this.tacheService = tacheService;
        this.objectMapper = new ObjectMapper();
    }


    // Créez une nouvelle tâche
    Tache nouvelleTache1 = new Tache("Aujourd'hui", "finir TP1",true);

    Tache nouvelleTache2 = new Tache("Demain", "finir examen",false);

    Tache tacheTrue = new Tache("Demain", "finir examen",true);
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String methode = exchange.getRequestMethod();
        String chemin = exchange.getRequestURI().getPath();

        switch (methode) {
            case "GET" -> {
                if (chemin.equals("/taches")) {
                    rechercherTaches(exchange);
                }
            }
            case "POST" -> {
                if (chemin.equals("/taches/creation")) {
                    ajouterTache(exchange);
                }
            }
            case "DELETE" -> {
                if (chemin.startsWith("/taches/suppression/")) {
                    supprimerTache(exchange);
                }
            }
            case "PUT" -> {
                if (chemin.startsWith("/taches/modification/")) {
                    modifierNomTache(exchange);
                }
            }
            default -> exchange.sendResponseHeaders(405, -1);
        }

    }

    private void ajouterTache(HttpExchange exchange) throws IOException {

        // Récupère le corps de la requête JSON et désérialise en TacheDTO
        InputStream tacheJsonRecus = exchange.getRequestBody();
        TacheDTO tacheDeserialisee = objectMapper.readValue(tacheJsonRecus, TacheDTO.class);

        // Ajoute la tâche via le service
        tacheService.ajouterTache(tacheDeserialisee);

        // Envoie une réponse JSON confirmant la création
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        String responseJson = objectMapper.writeValueAsString(tacheDeserialisee);
        exchange.sendResponseHeaders(200, responseJson.getBytes().length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseJson.getBytes());
        }

    }
    private void rechercherTaches(HttpExchange exchange) throws IOException {
        // Récupère toutes les tâches DTO depuis le service
        List<TacheDTO> tachesDTO = tacheService.rechercherTaches();

        // Sérialise la liste des TacheDTO en JSON pour la réponse
        String jsonResponse = objectMapper.writeValueAsString(tachesDTO);

        // Configure et envoie la réponse
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, jsonResponse.getBytes().length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(jsonResponse.getBytes());
        }

    }
    public void supprimerTache(HttpExchange exchange) throws IOException {

            // Récupérer la query string de l'URL
            String query = exchange.getRequestURI().getQuery();

            // Vérifier que la query string contient un paramètre "id"
            if (query != null && query.startsWith("id=")) {
                // Extraire l'ID depuis la query string
                int id = Integer.parseInt(query.substring(3)); // Récupérer l'ID après "id="

                // Appeler le service pour supprimer la tâche
                TacheService.supprimerTache(id);

                // Répondre avec succès
                String responseJson = "{\"message\": \"Tâche supprimée avec succès\"}";
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, responseJson.getBytes().length);

                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(responseJson.getBytes());
                }


        }
    }



    public void modifierNomTache(HttpExchange exchange) throws IOException {
        // Récupérer l'ID depuis la query string
        String query = exchange.getRequestURI().getQuery();
        if (query != null && query.startsWith("id=")) {
            int id = Integer.parseInt(query.substring(3)); // Extraire l'ID après "id="

            // Lire le corps JSON pour récupérer le nouveau nom
            InputStream input = exchange.getRequestBody();
            String body = new String(input.readAllBytes());

            // Désérialiser le JSON pour obtenir le nom
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonRecus = objectMapper.readTree(body);
            String nouveauNom = jsonRecus.get("nom").asText(); // Récupérer le champ "nom"

            // Appeler le service pour modifier le nom
            TacheService.modifierNomTache(id, nouveauNom);

            // Répondre avec succès
            String responseJson = "{\"message\": \"Nom de la tâche modifié avec succès\"}";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, responseJson.getBytes().length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(responseJson.getBytes());
            }
        }
    }


    }


