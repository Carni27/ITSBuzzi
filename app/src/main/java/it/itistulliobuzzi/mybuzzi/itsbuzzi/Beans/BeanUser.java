package it.itistulliobuzzi.mybuzzi.itsbuzzi.Beans;

import java.io.Serializable;

/**
 * Questa classe serve per savare
 * nel dispositivi le credenziali
 * salvate dell'utente
 * <p>
 * Created by borg on 20/04/2016.
 */
public class BeanUser implements Serializable {

    private static final long serialVersionUID = 304271321636566008L;

    private String username;
    private String password;

    public BeanUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BeanUser beanUser = (BeanUser) o;

        if (username != null ? !username.equals(beanUser.username) : beanUser.username != null)
            return false;
        return password != null ? password.equals(beanUser.password) : beanUser.password == null;

    }
}
