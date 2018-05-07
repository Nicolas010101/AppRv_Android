package fr.gsb.rv.gsb_rv_visiteur;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import fr.gsb.rv.entites.Visiteur;
import fr.gsb.rv.modeles.ModeleGsb;
import fr.gsb.rv.technique.Session;
import fr.gsb.rv.technique.Url;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "GSB_MAIN_ACTIVITY";
    TextView errorMessage;
    EditText etMatricule;
    EditText etMdp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        errorMessage = (TextView) findViewById(R.id.errorMessage);
        etMatricule = (EditText) findViewById(R.id.etMatricule);
        etMatricule.setText("");
        etMdp = (EditText) findViewById(R.id.etMdp);
        etMdp.setText("");

        //URL DU SERVEUR
        Url.ouvrir("192.168.43.221");
        //Url.ouvrir("192.168.0.44");
    }

    public void seConnecter(View vue){

        final String matricule = etMatricule.getText().toString() ;
        final String mdp = etMdp.getText().toString() ;


        System.out.println( matricule + " " + mdp ) ;

        final Visiteur leVisiteur = new Visiteur();

        try {
            String url = "http://"+Url.getUrl().getLurl()+":5000/seconnecter/" + matricule + "/" + mdp ;
            //System.out.println("tracker1");

            Response.Listener<JSONObject> ecouteurRep = new Response.Listener<JSONObject>(){

                @Override
                public void onResponse(JSONObject reponse) {

                    //System.out.println("tracker2");

                    try {

                        //ENREGISTREMENT DE MON VISITEUR
                        leVisiteur.setMatricule( matricule );
                        leVisiteur.setMdp( mdp );
                        leVisiteur.setNom( reponse.getString( "nom" ) );
                        leVisiteur.setPrenom( reponse.getString( "prenom" ));
                        leVisiteur.setNumDernierRv( reponse.getInt( "numRapport" ) );



                        //System.out.println("tracker3");

                        //Vider la session et mettre le visiteur dedans
                        Session.fermer();
                        Session.ouvrir( leVisiteur ) ;

                        //System.out.println("tracker 4 :" + leVisiteur);
                        //Traitement et renvoie vers la nouvelle activité
                        etMatricule.setText("");
                        etMdp.setText("");

                        /*errorMessage.setText("Bonjour " + Session.getSession().getLeVisiteur().getNom()+" "+Session.getSession().getLeVisiteur().getPrenom()
                                + System.lineSeparator() + System.lineSeparator() + System.lineSeparator());*/

                        //Notification clavier
                        Context context = getApplicationContext();
                        CharSequence text = "Bonjour " + Session.getSession().getLeVisiteur().getNom()+" "+Session.getSession().getLeVisiteur().getPrenom();
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        //System.out.println("tracker 5 :" + leVisiteur);

                        //Nouvelle vue

                        //Création d'un paquet contenant le matricule (chaîne de caractère)
                        Bundle paquet = new Bundle();
                        paquet.putString("matricule",matricule);

                        //Envoie de l'intention et du paquet à la nouvelle vue
                        Intent intentionEnvoyer = new Intent(MainActivity.this, menuRvActivity.class);
                        intentionEnvoyer.putExtras(paquet);

                        //System.out.println("tracker 6 :" + leVisiteur);

                        startActivity(intentionEnvoyer);

                        /*int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(MainActivity.this, "Ok ", duration);
                        toast.show();



                        Intent intentionEnvoyer = new Intent(MainActivity.this, MenuRvActivity.class);



                        startActivity(intentionEnvoyer);*/
                    }
                    catch ( Exception e ){
                        //Sinon on retourne null
                        //return null;
                        System.out.println("tracker - Error");
                        Log.e("APP-RV", "Erreur JSON : " + e.getMessage());

                    }

                }


            };

            //Instanciation de l'ecouteur des erreurs
            Response.ErrorListener ecouteurErr = new Response.ErrorListener(){



                //Méthode redéfinie de la classe Response
                @Override
                public void onErrorResponse( VolleyError error ){

                    //return null;
                    Log.e("APP-RV", "Erreur HTTP : " + error.getMessage());
                    System.out.println("tracker - ERREUR - HTTP");


                    int httpStatusCode = 500;
                    if(error.networkResponse != null){
                        httpStatusCode = error.networkResponse.statusCode;
                    }

                    if(httpStatusCode==404){
                        //Traitement pour informer l'utilisateur qu'il a fait une erreur
                        //etMatricule.setText("");
                        etMdp.setText("");
                        //errorMessageText = "Votre login ou mdp est incorrect";
                        errorMessage.setText("Votre login ou mdp est incorrect"
                                + System.lineSeparator());

                        Context context = getApplicationContext();
                        CharSequence text = "Votre login ou mdp est incorrect";
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                    else if (matricule == "" || mdp == ""){
                        //Traitement pour informer l'utilisateur qu'il a fait une erreur
                        //errorMessageText = "Votre login ou mdp est incorrect";
                        errorMessage.setText("Votre login ou mdp n'est pas renseigné"
                                + System.lineSeparator());

                        Context context = getApplicationContext();
                        CharSequence text = "Votre login ou mdp n'est pas renseigné";
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                    else{
                        //Traitement pour informer l'utilisateur que le serveur est injoignable
                        //errorMessageText = "Votre login ou mdp est incorrect";
                        errorMessage.setText("Le serveur est injoignable"
                                + System.lineSeparator());

                        Context context = getApplicationContext();
                        CharSequence text = "Le serveur est injoignable";
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }

            };

            JsonObjectRequest requete = new JsonObjectRequest
                    (Request.Method.GET, url, null, ecouteurRep, ecouteurErr);


            RequestQueue fileAttente = Volley.newRequestQueue( MainActivity.this ) ;
            fileAttente.add( requete );



        }

        catch ( Exception e ){
            //System.out.println("tracker 4 :" + leVisiteur);
        }
    }


    public void annuler(View vue){

        etMatricule.setText("");
        etMdp.setText("");
        //errorMessageText = "Vous avez annulé la procédure d'authentification";
        errorMessage.setText("Vous avez annulé la procédure d'authentification"
                + System.lineSeparator() + System.lineSeparator() + System.lineSeparator());


        Context context = getApplicationContext();
        CharSequence text = "Vous avez annulé la procédure d'authentification";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

   /* public void envoyer( View vue ){
        String matricule = etMatricule.getText().toString();

        Bundle paquet = new Bundle();
        paquet.putString("matricule",matricule);

        Intent intentionEnvoyer = new Intent(this, MainActivitySecondary.class);
        intentionEnvoyer.putExtras(paquet);

        startActivity(intentionEnvoyer);
    }*/
}
