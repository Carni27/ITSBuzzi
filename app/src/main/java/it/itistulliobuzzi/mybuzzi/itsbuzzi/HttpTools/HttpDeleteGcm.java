package it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import java.util.HashMap;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Constant;

/**
 * Questa &egrave; una sottoclasse dalla classe {@link HttpConnect}
 * e si occupa di inviare la matricola e il gcm id
 * per poterle rimovere dal server e non fargli arrivare più notifiche
 * <p>
 * Created by g.panza on 19/04/2016.
 */
public class HttpDeleteGcm extends HttpConnect {

    private Activity a;

    public HttpDeleteGcm(Activity a, HashMap<String, String> datiInvio) {
        super(a, Constant.URL_BUZZI + "deleteGcm.php", datiInvio, "Controllando i tuoi dati...");
        this.a = a;
    }

    @Override
    protected void handlerResponse(String s) {
        Log.e("HttpDeleteGcm", s);
        stopLoading();
        a.onBackPressed();
    }

    @Override
    protected View.OnClickListener getListnerError() {
        return null;
    }
}
