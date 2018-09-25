package database;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.*;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

import client.Client;

public class ClientDB extends SQLiteOpenHelper {




    public static final String TAG = "Hello";

    private static ClientDB instance = null;
    private  Context context;
    private static final String DBNAME = "clients";
    private static final String CLIENTNANE = "name";
    private static final String CLIENTAGE = "age";
    private static final String CLIENTSEXE = "sexe";
    private static final String CLIENTADDRESS = "addresse";
    private static final String CLIENTCITY = "ville";
    private static final String CLIENTEMAIL = "email";

    public static ClientDB getInstance(Context mContext){
        if(instance == null){
            instance = new ClientDB(mContext.getApplicationContext());
        }
        return instance;
    }

    private ClientDB(Context mContext){
        super(mContext, DBNAME, null, 1);
        context = mContext;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLcreate());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + DBNAME);
        onCreate(db);
    }

    /**
     * Methode qui cree la requete de creation de table SQL
     * @return requete sql
     */
    private String SQLcreate(){
        return "create table " + DBNAME + "(" + CLIENTNANE + " text primary key, " + CLIENTAGE + " integer, " +
                CLIENTSEXE + " text, " + CLIENTADDRESS + " text, " + CLIENTCITY + " text, " +
                CLIENTEMAIL + " text)";
    }

    /**
     * Methode qui ajoute le client a la bd
     * @param c client
     */
    public void ajouterClient(Client c){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = genererValues(c);

        long id = db.insert(DBNAME, null, values);
        Log.d(TAG, "ajouterClient: ");
        db.close();
    }

    /**
     * Methode qui prend un client et qui le format en execution sql
     * @param c le client
     * @return objet a entrer dans la bd
     */
    private ContentValues genererValues(Client c) {
        ContentValues values = new ContentValues();
        values.put(CLIENTNANE, c.getNomComplet());
        values.put(CLIENTAGE, c.getAge());
        values.put(CLIENTSEXE, c.getSexe());
        values.put(CLIENTADDRESS, c.getAddress());
        values.put(CLIENTCITY, c.getVille());
        values.put(CLIENTEMAIL, c.getEmail());
        return values;
    }

    /**
     * Methode qui cherche tout les clients dans la bd
     * @return liste de touts les clients
     */
    public ArrayList<Client> getClients(){
        ArrayList<Client> lst = new ArrayList<Client>();

            SQLiteDatabase db = getWritableDatabase();//

            lst = remplirListe(db, lst);

        return lst;
    }

    /**
     * Methode qui remplit la liste de clients dans la bd
     * @param db la base de donnees
     * @param lst la liste a remplir
     * @return la liste remplit
     */
    private ArrayList<Client> remplirListe(SQLiteDatabase db, ArrayList<Client> lst) {
        String request = "select * from " + DBNAME;
        Cursor data = db.rawQuery(request, null);

        if(data.moveToFirst()){
            do{

                Client c = new Client(data.getString(0),
                        data.getInt(1), data.getString(4));
                c.setSexe(Client.trouverGenre(data.getString(2)));
                c.setAddress(data.getString(3));
                c.setEmail(data.getString(5));
                lst.add(c);
            }while(data.moveToNext());
        }
        data.close();
        db.close();

        return lst;
    }

    /**
     * Methode qui ajuste le nom du contact
     * @param c les nouveau changement
     * @param nom l'identifiant
     * @return si a ete changer
     */
    public boolean ajusterNomClient(Client c, String nom){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = genererValues(c);

        db.update(DBNAME, values, CLIENTNANE +" =?", new String[]{nom});
        db.close();
        return true;
    }

    /**
     * Methode qui suprime le contact
     * @param id l'identifiant
     * @return si le contact a ete supprimer
     */
    public boolean supprimerClient(String id){
        SQLiteDatabase db = getWritableDatabase();
        int rows = db.delete(DBNAME, CLIENTNANE + " =?", new String[]{id});
        db.close();
        return true;
    }

    /**
     * Methode qui drop a table de la bd
     */
    public void supprimerTable(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("drop table if exists " + DBNAME);
        onCreate(db);
        db.close();
    }
}
