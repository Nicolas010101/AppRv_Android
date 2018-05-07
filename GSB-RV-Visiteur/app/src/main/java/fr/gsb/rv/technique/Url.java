package fr.gsb.rv.technique;

/**
 * Created by echowin on 30/03/18.
 */

public class Url {

    private static Url url = null;
    private String lUrl;

    private Url(String lUrl) {
        super();
        System.out.print( "Ouverture Session : " + lUrl );
        this.lUrl = lUrl;
    }

    private Url() {
        super();
        this.lUrl = "192.168.43.137";
    }

    public static void ouvrir(String lUrl) {
        //Visiteur visiteur = ModeleGsb.getInstance().seConnecter(matricule, mdp)
        Url.url = new Url(lUrl);
    }

    public static Url getUrl() {
        return Url.url;
    }

    public static void fermer() {
        Url.url = null;
    }

    public String getLurl() {
        return this.lUrl;
    }

    public void setLurl(String url) {
        this.lUrl = url;
    }
}
