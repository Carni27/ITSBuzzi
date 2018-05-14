package it.itistulliobuzzi.mybuzzi.itsbuzzi.Activity;

import android.content.Intent;
import android.widget.ListView;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.Adapter.ListAdapterRemoveCredentials;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.R;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Functions;

/**
 * Questa &egrave; una sottoclasse della classe {@link BaseActivity}
 * e si occupa di gestire la rimozione delle credenziali
 * salvate nel proprio dispositivo
 */

public class RemoveCredentialsActivity extends BaseActivity {

    public RemoveCredentialsActivity removeCredentialsActivity;
    private ListView lv;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_remove_credentials);
    }


    @Override
    protected void load() {

        removeCredentialsActivity = this;

        lv = findViewById(R.id.lvRemoveCredentials);
        setAdapterLv();
    }

    public void setAdapterLv() {
        lv.setAdapter(new ListAdapterRemoveCredentials(this, Functions.getCredenzialiSalvate()));
    }

    @Override
    public void onBackPressed() {
        Functions.switchToActivity(new Intent(removeCredentialsActivity, SettingsActivity.class), removeCredentialsActivity);
    }
}
