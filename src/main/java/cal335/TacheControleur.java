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
  public TacheControleur(){
      this.TachesSauvegardes=new ArrayList<>();
  }

    // Créez une nouvelle tâche
    Tache nouvelleTache1 = new Tache("Aujourd'hui", "finir TP1",true);
    Tache nouvelleTache2 = new Tache("Demain", "finir examen",false);
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ObjectMapper monMapper = new ObjectMapper();
        String methode = exchange.getRequestMethod();
        String chemin = exchange.getRequestURI().getPath();

        switch (methode){
            case "GET":
                if (chemin.equals("/taches")) {
                    rechercherTaches(exchange);
                }
                break;
            case "POST" :
                if (chemin.equals("/taches/creation")){
                    ajouterTache(exchange);
                }
                break;
            default:
                exchange.sendResponseHeaders(405, -1);
        }

    }

    private void ajouterTache(HttpExchange exchange) throws IOException {

        InputStream tacheJsonRecus = exchange.getRequestBody();
        ObjectMapper monObjectMapper = new ObjectMapper();

        Tache tacheDeserialisee = monObjectMapper.readValue(tacheJsonRecus, Tache.class);
        System.out.println(tacheDeserialisee);

        System.out.println("Description de la tâche : " + tacheDeserialisee.toString());

        // Ajouter la tâche à la liste
        TachesSauvegardes.add(tacheDeserialisee);

        exchange.getResponseHeaders().set("Content-Type", "application/json");

        // Créer une réponse pour le client
        String responseJson = monObjectMapper.writeValueAsString(tacheDeserialisee); // Convertir la tâche en JSON
        exchange.sendResponseHeaders( 200, responseJson.getBytes().length); // Envoi des headers de la réponse

        // Écrire la réponse dans le corps de la réponse
        OutputStream os = exchange.getResponseBody();
        os.write(responseJson.getBytes());
        os.close(); // Fermer le flux de sortie

    }
    private void rechercherTaches(HttpExchange exchange) throws IOException {
        ObjectMapper monObjectMapper = new ObjectMapper();


        // Ajoutez la tâche à la liste
        TachesSauvegardes.add(nouvelleTache1);
        TachesSauvegardes.add(nouvelleTache2);

        // Sérialisation de la liste des tâches en JSON
        String jsonResponse = monObjectMapper.writeValueAsString(TachesSauvegardes);
        System.out.println("Description de la tâche : " + nouvelleTache1.toString());

        exchange.getResponseHeaders().set("Content-Type","application/json");
        exchange.sendResponseHeaders(200,jsonResponse.getBytes().length);

        // Envoi de la réponse
        OutputStream os = exchange.getResponseBody();
        os.write(jsonResponse.getBytes());
        os.close();


    }

}
