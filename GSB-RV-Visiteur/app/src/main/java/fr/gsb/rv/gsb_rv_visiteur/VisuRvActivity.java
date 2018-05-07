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
import fr.gsb.rv.entites.Rapport_Visite;

/**
 * Created by echowin on 25/03/18.
 */

public class VisuRvActivity extends AppCompatActivity {

    TextView titre;
    TextView visiteurDesc;
    TextView praticienDesc;
    TextView bilanDesc;
    TextView motifDesc;
    TextView coefConfDesc;
    private Rapport_Visite rvSelectionne;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView( R.layout.activity_visu_rv);

        //Déclaration des composants utilisés
        titre = (TextView) findViewById( R.id.titre );
        visiteurDesc = (TextView) findViewById( R.id.visiteurDesc );
        praticienDesc = (TextView) findViewById( R.id.praticienDesc );
        bilanDesc = (TextView) findViewById( R.id.bilanDesc );
        motifDesc = (TextView) findViewById( R.id.motifDesc );
        coefConfDesc = (TextView) findViewById( R.id.coefConfDesc );

        //Récupération du paquet de l'activité précédente
        Bundle paquet = this.getIntent().getExtras();
        rvSelectionne = paquet.getParcelable( "monRv" );

        //Affichage rv selectionné
        //String rvSelect = rvSelectionne.toString();
        titre.setText( "Rapport n° " + rvSelectionne.getNum() + " du " + rvSelectionne.getDate() );
        visiteurDesc.setText( rvSelectionne.getVisiteur() );
        praticienDesc.setText( rvSelectionne.getPraticien() );
        bilanDesc.setText( rvSelectionne.getBilan() );
        motifDesc.setText( rvSelectionne.getMotif() );
        coefConfDesc.setText( "" + rvSelectionne.getCoefficientDeConfiance() );

    }

    public void voirEchantillon(View vue){

        //Création d'un paquet contenant le matricule (chaîne de caractère)
        Bundle paquet = new Bundle();
        paquet.putInt("numRapport",rvSelectionne.getNum());

        //Envoie de l'intention et du paquet à la nouvelle vue
        Intent intentionEnvoyer = new Intent(VisuRvActivity.this, VisuEchantActivity.class);
        intentionEnvoyer.putExtras(paquet);

        //System.out.println("tracker 6 :" + leVisiteur);

        startActivity(intentionEnvoyer);
    }

}
