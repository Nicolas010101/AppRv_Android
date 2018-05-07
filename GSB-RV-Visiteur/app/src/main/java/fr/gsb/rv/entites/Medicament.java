package fr.gsb.rv.entites;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by echowin on 15/03/18.
 */

public class Medicament implements Parcelable{

    private String depotLegal;
    private String nomCommercial;
    private String famille;
    private String composition;
    private String effets;
    private String contreIndications;
    private double prixEchantillon;
    private int quantite;

    public Medicament(String depotLegal, String nomCommercial, String famille, String composition, String effets, String contreIndications, double prixEchantillon) {
        this.depotLegal = depotLegal;
        this.nomCommercial = nomCommercial;
        this.famille = famille;
        this.composition = composition;
        this.effets = effets;
        this.contreIndications = contreIndications;
        this.prixEchantillon = prixEchantillon;
    }

    public Medicament(String depotLegal, String nomCommercial, String famille, String composition, String effets, String contreIndications, double prixEchantillon, int quantite) {
        this.depotLegal = depotLegal;
        this.nomCommercial = nomCommercial;
        this.famille = famille;
        this.composition = composition;
        this.effets = effets;
        this.contreIndications = contreIndications;
        this.prixEchantillon = prixEchantillon;
        this.quantite = quantite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags){

        dest.writeString(this.depotLegal);
        dest.writeString(this.nomCommercial);
        dest.writeString(this.famille);
        dest.writeString(this.composition);
        dest.writeString(this.effets);
        dest.writeString(this.contreIndications);
        dest.writeDouble(this.prixEchantillon);

    }

    public Medicament (Parcel in){
        this.depotLegal=in.readString();
        this.nomCommercial=in.readString();
        this.famille=in.readString();
        this.composition=in.readString();
        this.effets=in.readString();
        this.contreIndications=in.readString();
        this.prixEchantillon=in.readDouble();

    }


    public static final Parcelable.Creator<Medicament> CREATOR= new Parcelable.Creator<Medicament>(){

        public Medicament createFromParcel (Parcel source){

            return new Medicament(source);

        }

        public Medicament[] newArray (int size){
            return new Medicament[size];
        }

    };

    public String getDepotLegal() {
        return depotLegal;
    }

    public void setDepotLegal(String depotLegal) {
        this.depotLegal = depotLegal;
    }

    public String getNomCommercial() {
        return nomCommercial;
    }

    public void setNomCommercial(String nomCommercial) {
        this.nomCommercial = nomCommercial;
    }

    public String getFamille() {
        return famille;
    }

    public void setFamille(String famille) {
        this.famille = famille;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public String getEffets() {
        return effets;
    }

    public void setEffets(String effets) {
        this.effets = effets;
    }

    public String getContreIndications() {
        return contreIndications;
    }

    public void setContreIndications(String contreIndications) {
        this.contreIndications = contreIndications;
    }

    public double getPrixEchantillon() {
        return prixEchantillon;
    }

    public void setPrixEchantillon(double prixEchantillon) {
        this.prixEchantillon = prixEchantillon;
    }


    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return "Medicament{" +
                "depotLegal='" + depotLegal + '\'' +
                ", nomCommercial='" + nomCommercial + '\'' +
                ", famille='" + famille + '\'' +
                ", composition='" + composition + '\'' +
                ", effets='" + effets + '\'' +
                ", contreIndications='" + contreIndications + '\'' +
                ", prixEchantillon=" + prixEchantillon +
                ", quantite=" + quantite +
                '}';
    }
}
