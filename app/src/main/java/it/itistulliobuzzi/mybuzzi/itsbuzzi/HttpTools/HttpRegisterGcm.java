package it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools;

import android.app.Activity;
import android.view.View;

import java.util.HashMap;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Constant;

/**
 * Questa &egrave; una sottoclasse dalla classe {@link HttpConnect}
 * e si occupa di inviare la matricola e il gcm id
 * per poterle aggiungere dal server e far arrivare le notifiche
 *
 * Created by ricca on 19/04/2016.
 */
public class HttpRegisterGcm extends HttpConnect {

    private Activity a;

    public HttpRegisterGcm(Activity a, HashMap<String, String> datiInvio) {
        super(a, Constant.URL_BUZZI + "insertGcm.php", datiInvio, "Registrazione...");
        this.a = a;
    }

    @Override
    protected void handlerResponse(String s) {
        stopLoading();
        a.onBackPressed();
    }

    @Override
    protected View.OnClickListener getListnerError() {
        return null;
    }
}
