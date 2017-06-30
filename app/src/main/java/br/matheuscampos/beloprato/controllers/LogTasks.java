package br.matheuscampos.beloprato.controllers;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import br.matheuscampos.beloprato.Constants;
import br.matheuscampos.beloprato.R;
import br.matheuscampos.beloprato.view.LoginActivity;
import br.matheuscampos.beloprato.view.MainActivity;

/**
 * Created by matheusoliveira on 29/06/2017.
 */

public class LogTasks {

    private Context mContext;
    private ProgressDialog mProgressDialog;
    private AlertDialog.Builder mAlertDialog;
    private LoginActivity mLoginActivity;
    private MainActivity mMainActivity;
    private Preferences mPreferences;

    public LogTasks(Context context, LoginActivity loginActivity) {
        this.mContext = context;
        this.mLoginActivity = loginActivity;

        initComponents();

    }

    public LogTasks(Context context, MainActivity mainActivity) {
        this.mContext = context;
        this.mMainActivity = mainActivity;

        initComponents();
    }

    private void initComponents() {
        mPreferences = new Preferences(mContext);

        mProgressDialog = new ProgressDialog(mContext);
        mAlertDialog = new AlertDialog.Builder(mContext);
        mAlertDialog.setPositiveButton(mContext.getString(R.string.positive_button),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        mProgressDialog.setCancelable(false);
    }

    public void login(final String username, final String password) {

        RequestQueue queue = Volley.newRequestQueue(mContext);
        JSONObject object = new JSONObject();

        mProgressDialog.setTitle(mContext.getString(R.string.login));
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(mContext.getString(R.string.doing_login));
        mProgressDialog.show();

        mAlertDialog.setTitle(mContext.getString(R.string.login));

        try {
            object.put("user", username);
            object.put("password", password);
        } catch (JSONException e) {
            Toast.makeText(mContext, "Erro ao enviar dados", Toast.LENGTH_SHORT).show();
        }
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST,
                Constants.URL_LOGIN , object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        String ans, id;
                        try {
                            ans = response.getString("status");
                            id = response.getString("id");
                        } catch (JSONException e) {
                            ans = "Erro ao receber resposta";
                            id = "error";
                        }
                        if (ans.equals(Constants.LOGGED) || ans.equals(Constants.ALOGGED)
                                && !id.equals("error")) {
                            Intent intent = new Intent(mContext, MainActivity.class);
                            mContext.startActivity(intent);
                            mPreferences.saveUser(id, username, password);
                            mLoginActivity.finish();
                        } else
                            mAlertDialog.setMessage(ans).show();
                        mProgressDialog.cancel();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mProgressDialog.cancel();
                        String ans;
                        ans = error.toString();

                        mAlertDialog.setMessage(ans).show();

                    }
        });
        queue.add(postRequest);
    }

    public void logout() {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        JSONObject object = new JSONObject();
        HashMap<String, String> map = mPreferences.getUserToLogin();
        String username = map.get(Constants.USER);

        mProgressDialog.setTitle(mContext.getString(R.string.logout));
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(mContext.getString(R.string.doing_logout));
        mProgressDialog.show();

        try {
            object.put("user", username);
        } catch (JSONException e) {
            Toast.makeText(mContext, "Erro ao enviar dados", Toast.LENGTH_SHORT).show();
        }

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST,
                Constants.URL_LOGOUT, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String ans;
                        try {
                            ans = response.getString("status");
                        } catch (JSONException e) {
                            ans = "Erro ao receber resposta";
                        }
                        if (ans.equals(Constants.LOGGED_OUT)) {
                            mPreferences.removeUser();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(mContext, LoginActivity.class);
                                    mContext.startActivity(intent);
                                    mMainActivity.finish();
                                    mProgressDialog.cancel();
                                }
                            }, 500);
                        } else {
                            mProgressDialog.cancel();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mProgressDialog.cancel();
                    }
                });
        queue.add(postRequest);

    }

    public void loginSaved() {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        JSONObject object = new JSONObject();
        HashMap<String, String> map = mPreferences.getUserToLogin();
        String username = map.get(Constants.USER);
        String password = map.get(Constants.PASSWORD);

        if(!username.equals(Constants.NULL) && !password.equals(Constants.NULL)) {
            try {
                object.put("user", username);
                object.put("password", password);
            } catch (JSONException e) {
                Toast.makeText(mContext, "Erro ao enviar dados", Toast.LENGTH_SHORT).show();
            }
            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST,
                    Constants.URL_LOGIN, object,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            String ans;
                            try {
                                ans = response.getString("status");
                            } catch (JSONException e) {
                                ans = "Erro ao receber resposta";
                            }
                            if (ans.equals(Constants.LOGGED) || ans.equals(Constants.ALOGGED)) {
                                Intent intent = new Intent(mContext, MainActivity.class);
                                mContext.startActivity(intent);
                                mLoginActivity.finish();
                            } else {
                                mLoginActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mLoginActivity.setLoginVisibility();
                                    }
                                });
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            mLoginActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mLoginActivity.setLoginVisibility();
                                }
                            });
                        }
            });
            queue.add(postRequest);

        } else {
            mLoginActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLoginActivity.setLoginVisibility();
                }
            });
        }

    }

}
