package it.itistulliobuzzi.mybuzzi.itsbuzzi.Beans;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Questa classe serve per salvare le news finchè
 * non è chiesto nuovamente l'agiornamento di esse.
 * <p>
 * Created by ricca on 08/01/16.
 */
public class BeanNews {

    private String giorno, mese, anno, titolo, testo;

    public BeanNews(String giorno, String mese, String anno, String titolo, String testo) {
        this.giorno = giorno;
        this.mese = mese;
        this.anno = anno;
        this.titolo = titolo;
        this.testo = testo;
    }

    public BeanNews(String data, String titolo, String testo) {
        try {
            String splitData[] = data.split("-");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.valueOf(splitData[0]), Integer.valueOf(splitData[1]) - 1, Integer.valueOf(splitData[2]));
            SimpleDateFormat sdf = new SimpleDateFormat("MMM", Locale.ITALIAN);
            this.giorno = splitData[2];
            this.mese = sdf.format(calendar.getTime());
            this.anno = splitData[0];
            this.titolo = titolo;
            this.testo = testo;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getGiorno() {
        return giorno;
    }

    public void setGiorno(String giorno) {
        this.giorno = giorno;
    }

    public String getMese() {
        return mese;
    }

    public void setMese(String mese) {
        this.mese = mese;
    }

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
}
