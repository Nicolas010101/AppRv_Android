package fr.gsb.rv.entites;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by echowin on 22/03/18.
 */

public class DateRv implements Parcelable {
    private int jour;
    private int mois;
    private int annee;

    public DateRv(int jour, int mois, int annee) {
        this.jour = jour;
        this.mois = mois;
        this.annee = annee;
    }

    public DateRv(int mois, int annee) {
        this.mois = mois;
        this.annee = annee;
    }

    public int getJour() {
        return jour;
    }

    public void setJour(int jour) {
        this.jour = jour;
    }

    public int getMois() {
        return mois;
    }

    public void setMois(int mois) {
        this.mois = mois;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    //Methodes héritées de la classe parcelable pour "parcer" des objets dans les paquets bundle
    //envoyés dans les intentions
    @Override
    public int describeContents() {
        return 0;
    }

    //Permet de parser les attributs de l'objets : ECRITURE
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt( this.jour );
        dest.writeInt( this.mois );
        dest.writeInt( this.annee );
    }

    //Permets de récupérer les attributs de l'objet parsé : LECTURE
    public DateRv( Parcel in ){
        this.jour = in.readInt();
        this.mois = in.readInt();
        this.annee = in.readInt();
    }

    public static final Parcelable.Creator<DateRv> CREATOR = new Parcelable.Creator<DateRv>(){

        @Override
        public DateRv createFromParcel( Parcel source ){
            return new DateRv( source );
        }

        @Override
        public DateRv[] newArray( int size ){
            return new DateRv[ size ];
        }

    };

    @Override
    public String toString() {
        return "DateRv{" +
                "jour=" + jour +
                ", mois=" + mois +
                ", annee=" + annee +
                '}';
    }
}