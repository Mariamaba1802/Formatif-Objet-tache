package cal335.Modele;

public class TacheDTO {
    private String nom;
    private String description;
    private boolean aFaire;

    // Constructeur avec paramètres
    public TacheDTO(String nom, String description, boolean aFaire) {
        this.nom = nom;
        this.description = description;
        this.aFaire = aFaire;
    }

    // Constructeur par défaut
    public TacheDTO() {
    }

    // Getters et Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isaFaire() {
        return aFaire;
    }

    public void setaFaire(boolean aFaire) {
        this.aFaire = aFaire;
    }

    @Override
    public String toString() {
        return "Tache{" +
                "nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", aFaire=" + aFaire +
                '}';
    }
}
