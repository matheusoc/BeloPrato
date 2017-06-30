package br.matheuscampos.beloprato.controllers.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import br.matheuscampos.beloprato.R;
import br.matheuscampos.beloprato.view.OrderActivity;

/**
 * Created by matheusoliveira on 30/06/2017.
 */

public class ClientListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> mList;
    private OrderActivity mOrderActivity;
    private Dialog mDialog;

    public ClientListAdapter(OrderActivity activity, ArrayList<String> list, Dialog dialog) {
        this.mContext = activity.getApplicationContext();
        this.mOrderActivity = activity;
        this.mList = list;
        this.mDialog = dialog;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final String name = mList.get(position);

        LayoutInflater inflater = (LayoutInflater)
                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.select_client_list, null);

        TextView nameClient = (TextView) layout.findViewById(R.id.ClientName);
        Button selectButton = (Button) layout.findViewById(R.id.selectClientButton);

        nameClient.setText(name);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mOrderActivity.setmClientText(name);
                        mDialog.cancel();
                    }
                });

            }
        });

        return layout;
    }
}
