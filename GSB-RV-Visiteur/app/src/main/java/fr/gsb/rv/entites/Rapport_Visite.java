package fr.gsb.rv.entites;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by echowin on 15/03/18.
 */

public class Rapport_Visite implements Parcelable{

    private String visiteur;
    private int num;
    private String praticien;
    private String bilan;
    private String vu = "non";
    private DateFr date;
    private String motif;
    private int coefficientDeConfiance;


    private String dateString;

    public Rapport_Visite(String visiteur, int num, String praticien, String bilan, String dateString, String motif, int coefficientDeConfiance) {
        this.visiteur = visiteur;
        this.num = num;
        this.praticien = praticien;
        this.bilan = bilan;
        this.dateString = dateString;
        this.motif = motif;
        this.coefficientDeConfiance = coefficientDeConfiance;
    }

    public Rapport_Visite(String visiteur, String praticien, String bilan, DateFr date, String motif, int coefficientDeConfiance) {
        this.visiteur = visiteur;
        this.praticien = praticien;
        this.bilan = bilan;
        this.date = date;
        this.motif = motif;
        this.coefficientDeConfiance = coefficientDeConfiance;
    }

    public Rapport_Visite(String visiteur, int num, String praticien, String bilan, String vu, DateFr date, String motif, int coefficientDeConfiance) {
        this.visiteur = visiteur;
        this.num = num;
        this.praticien = praticien;
        this.bilan = bilan;
        this.vu = vu;
        this.date = date;
        this.motif = motif;
        this.coefficientDeConfiance = coefficientDeConfiance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags){

        dest.writeString(this.visiteur);
        dest.writeInt(this.num);
        dest.writeString(this.praticien);
        dest.writeString(this.bilan);
        dest.writeString(this.vu);
        dest.writeString(this.date.toString());
        dest.writeString(this.motif);
        dest.writeInt(this.coefficientDeConfiance);

    }

    public Rapport_Visite (Parcel in){

        this.visiteur=in.readString();
        this.num=in.readInt();
        this.praticien=in.readString();
        this.bilan=in.readString();
        this.vu=in.readString();
        this.date=new DateFr(in.readString());
        this.motif=in.readString();
        this.coefficientDeConfiance=in.readInt();

    }


    public static final Parcelable.Creator<Rapport_Visite> CREATOR= new Parcelable.Creator<Rapport_Visite>(){

        public Rapport_Visite createFromParcel (Parcel source){

            return new Rapport_Visite(source);

        }

        public Rapport_Visite[] newArray (int size){
            return new Rapport_Visite[size];
        }

    };

    public String getVisiteur() {
        return visiteur;
    }

    public void setVisiteurMatricule(String visiteur) {
        this.visiteur = visiteur;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPraticien() {
        return praticien;
    }

    public void setPraticien(String praticien) {
        this.praticien = praticien;
    }

    public String getBilan() {
        return bilan;
    }

    public void setBilan(String bilan) {
        this.bilan = bilan;
    }

    public String getVu() {
        return vu;
    }

    public void setVu(String vu) {
        this.vu = vu;
    }

    public DateFr getDate() {
        return date;
    }

    public void setDate(DateFr date) {
        this.date = date;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public int getCoefficientDeConfiance() {
        return coefficientDeConfiance;
    }

    public void setCoefficientDeConfiance(int coefficientDeConfiance) {
        this.coefficientDeConfiance = coefficientDeConfiance;
    }

    @Override
    public String toString() {
        return "Rapport_Visite{" +
                "visiteur='" + visiteur + '\'' +
                ", num=" + num +
                ", praticien=" + praticien +
                ", bilan='" + bilan + '\'' +
                ", vu='" + vu + '\'' +
                ", date=" + date +
                ", motif='" + motif + '\'' +
                ", coefficientDeConfiance=" + coefficientDeConfiance +
                '}';
    }
}
