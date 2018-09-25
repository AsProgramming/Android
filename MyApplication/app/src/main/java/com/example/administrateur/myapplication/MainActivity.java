package com.example.administrateur.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Volontaires volontaire;
    private Button btnTrouver;
    private Button btnReset;
    private Button btnAjout;
    private TextView lblTotal;
    private EditText txtNom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        debut();
        associer();

        btnReset.setVisibility(View.INVISIBLE);

        btnTrouver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, volontaire.getNom(),Toast.LENGTH_SHORT).show();
                mettreAJour();
                if(volontaire.getTotal() == 0){
                    btnReset.setVisibility(View.VISIBLE);
                    btnTrouver.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                debut();
                mettreAJour();
                visisbilite();
            }
        });

        btnAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = txtNom.getText().toString();

                if(nom.equals("")){
                    Toast.makeText(MainActivity.this, "Le nom ne peut-etre vide"
                            ,Toast.LENGTH_SHORT).show();
                }else{
                    volontaire.ajouter(nom);
                    txtNom.setText("");
                    mettreAJour();
                    visisbilite();
                }
            }
        });

    }

    /**
     * Methode qui met les volontaires ded depart dans la liste pour debuter
     */
    private void debut(){
        volontaire = new Volontaires();
    }

    /**
     * Methode qui met le nombre de volontaire disponible a jour
     */
    private void mettreAJour(){
        String nouveau = "Nombres de volontaires: "+ volontaire.getTotalText();
        lblTotal.setText(nouveau);
    }

    /**
     * Methode qui associe les aspects clickable du display
     */
    private void associer(){
        btnTrouver = (Button) findViewById(R.id.activity_main_btn_trouver);
        btnReset = (Button) findViewById(R.id.activity_main_btn_reset);
        btnAjout = (Button) findViewById(R.id.activity_main_btn_ajout);
        lblTotal = (TextView) findViewById(R.id.activity_main_lbl_total);
        txtNom = (EditText)  findViewById(R.id.activity_main_txt_nom);
    }

    /**
     * Methode qui ajuste la visibilite des button pour etre visible ou non
     */
    private void visisbilite(){
        btnTrouver.setVisibility(View.VISIBLE);
        btnReset.setVisibility(View.INVISIBLE);
    }

}
