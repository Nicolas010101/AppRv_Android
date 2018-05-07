package fr.gsb.rv.gsb_rv_visiteur;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.gsb.rv.entites.DateFr;
import fr.gsb.rv.entites.DateRv;

/**
 * Created by echowin on 13/12/17.
 */

public class RechercheRvActivity extends AppCompatActivity {

    Spinner spinnerMois ;
    Spinner spinnerAnnee ;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recherche_rv);

        //Récupération du Spinner déclaré dans le fichier main.xml de res/layout
        spinnerMois = (Spinner) findViewById(R.id.spinnerMois);
        spinnerAnnee = (Spinner) findViewById(R.id.spinnerAnnee);

        //Création d'une liste d'élément à mettre dans le Spinner(pour l'exemple)
        List<String> mois = new ArrayList<String>();
        for( int i = 1 ; i <= 12 ; i++ ){
            mois.add( "" + i ) ;
        }

        List<String> annee = new ArrayList<String>();
        int anneeActuelle = new DateFr().getAnnee();
        for( int i = anneeActuelle ; i >= 2000 ; i-- ){
            annee.add( "" + i ) ;
        }




		/*Le Spinner a besoin d'un adapter pour sa presentation alors on lui passe le context/l'activité(this) et
                un fichier de presentation par défaut( android.R.layout.simple_spinner_item)
		Avec la liste des elements (exemple) */
        ArrayAdapter adapterMois = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                mois
        );

        ArrayAdapter adapterAnnee = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                annee
        );


        /* On definit une présentation du spinner quand il est déroulé  (android.R.layout.simple_spinner_dropdown_item) */
        adapterMois.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterAnnee.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        //Enfin on passe l'adapter au Spinner et c'est tout
        spinnerMois.setAdapter(adapterMois);
        spinnerAnnee.setAdapter(adapterAnnee);

    }


    public void rechercheRvDate(View vue){

        //Récupération des valeurs sélectionnées
        int jour = 0;
        int mois = Integer.parseInt((String) spinnerMois.getSelectedItem());
        int annee = Integer.parseInt((String) spinnerAnnee.getSelectedItem()) ;

        //Création d'un objet DateRv avec le mois et l'année récupérée
        DateRv maDate = new DateRv(jour, mois, annee);

        //Création d'un paquet pour envoyer la date qui sera parcée (voir implémentation de la classe DateRv)
        Bundle paquet = new Bundle();
        paquet.putParcelable( "maDate", maDate );

        //Intentention envoyé à la prochaine activité avec le paquet
        Intent intentRechercheRvDate = new Intent(this, ListeRvActivity.class);
        intentRechercheRvDate.putExtras( paquet );

        //Démarrage de la nouvelle activité
        startActivity(intentRechercheRvDate);


    }




}
