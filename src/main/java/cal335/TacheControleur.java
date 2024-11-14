package cal335;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

}
