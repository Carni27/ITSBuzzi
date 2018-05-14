package it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.ogaclejapan.smarttablayout.utils.ViewPagerItemAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.Adapter.ListAdapterArgomenti;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Adapter.ListAdapterAvvisi;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Adapter.ListAdapterCompiti;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Adapter.ListAdapterComunicazioni;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Fragment.FragmentRegistro;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.MainActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.R;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Constant;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Functions;

/**
 * Questa &egrave; una sottoclasse dalla classe {@link HttpConnect}
 * e si occupa di ricevere i dati del registro a seconda
 * della classe dove un ragazzo si trova
 * <p/>
 * Created by ricca on 13/03/16.
 */
public class HttpRegistro extends HttpConnect {

    private Activity a;
    private ViewPagerItemAdapter adapter;
    private ViewPager viewPager;
    private JSONObject initCompiti;
    private FragmentRegistro fr;
    private int timeRepeated;
    private HashMap<String, String> datiInvio;
    private String messaggioCompiti = "";
    private String messaggioComunicazioni = "";
    private String messaggioAvvisi = "";
    private String messaggioArgomenti = "";
    private boolean inCompiti = false, inAvvisi = false, inComunicazioni = false;


    public HttpRegistro(Activity a, FragmentRegistro fr, HashMap<String, String> datiInvio,
                        ViewPagerItemAdapter adapter, ViewPager viewPager, int timeRepeated) {
        super(a, Constant.URL_BUZZI + "datiregistro.php", datiInvio, "Controllando i tuoi dati...");
        this.a = a;
        this.adapter = adapter;
        this.viewPager = viewPager;
        this.timeRepeated = timeRepeated;
        this.fr = fr;
        this.datiInvio = datiInvio;
    }

    @Override
    protected void handlerResponse(String result) {
        //parse JSON data
        if (result != null) {
            Log.e("HttpRegistro", result);
            try {
                final ArrayList<JSONObject> compiti = new ArrayList<>();
                final ArrayList<String> comunicazioni = new ArrayList<>();
                final ArrayList<String> avvisi = new ArrayList<>();
                final ArrayList<String> argomenti = new ArrayList<>();
                initCompiti = new JSONObject().put("testo", "Nessun compito per oggi.");
                compiti.add(initCompiti);
                avvisi.add("Nessun avviso per oggi.");
                argomenti.add("Nessun argomento svolto oggi.");
                comunicazioni.add("Nessuna comunicazione per oggi.");

                if (!result.equals("[]")) {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if (jsonObject != null) {
                            if (jsonObject.get("Comunicazione").equals("_")) {
                                avvisi.remove("Nessun avviso per oggi.");
                                avvisi.add(jsonObject.getString("testo"));
                                inAvvisi = true;
                            } else if (!jsonObject.get("Comunicazione").equals("")) {
                                comunicazioni.remove("Nessuna comunicazione per oggi.");
                                comunicazioni.add(jsonObject.getString("testo"));
                                inComunicazioni = true;
                            } else if (!jsonObject.get("compito").equals("")) {
                                compiti.remove(initCompiti);
                                compiti.add(jsonObject);
                                inCompiti = true;
                            }
                        }
                    }

                    String date[] = datiInvio.get(Constant.DATA).split("-");
                    String data = date[2] + "/" + date[1] + "/" + date[0];

                    this.messaggioAvvisi = "Avvisi per il *" + data + "*:\n";
                    this.messaggioArgomenti = "Argomenti per il *" + data + "*:\n Nessun argomenti svolto";
                    this.messaggioCompiti = "Compiti per il *" + data + "*:\n";
                    this.messaggioComunicazioni = "Comunicazioni per il *" + data + "*:\n";

                    if (inComunicazioni) {
                        for (String s : comunicazioni) {
                            this.messaggioComunicazioni += s + "\n";
                        }
                    } else {
                        this.messaggioComunicazioni += "Nessuna comunicazione";
                    }
                    if (inAvvisi) {
                        for (String s : avvisi) {
                            this.messaggioAvvisi += s + "\n";
                        }
                    } else {
                        this.messaggioAvvisi += "Nessun avviso";
                    }
                    if (inCompiti) {
                        for (JSONObject jo : compiti) {
                            try {
                                this.messaggioCompiti += "*" + jo.getString("Nome") + " " + jo.getString("Cognome") + "*\n";
                                this.messaggioCompiti += jo.getString("testo") + "\n";
                                jo.getString("compito");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        this.messaggioCompiti += "Nessun compito";
                    }
                }

                View page = adapter.getPage(0);
                String title = adapter.getPageTitle(0).toString();
                if (title.equals("Comunicazioni") && page != null) {
                    ((ListView) page.findViewById(R.id.listView_List)).setAdapter(new ListAdapterComunicazioni(a, comunicazioni));
                }
                page = adapter.getPage(1);
                title = adapter.getPageTitle(1).toString();
                if (title.equals("Compiti") && page != null) {
                    ((ListView) page.findViewById(R.id.listView_List)).setAdapter(new ListAdapterCompiti(a, compiti, initCompiti));
                }
                page = adapter.getPage(2);
                title = adapter.getPageTitle(2).toString();
                if (title.equals("Avvisi") && page != null) {
                    ((ListView) page.findViewById(R.id.listView_List)).setAdapter(new ListAdapterAvvisi(a, avvisi));
                }
                page = adapter.getPage(3);
                title = adapter.getPageTitle(3).toString();
                if (title.equals("Argomenti") && page != null) {
                    ((ListView) page.findViewById(R.id.listView_List)).setAdapter(new ListAdapterArgomenti(a, argomenti));
                }

                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    }

                    @Override
                    public void onPageSelected(int position) {
                        View page = adapter.getPage(position);
                        String title = adapter.getPageTitle(position).toString();
                        if (title.equals("Comunicazioni")) {
                            ((ListView) page.findViewById(R.id.listView_List)).setAdapter(new ListAdapterComunicazioni(a, comunicazioni));
                        }
                        if (title.equals("Compiti")) {
                            ((ListView) page.findViewById(R.id.listView_List)).setAdapter(new ListAdapterCompiti(a, compiti, initCompiti));
                        }
                        if (title.equals("Avvisi")) {
                            ((ListView) page.findViewById(R.id.listView_List)).setAdapter(new ListAdapterAvvisi(a, avvisi));
                        }
                        if (title.equals("Argomenti")) {
                            ((ListView) page.findViewById(R.id.listView_List)).setAdapter(new ListAdapterArgomenti(a, argomenti));
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                    }
                });
                stopLoading();
            } catch (JSONException e) {
                Log.e("JSONException", "Error: " + e.toString());
            }
        }

    }

    public String getMessaggioCompiti() {
        return this.messaggioCompiti;
    }

    public String getMessaggioComunicazioni() {
        return this.messaggioComunicazioni;
    }

    public String getMessaggioAvvisi() {
        return this.messaggioAvvisi;
    }

    public String getMessaggioArgomenti() {
        return this.messaggioArgomenti;
    }


    protected void cancelRequest() {
        if (timeRepeated == 0)
            MainActivity.gotoNews();
        else {
            fr.cancel();
        }
    }

    @Override
    protected View.OnClickListener getListnerError() {
        return listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.checkInternetConnectionGraphics(new FragmentRegistro(a), a);
            }
        };
    }
}
