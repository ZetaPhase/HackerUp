package io.zetaphase.hackerup;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends Activity {
    private static final String TAG = "LoginActivity";

    EditText _fullName;
    EditText _emailText;
    EditText _token;
    EditText _ipAddress;
    Button _loginButton;
    String response;
    int statusCode;
    TextView _getToken;
    final String MYPREFERENCES = "HACKERUP";

    private String serverAddress;
    //private String serverAddress = "192.168.1.65";
    //private String serverAddress = "10.10.179.241:8000";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _emailText = (EditText) findViewById(R.id.loginHangoutsEmail);
        _loginButton = (Button) findViewById(R.id.btn_login);
        _getToken = (TextView) findViewById(R.id.link_token);
        _token = (EditText) findViewById(R.id.loginToken);
        _fullName = (EditText) findViewById(R.id.loginName);
        _ipAddress = (EditText) findViewById(R.id.ipAddress);

        SharedPreferences sharedpreferences = getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);
        String apikey = sharedpreferences.getString("ApiKey", "null");
        if (!apikey.equals("null")) {
            String name = sharedpreferences.getString("FullName", "null");
            String email = sharedpreferences.getString("HangoutsEmail", "null");
            String token = sharedpreferences.getString("GHAuthToken", "null");
            String ipAddress = sharedpreferences.getString("IPAddress", "null");
            _emailText.setText(email);
            _fullName.setText(name);
            _token.setText(token);
            _ipAddress.setText(ipAddress);
            serverAddress = ipAddress;
            try {
                login();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    login();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        _getToken.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("TOKEN", "trying to create new token");
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/settings/tokens/new"));
                startActivity(browserIntent);
            }
        });

    }

    public void login() throws InterruptedException {
        Log.d(TAG, "Login");

        if (!validate()) {
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String email = _emailText.getText().toString();
        final String fullName = _fullName.getText().toString();
        final String token = _token.getText().toString();
        final String ipAddress = _ipAddress.getText().toString();
        String response = null;

        // TODO: Implement your own authentication logic here.

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject login = new JSONObject();
                try {
                    login.put("FullName", fullName);
                    login.put("hangoutsemail", email);
                    login.put("ghauthtoken", token);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("OBJECT", login.toString());
                serverAddress = ipAddress;
                String[] a = request(serverAddress + "/a/register", login);

                setStatusCode(Integer.valueOf(a[0]));
                setResponse(a[1].toString());
                Log.d("REPONSE", getResponse());
            }
        });

        thread.start();
        thread.join();

        // put away keyboard when login is pressed
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // TODO: Put your logic here
                        String r = getResponse();
                        int status = getStatusCode();
                        Log.d("RESPONSE", r);
                        if (status == 200) {
                            try {
                                JSONObject jsonObj = new JSONObject(r);
                                String fullname = jsonObj.get("FullName").toString();
                                String hangoutsemail = jsonObj.get("HangoutsEmail").toString();
                                String token = jsonObj.get("GHAuthToken").toString();
                                String apikey = jsonObj.get("ApiKey").toString();
                                SharedPreferences sharedpreferences = getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("FullName", fullname);
                                editor.putString("HangoutsEmail", hangoutsemail);
                                editor.putString("GHAuthToken", token);
                                editor.putString("ApiKey", apikey);
                                editor.putString("IPAddress", ipAddress);
                                editor.commit();
                                Log.d("SHAREDPREFERENCES", sharedpreferences.getString("ApiKey", ""));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            onLoginSuccess();
                        } else if (status == 401) {
                            onLoginFailed("Invalid Auth Token.");
                        } else if (status == 400) {
                            onLoginFailed("Invalid Fields.");
                        }
                        //onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    private void setResponse(String response) {
        this.response = response;
    }

    private String getResponse() {
        return this.response;
    }

    private void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    private int getStatusCode() {
        return this.statusCode;
    }

    private String[] request(String urlString, JSONObject jsonObj) {
        // TODO Auto-generated method stub

        StringBuffer chaine = new StringBuffer("");
        int code = -1;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "");
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setDoInput(true);
            connection.connect();


            Log.d("REQUESTOUTPUT", "requesting");
            byte[] b = jsonObj.toString().getBytes();
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(b);

            code = connection.getResponseCode();

            InputStream inputStream = connection.getInputStream();

            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = rd.readLine()) != null) {
                chaine.append(line);
            }

        } catch (IOException e) {
            // writing exception to log
            e.printStackTrace();
            onLoginFailed("Network error");
        }

        Log.d("STATUSCODE", "" + code);

        String result[] = new String[2];
        result[0] = "" + code;
        result[1] = chaine.toString();

        return result;
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    public void onLoginFailed(String type) {
        _loginButton.setEnabled(true);
        Toast.makeText(this, type, Toast.LENGTH_LONG).show();
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        //String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }
        /*
        if (password.isEmpty() || password.length() < 4 || password.length() > 15) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }
        */
        return valid;
    }
}