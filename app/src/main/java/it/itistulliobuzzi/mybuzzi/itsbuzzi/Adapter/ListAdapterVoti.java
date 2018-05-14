package it.itistulliobuzzi.mybuzzi.itsbuzzi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.Activity.SingleMateriaActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools.HttpNews;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools.HttpVisualizzaVoti;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.MainActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.R;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.CircularProgressBar;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Constant;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Functions;

/**
 * Questa &egrave; una sottoclasse dalla classe {@link BaseAdapter}
 * e si occupa di mostrare la lista delle news.
 *
 * Viene utilizzata nella classe {@link HttpNews}
 *
 * Created by ricca on 08/01/16.
 */
public class ListAdapterVoti extends BaseAdapter {

    private ArrayList<HashMap<String, String>> homes;
    private MainActivity mainActivity;
    private HttpVisualizzaVoti httpVisualizzaVoti;

    public ListAdapterVoti(MainActivity mainActivity, ArrayList<HashMap<String, String>> homes, HttpVisualizzaVoti httpVisualizzaVoti) {
        this.homes = homes;
        this.mainActivity = mainActivity;
        this.httpVisualizzaVoti = httpVisualizzaVoti;
    }

    @Override
    public int getCount() {
        return this.homes.size();
    }

    @Override
    public HashMap<String, String> getItem(int position) {
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
        convertView = inflater.inflate(R.layout.item_voto, null);
        final HashMap<String, String> bv = getItem(position);
        ((TextView) convertView.findViewById(R.id.textMateriaVoti)).setText(bv.get(Constant.MATERIA));
        ((TextView) convertView.findViewById(R.id.textQuadr)).setText(bv.get(Constant.QUADRIMESTRE));
        CircularProgressBar c3 = convertView.findViewById(R.id.progress_voto);
        c3.setMax(1000);
        c3.setTitle(Functions.round(Double.valueOf(bv.get(Constant.MEDIA)), 2, RoundingMode.HALF_UP).toString());
        c3.setSubTitle("");
        int media = Float.valueOf(Float.valueOf(bv.get(Constant.MEDIA)) * 100).intValue();
        if (media >= 600) {
            c3.setProgressColorPaint(mainActivity.getResources().getColor(R.color.colorPrimaryDark));
            c3.setTitleColor(mainActivity.getResources().getColor(R.color.colorPrimaryDark));
        } else {
            c3.setProgressColorPaint(Color.RED);
            c3.setTitleColor(Color.RED);
        }
        c3.setProgress(media);

        convertView.findViewById(R.id.layout_base_voti).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se carica stoppa il caricamento e ferma l'immagine
                if (httpVisualizzaVoti != null) {
                    httpVisualizzaVoti.cancel(true);
                    httpVisualizzaVoti.stopLoading();
                }
                Log.e("ListMaterie", "ok");
                Intent i = new Intent(mainActivity, SingleMateriaActivity.class);
                i.putExtra(Constant.ID_MATERIA, bv.get(Constant.ID_MATERIA));
                i.putExtra(Constant.MATERIA, bv.get(Constant.MATERIA));
                i.putExtra(Constant.ID_QUADRIMESTRE, bv.get(Constant.ID_QUADRIMESTRE));
                Functions.switchToActivity(i, mainActivity);
            }
        });

        return convertView;
    }

}
