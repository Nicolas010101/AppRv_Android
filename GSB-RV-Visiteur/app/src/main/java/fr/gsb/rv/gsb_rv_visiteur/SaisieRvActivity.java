package fr.gsb.rv.gsb_rv_visiteur;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import fr.gsb.rv.entites.DateFr;
import fr.gsb.rv.entites.Medicament;
import fr.gsb.rv.entites.Motif;
import fr.gsb.rv.entites.Praticien;
import fr.gsb.rv.entites.Rapport_Visite;
import fr.gsb.rv.technique.Session;
import fr.gsb.rv.technique.Url;

/**
 * Created by echowin on 27/03/18.
 */

public class SaisieRvActivity extends AppCompatActivity {


    private Context mContext;
    private Activity mActivity;

    private RelativeLayout mRelativeLayout;
    private PopupWindow mPopupWindow;


    TextView tvDateSelection;
    EditText etBilan;
    Spinner spPraticien;
    Spinner spMotif;
    Spinner spCoefConf;
    ArrayList<Praticien> lesPraticiens = new ArrayList<Praticien>();
    ArrayList<Motif> lesMotifs = new ArrayList<Motif>();
    List<String> lesPraticiensString = new ArrayList<String>();
    List<String> lesMotifsString = new ArrayList<String>();
    Button btnValider;
    TextView echantillonsIsSelected;
    TextView echantillonsSelected;
    String listEchantJson = null;

    DatePickerDialog.OnDateSetListener ecouteurDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            String dateSelection = String.format( "%02d/%02d/%04d" ,
                    dayOfMonth ,
                    month + 1,
                    year
            );

            tvDateSelection.setText( dateSelection );

            DateFr laDatedateSelection = new DateFr( year, month, dayOfMonth );
        }
    };

    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        //PAGE 4 du cours
        setContentView( R.layout.activity_saisie_rv);

        // Get the application context
        mContext = getApplicationContext();

        // Get the activity
        mActivity = SaisieRvActivity.this;

        // Get the widgets reference from XML layout
        mRelativeLayout = (RelativeLayout) findViewById(R.id.activity_saisie_rv);

        tvDateSelection = (TextView) findViewById(R.id.tvDateSelection);
        etBilan = (EditText) findViewById(R.id.etBilan);
        btnValider = (Button) findViewById(R.id.btnValider);

        //On désactive le bouton valider qui serat réactivé lorsque on sera revenu du choix des échantillons
        btnValider.setEnabled(false);

        //Date selection initilisée aujourd'hui
        DateFr dateJour = new DateFr();
        tvDateSelection.setText(dateJour.toString());


        //Récupération des paquets contenant les motifs et les praticiens
        Bundle paquet = this.getIntent().getExtras();
        lesPraticiens = paquet.getParcelableArrayList( "lesPraticiens" );
        lesMotifs = paquet.getParcelableArrayList( "lesMotifs" );


        //Récupération du Spinner déclaré dans le fichier main.xml de res/layout
        spPraticien = (Spinner) findViewById(R.id.spPraticien);
        spMotif = (Spinner) findViewById(R.id.spMotif);
        spCoefConf = (Spinner) findViewById(R.id.spCoefConf);

        //Création d'une liste d'élément à mettre dans le Spinner(pour l'exemple)
        List<String> coefConf = new ArrayList<String>();
        for( int i = 0 ; i <= 3 ; i++ ){

            String leCoeff = new String();
            switch ( i ){
                case 0:  leCoeff = " " + i + "   (Aucune confiance)";
                    break;
                case 1:  leCoeff = " " + i + "   (Confiance médiocre)";
                    break;
                case 2:  leCoeff = " " + i + "   (Moyennement confiance)";
                    break;
                case 3:  leCoeff = " " + i + "   (Parfaitement confiance)";
                    break;
            }
            coefConf.add( leCoeff ) ;
        }


        //Liste des praticiens
        for(Praticien lePraticien : lesPraticiens ){
            lesPraticiensString.add(" " + lePraticien.getNom() + " " + lePraticien.getPrenom()
                    + "  (" + lePraticien.getNum() + ")");
        }


        //Liste des motifs
        for(Motif leMotif : lesMotifs ){
            lesMotifsString.add(" " + leMotif.getLibelle());
        }


		/*Le Spinner a besoin d'un adapter pour sa presentation alors on lui passe le context/l'activité(this) et
                un fichier de presentation par défaut( android.R.layout.simple_spinner_item)
		Avec la liste des elements (exemple) */
        ArrayAdapter adapterCoefConf = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                coefConf
        );

        ArrayAdapter adapterPraticiens = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                lesPraticiensString
        );

        ArrayAdapter adapterMotifs = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                lesMotifsString
        );


        /* On definit une présentation du spinner quand il est déroulé  (android.R.layout.simple_spinner_dropdown_item) */
        //adapterMois.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterCoefConf.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterPraticiens.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterMotifs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        //Enfin on passe l'adapter au Spinner et c'est tout
        //spinnerMois.setAdapter(adapterMois);
        spCoefConf.setAdapter(adapterCoefConf);
        spPraticien.setAdapter(adapterPraticiens);
        spMotif.setAdapter(adapterMotifs);

        //ACTIVER OU DESACTIVER UN EDIT TEXT
        //EditText.setFocusable(false);
        //EditText.setFocusableInTouchMode(true);

    }

    public void selectionnerDate(View vue){

        DateFr dateJour = new DateFr(""+tvDateSelection.getText());

        int jour = dateJour.getJour();
        int mois = dateJour.getMois() -1;
        int annee = dateJour.getAnnee();

        DatePickerDialog picker = new DatePickerDialog( this , ecouteurDate , annee , mois , jour );
        picker.show();

    }

    protected void initialiserActivity(){

        tvDateSelection.setText(new DateFr().toString());
        etBilan.setText("");
        spCoefConf.setSelection(0);
        spMotif.setSelection(0);
        spPraticien.setSelection(0);
        listEchantJson = null;
        btnValider.setEnabled(false);

        int color = ContextCompat.getColor(this, R.color.colorAccent2);

        //Modification du message de saisie des echantillons
        echantillonsIsSelected = (TextView) findViewById(R.id.echantillonsIsSelected);
        echantillonsIsSelected.setText(" Vous n\'avez pas encore séléctionné d\'échantillon ! ");
        echantillonsIsSelected.setTextColor( color );

        //Modification du message indiquant pourquoi le visiteur ne peut pas valider
        echantillonsSelected = (TextView) findViewById(R.id.echantillonsSelected);
        echantillonsSelected.setText("Passez voir les échantillons");
        echantillonsSelected.setTextColor( color );

    }

    public void annuler(View vue){

        this.initialiserActivity();

    }

    public void valider(View vue){

        String matVisiteur = Session.getSession().getLeVisiteur().getMatricule();

        int praticienPosition = spPraticien.getSelectedItemPosition();
        String praticienCode = "" + lesPraticiens.get( praticienPosition ).getNum();

        String bilan = "" + etBilan.getText();

        DateFr laDate = new DateFr("" + tvDateSelection.getText());
        String maDate = "";
        if (laDate.getJour() < 10 && laDate.getMois() < 10){
            maDate = laDate.getAnnee() + "-0" + laDate.getMois() + "-0" + laDate.getJour();
        }
        else if (laDate.getJour() < 10){
            maDate = laDate.getAnnee() + "-" + laDate.getMois() + "-0" + laDate.getJour();
        }
        else if (laDate.getMois() < 10){
            maDate = laDate.getAnnee() + "-0" + laDate.getMois() + "-" + laDate.getJour();
        }
        else {
            maDate = laDate.getAnnee() + "-" + laDate.getMois() + "-" + laDate.getJour();
        }

        int positionMotif = spMotif.getSelectedItemPosition();
        String motif = "" + lesMotifs.get( positionMotif ).getCode();

        int coeffConfiance = spCoefConf.getSelectedItemPosition();


        //Création du rapport a envoyer/enregistrer : son numéro sera donné par les SGBD
        // et "vu/lu" sera automatiquement initialisé à "non"
        final Rapport_Visite monRapportToSend = new Rapport_Visite(
                matVisiteur,
                //num du dernier rapport plus 1 pour que le nouveau rapport respecte la contrainte de clé primaire
                Session.getSession().getLeVisiteur().getNumDernierRv() + 1,
                praticienCode,
                bilan,
                maDate,
                motif,
                coeffConfiance
        );

        //tvDateSelection.setText(monRapportToSend.toString());



        //RECUPERER NUM RAPPORT APRES INSERTION POUR PROCHAINE ACTIVITEE

        //création de la "fabrique" gson
        GsonBuilder fabrique = new GsonBuilder();
        //disableHtmlEscaping evite l'encodage des caractères au format HTML
        //final Gson gson = fabrique.create();
        final Gson gson = fabrique.disableHtmlEscaping().create();

        //Création url
        String url = "http://"+Url.getUrl().getLurl()+":5000/addrv";


        //Démarrage de la requête

        try {
            StringRequest requete = new StringRequest(
                    Request.Method.POST,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i( "APP_RV" , response );
                            //ON INCREMENTE MANUELLEMENT LE DERNIER RV en CAS DE SAISIES CONSECUTIVES
                            Session.getSession().getLeVisiteur().setNumDernierRv(Session.getSession().getLeVisiteur().getNumDernierRv() + 1);
                            SaisieRvActivity.this.initialiserActivity();


                            //POPUP
                            // Initialize a new instance of LayoutInflater service
                            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                            // Inflate the custom layout/view
                            View customView = inflater.inflate(R.layout.popup_saisie_rv_ok,null);

                            /*
                                public PopupWindow (View contentView, int width, int height)
                                    Create a new non focusable popup window which can display the contentView.
                                    The dimension of the window must be passed to this constructor.

                                    The popup does not provide any background. This should be handled by
                                    the content view.

                                Parameters
                                    contentView : the popup's content
                                    width : the popup's width
                                    height : the popup's height
                            */
                                        // Initialize a new instance of popup window
                                        mPopupWindow = new PopupWindow(
                                                customView,
                                                RelativeLayout.LayoutParams.WRAP_CONTENT,
                                                RelativeLayout.LayoutParams.WRAP_CONTENT
                                        );

                                        // Set an elevation value for popup window
                                        // Call requires API level 21
                                        if(Build.VERSION.SDK_INT>=21){
                                            mPopupWindow.setElevation(5.0f);
                                        }

                                        // Get a reference for the custom view close button
                                        ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);

                                        // Set a click listener for the popup window close button
                                        closeButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                // Dismiss the popup window
                                                mPopupWindow.dismiss();

                                                //RETOUR A FERMETURE DE CETTE ACTIVITE
                                                Intent intentionMenu = new Intent( SaisieRvActivity.this, menuRvActivity.class);
                                                startActivity(intentionMenu);
                                            }
                                        });

                            /*
                                public void showAtLocation (View parent, int gravity, int x, int y)
                                    Display the content view in a popup window at the specified location. If the
                                    popup window cannot fit on screen, it will be clipped.
                                    Learn WindowManager.LayoutParams for more information on how gravity and the x
                                    and y parameters are related. Specifying a gravity of NO_GRAVITY is similar
                                    to specifying Gravity.LEFT | Gravity.TOP.

                                Parameters
                                    parent : a parent view to get the getWindowToken() token from
                                    gravity : the gravity which controls the placement of the popup window
                                    x : the popup's x location offset
                                    y : the popup's y location offset
                            */
                                        // Finally, show the popup window at the center location of root relative layout
                                        mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER,0,0);

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e( "APP_RV" , ""+error.getMessage() );
                        }
                    }
            )
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parametres = new HashMap<String, String>();
                    parametres.put( "rv" , gson.toJson( monRapportToSend ));
                    if( listEchantJson != null ){
                        parametres.put( "echantillons" , listEchantJson );
                        System.out.println("_________TEST POST__________\n"
                                + listEchantJson);
                    }
                    return parametres;
                }
            };

            RequestQueue fileAttente = Volley.newRequestQueue( this );

            fileAttente.add(requete);
        }
        catch ( Exception e ){
            Log.e( "APP-RV" , e.getMessage() );
        }


    }

    public void selectionnerEchant(View vue){

        //réinitialisation des échantillons à vide si reclic sur le bouton
        listEchantJson = null;

        final ArrayList<Medicament> lesMedocs = new ArrayList<Medicament>();

        //creation de la route
        String url = "http://"+ Url.getUrl().getLurl()+":5000/medicaments" ;

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
                                response.getJSONObject(i).getDouble("prixEchantillon")
                        );

                        //Ajout du rapport à la liste de rapports
                        if(leMedoc != null){
                            lesMedocs.add(leMedoc);
                        }
                    }

                    //Nous allons ici démarer l'activité qui attend un résultat
                    //Sans le passage par cette activité l'utilisateur ne pourrat valider

                    //BUNDLE
                    Bundle paquet = new Bundle();
                    paquet.putParcelableArrayList("lesMedocs", lesMedocs);
                    //System.out.println(lesMedocs);

                    Intent intentionEnvoyer = new Intent( SaisieRvActivity.this, SaisieEchantActivity.class );

                    //Passation du bundle
                    intentionEnvoyer.putExtras( paquet );


                    startActivityForResult( intentionEnvoyer , 15 );

                }
                catch(JSONException e) {
                    Log.e("APP-RV", "Erreur JSON : " + e.getMessage());
                }

            }
        };

        Response.ErrorListener ecouteurErreur = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e( "APP-RV" , "Erreur HTTP : " + error.getMessage());

            }
        };

        //ENVOIE DE LA REQUETE POUR RECUPERER LA LISTE
        JsonArrayRequest requette = new JsonArrayRequest(Request.Method.GET , url , null ,
                ecouteurReponse , ecouteurErreur );

        RequestQueue fileAttente = Volley.newRequestQueue( this );
        fileAttente.add( requette );


    }

    //Méthode qui vérifie le retour de l'activité démarrée :
    protected void onActivityResult( int requestCode , int resultCode , Intent data ){

        super.onActivityResult( requestCode , resultCode , data );

        //Si on récupère bien le code envoyé
        if(requestCode == 15){
            //si l'activité nous à bien répondue
            if(resultCode == RESULT_OK ){

                //Reactivation bouton valider
                btnValider.setEnabled(true);

                //Modification du message de saisie des echantillons
                echantillonsIsSelected = (TextView) findViewById(R.id.echantillonsIsSelected);
                echantillonsIsSelected.setText("echantillons enregistrés");
                int color = ContextCompat.getColor(this, R.color.colorPrimary);
                echantillonsIsSelected.setTextColor( color );

                //Suppression du message indiquant pourquoi le visiteur ne peut pas valider
                echantillonsSelected = (TextView) findViewById(R.id.echantillonsSelected);
                echantillonsSelected.setText("");

                //Récupération des échantillons s'il y en a
                if(data.getExtras().getString("listEchantJson") != null){
                    Bundle paquet = data.getExtras();
                    listEchantJson = paquet.getString("listEchantJson");
                    System.out.println("_________MES ECHANTILLONS JSON : saisiRv__________\n"
                                            + listEchantJson);
                }


            }

        }

    }

}
