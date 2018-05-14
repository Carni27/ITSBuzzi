package it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import java.util.HashMap;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.MainActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Constant;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Functions;

/**
 * Created by Riccardo on 10/06/2016.
 */
public class HttpEliminaVoti extends HttpConnect {

    private Activity a;
    private HttpEliminaVoti httpEliminaVoti;
    private HttpVisualizzaSingoliVoti httpVvsv;

    public HttpEliminaVoti(Activity a, HashMap<String, String> datiInvio, HttpVisualizzaSingoliVoti httpVvsv) {
        super(a, Constant.URL_BUZZI + "eliminaVoti.php", datiInvio, "", new HttpOptions().setErrorActivity(true));
        this.a = a;
        this.httpEliminaVoti = this;
        this.httpVvsv = httpVvsv;
    }

    @Override
    protected void handlerResponse(String result) {
        if (result != null) {
            Log.e("Elimina voti", super.getResult());
            /*Intent i = new Intent(a, SingleMateriaActivity.class);
            i.putExtra(Constant.ID_MATERIA, httpVvsv.getDatiInvio().get(Constant.ID_MATERIA));
            i.putExtra(Constant.MATERIA, httpVvsv.getDatiInvio().get(Constant.MATERIA));
            Functions.switchToActivity(i, a);
            */
            Intent i = new Intent(a, MainActivity.class);
            i.putExtra(Constant.EXIT_INS_VOTI_ACTIVITY, true);
            Functions.switchToActivity(i, a);
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
                httpEliminaVoti.execute();
            }
        };
    }

}
