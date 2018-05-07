package fr.gsb.rv.gsb_rv_visiteur;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import fr.gsb.rv.entites.DateFr;
import fr.gsb.rv.entites.DateRv;
import fr.gsb.rv.entites.Rapport_Visite;
import fr.gsb.rv.technique.Url;

/**
 * Created by echowin on 13/12/17.
 */

public class ListeRvActivity extends AppCompatActivity {

    TextView tvRvSelection;
    ListView lvRapportsVisite;
    int leMois;
    int lAnnee;
    private ArrayList<Rapport_Visite> lesRvSelectionnes = new ArrayList<Rapport_Visite>();
    private ArrayList<String> lesRv = new ArrayList<String>();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_liste_rv);

        //Déclaration des composants utilisés
        tvRvSelection = (TextView) findViewById( R.id.tvRvSelection );
        lvRapportsVisite = (ListView) findViewById( R.id.lvRapportsVisite );

        //Récupération du paquet de l'activité précédente
        Bundle paquet = this.getIntent().getExtras();
        DateRv laDate = paquet.getParcelable( "maDate" );

        leMois = laDate.getMois();
        lAnnee = laDate.getAnnee();

        //Création de la liste qui servira au ListView

            //creation de la route
            String url = "http://"+ Url.getUrl().getLurl()+":5000/rv/" + leMois + "/" + lAnnee ;

            //Création de l'écouteur
            Response.Listener<JSONArray> ecouteurReponse = new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {

                            for (int i = 0; i < response.length(); i++) {
                                Log.i("APP-RV", response.getJSONObject(i).getString("num"));

                                //TRANSFORMATION DATE
                                //recupération de la date
                                String dateBdd = response.getJSONObject(i).getString("date");
                                //Mise en forme de la date
                                int year = Integer.parseInt(dateBdd.substring(0,4));
                                int month = Integer.parseInt(dateBdd.substring(5,7));
                                int day = Integer.parseInt(dateBdd.substring(8,10));
                                DateFr maDate = new DateFr(day, month, year);

                                //Création du rapport
                                Rapport_Visite leRapport = new Rapport_Visite(
                                        response.getJSONObject(i).getString("visiteur")
                                        + "  ( " +
                                        response.getJSONObject(i).getString("visiteurMatricule")
                                        + " )",
                                        response.getJSONObject(i).getInt("num"),
                                        response.getJSONObject(i).getString("praticien")
                                         + "  ( " +
                                        response.getJSONObject(i).getString("praticienNum")
                                        + " )",
                                        response.getJSONObject(i).getString("bilan"),
                                        response.getJSONObject(i).getString("vu"),
                                        maDate,
                                        response.getJSONObject(i).getString("motif"),
                                        response.getJSONObject(i).getInt("coefficientDeConfiance")
                                );


                                //Ajout du rapport à la liste de rapports
                                if(leRapport != null){
                                    lesRvSelectionnes.add(leRapport);
                                }


                            }


                            for (Rapport_Visite unRv : lesRvSelectionnes) {
                                lesRv.add("Rapport n° : " + unRv.getNum()
                                        + " du " + unRv.getDate()
                                    + "\n Visiteur : " + unRv.getVisiteur()
                                    + "\n Motif : " + unRv.getMotif()
                                        + "\n");
                            }


                            //Création d'un adaptateur pour enregistrer le tableau ou la liste dans la ListView
                            ArrayAdapter<String>adaptateur = new ArrayAdapter<String>(
                                    ListeRvActivity.this,
                                    android.R.layout.simple_expandable_list_item_1,
                                    lesRv
                            ) ;

                            //ajout de l'adaptateur dans le ListView
                            lvRapportsVisite.setAdapter( adaptateur );

                            //Action lors du clic sur un rapport de visite
                            lvRapportsVisite.setOnItemClickListener(

                                    new AdapterView.OnItemClickListener() {

                                        @Override
                                        public void onItemClick( AdapterView<?> parent ,
                                                                 View vue ,
                                                                 int position ,
                                                                 long id) {

                                            String rapportVisiteSelectionne = lesRv.get( position ) ;
                                            //Affichage rapport
                                            //tvRvSelection.setText( rapportVisiteSelectionne );


                                            //Création d'un paquet pour envoyer le rapport visite qui sera parcé (voire implémentation de la classe DateRv)
                                            Bundle paquet = new Bundle();
                                            paquet.putParcelable( "monRv", lesRvSelectionnes.get( position ) );


                                            //Pause activité 3secondes
                                            /*try {
                                                Thread.sleep(3000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }*/

                                            //Intentention envoyé à la prochaine activité avec le paquet
                                            Intent intentRV = new Intent(ListeRvActivity.this, VisuRvActivity.class);
                                            intentRV.putExtras( paquet );

                                            //Démarrage de la nouvelle activité
                                            startActivity(intentRV);

                                        }

                                    }

                            );



                    }
                    catch(JSONException e){
                        Log.e( "APP-RV" , "Erreur JSON : " + e.getMessage());

                        lesRv.add(" Aucun rapport de visite pour cette période ");

                        //Création d'un adaptateur pour enregistrer le tableau ou la liste dans la ListView
                        ArrayAdapter<String>adaptateur = new ArrayAdapter<String>(
                                ListeRvActivity.this,
                                android.R.layout.simple_expandable_list_item_1,
                                lesRv
                        ) ;

                        //ajout de l'adaptateur dans le ListView
                        lvRapportsVisite.setAdapter( adaptateur );

                        //Action lors du clic sur un rapport de visite
                        lvRapportsVisite.setOnItemClickListener(

                                new AdapterView.OnItemClickListener() {

                                    @Override
                                    public void onItemClick( AdapterView<?> parent ,
                                                             View vue ,
                                                             int position ,
                                                             long id) {

                                        String rapportVisiteSelectionne = lesRv.get( position ) ;
                                        tvRvSelection.setText( rapportVisiteSelectionne );

                                    }

                                }

                        );
                    }
                }
            };

            Response.ErrorListener ecouteurErreur = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e( "APP-RV" , "Erreur HTTP : " + error.getMessage());

                    lesRv.add(" Serveur injoignable ");

                    //Création d'un adaptateur pour enregistrer le tableau ou la liste dans la ListView
                    ArrayAdapter<String>adaptateur = new ArrayAdapter<String>(
                            ListeRvActivity.this,
                            android.R.layout.simple_expandable_list_item_1,
                            lesRv
                    ) ;

                    //ajout de l'adaptateur dans le ListView
                    lvRapportsVisite.setAdapter( adaptateur );

                    //Action lors du clic sur un rapport de visite
                    lvRapportsVisite.setOnItemClickListener(

                            new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick( AdapterView<?> parent ,
                                                         View vue ,
                                                         int position ,
                                                         long id) {

                                    String rapportVisiteSelectionne = lesRv.get( position ) ;
                                    tvRvSelection.setText( rapportVisiteSelectionne );

                                }

                            }

                    );
                }
            };


        //ENVOIE DE LA REQUETE POUR RECUPERER LA LISTE
        JsonArrayRequest requette = new JsonArrayRequest(Request.Method.GET , url , null ,
                ecouteurReponse , ecouteurErreur );

        RequestQueue fileAttente = Volley.newRequestQueue( this );
        fileAttente.add( requette );



    }


}
