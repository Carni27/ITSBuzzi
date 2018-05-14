package it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Constant;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Data;

/**
 * Created by Riccardo on 06/07/2016.
 */
public class HttpOttieniMaterie  extends HttpConnect {

    private Activity a;
    private HttpOttieniMaterie httpOttieniMaterie;
    private Boolean finito;
    private HashMap<String, String> result;

    public HttpOttieniMaterie(Activity a, HashMap<String, String> datiInvio) {
        super(a, Constant.URL_BUZZI + "ottieniMaterie.php", datiInvio, "", new HttpOptions().showProgressBar(false));
        this.a = a;
        this.httpOttieniMaterie = this;
        this.finito = false;
        result = new HashMap<>();
    }

    @Override
    protected void handlerResponse(String r) {
        if (result != null) {
            try {
                if (!result.equals("[]")) {
                    JSONArray jsonArray = new JSONArray(r);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if (jsonObject != null) {
                            result.put(jsonObject.getString("Descrizione"), jsonObject.getString("ID"));
                        }
                    }
                }
                Data.setMaterie(result);
                stopLoading();
            } catch (JSONException e) {
                Log.e("JSONException", "Error: " + e.toString());
            }
        }
        finito = true;
    }

    /**
     *
     * @return <ul type='disc'>
     * <li><b>True</b>: Ha finto di elaborare i dati</li>
     * <li><b>False</b>: Non ha finito</li>
     * </ul>
     */
    public Boolean isFinito() { return finito; }

    @Override
    protected View.OnClickListener getListnerError() {
        return listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                httpOttieniMaterie.execute();
            }
        };
    }
}
