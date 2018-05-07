package fr.gsb.rv.entites;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by echowin on 15/03/18.
 */

public class Praticien implements Parcelable{

    private int num;
    private String nom;
    private String prenom;
    private String adresse;
    private String codePostal;
    private String ville;
    private int coefficientNotoriete;
    private Type_Praticien typePraticien;


    public Praticien(int num, String nom, String prenom, int coefficientNotoriete) {
        this.num = num;
        this.nom = nom;
        this.prenom = prenom;
        this.coefficientNotoriete = coefficientNotoriete;
    }

    public Praticien(int num, String nom, String prenom, String adresse, String codePostal, String ville, int coefficientNotoriete, Type_Praticien typePraticien) {
        this.num = num;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
        this.coefficientNotoriete = coefficientNotoriete;
        this.typePraticien = typePraticien;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public int getCoefficientNotoriete() {
        return coefficientNotoriete;
    }

    public void setCoefficientNotoriete(int coefficientNotoriete) {
        this.coefficientNotoriete = coefficientNotoriete;
    }

    public Type_Praticien getTypePraticien() {
        return typePraticien;
    }

    public void setTypePraticienCode(Type_Praticien typePraticien) {
        this.typePraticien = typePraticien;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags){

        dest.writeInt(this.num);
        dest.writeString(this.nom);
        dest.writeString(this.prenom);
        dest.writeInt(this.coefficientNotoriete);

    }

    public Praticien (Parcel in){

        this.num=in.readInt();
        this.nom=in.readString();
        this.prenom=in.readString();
        this.coefficientNotoriete=in.readInt();

    }


    public static final Parcelable.Creator<Praticien> CREATOR= new Parcelable.Creator<Praticien>(){

        public Praticien createFromParcel (Parcel source){

            return new Praticien(source);

        }

        public Praticien[] newArray (int size){
            return new Praticien[size];
        }

    };

    @Override
    public String toString() {
        return "Praticien{" +
                "num=" + num +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", ville='" + ville + '\'' +
                ", coefficientNotoriete='" + coefficientNotoriete + '\'' +
                ", typePraticien='" + typePraticien + '\'' +
                '}';
    }
}
