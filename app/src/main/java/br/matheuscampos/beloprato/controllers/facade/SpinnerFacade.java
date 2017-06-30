package br.matheuscampos.beloprato.controllers.facade;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;


/**
 * Created by matheusoliveira on 30/06/2017.
 */

public abstract class SpinnerFacade {

    public static ArrayAdapter getSpinnerDeskArray(Context context) {
        ArrayList<String> arraySpinner = new ArrayList<>();
        arraySpinner.add("Mesa 1");
        arraySpinner.add("Mesa 2");
        arraySpinner.add("Mesa 3");
        arraySpinner.add("Mesa 4");

        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item,
                arraySpinner);
        return adapter;
    }

}
