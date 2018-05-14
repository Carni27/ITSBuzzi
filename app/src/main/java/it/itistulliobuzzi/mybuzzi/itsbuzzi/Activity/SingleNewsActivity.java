package it.itistulliobuzzi.mybuzzi.itsbuzzi.Activity;

import android.content.Intent;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.MainActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.R;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Constant;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Functions;


/**
 * Questa &egrave; una sottoclasse della classe {@link BaseActivity}
 * e si occupa di mostrare le singole news del buzzi.
 * <p>
 * Created by Riccardo on 09/05/2016.
 */
public class SingleNewsActivity extends BaseActivity {

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_single_news);
    }

    @Override
    protected void load() {
        if (getIntent().getExtras() != null) {
            ((TextView) findViewById(R.id.title_single_news)).setText(getIntent().getExtras().getString("title_single_news"));
            ((TextView) findViewById(R.id.date_single_news)).setText(getIntent().getExtras().getString("date_single_news"));

            TextView testo = (findViewById(R.id.text_single_news));
            //URLImageParser p = new URLImageParser(testo, this);
            //Spanned htmlSpan = Html.fromHtml(adjustingText(getIntent().getExtras().getString("text_single_news")), p, null);
            testo.setText(Html.fromHtml(adjustingText(getIntent().getExtras().getString("text_single_news"))));
            ((TextView) findViewById(R.id.text_single_news)).setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    @Override
    public void onBackPressed() {
        Functions.switchToActivity(new Intent(this, MainActivity.class), this);
    }

    private String adjustingText(String s) {
        return s == null ? "" : s.replace("../..", Constant.HOME_BUZZI_SITE);
    }

}
