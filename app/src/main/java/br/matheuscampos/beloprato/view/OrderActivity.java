package br.matheuscampos.beloprato.view;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import br.matheuscampos.beloprato.R;
import br.matheuscampos.beloprato.controllers.facade.SpinnerFacade;
import br.matheuscampos.beloprato.view.Dialog.SelectClientDialog;

/**
 * Created by matheusoliveira on 30/06/2017.
 */

public class OrderActivity extends AppCompatActivity
        implements View.OnClickListener{

    private Spinner mDeskSpinner;

    private LinearLayout mClientLayout;
    private TextView mClientText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initComponents();
    }

    private void initComponents() {
        mDeskSpinner = (Spinner) findViewById(R.id.deskSpinner);
        mDeskSpinner.setAdapter(SpinnerFacade.getSpinnerDeskArray(this));

        mClientLayout = (LinearLayout) findViewById(R.id.clientLayout);
        mClientLayout.setOnClickListener(this);

        mClientText = (TextView) findViewById(R.id.clientEditText);
        mClientText.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clientLayout:
                DialogFragment dialogFragment = new SelectClientDialog();
                dialogFragment.show(getFragmentManager(), "Client Dialog");
                break;
        }
    }

    public void setmClientText(String name) {
        mClientText.setText(name);
    }
}
