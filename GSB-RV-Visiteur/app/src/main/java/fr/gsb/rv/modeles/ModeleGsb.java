package fr.gsb.rv.modeles;

import android.app.DownloadManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import fr.gsb.rv.entites.Rapport_Visite;
import fr.gsb.rv.entites.Visiteur;
import fr.gsb.rv.gsb_rv_visiteur.MainActivity;
import fr.gsb.rv.technique.Session;

public class
ModeleGsb {

	private static ModeleGsb modele = null ;
    //private static MainActivity mainActivity;


    private List<Visiteur> lesVisiteurs = new ArrayList<Visiteur>() ;
	
	private ModeleGsb(){
		super() ;
		this.peupler() ;
	}
	
	public static ModeleGsb getInstance(){
		if( ModeleGsb.modele == null ){
			modele = new ModeleGsb() ;
		}
		return ModeleGsb.modele ;
	}



	
	private void peupler(){
		this.lesVisiteurs.add( new Visiteur("a131","azerty","Villechalane","Louis",0) ) ;
		this.lesVisiteurs.add( new Visiteur("b13","azerty","Bentot","Pascal",1) ) ;
		this.lesVisiteurs.add( new Visiteur("b16","azerty","Bioret","Luc",2) ) ;
		this.lesVisiteurs.add( new Visiteur("a17","azerty","Andre","David",3) ) ;
	}




}
