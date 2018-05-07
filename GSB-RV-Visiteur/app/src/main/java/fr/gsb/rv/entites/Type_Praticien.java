package fr.gsb.rv.entites;

/**
 * Created by echowin on 15/03/18.
 */

public class Type_Praticien {

    private String code;
    private String libelle;
    private String lieu;

    public Type_Praticien(String code, String libelle, String lieu) {
        this.code = code;
        this.libelle = libelle;
        this.lieu = lieu;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    @Override
    public String toString() {
        return "Type_Praticien{" +
                "code='" + code + '\'' +
                ", libelle='" + libelle + '\'' +
                ", lieu='" + lieu + '\'' +
                '}';
    }
}
