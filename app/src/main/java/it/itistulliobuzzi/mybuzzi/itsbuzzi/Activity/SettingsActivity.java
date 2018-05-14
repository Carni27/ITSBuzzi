package it.itistulliobuzzi.mybuzzi.itsbuzzi.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.TextView;

import com.rey.material.widget.Switch;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.MainActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.R;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Constant;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Data;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Functions;

/**
 * Questa &egrave; una sottoclasse della classe {@link BaseActivity}
 * e si occupa di gestire la parte delle impostazioni
 * della applicazione
 */

public class SettingsActivity extends BaseActivity {

    private SettingsActivity settingsActivity;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_settings);
    }

    @Override
    protected void load() {
        settingsActivity = this;

        if (!Functions.isLogged()) {
            findViewById(R.id.buttonLogout).setVisibility(View.GONE);
            findViewById(R.id.viewLinear2).setVisibility(View.GONE);
        } else {
            findViewById(R.id.buttonLogout).setVisibility(View.VISIBLE);
            findViewById(R.id.viewLinear2).setVisibility(View.VISIBLE);
        }

        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.deleteGcm(settingsActivity);
                SharedPreferences.Editor editor = Data.getSharedpreferences().edit();
                editor.putBoolean(Constant.LOGGED, false);
                editor.apply();
            }
        });

        findViewById(R.id.removeCredentialButtonSettings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.switchToActivity(new Intent(settingsActivity, RemoveCredentialsActivity.class), settingsActivity);
            }
        });

        ((Switch)findViewById(R.id.sw_comunica)).setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(Switch view, boolean checked) {
                Functions.saveInPrefereces(Constant.SWITCH_COMUNICAZIONI, checked);
            }
        });

        ((Switch)findViewById(R.id.sw_es_casa)).setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(Switch view, boolean checked) {
                Functions.saveInPrefereces(Constant.SWITCH_ES_CASA, checked);
            }
        });

        ((Switch)findViewById(R.id.sw_verifiche)).setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(Switch view, boolean checked) {
                Functions.saveInPrefereces(Constant.SWITCH_VERIFICA, checked);
            }
        });


        //Setto gli switch come si trovano
        ((Switch)findViewById(R.id.sw_verifiche)).setChecked(Data.getSharedpreferences().getBoolean(Constant.SWITCH_VERIFICA, true));
        ((Switch)findViewById(R.id.sw_comunica)).setChecked(Data.getSharedpreferences().getBoolean(Constant.SWITCH_COMUNICAZIONI, true));
        ((Switch)findViewById(R.id.sw_es_casa)).setChecked(Data.getSharedpreferences().getBoolean(Constant.SWITCH_ES_CASA, true));

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            ((TextView) findViewById(R.id.info_vers_app)).setText("Versione: " + pInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onBackPressed() {
        Functions.switchToActivity(new Intent(settingsActivity, MainActivity.class), settingsActivity);
    }
}

