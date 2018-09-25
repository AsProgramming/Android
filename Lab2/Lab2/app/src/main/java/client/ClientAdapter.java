package client;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a0704335.lab2.R;

import java.util.ArrayList;

import client.Client;

public class ClientAdapter extends ArrayAdapter<Client> {

    private Context mContext;

    public ClientAdapter(Context context, int resource, ArrayList<Client> list) {
        super(context, resource, list);
        this.mContext = context;//nom age ville
    }

    private class ClientHolder{
        TextView txtNom;
        TextView txtAge;
        TextView txtVille;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ClientHolder holder = null;
        Client c = getItem(position);

        LayoutInflater mInflater = (LayoutInflater)
                mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            if(convertView == null){
                convertView = mInflater.inflate(R.layout.layout_client, null);
                holder = new ClientHolder();
                creerHolder(convertView, holder);
            }else
                holder =  (ClientHolder) convertView.getTag();

            mettreHolder(holder, c);

        return convertView;
    }

    /**
     * Nethode qui creer le placeholder des inos a afficher
     * @param v
     * @param holder
     */
    private void creerHolder(View v, ClientHolder holder){
        holder.txtNom =  (TextView) v.findViewById(R.id.textviewName);
        holder.txtVille =  (TextView) v.findViewById(R.id.textviewVille);
        holder.txtAge =  (TextView) v.findViewById(R.id.textviewAge);
        v.setTag(holder);
    }

    /**
     * Methode qui met les infos a presenter
     * @param holder
     * @param c
     */
    private void mettreHolder(ClientHolder holder, Client c) {
        holder.txtNom.setText(c.getNomComplet());
        holder.txtAge.setText(c.getAge());
        holder.txtVille.setText(c.getVille());
    }
}
