package cal335.Modele;

import cal335.Modele.Tache;
import cal335.Modele.TacheDTO;

import java.util.ArrayList;
import java.util.List;

public class TacheMapper {

    public static Tache toModel(TacheDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Tache(dto.getNom(), dto.getDescription(), dto.isaFaire());
    }

    public static TacheDTO  toDTO(Tache tache){
        if (tache == null) {
            return null;
        }

        return new TacheDTO(tache.getNom(), tache.getDescription(), tache.isaFaire());
    }
    public static List<TacheDTO> versDTOs(List<Tache> taches) {
        if (taches == null || taches.isEmpty()) {
            return new ArrayList<>();
        }
        List<TacheDTO> tachesDTOs = new ArrayList<>();
        for (Tache tache : taches) {
            tachesDTOs.add(toDTO(tache));
        }
        return tachesDTOs;
    }


    public static List<Tache> versEntites(List<TacheDTO> tachesDTOs) {
        if (tachesDTOs == null || tachesDTOs.isEmpty()) {
            return new ArrayList<>();
        }

        List<Tache> taches = new ArrayList<>();
        for (TacheDTO tacheDTO : tachesDTOs) {
            taches.add(toModel(tacheDTO));
        }
        return taches;
    }

}
