package it.itistulliobuzzi.mybuzzi.itsbuzzi.Activity;

import android.content.Intent;
import android.widget.ListView;

import com.db.chart.view.LineChartView;

import java.util.HashMap;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools.HttpVisualizzaSingoliVoti;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.MainActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.R;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Constant;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Functions;

public class SingleMateriaActivity extends BaseActivity {

    private String idquadr;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_single_materia);
    }

    @Override
    protected void load() {

        String materia = getIntent().getStringExtra(Constant.MATERIA);
        String idmatria = getIntent().getStringExtra(Constant.ID_MATERIA);
        idquadr = getIntent().getStringExtra(Constant.ID_QUADRIMESTRE);

        setTitle(materia);

        ListView lv = findViewById(R.id.list_singoli_voti);
        LineChartView lcv = findViewById(R.id.linechart);

        HashMap<String, String> datiInvio = new HashMap<>();
        datiInvio.put(Constant.PASSWORD_PAGINA, Constant.PASSWPRD_PAGINA_VALORE);
        datiInvio.put(Constant.ID_MATERIA, idmatria);
        datiInvio.put(Constant.ID_QUADRIMESTRE, idquadr);
        datiInvio.put(Constant.MATRICOLA, Functions.getMatricola());
        HttpVisualizzaSingoliVoti httpVisualizzaSingoliVoti = new HttpVisualizzaSingoliVoti(this, datiInvio, lv, lcv, materia);
        httpVisualizzaSingoliVoti.execute();
    }

    public String getIdQuadr(){ return idquadr; }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra(Constant.EXIT_INS_VOTI_ACTIVITY, true);
        Functions.switchToActivity(i, this);
    }
}
