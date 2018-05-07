package fr.gsb.rv.entites;

/**
 * Created by echowin on 06/04/18.
 */

public class Echantillons {

    private String depotLegalMedoc;
    private String visMatricule;
    private int numRapport;
    private int quantite;

    public Echantillons(String depotLegalMedoc, String visMatricule, int numRapport, int quantite) {
        this.depotLegalMedoc = depotLegalMedoc;
        this.visMatricule = visMatricule;
        this.numRapport = numRapport;
        this.quantite = quantite;
    }

    public String getDepotLegalMedoc() {
        return depotLegalMedoc;
    }

    public void setDepotLegalMedoc(String depotLegalMedoc) {
        this.depotLegalMedoc = depotLegalMedoc;
    }

    public String getVisMatricule() {
        return visMatricule;
    }

    public void setVisMatricule(String visMatricule) {
        this.visMatricule = visMatricule;
    }

    public int getNumRapport() {
        return numRapport;
    }

    public void setNumRapport(int numRapport) {
        this.numRapport = numRapport;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return "Echantillons{" +
                "depotLegalMedoc='" + depotLegalMedoc + '\'' +
                ", visMatricule='" + visMatricule + '\'' +
                ", numRapport=" + numRapport +
                ", quantite=" + quantite +
                '}';
    }
}
