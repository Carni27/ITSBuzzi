package it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import java.util.HashMap;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Constant;

/**
 * Created by Riccardo on 10/06/2016.
 */
public class HttpUpdateVoti extends HttpConnect {

    private Activity a;
    private HttpUpdateVoti httpInserimentoVoti;

    public HttpUpdateVoti(Activity a, HashMap<String, String> datiInvio) {
        super(a, Constant.URL_BUZZI + "updateVoti.php", datiInvio, "", new HttpOptions().showProgressBar(false).setErrorActivity(true));
        this.a = a;
        this.httpInserimentoVoti = this;
    }

    @Override
    protected void handlerResponse(String result) {
        if (result != null) {
            Log.e("Update voti", result);
            a.onBackPressed();
        }
    }

    @Override
    protected void cancelRequest() {
    }

    @Override
    protected View.OnClickListener getListnerError() {
        return listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                httpInserimentoVoti.execute();
            }
        };
    }

}
