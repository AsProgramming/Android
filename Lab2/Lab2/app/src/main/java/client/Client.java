package client;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import com.example.a0704335.lab2.AjoutClient;
import com.example.a0704335.lab2.MainActivity;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Client implements Serializable {

    public enum GENRE{
        HOMME("Homme"),
        FEMME("Femme"),
        AUTRE("Autre");

        private String valeur;
        private GENRE(String indice){
            valeur = indice;
        }

        @Override
        public String toString(){
            return valeur;
        }
    }

    private String prenom;
    private String nom;
    private int age;
    private GENRE sexe;
    private String address;
    private String ville;
    private String email;

    public Client(String prenom,String nom, GENRE sexe, int age,
                  String address, String ville, String email){
        setPrenom(prenom);
        setNom(nom);
        this.sexe = sexe;
        this.age = age;
        this.address = address;
        this.ville = ville;
        this.email = email;
    }

    public Client(String nom, int age, String ville){
        separer(nom);
        this.age = age;
        this.ville = ville;
    }

    public Client(String nom){
        separer(nom);
    }

    private void separer(String nom) {
        String[] aSeparer = new String[2];
        aSeparer = nom.split(" ");
        this.prenom = aSeparer[0];
        this.nom = aSeparer[1];
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public String getNomComplet(){
        return getPrenom()+" "+getNom();
    }

    public void setNom(String nom) { this.nom = nom; }

    public String getAge() {
        return Integer.toString(age);
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSexe(){
        return sexe.toString();
    }

    public String[] getAddressSplit() {
        String[] tmp = getAddress().split(" ");
        return tmp;
    }

    public void setSexe(GENRE genre) {
        sexe = genre;
    }

    public static GENRE trouverGenre(String genre){
        switch(genre){
            case "Femme":
                return GENRE.FEMME;
            case "Autre":
                return GENRE.AUTRE;
            default:
                return GENRE.HOMME;
        }
    }
}
