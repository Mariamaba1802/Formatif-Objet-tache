package cal335;

import cal335.Controleur.TacheControleur;
import cal335.Service.TacheService;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    private static final int PORT = 8082;

    public static void main(String[] args) throws IOException {

        HttpServer serveur = HttpServer.create(new InetSocketAddress(PORT), 0);

        TacheService tacheService = new TacheService();

        TacheControleur controleur = new TacheControleur(tacheService);
        serveur.createContext("/taches", controleur);
        serveur.setExecutor(null);

        serveur.start();

        System.out.println("Serveur démarré sur le port 8082");
    }
}
