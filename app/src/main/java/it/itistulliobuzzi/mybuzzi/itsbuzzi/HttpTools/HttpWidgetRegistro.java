package it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools;

import android.view.View;

import java.util.HashMap;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Constant;

/**
 * Created by Riccardo on 03/06/2016.
 */
public class HttpWidgetRegistro extends HttpConnect {

    public HttpWidgetRegistro(HashMap<String, String> datiInvio) {
        super(null, Constant.URL_BUZZI + "datiregistro.php", datiInvio, "",
                new HttpOptions().showProgressBar(false).showErrorView(false));
    }

    @Override
    protected void handlerResponse(String result) {  }

    @Override
    protected View.OnClickListener getListnerError() {
        return null;
    }
}
