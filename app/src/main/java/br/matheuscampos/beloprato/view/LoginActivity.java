package br.matheuscampos.beloprato.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import br.matheuscampos.beloprato.R;
import br.matheuscampos.beloprato.controllers.LogTasks;

/**
 * Created by matheusoliveira on 29/06/2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;

    private Button mEnterButton;

    private ProgressBar mProgressBar;

    private LinearLayout mLoginLayout;

    private LogTasks task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponents();
        task.loginSaved();

    }

    private void initComponents() {
        mUsernameEditText = (EditText) findViewById(R.id.username_enter);
        mPasswordEditText = (EditText) findViewById(R.id.password_enter);

        mEnterButton = (Button) findViewById(R.id.enter_button);
        mEnterButton.setOnClickListener(this);

        mProgressBar = (ProgressBar) findViewById(R.id.spinner);

        mLoginLayout = (LinearLayout) findViewById(R.id.loginLinearLayout);

        task = new LogTasks(this, this);
    }

    public void setLoginVisibility() {
        mLoginLayout.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.enter_button:
                String username = mUsernameEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                task.login(username, password);
                break;
        }
    }
}
