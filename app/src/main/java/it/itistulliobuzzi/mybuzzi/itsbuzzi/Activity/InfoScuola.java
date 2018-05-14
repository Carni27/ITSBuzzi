package it.itistulliobuzzi.mybuzzi.itsbuzzi.Activity;

import android.content.Intent;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.MainActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.R;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Functions;

/**
 * Created by Riccardo on 07/07/2016.
 */
public class InfoScuola extends BaseActivity {


    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_info_scuola);
    }

    @Override
    public void onBackPressed() {
        Functions.switchToActivity(new Intent(this, MainActivity.class), this);
    }

}
