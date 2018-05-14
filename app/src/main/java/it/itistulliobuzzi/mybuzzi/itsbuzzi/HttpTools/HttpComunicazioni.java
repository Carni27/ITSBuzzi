package it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.Fragment.FragmentComunicazioni;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Constant;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Functions;

/**
 * Created by g.panza on 20/04/2016.
 */
public class HttpComunicazioni extends HttpConnect {

    private Activity a;
    private ListView lv;
    private HashMap<String, String> datiInvio;

    public HttpComunicazioni(Activity a, HashMap<String, String> datiInvio, ListView lv) {
        super(a, Constant.URL_BUZZI + "comunicazioni.php", datiInvio, "Caricamento delle comunicazioni...");
        this.a = a;
        this.lv = lv;
        this.datiInvio = datiInvio;
    }

    @Override
    protected void handlerResponse(String result) {
        if (result != null) {
            Log.e("HttpComunicazioni", result);
            Functions.showAlertDialog(a, "Comunicazioni", result);
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
                Functions.checkInternetConnectionGraphics(new FragmentComunicazioni(a), a);
            }
        };
    }
}
