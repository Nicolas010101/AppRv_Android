package fr.gsb.rv.technique;

import fr.gsb.rv.entites.*;
import fr.gsb.rv.gsb_rv_visiteur.MainActivity;
import fr.gsb.rv.modeles.*;

public class Session {

	private static Session session = null;
	private Visiteur leVisiteur;

	private Session(Visiteur leVisiteur) {
		super();
		System.out.print( "Ouverture Session : " + leVisiteur );
		this.leVisiteur = leVisiteur;
	}

	public static void ouvrir(Visiteur visiteur) {
		//Visiteur visiteur = ModeleGsb.getInstance().seConnecter(matricule, mdp)
		Session.session = new Session(visiteur);
	}

	/*public static boolean ouvrir(MainActivity mainActivity, String matricule, String mdp) {
		//Visiteur visiteur = ModeleGsb.getInstance().seConnecter(matricule, mdp) ;
		Visiteur visiteur = ModeleGsb.getInstance().seConnecter(mainActivity, matricule, mdp);
		System.out.println("tracker 4 :" + visiteur);
		if (visiteur != null) {
			Session.session = new Session(visiteur);
			return true;
		} else {
			return false;
		}
	}*/

	public static Session getSession() {
		return Session.session;
	}

	public static void fermer() {
		Session.session = null;
	}

	public Visiteur getLeVisiteur() {
		return this.leVisiteur;
	}

	public void setLeVisiteur(Visiteur visiteur) {
		this.leVisiteur = visiteur;
		System.out.println("tracker 5 :" + visiteur);
	}



/*
public boolean isOpen(){
	if(this.getLeVisiteur().getMatricule() != null){
		return true;
	}
	else{
		return false;
	}
}
*/




}
