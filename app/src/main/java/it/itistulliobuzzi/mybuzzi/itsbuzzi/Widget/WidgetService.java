package it.itistulliobuzzi.mybuzzi.itsbuzzi.Widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViewsService;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Constant;

/**
 * Created by Riccardo on 03/06/2016.
 */
public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        boolean loggato = getSharedPreferences(Constant.SHAREDPREFERENCESKEY, Context.MODE_PRIVATE).getBoolean(Constant.LOGGED, false);
        String username = loggato ? getSharedPreferences(Constant.SHAREDPREFERENCESKEY, Context.MODE_PRIVATE).getString(Constant.USERNAME, "") : null;

        return (new ListCompiti(this.getApplicationContext(), intent, username));
    }
}
