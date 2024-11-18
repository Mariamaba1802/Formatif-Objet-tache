package cal335.Service;

import cal335.Modele.Tache;
import cal335.Modele.TacheDTO;
import cal335.Modele.TacheMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TacheService {
    private final HashMap<Boolean, List<Tache>> MapTaches;

public TacheService (){
    this.MapTaches = new HashMap<>();
    MapTaches.put(true, new ArrayList<>());
    MapTaches.put(false, new ArrayList<>());
}
   public  void ajouterTache(TacheDTO tacheDto) {
    if (!MapTaches.containsKey(tacheDto.isaFaire())){
        MapTaches.put(tacheDto.isaFaire(),new ArrayList<>());
    }
        MapTaches.get(tacheDto.isaFaire()).add(TacheMapper.toModel(tacheDto));
    }
   public  List<TacheDTO> rechercherTaches() {
        List<TacheDTO> toutesLesTaches = new ArrayList<>();
        List<TacheDTO> tacheFait = TacheMapper.versDTOs(MapTaches.get(true));
        List<TacheDTO> tacheAFaire= TacheMapper.versDTOs(MapTaches.get(false));
        toutesLesTaches.addAll(tacheAFaire);
        toutesLesTaches.addAll(tacheFait);
        return toutesLesTaches;
    }
}

