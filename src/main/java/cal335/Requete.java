package cal335;

import cal335.Modele.Tache;
import cal335.Service.TacheDAO;

public class Requete {
    public static void main(String[] args) {
        Tache tache1 = new Tache("task2","tp2",false);

        //insertion Tache1
        //TacheDAO.insererTache(tache1);

        //obtenir toutes les taches
        //TacheDAO.obtenirTaches();

       // TacheDAO.mettreAJourNomTache(1,"mise a jour");
TacheDAO.supprimerTache(1);

    }
}
