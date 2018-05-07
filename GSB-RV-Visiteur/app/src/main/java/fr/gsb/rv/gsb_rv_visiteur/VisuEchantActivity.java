package fr.gsb.rv.gsb_rv_visiteur;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;

import fr.gsb.rv.entites.DateFr;
import fr.gsb.rv.entites.DateRv;
import fr.gsb.rv.entites.Medicament;
import fr.gsb.rv.entites.Rapport_Visite;
import fr.gsb.rv.technique.Url;

/**
 * Created by echowin on 25/03/18.
 */

public class VisuEchantActivity extends AppCompatActivity {

    TextView tvEchantillonSelection;
    ListView lvEchantillons;
    int numRapport;
    private ArrayList<Medicament> lesEchantillonsSelectionnes = new ArrayList<Medicament>();
    private ArrayList<String> lesEchantillons = new ArrayList<String>();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_visu_echant);

        //Déclaration des composants utilisés
        tvEchantillonSelection = (TextView) findViewById( R.id.tvEchantillonSelection );
        lvEchantillons = (ListView) findViewById( R.id.lvEchantillons );

        //Récupération du paquet de l'activité précédente
        Bundle paquet = this.getIntent().getExtras();
        int numRapport = paquet.getInt( "numRapport" );

        //Création de la liste qui servira au ListView

        //creation de la route
        String url = "http://"+ Url.getUrl().getLurl()+":5000/echantillons/" + numRapport ;

        //Création de l'écouteur
        Response.Listener<JSONArray> ecouteurReponse = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    for (int i = 0; i < response.length(); i++) {
                        Log.i("APP-RV", response.getJSONObject(i).getString("depotLegal"));


                        //Création de l'échantillon
                        Medicament leMedoc = new Medicament(
                                response.getJSONObject(i).getString("depotLegal"),
                                response.getJSONObject(i).getString("nomCommercial"),
                                response.getJSONObject(i).getString("famille"),
                                response.getJSONObject(i).getString("composition"),
                                response.getJSONObject(i).getString("effets"),
                                response.getJSONObject(i).getString("contreIndications"),
                                response.getJSONObject(i).getDouble("prixEchantillon"),
                                response.getJSONObject(i).getInt("quantite")
                        );


                        //Ajout du rapport à la liste des echantillons
                        if(leMedoc != null){
                            lesEchantillonsSelectionnes.add(leMedoc);
                        }


                    }

                    for (Medicament unMedoc : lesEchantillonsSelectionnes) {
                        lesEchantillons.add(unMedoc.getNomCommercial() + "   quantité : " + unMedoc.getQuantite());
                    }


                    //Création d'un adaptateur pour enregistrer le tableau ou la liste dans la ListView
                    ArrayAdapter<String> adaptateur = new ArrayAdapter<String>(
                            VisuEchantActivity.this,
                            android.R.layout.simple_expandable_list_item_1,
                            lesEchantillons
                    ) ;

                    //ajout de l'adaptateur dans le ListView
                    lvEchantillons.setAdapter( adaptateur );

                    //Action lors du clic sur un echantillon
                    lvEchantillons.setOnItemClickListener(

                            new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick( AdapterView<?> parent ,
                                                         View vue ,
                                                         int position ,
                                                         long id) {

                                    Medicament rapportVisiteSelectionne = lesEchantillonsSelectionnes.get( position ) ;
                                    tvEchantillonSelection.setText(

                                            rapportVisiteSelectionne.getNomCommercial() + " ======> "
                                                    + "\n\n Famille : " + rapportVisiteSelectionne.getFamille()
                                                    + "\n\n Composition : " + rapportVisiteSelectionne.getComposition()
                                                    + "\n\n Effets : " + rapportVisiteSelectionne.getEffets()
                                                    + "\n\n Contre-indications : " + rapportVisiteSelectionne.getContreIndications()
                                                    + "\n\n Prix unitaire : " + rapportVisiteSelectionne.getPrixEchantillon() + "€"
                                    );


                                }

                            }

                    );



                }
                catch(JSONException e){
                    Log.e( "APP-RV" , "Erreur JSON : " + e.getMessage());

                    lesEchantillons.add(" Aucun échantillon pour ce rapport de visite ");

                    //Création d'un adaptateur pour enregistrer le tableau ou la liste dans la ListView
                    ArrayAdapter<String>adaptateur = new ArrayAdapter<String>(
                            VisuEchantActivity.this,
                            android.R.layout.simple_expandable_list_item_1,
                            lesEchantillons
                    ) ;

                    //ajout de l'adaptateur dans le ListView
                    lvEchantillons.setAdapter( adaptateur );

                    //Action lors du clic sur un echantillon
                    lvEchantillons.setOnItemClickListener(

                            new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick( AdapterView<?> parent ,
                                                         View vue ,
                                                         int position ,
                                                         long id) {

                                    /*response.getJSONObject(i).getString("depotLegal"),
                                    response.getJSONObject(i).getString("nomCommercial"),
                                    response.getJSONObject(i).getString("famille"),
                                    response.getJSONObject(i).getString("composition"),
                                    response.getJSONObject(i).getString("effets"),
                                    response.getJSONObject(i).getString("contreIndications"),
                                    response.getJSONObject(i).getDouble("prixEchantillon"),
                                    response.getJSONObject(i).getInt("quantite")*/

                                    String rapportVisiteSelectionne = lesEchantillons.get( position ) ;
                                    tvEchantillonSelection.setText( rapportVisiteSelectionne );

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

                lesEchantillons.add(" Serveur injoignable ");

                //Création d'un adaptateur pour enregistrer le tableau ou la liste dans la ListView
                ArrayAdapter<String>adaptateur = new ArrayAdapter<String>(
                        VisuEchantActivity.this,
                        android.R.layout.simple_expandable_list_item_1,
                        lesEchantillons
                ) ;

                //ajout de l'adaptateur dans le ListView
                lvEchantillons.setAdapter( adaptateur );

                //Action lors du clic sur un echantillon
                lvEchantillons.setOnItemClickListener(

                        new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick( AdapterView<?> parent ,
                                                     View vue ,
                                                     int position ,
                                                     long id) {

                                String rapportVisiteSelectionne = lesEchantillons.get( position ) ;
                                tvEchantillonSelection.setText( rapportVisiteSelectionne );

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
