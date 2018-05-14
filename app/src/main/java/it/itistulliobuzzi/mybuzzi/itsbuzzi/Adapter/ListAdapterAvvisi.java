package it.itistulliobuzzi.mybuzzi.itsbuzzi.Adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.R;

/**
 * Questa &egrave; una sottoclasse dalla classe {@link BaseAdapter}
 * e si occupa di mostrare la lista degli avvisi.
 * <p>
 * Viene utilizzata nella classe {@link it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools.HttpRegistro}
 * <p>
 * Created by ricca on 21/03/16.
 */
public class ListAdapterAvvisi extends BaseAdapter {

    private ArrayList<String> testi;
    private Activity mainActivity;

    public ListAdapterAvvisi(Activity mainActivity, ArrayList<String> testi) {
        this.testi = testi;
        this.mainActivity = mainActivity;
    }

    @Override
    public int getCount() {
        return this.testi.size();
    }

    @Override
    public String getItem(int position) {
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
        convertView = inflater.inflate(R.layout.item_registro, null);
        ((TextView) convertView.findViewById(R.id.text_registro)).setText(Html.fromHtml(getItem(position)));

        return convertView;
    }
}
