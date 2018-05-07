package fr.gsb.rv.entites;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class Visiteur implements Parcelable{

	private String matricule ;
	private String nom ;
	private String prenom ;
	private String adresse ;
	private String codePostal ;
	private String ville ;
	private Date dateEmbauche;
	private String secteurCode;
	private String laboratoireCode;
	private String mdp ;
	//POUR RECUPERER LE DERNIER RV DANS LA SESSION POUR REALISER L'AJOUT
	private int numDernierRv;

	public Visiteur() {
		this.matricule = null;
		this.nom = null;
		this.prenom = null;
		this.adresse = null;
		this.codePostal = null;
		this.ville = null;
		this.dateEmbauche = null;
		this.secteurCode = null;
		this.laboratoireCode = null;
		this.mdp = null;
	}
	
	public Visiteur(String matricule, String mdp, String nom, String prenom, int numDernierRv) {
		super();
		this.matricule = matricule;
		this.mdp = mdp ;
		this.nom = nom;
		this.prenom = prenom;
		this.numDernierRv = numDernierRv;
	}

	public Visiteur(String matricule, String nom, String prenom, String adresse, String codePostal, String ville, Date dateEmbauche, String secteurCode, String laboratoireCode, String mdp) {
		this.matricule = matricule;
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.codePostal = codePostal;
		this.ville = ville;
		this.dateEmbauche = dateEmbauche;
		this.secteurCode = secteurCode;
		this.laboratoireCode = laboratoireCode;
		this.mdp = mdp;
	}

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
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

	public Date getDateEmbauche() {
		return dateEmbauche;
	}

	public void setDateEmbauche(Date dateEmbauche) {
		this.dateEmbauche = dateEmbauche;
	}

	public String getSecteurCode() {
		return secteurCode;
	}

	public void setSecteurCode(String secteurCode) {
		this.secteurCode = secteurCode;
	}

	public String getLaboratoireCode() {
		return laboratoireCode;
	}

	public void setLaboratoireCode(String laboratoireCode) {
		this.laboratoireCode = laboratoireCode;
	}

	public String getMdp() {
		return mdp;
	}

	public int getNumDernierRv() {
		return numDernierRv;
	}

	public void setNumDernierRv(int numDernierRv) {
		this.numDernierRv = numDernierRv;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags){

		dest.writeString(this.matricule);
		dest.writeString(this.mdp);
		dest.writeString(this.nom);
		dest.writeString(this.prenom);
		dest.writeInt(this.numDernierRv);

	}

	public Visiteur (Parcel in){

		this.matricule=in.readString();
		this.mdp=in.readString();
		this.nom=in.readString();
		this.prenom=in.readString();
		this.numDernierRv = in.readInt();

	}


	public static final Parcelable.Creator<Visiteur> CREATOR= new Parcelable.Creator<Visiteur>(){

		public Visiteur createFromParcel (Parcel source){

			return new Visiteur(source);

		}

		public Visiteur[] newArray (int size){
			return new Visiteur[size];
		}

	};

	@Override
	public String toString() {
		return "Visiteur{" +
				"matricule='" + matricule + '\'' +
				", nom='" + nom + '\'' +
				", prenom='" + prenom + '\'' +
				", adresse='" + adresse + '\'' +
				", codePostal='" + codePostal + '\'' +
				", ville='" + ville + '\'' +
				", dateEmbauche=" + dateEmbauche +
				", secteurCode='" + secteurCode + '\'' +
				", laboratoireCode='" + laboratoireCode + '\'' +
				", mdp='" + mdp + '\'' +
				'}';
	}


}
