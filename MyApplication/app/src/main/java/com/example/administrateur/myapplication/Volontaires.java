package com.example.administrateur.myapplication;

import java.util.HashMap;
import java.util.Random;

public class Volontaires {

    private HashMap<Integer, String> listeNoms;
    private  int nbTotal;
    private int cle;
    private String nom;

    public Volontaires()
    {
        nbTotal = 17;
        cle = nbTotal;
        init();
    }

    /**
     * Methode qui instantie le hasmhap avec les noms des volontaires du debut
     */
    private void init(){
        listeNoms = new HashMap<>();
        listeNoms.put(0, "Nicolas");
        listeNoms.put(1, "Yaya Kader");
        listeNoms.put(2, "Maximilien");
        listeNoms.put(3, "Olivier");
        listeNoms.put(4, "Justin");
        listeNoms.put(5, "Gabriel");
        listeNoms.put(6, "Ayoub");
        listeNoms.put(7, "Frederic");
        listeNoms.put(8, "Edwin Stanley");
        listeNoms.put(9, "William T");
        listeNoms.put(10, "Jeanpy Ilunga");
        listeNoms.put(11, "Etienne R");
        listeNoms.put(12, "He Sun");
        listeNoms.put(13, "Etienne T");
        listeNoms.put(14, "William R");
        listeNoms.put(15, "Andres Augusto");
        listeNoms.put(16, "Adrian");
    }

    /**
     * Methode qui renvoi le nom du volontaire piger
     * @return nom du volontaire piger
     */
    public String getNom(){
        piger();
        return nom;
    }

    /**
     * Methode qui ajoute le nouveau nom ajouter par le user
     * @param nouveau nom a ajouter
     */
    public void ajouter(String nouveau){
        listeNoms.put(cle, nouveau);
        cle++;
        nbTotal++;
    }

    /**
     * Methode qui pige le nom dans la liste au hasard
     */
    private void piger(){
        if(nbTotal > 0){
            Random r = new Random();
            int piger = r.nextInt(cle);
            nom = listeNoms.get(piger);
            if(nom == null){
                piger();
            }else{
                listeNoms.remove(piger);
                nbTotal--;
            }
        }
    }

    /**
     * Methode qui retourne le nombre de volontaires restant
     * @return
     */
    public int getTotal(){
        return nbTotal;
    }

    /**
     * Methode qui retourne le nom de volontaires en String
     * @return
     */
    public String getTotalText(){
        return String.valueOf(nbTotal);
    }

}
