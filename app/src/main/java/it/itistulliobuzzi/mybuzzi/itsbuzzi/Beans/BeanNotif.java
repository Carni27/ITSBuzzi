package it.itistulliobuzzi.mybuzzi.itsbuzzi.Beans;

import java.io.Serializable;

/**
 * Created by a31167 on 01/06/2016.
 */
public class BeanNotif implements Serializable {

    private static final long serialVersionUID = 304271321656566008L;

    private String nome;
    private String cognome;
    private String testo;
    private String id;
    private String data;
    private String compito;
    private String comunicazione;

    /**
     *
     * @param nome String nome
     * @param cognome String cognome
     * @param testo String testo
     * @param id String id
     * @param compito String compito
     * @param comunicazione String comunicazione
     */
    public BeanNotif(String nome, String cognome, String testo, String id, String data, String compito, String comunicazione) {
        this.nome = nome;
        this.cognome = cognome;
        this.testo = testo;
        this.id = id;
        this.data = data;
        this.compito = compito;
        this.comunicazione = comunicazione;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCompito() {
        return compito;
    }

    public void setCompito(String compito) {
        this.compito = compito;
    }

    public String getComunicazione() {
        return comunicazione;
    }

    public void setComunicazione(String comunicazione) {
        this.comunicazione = comunicazione;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BeanNotif beanNotif = (BeanNotif) o;

        if (!nome.equals(beanNotif.nome)) return false;
        if (!cognome.equals(beanNotif.cognome)) return false;
        if (!testo.equals(beanNotif.testo)) return false;
        return id.equals(beanNotif.id);

    }

    @Override
    public int hashCode() {
        int result = nome.hashCode();
        result = 31 * result + cognome.hashCode();
        result = 31 * result + testo.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }
}
