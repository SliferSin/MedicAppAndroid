package alberto.medicconsultapp;

/**
 * Created by Ashto on 24/02/2017.
 */

public class Usuario {
    private String nom;
    private String cognom;
    private String edat;

    public Usuario(String nom,String cognom,String edat){
        this.nom = nom;
        this.cognom = cognom;
        this.edat = edat;
    }

    public String getNom(){
        return this.nom;
    }
    public void setNom(String nom){
        this.nom = nom;
    }
    public String getCognom(){
        return this.cognom;
    }
}
