package it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.db.chart.model.LineSet;
import com.db.chart.model.Point;
import com.db.chart.view.LineChartView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.Activity.SingleMateriaActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Adapter.ListAdapterSingleVoti;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Beans.BeanVoti;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.MainActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.R;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Constant;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Functions;

/**
 * Created by a31167 on 07/06/2016.
 */
public class HttpVisualizzaSingoliVoti extends HttpConnect {

    private SingleMateriaActivity a;
    private ListView lv;
    private LineChartView lcv;
    private HashMap<String, String> datiInvio;
    private String materia;

    public HttpVisualizzaSingoliVoti(SingleMateriaActivity a, HashMap<String, String> datiInvio, ListView lv, LineChartView lcv, String materia) {
        super(a, Constant.URL_BUZZI + "visualizzaSingoliVoti.php", datiInvio, "", new HttpOptions());
        this.a = a;
        this.lv = lv;
        this.lcv = lcv;
        this.datiInvio = datiInvio;
        this.materia = materia;
    }

    @Override
    protected void handlerResponse(String result) {
        if (result != null) {
            Log.e("VisSingoliVoti", result);
            try {
                ArrayList<BeanVoti> list = new ArrayList();
                LineSet dataset = new LineSet();
                if (!result.equals("[]")) {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if (jsonObject != null) {
                            BeanVoti bv = new BeanVoti(Integer.valueOf(jsonObject.getString("idvoti"))
                                    , null,//"Non presente",
                                    jsonObject.getString("voto"), null,// jsonObject.getString("materia"),
                                    jsonObject.getString("datavoto"), jsonObject.getString("tipo"),
                                    jsonObject.getString("note"));
                            list.add(bv);
                            String data[] = bv.getDatavoto().split("-");
                            dataset.addPoint(new Point(data[0].substring(2) + '-' + data[1] + '-' + data[2], Float.parseFloat(bv.getVoto())));

                        }
                    }

                    dataset.setColor(a.getResources().getColor(R.color.colorPrimary));
                    dataset.setDotsColor(a.getResources().getColor(R.color.colorPrimaryDark));

                    lcv.addData(dataset);
                    lcv.setXAxis(false);
                    lcv.show();

                    lv.setAdapter(new ListAdapterSingleVoti(a, list, this, materia));

                } else {
                    //Vai ai voti
                }
                stopLoading();
            } catch (JSONException e) {
                Log.e("JSONException", "Error: " + e.toString());
            }
        }
    }

    @Override
    protected View.OnClickListener getListnerError() {
        return listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(a, MainActivity.class);
                i.putExtra(Constant.EXIT_INS_VOTI_ACTIVITY, true);
                Functions.switchToActivity(i, a);
            }
        };
    }
}