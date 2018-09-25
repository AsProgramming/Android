package com.example.a0704335.lab2;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Pattern;

import client.Client;
import database.ClientDB;
import utils.UtilsRegex;

public class AjoutClient extends AppCompatActivity {

    private EditText editPrenom;
    private EditText editNom;
    private EditText editAge;
    private EditText editNumero;
    private EditText editRue;
    private EditText editPostal;
    private EditText editVille;
    private EditText editEmail;
    private Spinner genreSpinner;
    private Button btnOK;
    private FloatingActionButton btnModifier;
    private ArrayList<Client> objet;
    private Client original;
    private ClientDB db;
    private boolean ajout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout);

        genreSpinner = (Spinner) findViewById(R.id.ajout_client_spinner_genre);
        ArrayAdapter<String> myAdapt = new ArrayAdapter<String>(AjoutClient.this,
                android.R.layout.simple_expandable_list_item_1,
                getResources().getStringArray(R.array.genres));
        myAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(myAdapt);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editPrenom = (EditText) findViewById(R.id.ajout_client_editText_prenom);
        editNom = (EditText) findViewById(R.id.ajout_client_editText_nom);
        editAge = (EditText) findViewById(R.id.ajout_client_editText_age);
        editNumero = (EditText) findViewById(R.id.ajout_client_editText_numero);
        editRue = (EditText) findViewById(R.id.ajout_client_editText_rue);
        editPostal = (EditText) findViewById(R.id.ajout_client_editText_codepostal);
        editVille = (EditText) findViewById(R.id.ajout_client_editText_ville);
        editEmail = (EditText) findViewById(R.id.ajout_client_editText_email);
        btnOK = (Button) findViewById(R.id.ajout_client_btn_ajout);
        btnModifier = (FloatingActionButton) findViewById(R.id.activity_ajout_actionBtn_modifier);
        editPrenom.requestFocus();
       verifierBundle();

        btnModifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOK.setVisibility(View.VISIBLE);
                activerEdition(true);
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ajout && champsValider()){
                    MainActivity.ajoutContact(getNouveauContact());
                    avertissement(true, getNouveauContact().getNomComplet());
                }else if(champsValider()){
                    MainActivity.modifierContact(getNouveauContact(), original.getNomComplet());
                    avertissement(false, getNouveauContact().getNomComplet());
                }
            }
        });

    }

    /**
     * Methode qui cree le toast a afficher
     * @param ajout
     * @param nom
     */
    private void avertissement(boolean ajout, String nom) {
        if(ajout){
            Toast.makeText(AjoutClient.this,
                    "Ajout du nouveau contact: " + nom, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(AjoutClient.this,
                    "Modification du contact: " + nom, Toast.LENGTH_SHORT).show();
        }
        ecranPrincipale();
    }

    /**
     * Methode qui prepare lintent pour aller a la page dacceuil
     */
    private void ecranPrincipale(){
        Intent intent = new Intent(AjoutClient.this, MainActivity.class);;
        startActivity(intent);
    }

    /**
     * Methode qui verifie les champs dentrees
     * @return
     */
    public boolean champsValider() {
        int compteur = 0;

        if(nonValide(editPrenom, UtilsRegex.NOM)){
            compteur++;
        }
        if(nonValide(editNom, UtilsRegex.NOM)){
            compteur++;
        }
        if(nonValide(editAge, UtilsRegex.AGE)){
            compteur++;
        }
        if(nonValide(editNumero, UtilsRegex.NUMERO)){
            compteur++;
        }
        if(nonValide(editRue, UtilsRegex.RUE)){
            compteur++;
        }
        if(nonValide(editPostal, UtilsRegex.CODEPOSTAL)){
            compteur++;
        }
        if(nonValide(editVille, UtilsRegex.NOM)){
            compteur++;
        }
        if(nonValide(editEmail, UtilsRegex.EMAIL)){
            compteur++;
        }
        return compteur == 0;
    }

    /**
     * Methode qui verifie le bundle passer en parametre
     */
    private void verifierBundle() {
        Bundle bundle = getIntent().getExtras();

        objet =  (ArrayList<Client>) bundle.getSerializable("client");

        if(objet.get(0)!=null){
            btnModifier.setVisibility(View.VISIBLE);
            btnOK.setVisibility(View.INVISIBLE);
            ajout = false;
            mettreInfos();
        }else{
            ajout = true;
            btnModifier.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Methode qui met les infos dans les edit text
     */
    private void mettreInfos() {
        original = objet.get(0);

        editPrenom.setText(objet.get(0).getPrenom());
        editNom.setText(objet.get(0).getNom());
        editAge.setText(objet.get(0).getAge());
        genreSpinner.setSelection(trouverValeur(objet.get(0).getSexe()));

        editNumero.setText(objet.get(0).getAddressSplit()[0]);
        editRue.setText(objet.get(0).getAddressSplit()[1]);
        editPostal.setText(objet.get(0).getAddressSplit()[2]);

        editVille.setText(objet.get(0).getVille());
        editEmail.setText(objet.get(0).getEmail());

        activerEdition(false);


    }

    /**
     * Active les champs dentreer
     * @param actif
     */
    private void activerEdition(boolean actif){
        editPrenom.setEnabled(actif);
        editNom.setEnabled(actif);
        editAge.setEnabled(actif);
        genreSpinner.setEnabled(actif);
        editNumero.setEnabled(actif);
        editRue.setEnabled(actif);
        editPostal.setEnabled(actif);
        editVille.setEnabled(actif);
        editEmail.setEnabled(actif);
    }

    /**
     * Methode qui cree le contact a partir des champs ajuster
     * @return
     */
    private Client getNouveauContact(){
        String nouv = editPrenom.getText().toString().trim() +" "+ editNom.getText().toString().trim();
        Client contact = new Client(nouv);
        contact.setAge(Integer.parseInt(editAge.getText().toString().trim()));
        contact.setSexe(Client.trouverGenre(genreSpinner.getSelectedItem().toString()));
        contact.setVille(editVille.getText().toString().trim());
        contact.setEmail(editEmail.getText().toString().trim());
        contact.setAddress(joindreAddresse().trim());
        return contact;
    }

    /**
     * Methode qui trouve la valeur du genre
     * @param genre
     * @return
     */
   private int trouverValeur(String genre){
      switch(genre){
          case "Femme":
              return 1;
          case "Autre":
               return 2;
               default:
               return 0;
       }
   }

    /**
     * Methode qui rejoins l'addresse au complet
     * @return
     */
    private String joindreAddresse(){
        return editNumero.getText().toString() + " " + editRue.getText().toString() + " " +
                editPostal.getText().toString();
    }

    /**
     * Methode qui verifie les champs de donnees
     * @param verifier
     * @param regex
     * @return
     */
    private boolean nonValide(EditText verifier, String regex){
        String aVerifier = verifier.getText().toString().trim();
        if(aVerifier.isEmpty()){
            verifier.setError("Les informations ne peuvent etre vide");
            return true;
        }else if(!verifierEntre(aVerifier, regex)){
            verifier.setError("Les informations sont invalide");
            return true;
        }else{
            verifier.setError(null);
            return false;
        }
    }

    private boolean verifierEntre(String verifier, String regex){
        return Pattern.compile(regex).matcher(verifier).matches();
    }


}
