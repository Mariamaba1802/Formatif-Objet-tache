package cal335;

public class TacheMapper {

    public static Tache toModel(TacheDTO dto) {
        Tache tache = new Tache();
        tache.setNom(dto.getNom());
        tache.setDescription(dto.getDescription());
        tache.setaFaire(dto.isaFaire());
        return tache;
    }

    public static TacheDTO  toDTO(Tache compte){
        TacheDTO dto = new TacheDTO();
        dto.setNom(compte.getNom());
        dto.setDescription(compte.getDescription());
        dto.setaFaire(compte.isaFaire());
        return dto;
    }


}
