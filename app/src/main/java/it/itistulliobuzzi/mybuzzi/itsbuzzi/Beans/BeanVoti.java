package it.itistulliobuzzi.mybuzzi.itsbuzzi.Beans;

import java.io.Serializable;

/**
 * Created by a31167 on 07/06/2016.
 */
public class BeanVoti implements Serializable {

    static final long serialVersionUID = 304271321636566008L;

    private int id;
    private String classe;
    private String voto;
    private String materia;
    private String datavoto;
    private String tipo;
    private String note;


    /**
     * @param id Id del voto
     * @param classe Classe del voto
     * @param voto Valore del voto
     * @param materia Materia del voto
     * @param datavoto data del voto
     * @param tipo tipo di voto
     * @param note note dello studente relative al voto
     */
    public BeanVoti(int id, String classe, String voto, String materia, String datavoto, String tipo, String note) {
        this.id = id;
        this.classe = classe;
        this.voto = voto;
        this.materia = materia;
        this.datavoto = datavoto;
        this.tipo = tipo;
        this.note = note;
    }

    public int getID() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getVoto() {
        return voto;
    }

    public void setVoto(String voto) {
        this.voto = voto;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getDatavoto() {
        return datavoto;
    }

    public void setDatavoto(String datavoto) {
        this.datavoto = datavoto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BeanVoti beanVoti = (BeanVoti) o;

        if (classe != null ? !classe.equals(beanVoti.classe) : beanVoti.classe != null)
            return false;
        if (voto != null ? !voto.equals(beanVoti.voto) : beanVoti.voto != null) return false;
        if (materia != null ? !materia.equals(beanVoti.materia) : beanVoti.materia != null)
            return false;
        if (datavoto != null ? !datavoto.equals(beanVoti.datavoto) : beanVoti.datavoto != null)
            return false;
        if (tipo != null ? !tipo.equals(beanVoti.tipo) : beanVoti.tipo != null) return false;
        return note != null ? note.equals(beanVoti.note) : beanVoti.note == null;

    }

    @Override
    public int hashCode() {
        int result = classe != null ? classe.hashCode() : 0;
        result = 31 * result + (voto != null ? voto.hashCode() : 0);
        result = 31 * result + (materia != null ? materia.hashCode() : 0);
        result = 31 * result + (datavoto != null ? datavoto.hashCode() : 0);
        result = 31 * result + (tipo != null ? tipo.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
    }
}
