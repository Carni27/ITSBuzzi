package it.itistulliobuzzi.mybuzzi.itsbuzzi.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.MainActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.R;

/**
 * Questa è una sottoclasse astratta della classe {@link AppCompatActivity}
 * e serve per uniformare tutte le activity secondarie nella applicazione
 * (Activity diverse da {@link MainActivity})
 *
 * Created by ricca on 09/11/2015.
 */
abstract class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        load();

    }


    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    //public void setTitle(String title) { this.getToolbar().setTitle(title); }

    /**
     * Implementare per completare l'interfaccia grafica
     */
    protected void load(){ }

    /**
     * Implementare per impostare il contentView
     */
    abstract protected void setContentView ();

    /**
     * Implementare per gestire il pulsante in alto a sinistra con la "freccia indietro"
     */
    abstract public void onBackPressed();

}
