package fr.gsb.rv.entites;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rk on 10/12/17.
 */

public class Motif implements Parcelable{

    private int code ;
    private String libelle ;

    public Motif(int code, String libelle) {
        super();
        this.code = code;
        this.libelle = libelle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags){

        dest.writeInt(this.code);
        dest.writeString(this.libelle);

    }

    public Motif (Parcel in){

        this.code=in.readInt();
        this.libelle=in.readString();

    }


    public static final Parcelable.Creator<Motif> CREATOR= new Parcelable.Creator<Motif>(){

        public Motif createFromParcel (Parcel source){

            return new Motif(source);

        }

        public Motif[] newArray (int size){
            return new Motif[size];
        }

    };

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "Motif [code=" + code + ", libelle=" + libelle + "]";
    }

}
