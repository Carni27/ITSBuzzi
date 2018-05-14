package it.itistulliobuzzi.mybuzzi.itsbuzzi.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.Beans.BeanUser;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.R;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Activity.RemoveCredentialsActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Functions;

/**
 * Questa &egrave; una sottoclasse della classe {@link BaseAdapter}
 * e si occupa di mostrare e rimuovere la lista delle credeziali
 * salvate nel dispositivo
 *
 * Viene utilizzata nella classe {@link it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools.HttpRegistro}
 *
 * Created by borg on 21/04/2016.
 */
public class ListAdapterRemoveCredentials extends BaseAdapter {

    private ArrayList<BeanUser> testi;
    private Activity mainActivity;

    public ListAdapterRemoveCredentials(Activity mainActivity, ArrayList<BeanUser> testi) {
        this.testi = testi;
        this.mainActivity = mainActivity;
    }

    @Override
    public int getCount() {
        return this.testi.size();
    }

    @Override
    public BeanUser getItem(int position) {
        return this.testi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mainActivity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_remove_credentials, null);

        ((TextView) convertView.findViewById(R.id.removeCredentialsText)).setText(testi.get(position).getUsername());
        convertView.findViewById(R.id.removeCredentialsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener positive = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Functions.removeCredenzialeSalvata(testi.get(position));
                        ((RemoveCredentialsActivity) mainActivity).setAdapterLv();
                        Log.e("LARemoveCredentials", "Cliccato");
                    }
                };

                DialogInterface.OnClickListener negative = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };

                Functions.showAlertDialog(mainActivity, "Rimozione credenziali",
                        "Sicuro di voler rimuovere le credenziali?", positive, negative);
            }
        });

        return convertView;
    }
}
