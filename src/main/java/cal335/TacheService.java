package cal335;

import java.util.ArrayList;
import java.util.List;

public class TacheService {
    private final List<Tache> listeDesTaches = new ArrayList<>();

    private void ajouterTache() {
        Tache nouvelleTache = null;
        listeDesTaches.add(nouvelleTache);
    }
    private void rechercherTaches() {
        List<Tache> listeDesTaches = this.listeDesTaches;
    }
}

