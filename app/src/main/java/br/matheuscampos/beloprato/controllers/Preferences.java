package br.matheuscampos.beloprato.controllers;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

import br.matheuscampos.beloprato.Constants;
import br.matheuscampos.beloprato.R;

/**
 * Created by matheusoliveira on 29/06/2017.
 */

public class Preferences {

    private SharedPreferences preferences;
    private Context mContext;

    public Preferences (Context context) {
        this.mContext = context;
    }

    public void saveUser (String id, String username, String password) {
        preferences = mContext.getSharedPreferences(mContext.getString(R.string.shared_users),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.ID, id);
        editor.putString(Constants.USER, username);
        editor.putString(Constants.PASSWORD, password);
        editor.commit();
    }

    public HashMap<String, String> getUserToLogin() {
        preferences = mContext.getSharedPreferences(mContext.getString(R.string.shared_users),
                Context.MODE_PRIVATE);
        HashMap<String, String> map = new HashMap<>();
        map.put(Constants.USER, preferences.getString(Constants.USER, Constants.NULL));
        map.put(Constants.PASSWORD, preferences.getString(Constants.PASSWORD, Constants.NULL));
        return map;
    }

    public void removeUser () {
        preferences = mContext.getSharedPreferences(mContext.getString(R.string.shared_users),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(Constants.ID);
        editor.remove(Constants.USER);
        editor.remove(Constants.PASSWORD);
        editor.commit();
    }

}
