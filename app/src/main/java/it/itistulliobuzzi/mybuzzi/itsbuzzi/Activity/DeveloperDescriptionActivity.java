package it.itistulliobuzzi.mybuzzi.itsbuzzi.Activity;


import android.content.Intent;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.MainActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.R;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Functions;

/**
 * Questa &egrave; una sottoclasse della classe {@link BaseActivity}
 * e si occupa semplicemente di mostrare le descrizioni degli
 * sviluppatori e di cosa si sono accupati all'interno del progetto
 */

public class DeveloperDescriptionActivity extends BaseActivity {


    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_developer_description);
    }

    @Override
    public void onBackPressed() {
        Functions.switchToActivity(new Intent(this, MainActivity.class), this);
    }

}
