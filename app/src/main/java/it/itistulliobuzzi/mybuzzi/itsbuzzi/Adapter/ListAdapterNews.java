package it.itistulliobuzzi.mybuzzi.itsbuzzi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.Beans.BeanNews;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools.HttpNews;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.MainActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.R;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Activity.SingleNewsActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Functions;

/**
 * Questa &egrave; una sottoclasse dalla classe {@link BaseAdapter}
 * e si occupa di mostrare la lista delle news.
 *
 * Viene utilizzata nella classe {@link it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools.HttpNews}
 *
 * Created by ricca on 08/01/16.
 */
public class ListAdapterNews extends BaseAdapter {

    private ArrayList<BeanNews> homes;
    private MainActivity mainActivity;
    private HttpNews httpNews;

    public ListAdapterNews(MainActivity mainActivity, ArrayList<BeanNews> homes, HttpNews httpNews) {
        this.homes = homes;
        this.mainActivity = mainActivity;
        this.httpNews = httpNews;
    }

    @Override
    public int getCount() {
        return this.homes.size();
    }

    @Override
    public BeanNews getItem(int position) {
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
        convertView = inflater.inflate(R.layout.item_news, null);
        final BeanNews bh = getItem(position);
        ((TextView) convertView.findViewById(R.id.anno_tv)).setText(bh.getAnno());
        ((TextView) convertView.findViewById(R.id.mese_tv)).setText(bh.getMese());
        ((TextView) convertView.findViewById(R.id.giorno_tv)).setText(bh.getGiorno());
        ((TextView) convertView.findViewById(R.id.titolo_tv)).setText(bh.getTitolo());
        ((TextView) convertView.findViewById(R.id.descrizione_tv)).setText(Html.fromHtml(bh.getTesto()));

        convertView.findViewById(R.id.layout_base_news).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se carica stoppa il caricamento e ferma l'immagine
                if (httpNews != null) {
                    httpNews.cancel(true);
                    httpNews.stopLoading();
                }
                Intent i = new Intent(mainActivity, SingleNewsActivity.class);
                i.putExtra("title_single_news", bh.getTitolo());
                i.putExtra("date_single_news", bh.getAnno() + "/" + bh.getMese() + "/" + bh.getGiorno());
                i.putExtra("text_single_news", bh.getTesto());
                Functions.switchToActivity(i, mainActivity);
            }
        });

        return convertView;
    }

}
