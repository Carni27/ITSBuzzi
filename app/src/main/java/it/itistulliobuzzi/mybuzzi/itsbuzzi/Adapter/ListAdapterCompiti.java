package it.itistulliobuzzi.mybuzzi.itsbuzzi.Adapter;

import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.R;

/**
 * Questa &egrave; una sottoclasse dalla classe {@link BaseAdapter}
 * e si occupa di mostrare la lista dei compiti.
 * <p>
 * Viene utilizzata nella classe {@link it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools.HttpRegistro}
 * <p>
 * Created by ricca on 14/03/16.
 */
public class ListAdapterCompiti extends BaseAdapter {

    private ArrayList<JSONObject> testi;
    private Activity mainActivity;
    private JSONObject initCompiti;

    public ListAdapterCompiti(Activity mainActivity, ArrayList<JSONObject> testi, JSONObject initCompiti) {
        this.testi = testi;
        this.mainActivity = mainActivity;
        this.initCompiti = initCompiti;
    }

    @Override
    public int getCount() {
        return this.testi.size();
    }

    @Override
    public JSONObject getItem(int position) {
        return this.testi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mainActivity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_compito, null);
        try {
            Log.e("ListAdapterCompiti", getItem(position).getString("testo"));
            if (getItem(position).equals(initCompiti)) {
                ((TextView) convertView.findViewById(R.id.name_prof_compito)).setText(Html.fromHtml(getItem(position).getString("testo")));
                (convertView.findViewById(R.id.text_compito)).setVisibility(View.GONE);
                (convertView.findViewById(R.id.layout_letter_name_prof_compito)).setVisibility(View.GONE);
            } else {
                ((TextView) convertView.findViewById(R.id.text_compito)).setText(Html.fromHtml(getItem(position).getString("testo")));
                ((TextView) convertView.findViewById(R.id.name_prof_compito)).setText(Html.fromHtml(getItem(position).getString("Nome") + " " + getItem(position).getString("Cognome")));
                ((TextView) convertView.findViewById(R.id.letter_name_prof_compito)).setText(Html.fromHtml((String.valueOf(getItem(position).getString("Nome").charAt(0))).toUpperCase()));
                (convertView.findViewById(R.id.layout_letter_name_prof_compito)).setVisibility(View.VISIBLE);
                if (getItem(position).getString("compito").equals("1")) {

                    int colorFrom = Color.YELLOW;
                    int colorTo = Color.WHITE; //Colore sfondo
                    int duration = 1000;
                    ObjectAnimator oa = ObjectAnimator.ofObject(convertView.findViewById(R.id.layout_base_item_compito), "backgroundColor", new IntEvaluator(), colorFrom, colorTo);
                    oa.setDuration(duration);
                    oa.setRepeatCount(ValueAnimator.INFINITE);
                    oa.setRepeatMode(ValueAnimator.REVERSE);
                    oa.start();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
