package it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.Adapter.ListAdapterNews;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Beans.BeanNews;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Fragment.FragmentNews;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.MainActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Constant;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Data;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Functions;

/**
 * Questa &egrave; una sottoclasse dalla classe {@link HttpConnect}
 * e si occupa ricevere le news del buzzi
 * <p>
 * Created by ricca on 20/04/2016.
 */
public class HttpNews extends HttpConnect {

    private MainActivity a;
    private ListView lv;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView nullaPresente;
    private HashMap<String, String> datiInvio;

    public HttpNews(MainActivity a, HashMap<String, String> datiInvio, ListView lv, SwipeRefreshLayout swipeRefreshLayout, TextView nullaPresente) {
        super(a, Constant.URL_BUZZI + "news.php", datiInvio, "Caricamento delle news...", new HttpOptions().showProgressBar(false));
        this.datiInvio = datiInvio;
        this.a = a;
        this.lv = lv;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.nullaPresente = nullaPresente;
    }

    @Override
    protected void handlerResponse(String result) {
        if (result != null) {
            try {
                ArrayList<BeanNews> list = new ArrayList();
                if (!result.equals("[]")) {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if (jsonObject != null) {
                            list.add(new BeanNews(jsonObject.getString("data"), jsonObject.getString("titolo"), jsonObject.getString("testo")));
                        }
                    }
                    //Cancello le news sbagliate
                    ArrayList<BeanNews> listRemove = new ArrayList();
                    for (BeanNews bh : list) {
                        if (bh.getAnno() == null || bh.getMese() == null || bh.getGiorno() == null || bh.getTitolo() == null || bh.getTesto() == null) {
                            listRemove.add(bh);
                        }
                    }
                    list.removeAll(listRemove);
                    lv.setAdapter(new ListAdapterNews(a, list, this));
                    Data.setArrayNews(list);
                    //Potrebbe essere vuoto nel caso tutte le news siano sbagliate
                    if (list.isEmpty()) {
                        nullaPresente.setVisibility(View.VISIBLE);
                    } else {
                        nullaPresente.setVisibility(View.GONE);
                    }
                } else {
                    //Mostro la textView quando non c'è nulla
                    lv.setAdapter(null);
                    nullaPresente.setVisibility(View.VISIBLE);
                }
                stopLoading();
            } catch (JSONException e) {
                Log.e("JSONException", "Error: " + e.toString());
            }
        }
    }

    @Override
    public void stopLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void cancelRequest() {
    }

    @Override
    protected View.OnClickListener getListnerError() {
        return listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.checkInternetConnectionGraphics(new FragmentNews(a), a);
            }
        };
    }
}
