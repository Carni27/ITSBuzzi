package it.itistulliobuzzi.mybuzzi.itsbuzzi;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.FrameLayout;

import com.getbase.floatingactionbutton.AddFloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.rey.material.widget.Button;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import io.fabric.sdk.android.Fabric;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Activity.DeveloperDescriptionActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Activity.InfoScuola;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Activity.InserimentoVotiActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Activity.LoginActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Activity.SettingsActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Fragment.FragmentNews;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Fragment.FragmentRegistro;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Fragment.FragmentTwitter;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Fragment.FragmentVisualizzaVoti;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Fragment.PdfReader;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools.HttpOttieniMaterie;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools.HttpOttieniQuadrimestri;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools.HttpOttieniTipoVoto;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Constant;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Data;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Functions;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.ObjectSerializer.ObjectSerializer;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.TextViewTyper;

/**
 * Questa classe &egrave; l'activity principale ed &egrave; una sottoclasse della classe
 * {@link AppCompatActivity}.
 * Si occupa di gestire lo scambio tra i vari fragment
 * dell'applicazione e gestisce la parte grafica relativa alla
 * tendina laterale.
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "egZlWitKi6y9zvlNfPARDl3lJ";
    private static final String TWITTER_SECRET = "VurdQ27X09mPkmM3rMb7eGjYMDBiZX92zm8mAgSgGgGKpghc4z";

    private static MainActivity mainActivity;
    private static FloatingActionsMenu menuContacts;
    private static AddFloatingActionButton addVoto;
    private static FloatingActionButton fabAttivaMenu;
    private static DrawerLayout drawer;
    private View viewHeaderCopy = null;
    private boolean inHome = true, inPdfReader = false, inActivity = false, inVoti = false, inRegistro = false;
    private Fragment fragment = null;
    private Intent intent;

    /**
     * Setta la pagina al fragment news
     */
    public static void gotoNews() {
        mainActivity.onNavigationItemSelected(new MenuItem() {
            @Override
            public int getItemId() {
                return R.id.nav_home;
            }

            @Override
            public int getGroupId() {
                return 0;
            }

            @Override
            public int getOrder() {
                return 0;
            }

            @Override
            public MenuItem setTitle(CharSequence title) {
                return null;
            }

            @Override
            public MenuItem setTitle(int title) {
                return null;
            }

            @Override
            public CharSequence getTitle() {
                return null;
            }

            @Override
            public MenuItem setTitleCondensed(CharSequence title) {
                return null;
            }

            @Override
            public CharSequence getTitleCondensed() {
                return null;
            }

            @Override
            public MenuItem setIcon(Drawable icon) {
                return null;
            }

            @Override
            public MenuItem setIcon(int iconRes) {
                return null;
            }

            @Override
            public Drawable getIcon() {
                return null;
            }

            @Override
            public MenuItem setIntent(Intent intent) {
                return null;
            }

            @Override
            public Intent getIntent() {
                return null;
            }

            @Override
            public MenuItem setShortcut(char numericChar, char alphaChar) {
                return null;
            }

            @Override
            public MenuItem setNumericShortcut(char numericChar) {
                return null;
            }

            @Override
            public char getNumericShortcut() {
                return 0;
            }

            @Override
            public MenuItem setAlphabeticShortcut(char alphaChar) {
                return null;
            }

            @Override
            public char getAlphabeticShortcut() {
                return 0;
            }

            @Override
            public MenuItem setCheckable(boolean checkable) {
                return null;
            }

            @Override
            public boolean isCheckable() {
                return false;
            }

            @Override
            public MenuItem setChecked(boolean checked) {
                return null;
            }

            @Override
            public boolean isChecked() {
                return false;
            }

            @Override
            public MenuItem setVisible(boolean visible) {
                return null;
            }

            @Override
            public boolean isVisible() {
                return false;
            }

            @Override
            public MenuItem setEnabled(boolean enabled) {
                return null;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }

            @Override
            public boolean hasSubMenu() {
                return false;
            }

            @Override
            public SubMenu getSubMenu() {
                return null;
            }

            @Override
            public MenuItem setOnMenuItemClickListener(OnMenuItemClickListener menuItemClickListener) {
                return null;
            }

            @Override
            public ContextMenu.ContextMenuInfo getMenuInfo() {
                return null;
            }

            @Override
            public void setShowAsAction(int actionEnum) {

            }

            @Override
            public MenuItem setShowAsActionFlags(int actionEnum) {
                return null;
            }

            @Override
            public MenuItem setActionView(View view) {
                return null;
            }

            @Override
            public MenuItem setActionView(int resId) {
                return null;
            }

            @Override
            public View getActionView() {
                return null;
            }

            @Override
            public MenuItem setActionProvider(ActionProvider actionProvider) {
                return null;
            }

            @Override
            public ActionProvider getActionProvider() {
                return null;
            }

            @Override
            public boolean expandActionView() {
                return false;
            }

            @Override
            public boolean collapseActionView() {
                return false;
            }

            @Override
            public boolean isActionViewExpanded() {
                return false;
            }

            @Override
            public MenuItem setOnActionExpandListener(OnActionExpandListener listener) {
                return null;
            }
        });
    }

    public static void gotoVoti() {
        mainActivity.onNavigationItemSelected(new MenuItem() {
            @Override
            public int getItemId() {
                return R.id.nav_voti;
            }

            @Override
            public int getGroupId() {
                return 0;
            }

            @Override
            public int getOrder() {
                return 0;
            }

            @Override
            public MenuItem setTitle(CharSequence title) {
                return null;
            }

            @Override
            public MenuItem setTitle(int title) {
                return null;
            }

            @Override
            public CharSequence getTitle() {
                return null;
            }

            @Override
            public MenuItem setTitleCondensed(CharSequence title) {
                return null;
            }

            @Override
            public CharSequence getTitleCondensed() {
                return null;
            }

            @Override
            public MenuItem setIcon(Drawable icon) {
                return null;
            }

            @Override
            public MenuItem setIcon(int iconRes) {
                return null;
            }

            @Override
            public Drawable getIcon() {
                return null;
            }

            @Override
            public MenuItem setIntent(Intent intent) {
                return null;
            }

            @Override
            public Intent getIntent() {
                return null;
            }

            @Override
            public MenuItem setShortcut(char numericChar, char alphaChar) {
                return null;
            }

            @Override
            public MenuItem setNumericShortcut(char numericChar) {
                return null;
            }

            @Override
            public char getNumericShortcut() {
                return 0;
            }

            @Override
            public MenuItem setAlphabeticShortcut(char alphaChar) {
                return null;
            }

            @Override
            public char getAlphabeticShortcut() {
                return 0;
            }

            @Override
            public MenuItem setCheckable(boolean checkable) {
                return null;
            }

            @Override
            public boolean isCheckable() {
                return false;
            }

            @Override
            public MenuItem setChecked(boolean checked) {
                return null;
            }

            @Override
            public boolean isChecked() {
                return false;
            }

            @Override
            public MenuItem setVisible(boolean visible) {
                return null;
            }

            @Override
            public boolean isVisible() {
                return false;
            }

            @Override
            public MenuItem setEnabled(boolean enabled) {
                return null;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }

            @Override
            public boolean hasSubMenu() {
                return false;
            }

            @Override
            public SubMenu getSubMenu() {
                return null;
            }

            @Override
            public MenuItem setOnMenuItemClickListener(OnMenuItemClickListener menuItemClickListener) {
                return null;
            }

            @Override
            public ContextMenu.ContextMenuInfo getMenuInfo() {
                return null;
            }

            @Override
            public void setShowAsAction(int actionEnum) {

            }

            @Override
            public MenuItem setShowAsActionFlags(int actionEnum) {
                return null;
            }

            @Override
            public MenuItem setActionView(View view) {
                return null;
            }

            @Override
            public MenuItem setActionView(int resId) {
                return null;
            }

            @Override
            public View getActionView() {
                return null;
            }

            @Override
            public MenuItem setActionProvider(ActionProvider actionProvider) {
                return null;
            }

            @Override
            public ActionProvider getActionProvider() {
                return null;
            }

            @Override
            public boolean expandActionView() {
                return false;
            }

            @Override
            public boolean collapseActionView() {
                return false;
            }

            @Override
            public boolean isActionViewExpanded() {
                return false;
            }

            @Override
            public MenuItem setOnActionExpandListener(OnActionExpandListener listener) {
                return null;
            }
        });
    }

    public static FloatingActionsMenu getmenuContacts() {
        return menuContacts;
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Serve a fare l'accesso a twitter
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        //Carico le preferences
        Data.setSharedpreferences(getSharedPreferences(Constant.SHAREDPREFERENCESKEY, Context.MODE_PRIVATE));
        mainActivity = this;

        setContentView(R.layout.activity_main);

        //Setto la toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        inizializzazioneMenuContatti();

        addVoto = (AddFloatingActionButton) findViewById(R.id.fabaddVoto);
        addVoto.setVisibility(View.GONE);
        addVoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap invioT = new HashMap();
                invioT.put(Constant.PASSWORD_PAGINA, Constant.PASSWPRD_PAGINA_VALORE);
                final HttpOttieniTipoVoto hotv = new HttpOttieniTipoVoto(mainActivity, invioT);

                HashMap invioM = new HashMap();
                invioM.put(Constant.PASSWORD_PAGINA, Constant.PASSWPRD_PAGINA_VALORE);
                invioM.put(Constant.MATRICOLA, Functions.getMatricola());
                final HttpOttieniMaterie hom = new HttpOttieniMaterie(mainActivity, invioM);

                HashMap invioQ = new HashMap();
                invioQ.put(Constant.PASSWORD_PAGINA, Constant.PASSWPRD_PAGINA_VALORE);
                final HttpOttieniQuadrimestri hoq = new HttpOttieniQuadrimestri(mainActivity, invioQ);

                hotv.execute();
                hom.execute();
                hoq.execute();

                new AsyncTask<Void, Void, Void>(){

                    ProgressDialog progressDialog;
                    @Override
                    protected void onPreExecute() {
                            progressDialog = new ProgressDialog(mainActivity);
                            progressDialog.setMessage("Caricamento...");
                            progressDialog.show();
                    }
                    @Override
                    protected Void doInBackground(Void... voids) {
                        while(!hotv.isFinito() || !hom.isFinito() || !hoq.isFinito()){
                            //Aspetta
                        }
                        progressDialog.dismiss();
                        Functions.checkInternetConnectionGraphics(new Intent(MainActivity.this, InserimentoVotiActivity.class), MainActivity.this);
                        return null;
                    }
                }.execute();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);

        //Quando apro il drawer chiudo il floatingButton
        drawer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    menuContacts.collapse();
                }
            }
        });

        toggle.syncState();

        changeNavigationLogin();

        Intent intent = getIntent();
        if (intent.getBooleanExtra(Constant.INTENT_NOTIFICATION, false)) {
            SharedPreferences.Editor editor = getSharedPreferences(Constant.SHAREDPREFERENCESKEY, Context.MODE_PRIVATE).edit();
            try {
                editor.putString(Constant.ARRAY_NOTIFICATION, ObjectSerializer.serialize(new ArrayList()));
                editor.apply();
            } catch (IOException e) {
                e.printStackTrace();
            }
            gotoRegistro(
                    new String[]{Constant.INTENT_NOTIFICATION_DATA, intent.getStringExtra(Constant.INTENT_NOTIFICATION_DATA)}
            );
        } else {
            gotoNews();
        }
        //Se si logga apro il drawer
        Intent i = getIntent();
        if (Functions.isLogged() && i.getBooleanExtra(Constant.EXIT_LOG_ACTIVITY, false)) {
            drawer.openDrawer(GravityCompat.START);
        }
        if(Functions.isLogged() && i.getBooleanExtra(Constant.EXIT_INS_VOTI_ACTIVITY, false)){
            gotoVoti();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        inActivity = false;
        inPdfReader = false;
        inHome = false;
        inVoti = false;
        inRegistro = false;
        //Annulla il caricamento delle news se è in esecuzione e viene cambiato o fragment o activity
        if (fragment instanceof FragmentNews) {
            ((FragmentNews) fragment).stopRefresh();
        }
        if (fragment instanceof FragmentVisualizzaVoti) {
            ((FragmentVisualizzaVoti) fragment).stopRefresh();
        }

        if (id == R.id.nav_home) {
            inHome = true;
            menuContacts.setVisibility(View.VISIBLE);
            fragment = new FragmentNews(this);

        } else if (id == R.id.nav_registro) {
            gotoRegistro();

        /*} else if (id == R.id.nav_comunicazioni) {
            inHome = false;
            inPdfReader = false;
            inActivity = false;
            fragment = new FragmentComunicazioni(mainActivity);

            Toast.makeText(this.getBaseContext(), "In fase di sviluppo", Toast.LENGTH_SHORT).show();
        */
        } else if (id == R.id.nav_assenze) {
            inPdfReader = true;
            try{
            String matricola = Functions.getMatricola();
            String nome = Functions.getNome();
            String cognome = Functions.getCognome();
            String nameFile = matricola + "_" + cognome.toLowerCase() + nome.toLowerCase() + ".pdf";
                fragment = new PdfReader(this, "http://www.itistulliobuzzi.it/esportazioni/assenze/"
                        + URLEncoder.encode(nameFile, "UTF-8"), String.valueOf(System.currentTimeMillis() / 1000), "Assenze");
            } catch (NullPointerException e) {
                e.printStackTrace();
            }catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        /*} else if (id == R.id.nav_valutazioni) {
            inHome = false;
            inPdfReader = false;
            inActivity = false;
            menuContacts.setVisibility(View.INVISIBLE);
            fragment = new FragmentValutazioni(this);

            Toast.makeText(this.getBaseContext(), "In fase di sviluppo", Toast.LENGTH_SHORT).show();
        */
        } else if (id == R.id.nav_voti) {
            inVoti = true;
            Functions.showAlertDialogMostrabile(this, "Qusta parte di voti non è ufficiale. Serve solo a te studente per ricordati i voti che hai preso durante l'anno scolastico", "1");
            fragment = new FragmentVisualizzaVoti(this);

        } else if (id == R.id.nav_var_orario) {
            fragment = new PdfReader(this,
                    "http://www.itistulliobuzzi.it/buzziwebsite/home/pdf/variazioni%20giornaliere%20orario.pdf",
                    String.valueOf(System.currentTimeMillis() / 1000), "Variazione Giornaliere");

        } else if (id == R.id.nav_twitter) {
            fragment = new FragmentTwitter(this);

        } else if (id == R.id.nav_impostazioni) {
            inActivity = true;
            intent = new Intent(this, SettingsActivity.class);

        } else if (id == R.id.nav_info_scuola) {
            inActivity = true;
            intent = new Intent(this, InfoScuola.class);
        }

        if(!inHome){
            fabAttivaMenu.setVisibility(View.INVISIBLE);
            menuContacts.setVisibility(View.INVISIBLE);
            menuContacts.collapse();
        }else{
            fabAttivaMenu.setVisibility(View.VISIBLE);
            menuContacts.setVisibility(View.VISIBLE);
        }
        if(!inVoti){
            addVoto.setVisibility(View.INVISIBLE);
        }else{
            addVoto.setVisibility(View.VISIBLE);
        }

        if (!inActivity) {
            Functions.checkInternetConnectionGraphics(fragment, this);
        } else {
            Functions.checkInternetConnectionGraphics(intent, this);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (menuContacts.isExpanded()) {
            menuContacts.collapse();
        } else if (inPdfReader) {
            PdfReader.cancelDownload();
            gotoNews();
        } else if (!inHome) {
            gotoNews();
        } else {
            finish();
        }
    }

    /**
     * Si occupa di inizializzare tutte le funzione per gestire la sezione dei contatti
     */
    private void inizializzazioneMenuContatti(){
        //Imposto i floating button
        //Chiama
        FloatingActionButton fabChiama = findViewById(R.id.fabChiama);
        fabChiama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:057458981"));
                startActivity(intent);
            }
        });
        //Invia Email
        FloatingActionButton fabMail = findViewById(R.id.fabMail);
        fabMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"potf010003@istruzione.it"});

                try {
                    mainActivity.startActivity(Intent.createChooser(emailIntent, "Scegli una mail"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Snackbar.make(v, "Nessuna app di mail installata", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });

        final FrameLayout whiteScreen = findViewById(R.id.white_screen);
        menuContacts = findViewById(R.id.fabMenu);
        fabAttivaMenu = findViewById(R.id.fabAttivaMenu);

        //Mostra e rimuove lo schermo bianco quando si apre il menu
        menuContacts.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                whiteScreen.setVisibility(View.VISIBLE);
                fabAttivaMenu.setVisibility(View.GONE);
            }

            @Override
            public void onMenuCollapsed() {
                whiteScreen.setVisibility(View.INVISIBLE);
                fabAttivaMenu.setVisibility(View.VISIBLE);
            }
        });

        // Uso questo per attivare l'espansione dei contatti
        fabAttivaMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuContacts.setVisibility(View.VISIBLE);
                menuContacts.expand();
            }
        });
    }

    /**
     * Controlla se &egrave; loggato o meno e cambia la tendina laterale
     */
    private void changeNavigationLogin() {
        //Setto la lista del navigation a seconda se è loggato o meno
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Metto l'header in una view per recuperare le view al suo interno
        if (viewHeaderCopy != null)
            navigationView.removeHeaderView(viewHeaderCopy);
        final View viewHeader = LayoutInflater.from(this.getBaseContext()).inflate(R.layout.nav_header_main, null);
        final Button login = viewHeader.findViewById(R.id.loginButton);
        viewHeader.findViewById(R.id.logoBuzziNav).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Functions.switchToActivity(new Intent(mainActivity, DeveloperDescriptionActivity.class), mainActivity);
                return false;
            }
        });

        // svuoto il menu e ci metto quello nuovo
        Menu m = navigationView.getMenu();
        m.clear();

        if (Functions.isLogged()) {
            navigationView.inflateMenu(R.menu.activity_main_drawer_logged);
            TextViewTyper prova = viewHeader.findViewById(R.id.prova);
            prova.setVisibility(View.VISIBLE);
            prova.typer(Html.fromHtml("Ciao, " + Functions.getNome() + " " + Functions.getCognome()).toString(), 125);
            login.setVisibility(View.GONE);
        }

        if (!Functions.isLogged()) {
            navigationView.inflateMenu(R.menu.activity_main_drawer_no_logged);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Functions.isOnline(mainActivity)) {
                        Functions.switchToActivity(new Intent(mainActivity, LoginActivity.class), mainActivity);
                    } else {
                        drawer.closeDrawer(GravityCompat.START);
                    }
                }
            });
        }

        //Setto l'header dell navigation
        viewHeaderCopy = viewHeader;
        navigationView.addHeaderView(viewHeader);
    }

    /**
     * Setta la pagina al fragment registro
     */
    private Fragment gotoRegistro() {
        inHome = false;
        inPdfReader = false;
        inActivity = false;
        inRegistro = true;
        menuContacts.setVisibility(View.INVISIBLE);
        return fragment = new FragmentRegistro(this);
    }

    /**
     * Setta la pagina al fragment registro. Inoltre vengono aggiunti agli
     * argomenti del fragment i dati passati.
     *
     * @param args Matrice di tipo stringa. &egrave; composta nel seguente modo
     *             0 -> {0 -> 'chiave', 1 -> 'valore'},
     *             1 -> {0 -> 'chiave', 1 -> 'valore'},
     *             2 -> {0 -> 'chiave', 1 -> 'valore'},
     *             ...
     */
    private void gotoRegistro(String[]... args) {
        Fragment f = gotoRegistro();
        Bundle bundle = new Bundle();
        for (String[] arg : args) {
            bundle.putString(arg[0], arg[1]);
        }
        fabAttivaMenu.setVisibility(View.INVISIBLE);
        menuContacts.setVisibility(View.INVISIBLE);
        menuContacts.collapse();
        f.setArguments(bundle);
        Functions.checkInternetConnectionGraphics(f, this);
    }

}
