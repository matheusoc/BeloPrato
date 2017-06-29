package br.matheuscampos.beloprato.model;

/**
 * Created by matheusoliveira on 29/06/2017.
 */

public class User {
    private String username;

    private String password;

    public User (String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User (){}
}
