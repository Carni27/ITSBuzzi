package it.itistulliobuzzi.mybuzzi.itsbuzzi.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.Activity.InserimentoVotiActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Activity.SingleMateriaActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Beans.BeanVoti;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools.HttpEliminaVoti;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools.HttpNews;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools.HttpOttieniMaterie;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools.HttpOttieniQuadrimestri;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools.HttpOttieniTipoVoto;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools.HttpVisualizzaSingoliVoti;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.R;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.CircularProgressBar;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Constant;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Functions;

/**
 * Questa &egrave; una sottoclasse dalla classe {@link BaseAdapter}
 * e si occupa di mostrare la lista dei voti.
 *
 * Viene utilizzata nella classe {@link HttpNews}
 *
 * Created by ricca on 08/01/16.
 */
public class ListAdapterSingleVoti extends BaseAdapter {

    private ArrayList<BeanVoti> homes;
    private SingleMateriaActivity mainActivity;
    private HttpVisualizzaSingoliVoti httpVvsv;
    private String materia;

    public ListAdapterSingleVoti(SingleMateriaActivity mainActivity, ArrayList<BeanVoti> homes, HttpVisualizzaSingoliVoti httpVvsv, String materia) {
        this.homes = homes;
        this.mainActivity = mainActivity;
        this.httpVvsv = httpVvsv;
        this.materia = materia;
    }

    @Override
    public int getCount() {
        return this.homes.size();
    }

    @Override
    public BeanVoti getItem(int position) {
        return this.homes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mainActivity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_single_voto, null);
        final BeanVoti bv = getItem(position);
        ((TextView) convertView.findViewById(R.id.data_singolo_voto)).setText(bv.getDatavoto());
        ((TextView) convertView.findViewById(R.id.tipo_singolo_voto)).setText(bv.getTipo());
        ((TextView) convertView.findViewById(R.id.notr_singolo_voto)).setText(bv.getNote());
        CircularProgressBar c3 = convertView.findViewById(R.id.progress_single_voto);
        c3.setMax(1000);
        c3.setTitle(bv.getVoto());
        c3.setSubTitle("");
        int voto = Float.valueOf(Float.valueOf(bv.getVoto()) * 100).intValue();
        c3.setProgress(voto);
        if (voto < 600) {
            c3.setProgressColorPaint(Color.RED);
            c3.setTitleColor(Color.RED);
        } else {
            c3.setProgressColorPaint(mainActivity.getResources().getColor(R.color.colorPrimaryDark));
            c3.setTitleColor(mainActivity.getResources().getColor(R.color.colorPrimaryDark));
        }


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
                builder.setTitle("")
                        .setItems(R.array.operazioni_voti, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {   //Elimina
                                    modificaVoto(bv);
                                } else if (which == 1) {         //Modifica
                                    eliminaVoto(bv);
                                }
                            }
                        });
                builder.show();
            }
        });

        return convertView;
    }

    private void eliminaVoto(BeanVoti bv) {
        HashMap<String, String> datiInvio = new HashMap<>();
        datiInvio.put(Constant.IDVOTO, String.valueOf(bv.getID()));
        datiInvio.put(Constant.PASSWORD_PAGINA, Constant.PASSWPRD_PAGINA_VALORE);
        HttpEliminaVoti httpEliminaVoti = new HttpEliminaVoti(mainActivity, datiInvio, httpVvsv);
        Log.e("LASingleVoti", "Eseguo");
        httpEliminaVoti.execute();
    }

    private void modificaVoto(BeanVoti bv) {
        final Intent i = new Intent(mainActivity, InserimentoVotiActivity.class);
        i.putExtra(Constant.MOD_VOTI_ID, bv.getID());
        i.putExtra(Constant.MOD_VOTI_DATA, bv.getDatavoto());
        i.putExtra(Constant.MOD_VOTI_VOTO, bv.getVoto());
        i.putExtra(Constant.MOD_VOTI_TIPO, bv.getTipo());
        i.putExtra(Constant.MOD_VOTI_MATERIA, materia);
        i.putExtra(Constant.MOD_VOTI_NOTE, bv.getNote());
        i.putExtra(Constant.MOD_QUADR_TIPO, mainActivity.getIdQuadr());
        i.putExtra(Constant.MOD_VOTI, true);

        HashMap invioT = new HashMap();
        invioT.put(Constant.PASSWORD_PAGINA, Constant.PASSWPRD_PAGINA_VALORE);
        final HttpOttieniTipoVoto hotv = new HttpOttieniTipoVoto(mainActivity, invioT);

        HashMap invioM = new HashMap();
        invioM.put(Constant.PASSWORD_PAGINA, Constant.PASSWPRD_PAGINA_VALORE);
        invioM.put(Constant.MATRICOLA, Functions.getMatricola());
        final HttpOttieniMaterie hom = new HttpOttieniMaterie(mainActivity, invioM);

        HashMap invioQ = new HashMap();
        invioQ.put(Constant.PASSWORD_PAGINA, Constant.PASSWPRD_PAGINA_VALORE);
        final HttpOttieniQuadrimestri hoq = new HttpOttieniQuadrimestri(mainActivity, invioQ);

        hotv.execute();
        hom.execute();
        hoq.execute();

        new AsyncTask<Void, Void, Void>() {

            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                progressDialog = new ProgressDialog(mainActivity);
                progressDialog.setMessage("Caricamento...");
                progressDialog.show();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                while (!hotv.isFinito() || !hom.isFinito() || !hoq.isFinito()) {
                    //Aspetta
                }
                progressDialog.dismiss();
                Functions.checkInternetConnectionGraphics(i, mainActivity);
                return null;
            }
        }.execute();
    }

}
