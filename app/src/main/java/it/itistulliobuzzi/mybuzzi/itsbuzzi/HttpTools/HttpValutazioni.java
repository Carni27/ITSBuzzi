package it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.Fragment.FragmentValutazioni;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.MainActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Constant;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Functions;

/**
 * Created by g.panza on 13/04/2016.
 */
public class HttpValutazioni extends HttpConnect {

    private MainActivity a;
    private ListView lv;
    private HashMap<String, String> datiInvio;

    public HttpValutazioni(MainActivity a, HashMap<String, String> datiInvio, ListView lv) {
        super(a, Constant.URL_BUZZI + "valutazioni.php", datiInvio, "Caricamento delle valutazioni...");
        this.a = a;
        this.lv = lv;
        this.datiInvio = datiInvio;
    }

    @Override
    protected void handlerResponse(String result) {
        if (result != null) {
            Log.e("HttpValutazioni", result);
            Functions.showAlertDialog(a, "Valutazioni", result);
            try {
                if (!result.equals("[]")) {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if (jsonObject != null) {

                        }
                    }
                }
                stopLoading();
            } catch (JSONException e) {
                Log.e("JSONException", "Error: " + e.toString());
            }
        }

    }

    @Override
    protected View.OnClickListener getListnerError() {
        return listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.checkInternetConnectionGraphics(new FragmentValutazioni(a), a);
            }
        };
    }
}
