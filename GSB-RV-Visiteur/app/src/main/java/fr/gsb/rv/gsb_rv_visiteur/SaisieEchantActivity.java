package fr.gsb.rv.gsb_rv_visiteur;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import fr.gsb.rv.entites.Echantillons;
import fr.gsb.rv.entites.Medicament;
import fr.gsb.rv.entites.Rapport_Visite;
import fr.gsb.rv.technique.Session;

/**
 * Created by echowin on 05/04/18.
 */

public class SaisieEchantActivity extends AppCompatActivity{

    Button btnSelectEchant;
    String listEchantJson;
    //liste des medocs affichés
    ArrayList<Medicament> lesMedocs = new ArrayList<Medicament>();
    //liste des échantillons
    ArrayList<Echantillons> lesEchantillons = new ArrayList<Echantillons>();
    //liste des échantillons à enregistrer (quantite > 0)
    ArrayList<Echantillons> lesEchantillonsSelect = new ArrayList<Echantillons>();
    Echantillons lEchantillon;
    ListView lvEchantillons;
    TextView tvEchantillonSelection;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //PAGE 4 du cours
        setContentView(R.layout.activity_saisie_echant);

        btnSelectEchant = (Button) findViewById(R.id.btnSelectEchant);
        lvEchantillons = (ListView) findViewById(R.id.lvEchantillons);
        tvEchantillonSelection = (TextView) findViewById(R.id.tvEchantillonSelection);

        //recupérations du paquets contenant la liste des médicaments
        Bundle paquet = this.getIntent().getExtras();
        lesMedocs = paquet.getParcelableArrayList("lesMedocs");
        //System.out.println("Vue SAISIE ECHANTILLON \n"+lesMedocs);

        //On entre tous les médicaments dans une liste d'échantillons
        for(Medicament unMedicament : lesMedocs){

            //on crée un échantillon
            lEchantillon = new Echantillons(
                    unMedicament.getDepotLegal(),
                    Session.getSession().getLeVisiteur().getMatricule(),
                    //rv ajouté = dernier rv du visiteur + 1
                    Session.getSession().getLeVisiteur().getNumDernierRv() + 1,
                    //qte [0;20] => position [0;20]
                    0
            );

            //on l'entre dans la liste d'echantillons
            lesEchantillons.add(lEchantillon);

        }

        //Création de la liste cliquable grâce à la classe interne ItemAdaptateur créée en dessous
        ItemAdaptateur adaptateur = new ItemAdaptateur( this );
        lvEchantillons.setAdapter( adaptateur );


    }

    private class ItemAdaptateur extends ArrayAdapter<Medicament>{

        ItemAdaptateur( Activity contexte ){
            super( contexte , R.layout.item_saisie_echant , lesMedocs );
        }

        //affichage/selection des lignes
        @Override
        public View getView(final int position , View convertView , ViewGroup parent ){
            View ligneItem = convertView;

            //liste par défaut si ligne vide
            if (ligneItem == null){
                LayoutInflater convertisseur = getLayoutInflater();
                ligneItem = convertisseur.inflate( R.layout.item_saisie_echant , parent , false );
            }

            //Medicament mis dans le textView
            TextView tvNomMedicament = (TextView) ligneItem.findViewById( R.id.tvNomMedicament );
            tvNomMedicament.setText(lesMedocs.get( position ).getNomCommercial());

            //Liste des quantites
            List<String> quantites = new ArrayList<String>();
            for (int i = 0 ; i <= 20 ; i++){
                quantites.add(""+i);
            }

            //Liste déroulante des quantités attribué au médicament
            Spinner spQuantite = (Spinner) ligneItem.findViewById( R.id.spQuantite );

            //adaptateur qui sera attribuée a la liste déroulant
            ArrayAdapter<String> arrayAdapterQuantite = new ArrayAdapter<String>(
                    SaisieEchantActivity.this,
                    android.R.layout.simple_spinner_item,
                    quantites
            );

            arrayAdapterQuantite.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            //Passage de l'adaptateur à la liste
            spQuantite.setAdapter( arrayAdapterQuantite );

            //position de la liste sur la quantité disponible du produit
            //int quantiteMax = 5
            //spQuantite.setSelection( quantiteMax );

            //ICI JE FAIS AVEC UNE VALEUR ALEATOIRE (min 0 et max 20)
            /*Random r = new Random();
            int quantiteMax = 0 + r.nextInt(20 - 0);
            spQuantite.setSelection( quantiteMax );*/

            //On mets un tag au spinner avec sa position pour retrouver sa position dans le listView
            spQuantite.setTag( Integer.valueOf( position ) );

            spQuantite.setOnItemSelectedListener(

                    new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int positionQuantite, long id) {
                            //On recupère la position du medicament
                            Integer positionMedicament = (Integer) ((Spinner) view.getParent()).getTag();

                            //On modifie la quantite de l'échantillon à la position séléctionnée
                            //La position de la quantite est ici relative à la quantite
                            // de la position 0 à 20 on à la quantité de 0 à 20
                            lesEchantillons.get( positionMedicament ).setQuantite( positionQuantite );




                            //Affichage des échantions
                            String echantillonsSelect = "";
                            for (Echantillons echantAffiche : lesEchantillons) {
                                //Si la quantite supérieur à 0
                                if(echantAffiche.getQuantite() > 0){

                                    //Récupératin du nom commercial de l'échantillon
                                    String nomMedoc = "";
                                    for(Medicament unM : lesMedocs){
                                        if (unM.getDepotLegal().equals(echantAffiche.getDepotLegalMedoc())){
                                            nomMedoc = unM.getNomCommercial();
                                        }
                                    }

                                    echantillonsSelect = echantillonsSelect + echantAffiche.getQuantite()
                                            + " " + nomMedoc + "   ;   ";

                                    //Enregistrement des echantillons pris en compte dans la liste à renvoyer
                                    if (!lesEchantillonsSelect.contains(echantAffiche)){
                                        lesEchantillonsSelect.add(echantAffiche);
                                    }
                                    //Sinon modification de celui enregistré
                                    else {
                                        int i = 0;
                                        for(Echantillons unE : lesEchantillonsSelect){
                                            if (unE.getDepotLegalMedoc() == echantAffiche.getDepotLegalMedoc()){

                                                lesEchantillonsSelect.set( i , echantAffiche);

                                                //SI quantite = 0 on le supprime
                                                if (lesEchantillonsSelect.get( i ).getQuantite() == 0){
                                                    lesEchantillonsSelect.remove( i );
                                                }

                                            }
                                            i = i + 1;
                                        }
                                    }
                                    System.out.println("___^.^___" + lesEchantillonsSelect);


                                }

                            }
                            tvEchantillonSelection.setText(echantillonsSelect);


                        }

                        public void onNothingSelected(AdapterView<?> parent){

                        }

                    }

            );

            return ligneItem ;

        }

    }

    public void enregistrer(View view){


        //création de la "fabrique" gson
        GsonBuilder fabrique = new GsonBuilder();
        //disableHtmlEscaping evite l'encodage des caractères au format HTML
        final Gson gson = fabrique.disableHtmlEscaping().create();

        listEchantJson = "[";

        //on cré un variable de vérification du nombre déléments vide
        int nbEchantVide = 0;
        //on parcours les echantillons
        for(Echantillons unEchantillon : lesEchantillons){

            //si la quantite est superieur à 0
            if (unEchantillon.getQuantite() > 0){

                //SI la chaine JSON est encore vide
                if (listEchantJson.equals("[")){

                    //Création de l'échantillon au format JSON
                    listEchantJson = listEchantJson + gson.toJson( unEchantillon );

                }
                //SINON
                else{

                    //Ajout de l'échantillon au format JSON
                    listEchantJson = listEchantJson + ", " + gson.toJson( unEchantillon );

                }


            }
            else {

                //incrémentation de la variable de comptage d'échantillons vide
                nbEchantVide += 1;

            }


        }

        listEchantJson = listEchantJson + "]";

        //Si on ne posssède pas d'échantillons
        if (lesEchantillonsSelect.size() == nbEchantVide){
            listEchantJson = null ;
        }


        System.out.println("_________MES ECHANTILLONS JSON : saisiEchant__________\n"
                + listEchantJson);

        Bundle paquet = new Bundle();
        paquet.putString( "listEchantJson" , listEchantJson );

        //Retour à la vue précédante
        Intent intentionRetour = new Intent( );
        intentionRetour.putExtras( paquet );

        //Retour sur l'activité précédente avec la réponse
        setResult( RESULT_OK , intentionRetour );
        finish();

    }


}
