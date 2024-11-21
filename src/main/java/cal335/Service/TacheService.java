package cal335.Service;

import cal335.Modele.Tache;
import cal335.Modele.TacheDTO;
import cal335.Modele.TacheMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TacheService {
    private final HashMap<Boolean, List<Tache>> MapTaches;

public TacheService () {
    this.MapTaches = new HashMap<>();
    MapTaches.put(true, new ArrayList<>());
    MapTaches.put(false, new ArrayList<>());
}

    public void ajouterTache(TacheDTO tacheDto) {
        if (!MapTaches.containsKey(tacheDto.isaFaire())) {
            MapTaches.put(tacheDto.isaFaire(), new ArrayList<>());
        }

        // Mapper le DTO en modèle
        Tache tache = TacheMapper.toModel(tacheDto);

        // Ajouter la tâche à la structure en mémoire
        MapTaches.get(tacheDto.isaFaire()).add(tache);

        // Appeler TacheDAO pour insérer dans la base de données
        TacheDAO.insererTache(tache);
    }

    public List<TacheDTO> rechercherTaches() {
        List<TacheDTO> toutesLesTaches = new ArrayList<>();

        // Obtenir les tâches depuis la base de données
        List<Tache> tachesDepuisBase = TacheDAO.obtenirTaches();

        // Convertir les tâches en DTO
        toutesLesTaches.addAll(TacheMapper.versDTOs(tachesDepuisBase));

        return toutesLesTaches;
    }

    public static void modifierNomTache(int id, String nouveauNom){
    TacheDAO.mettreAJourNomTache(id,nouveauNom);
    }

    public static void supprimerTache(int id){
    TacheDAO.supprimerTache(id);
    }
}

