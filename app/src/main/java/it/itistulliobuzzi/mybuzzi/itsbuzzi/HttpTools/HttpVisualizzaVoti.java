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

import it.itistulliobuzzi.mybuzzi.itsbuzzi.Adapter.ListAdapterVoti;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Fragment.FragmentNews;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.MainActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Constant;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Functions;

/**
 * Created by a31167 on 07/06/2016.
 */
public class HttpVisualizzaVoti extends HttpConnect {

    private MainActivity a;
    private ListView lv;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView nullaPresente;

    public HttpVisualizzaVoti(MainActivity a, HashMap<String, String> datiInvio, ListView lv, SwipeRefreshLayout swipeRefreshLayout, TextView nullaPresente) {
        super(a, Constant.URL_BUZZI + "visualizzaVoti.php", datiInvio, "", new HttpOptions().showProgressBar(false));
        this.a = a;
        this.lv = lv;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.nullaPresente = nullaPresente;
    }

    @Override
    protected void handlerResponse(String result) {
        if (result != null) {
            try {
                Log.e("gg", result);
                ArrayList<HashMap<String, String>> list = new ArrayList();
                if (!result.equals("[]")) {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if (jsonObject != null) {
                            HashMap<String, String> hs = new HashMap<String, String>();
                            hs.put(Constant.MEDIA, jsonObject.getString("votomedio"));
                            hs.put(Constant.MATERIA, jsonObject.getString("descrizione"));
                            hs.put(Constant.ID_MATERIA, jsonObject.getString("idmateria"));
                            hs.put(Constant.QUADRIMESTRE, jsonObject.getString("Quadrimestre"));
                            hs.put(Constant.ID_QUADRIMESTRE, jsonObject.getString("idquadrimestre"));
                            list.add(hs);
                        }
                    }
                    //Salvo i voti
                    //TODO Aspettare il bindini per i file
                    lv.setAdapter(new ListAdapterVoti(a, list, this));
                } else {
                    showIfIsEmpty();
                }
                stopLoading();
            } catch (JSONException e) {
                Log.e("JSONExHttpVoti", "Error: " + e.toString());
            }
        }
    }

    private void showIfIsEmpty() {
        lv.setAdapter(null);
        nullaPresente.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void cancelRequest() {  }

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
