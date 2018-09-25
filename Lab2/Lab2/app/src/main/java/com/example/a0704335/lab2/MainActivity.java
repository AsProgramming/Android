package com.example.a0704335.lab2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import client.*;
import database.ClientDB;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnClickListener {

    public static final String TAG = "Hello";
    private static final String[] SELECTION = {"Afficher", "Supprimer"};
    private AlertDialog alert;
    private ClientAdapter adap;
    private static ArrayList<Client> liste;
    private ListView lstClient;
    private FloatingActionButton btnAjout;
    private static ClientDB db;
    private int selection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstClient = (ListView) this.findViewById(R.id.main_activity_lstview_clients);
        btnAjout = (FloatingActionButton) findViewById(R.id.main_activity_btn_ajouter);

        db = ClientDB.getInstance(this);
        liste = new ArrayList<Client>();
/*******************

//        Pour faciliter la creation de contacts

        db.ajouterClient(new Client("Jon", "Cain", Client.GENRE.HOMME, 54, "58 Gaspe h6y2l4", "Montreal", "presidentcain@gmail.com"));
         db.ajouterClient(new Client("Joel", "Embiied", Client.GENRE.HOMME, 28, "58 Forest j9j0o0", "Montreal", "joelembiied@gmail.com"));

        db.ajouterClient(new Client("Samanta", "Fox", Client.GENRE.FEMME, 38, "58 berverly h8h8o0", "Montreal", "samfoxy@gmail.com"));
        db.ajouterClient(new Client("Mila", "Melocevich", Client.GENRE.FEMME, 38, "58 berverly h8h8o0", "Montreal", "meosevich@gmail.com"));
        db.ajouterClient(new Client("Megan", "Fox", Client.GENRE.FEMME, 28, "58 berverly h8h8o0", "Montreal", "mfox@gmail.com"));
*/


        liste = db.getClients();

            adap = new ClientAdapter(this, R.layout.layout_client, liste);
            lstClient.setAdapter(adap);
            creerDialog();


            lstClient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selection = position;
                    alert.show();
                }
            });

            btnAjout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent ajouter = new Intent(MainActivity.this, AjoutClient.class);
                    ajouter.putExtras(ajoutObjet(false));
                    startActivity(ajouter);
                }
            });
    }

    /**
     * Methode qui cree le popup
     */
    private void creerDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Options");
        builder.setItems(SELECTION,this);
        builder.setNegativeButton("Annuler",null);
        alert = builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int position) {
        montrerEcran(position);
    }

    /**
     * Methode qui montre le popup dans lecran
     * @param position
     */
    private void montrerEcran(int position) {
        switch (position) {
            case 1:
                db.supprimerClient(liste.get(selection).getNomComplet());
                avertissementMain();
                break;
            default:
                Intent afficher = new Intent(MainActivity.this, AjoutClient.class);
                afficher.putExtras(ajoutObjet(true));
                startActivity(afficher);
                break;
        }
    }

    /**
     * Methode qui cree le toast lors de modification
     */
    private void avertissementMain() {
        Toast.makeText(MainActivity.this, "Suppresion du contact: "
                + liste.get(selection).getNomComplet(), Toast.LENGTH_SHORT).show();
        liste.remove(selection);
        adap.notifyDataSetChanged();
    }

    /**
     * Methode qui fait l'ajout du client dans le intent
     * @param verifier
     * @return
     */
    private Bundle ajoutObjet(boolean verifier){
        Bundle bundle = new Bundle();
        ArrayList<Client> parametre = new ArrayList<Client>();
        if(verifier){
            parametre.add(liste.get(selection));
            bundle.putSerializable("client", parametre);
        }else{
            Client dummy = null;
            parametre.add(dummy);
            bundle.putSerializable("client", parametre);
        }

        return bundle;
    }

    /**
     * Methode pour ajouter un contact du 2e ecran
     * @param c
     */
    public static void ajoutContact(Client c){
        db.ajouterClient(c);
    }

    /**
     * Methode pour modifier un contact du 2e ecran
     * @param c
     * @param nom
     */
    public static void modifierContact(Client c, String nom){
        db.ajusterNomClient(c, nom);
    }


}
