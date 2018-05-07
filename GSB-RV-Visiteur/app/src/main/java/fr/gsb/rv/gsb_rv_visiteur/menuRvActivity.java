package fr.gsb.rv.gsb_rv_visiteur;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

import fr.gsb.rv.entites.Motif;
import fr.gsb.rv.entites.Praticien;
import fr.gsb.rv.technique.Session;
import fr.gsb.rv.technique.Url;

public class menuRvActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView tvMatricule;
    ArrayList<Praticien> lesPraticiens = new ArrayList<Praticien>();
    ArrayList<Motif> lesMotifs = new ArrayList<Motif>();
    Bundle paquet = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //MENU
        setContentView(R.layout.activity_menu_rv);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        int color = ContextCompat.getColor(this, R.color.colorPrimary);
        fab.setBackgroundColor(color);
        fab.setDrawingCacheBackgroundColor(color);
        fab.setColorFilter(color);
        fab.setRippleColor(color);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Vous êtes déconnecté", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                // Handle the camera action
                Session.fermer();
                //DEMARRER PROCHAINE ACTIVITE AU CLIC SUR LE BOUTON
                Intent intentRetourner = new Intent(menuRvActivity.this, MainActivity.class);
                startActivity(intentRetourner);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_menu_rv);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Nom visiteur dans le menu
        Menu menu = navigationView.getMenu();
        //On recupere la vue du header
        View header = navigationView.getHeaderView(0);

        //TextView contenu dans le header
        TextView nameMenu = (TextView) header.findViewById(R.id.nameInNav) ;
        nameMenu.setText(Session.getSession().getLeVisiteur().getNom()
            + " " + Session.getSession().getLeVisiteur().getPrenom());

        //autre
        tvMatricule = (TextView) findViewById(R.id.tvMatricule);

        if(this.getIntent().getExtras() != null){
            Bundle paquet = this.getIntent().getExtras();
            String matricule = paquet.getString("matricule");
        }


        String nomPrenom = Session.getSession().getLeVisiteur().getNom()+" "+Session.getSession().getLeVisiteur().getPrenom()
                /*+ " ( " + matricule + " )"*/;

        tvMatricule.setText(nomPrenom);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_menu_rv);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        /*getMenuInflater().inflate(R.menu.menu_rv, menu);*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_deconnexion) {

            // Handle the camera action
            Session.fermer();
            //DEMARRER PROCHAINE ACTIVITE AU CLIC SUR LE BOUTON
            Intent intentRetourner = new Intent(this, MainActivity.class);
            startActivity(intentRetourner);

        } else if (id == R.id.nav_saisir) {

            //LES PRATICIENS
            //creation de la route
            String urlPraticien = "http://"+ Url.getUrl().getLurl()+":5000/praticiens";

            //Création de l'écouteur
            Response.Listener<JSONArray> ecouteurReponsePraticien = new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {

                        for (int i = 0; i < response.length(); i++) {
                            Log.i("APP-RV", response.getJSONObject(i).getString("num"));


                            //Création du praticien
                            Praticien lePraticien = new Praticien(
                                    response.getJSONObject(i).getInt("num"),
                                    response.getJSONObject(i).getString("nom"),
                                    response.getJSONObject(i).getString("prenom"),
                                    response.getJSONObject(i).getInt("coefficientNotoriete")
                            );


                            //Ajout du rapport à la liste de rapports
                            if(lePraticien != null){
                                lesPraticiens.add(lePraticien);
                            }


                        }

                        //Création d'un paquet pour envoyer le rapport visite qui sera parcé (voire implémentation de la classe DateRv)
                        paquet.putParcelableArrayList( "lesPraticiens", lesPraticiens);

                        if(lesMotifs != null){
                            for(Motif m : lesMotifs){
                                System.out.println(m.getLibelle() + System.lineSeparator());

                            }

                            paquet.putParcelableArrayList( "lesMotifs", lesMotifs);

                        }

                        //Passage de l'intention à la nouvelle activité et démarrage de l'activité
                        //La requete pour les motifs est exécutée en premier
                        //Et cette requette en deuxieme les deux paquets seront donc envoyés
                        Intent intentionEnvoyer = new Intent(menuRvActivity.this, SaisieRvActivity.class);
                        intentionEnvoyer.putExtras(paquet);
                        startActivity(intentionEnvoyer);



                    }
                    catch(JSONException e){
                        Log.e( "APP-RV" , "Erreur JSON : " + e.getMessage());

                    }
                }
            };

            Response.ErrorListener ecouteurErreurPraticien = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e( "APP-RV" , "Erreur HTTP : " + error.getMessage());

                }
            };




            //LES MOTIFS ET ENVOIE A LA NOUVELLE VUE
            //creation de la route
            String urlMotif = "http://"+Url.getUrl().getLurl()+":5000/motifs";

            //Création de l'écouteur
            Response.Listener<JSONArray> ecouteurReponseMotif = new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {

                        for (int i = 0; i < response.length(); i++) {
                            Log.i("APP-RV", response.getJSONObject(i).getString("code"));


                            //Création du praticien
                            Motif leMotif = new Motif(
                                    response.getJSONObject(i).getInt("code"),
                                    response.getJSONObject(i).getString("libelle")
                            );


                            //Ajout du rapport à la liste de rapports
                            if(leMotif != null){
                                lesMotifs.add(leMotif);
                            }


                        }




                    }
                    catch(JSONException e){
                        Log.e( "APP-RV" , "Erreur JSON : " + e.getMessage());

                    }
                }
            };

            Response.ErrorListener ecouteurErreurMotif = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e( "APP-RV" , "Erreur HTTP : " + error.getMessage());

                }
            };




            //ENVOIE DE LA REQUETE POUR RECUPERER LA LISTE
            JsonArrayRequest requettePraticien = new JsonArrayRequest(Request.Method.GET , urlPraticien , null ,
                    ecouteurReponsePraticien , ecouteurErreurPraticien );

            JsonArrayRequest requetteMotif = new JsonArrayRequest(Request.Method.GET , urlMotif , null ,
                    ecouteurReponseMotif , ecouteurErreurMotif );

            RequestQueue fileAttente = Volley.newRequestQueue( this );


            //LA REQUETTE LA MOINS LOURDE EST EXECUTEE EN PREMIERE!!!
            //IL faut donc faire l'envoie du paquet contenant les resultats des deux requêtes dans la plus lourde
            //OU faire une requête imbriquée!!!!!!!!!!
            fileAttente.add( requettePraticien );
            fileAttente.add( requetteMotif );

        } else if (id == R.id.nav_visu) {

            //DEMARRER PROCHAINE ACTIVITE AU CLIC SUR LE BOUTON
            Intent intentRetourner = new Intent(this, RechercheRvActivity.class);
            startActivity(intentRetourner);

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_menu_rv);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void consultRv(View vue){

        //DEMARRER PROCHAINE ACTIVITE AU CLIC SUR LE BOUTON
        Intent intentRetourner = new Intent(this, RechercheRvActivity.class);
        startActivity(intentRetourner);

    }

    public void SaisirRv(View vue){

        //LES PRATICIENS
        //creation de la route
        String urlPraticien = "http://"+ Url.getUrl().getLurl()+":5000/praticiens";

        //Création de l'écouteur
        Response.Listener<JSONArray> ecouteurReponsePraticien = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    for (int i = 0; i < response.length(); i++) {
                        Log.i("APP-RV", response.getJSONObject(i).getString("num"));


                        //Création du praticien
                        Praticien lePraticien = new Praticien(
                                response.getJSONObject(i).getInt("num"),
                                response.getJSONObject(i).getString("nom"),
                                response.getJSONObject(i).getString("prenom"),
                                response.getJSONObject(i).getInt("coefficientNotoriete")
                        );


                        //Ajout du rapport à la liste de rapports
                        if(lePraticien != null){
                            lesPraticiens.add(lePraticien);
                        }


                    }

                    //Création d'un paquet pour envoyer le rapport visite qui sera parcé (voire implémentation de la classe DateRv)
                    paquet.putParcelableArrayList( "lesPraticiens", lesPraticiens);

                    if(lesMotifs != null){
                        for(Motif m : lesMotifs){
                            System.out.println(m.getLibelle() + System.lineSeparator());

                        }

                        paquet.putParcelableArrayList( "lesMotifs", lesMotifs);

                    }

                    //Passage de l'intention à la nouvelle activité et démarrage de l'activité
                    //La requete pour les motifs est exécutée en premier
                    //Et cette requette en deuxieme les deux paquets seront donc envoyés
                    Intent intentionEnvoyer = new Intent(menuRvActivity.this, SaisieRvActivity.class);
                    intentionEnvoyer.putExtras(paquet);
                    startActivity(intentionEnvoyer);



                }
                catch(JSONException e){
                    Log.e( "APP-RV" , "Erreur JSON : " + e.getMessage());

                }
            }
        };

        Response.ErrorListener ecouteurErreurPraticien = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e( "APP-RV" , "Erreur HTTP : " + error.getMessage());

            }
        };




        //LES MOTIFS ET ENVOIE A LA NOUVELLE VUE
        //creation de la route
        String urlMotif = "http://"+Url.getUrl().getLurl()+":5000/motifs";

        //Création de l'écouteur
        Response.Listener<JSONArray> ecouteurReponseMotif = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    for (int i = 0; i < response.length(); i++) {
                        Log.i("APP-RV", response.getJSONObject(i).getString("code"));


                        //Création du praticien
                        Motif leMotif = new Motif(
                                response.getJSONObject(i).getInt("code"),
                                response.getJSONObject(i).getString("libelle")
                        );


                        //Ajout du rapport à la liste de rapports
                        if(leMotif != null){
                            lesMotifs.add(leMotif);
                        }


                    }




                }
                catch(JSONException e){
                    Log.e( "APP-RV" , "Erreur JSON : " + e.getMessage());

                }
            }
        };

        Response.ErrorListener ecouteurErreurMotif = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e( "APP-RV" , "Erreur HTTP : " + error.getMessage());

            }
        };




        //ENVOIE DE LA REQUETE POUR RECUPERER LA LISTE
        JsonArrayRequest requettePraticien = new JsonArrayRequest(Request.Method.GET , urlPraticien , null ,
                ecouteurReponsePraticien , ecouteurErreurPraticien );

        JsonArrayRequest requetteMotif = new JsonArrayRequest(Request.Method.GET , urlMotif , null ,
                ecouteurReponseMotif , ecouteurErreurMotif );

        RequestQueue fileAttente = Volley.newRequestQueue( this );


        //LA REQUETTE LA MOINS LOURDE EST EXECUTEE EN PREMIERE!!!
        //IL faut donc faire l'envoie du paquet contenant les resultats des deux requêtes dans la plus lourde
        //OU faire une requête imbriquée!!!!!!!!!!
        fileAttente.add( requettePraticien );
        fileAttente.add( requetteMotif );

    }
}
