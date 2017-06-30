package br.matheuscampos.beloprato.view.Dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import br.matheuscampos.beloprato.R;
import br.matheuscampos.beloprato.controllers.adapters.ClientListAdapter;
import br.matheuscampos.beloprato.view.OrderActivity;

/**
 * Created by matheusoliveira on 30/06/2017.
 */

public class SelectClientDialog extends DialogFragment{

    private Context mContext;
    private OrderActivity mActivity;
    private ListView clientList;
    private EditText searchText;
    private ArrayList<String> list;
    private Dialog dialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.mActivity = (OrderActivity) getActivity();
        this.mContext = getActivity();
        return dialog();
    }

    private Dialog dialog() {

        dialog = new Dialog(mContext);

        LayoutInflater inflater = (LayoutInflater)
                mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.select_client_dialog, null);

        searchText = (EditText) layout.findViewById(R.id.search_client_text);
        clientList = (ListView) layout.findViewById(R.id.clientListView);

        searchText.addTextChangedListener(textListener());

        list = new ArrayList<>();
        list.add("Matheus de Oliveira Campos");
        for(int i = 1; i <= 999; i++) {
            list.add("Client " + i);
        }

       setClientListAdapter(list);

        dialog.setContentView(layout);
        return dialog;
    }

    private TextWatcher textListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<String> newArray = new ArrayList<>();
                if(s.length() >= 1) {
                    for (String name : list) {
                        if (name.contains(s)) {
                            newArray.add(name);
                        }
                    }
                    if(!(list.size() == newArray.size())) {
                        setClientListAdapter(newArray);
                    }
                } else {
                    setClientListAdapter(list);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    private void setClientListAdapter(ArrayList<String> lista) {
        ClientListAdapter adapter = new ClientListAdapter(mActivity, lista, dialog);
        clientList.setAdapter(adapter);
    }
}
