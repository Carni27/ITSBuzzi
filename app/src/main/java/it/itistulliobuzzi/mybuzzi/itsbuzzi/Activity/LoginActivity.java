package it.itistulliobuzzi.mybuzzi.itsbuzzi.Activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.rey.material.widget.Button;
import com.rey.material.widget.Switch;

import java.util.ArrayList;
import java.util.HashMap;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.Beans.BeanUser;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools.HttpLogin;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.MainActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.R;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Constant;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Functions;

/**
 * Questa &egrave; una sottoclasse della classe {@link BaseActivity}
 * e si occupa di mostrare e prelevare i dati dell'utente
 * inseriti per poi inviarli alla classe {@link HttpLogin}
 * la quale si occuperà dell'invio e ricezione dei dati
 */

public class LoginActivity extends BaseActivity {

    private LoginActivity loginActivity;
    private boolean NoDoMD5;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void load() {

        loginActivity = this;
        NoDoMD5 = false;

        final AutoCompleteTextView usernameEt = loginActivity.findViewById(R.id.username);
        final EditText passwordEt = loginActivity.findViewById(R.id.password);
        final Switch salvaCredenziali = loginActivity.findViewById(R.id.salva_credenziali);
        final Button login = findViewById(R.id.buttonLoginActivity);

        final ArrayList<String> userString = new ArrayList<String>();
        final ArrayList<BeanUser> user = Functions.getCredenzialiSalvate();
        if (user != null) {
            for (BeanUser u : user) {
                userString.add(u.getUsername());
            }
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, userString.toArray());
        usernameEt.setAdapter(adapter);
        usernameEt.setThreshold(1);

        usernameEt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String username = userString.get(position);
                String passwordText = "";
                if (user != null) {
                    for (BeanUser bu : user) {
                        if (bu.getUsername().equals(username)) {
                            passwordText = bu.getPassword();
                            NoDoMD5 = true;
                            break;
                        }
                    }
                    passwordEt.setText(passwordText);
                    login.performClick();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Functions.isOnline(loginActivity)) {
                    HashMap<String, String> datiInvio = new HashMap<>();
                    datiInvio.put(Constant.USERNAME, usernameEt.getText().toString());
                    datiInvio.put(Constant.PASSWORD, NoDoMD5 ? passwordEt.getText().toString() : Functions.MD5(passwordEt.getText().toString()));
                    datiInvio.put(Constant.PASSWORD_PAGINA, Constant.PASSWPRD_PAGINA_VALORE);
                    HttpLogin httpLogin = new HttpLogin(loginActivity, datiInvio, !NoDoMD5 && salvaCredenziali.isChecked());
                    httpLogin.execute();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra(Constant.EXIT_LOG_ACTIVITY, true);
        Functions.switchToActivity(i, this);
    }

}
