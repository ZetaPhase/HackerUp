package io.zetaphase.hackerup

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import org.json.JSONException
import org.json.JSONObject

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

class LoginActivity : Activity() {

    internal var _fullName: EditText
    internal var _emailText: EditText
    internal var _token: EditText
    internal var _ipAddress: EditText
    internal var _loginButton: Button
    private var response: String
    private var statusCode: Int = 0
    internal var _getToken: TextView
    internal val MYPREFERENCES = "HACKERUP"

    private var serverAddress: String? = null
    //private String serverAddress = "192.168.1.65";
    //private String serverAddress = "10.10.179.241:8000";

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        _emailText = findViewById(R.id.loginHangoutsEmail) as EditText
        _loginButton = findViewById(R.id.btn_login) as Button
        _getToken = findViewById(R.id.link_token) as TextView
        _token = findViewById(R.id.loginToken) as EditText
        _fullName = findViewById(R.id.loginName) as EditText
        _ipAddress = findViewById(R.id.ipAddress) as EditText

        val sharedpreferences = getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE)
        val apikey = sharedpreferences.getString("ApiKey", "null")
        if (apikey != "null") {
            val name = sharedpreferences.getString("FullName", "null")
            val email = sharedpreferences.getString("HangoutsEmail", "null")
            val token = sharedpreferences.getString("GHAuthToken", "null")
            val ipAddress = sharedpreferences.getString("IPAddress", "null")
            _emailText.setText(email)
            _fullName.setText(name)
            _token.setText(token)
            _ipAddress.setText(ipAddress)
            serverAddress = ipAddress
            try {
                login()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

        }

        _loginButton.setOnClickListener {
            try {
                login()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }

        _getToken.setOnClickListener {
            Log.d("TOKEN", "trying to create new token")
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/settings/tokens/new"))
            startActivity(browserIntent)
        }

    }

    @Throws(InterruptedException::class)
    fun login() {
        Log.d(TAG, "Login")

        if (!validate()) {
            return
        }

        _loginButton.isEnabled = false

        val progressDialog = ProgressDialog(this@LoginActivity)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Authenticating...")
        progressDialog.show()

        val email = _emailText.text.toString()
        val fullName = _fullName.text.toString()
        val token = _token.text.toString()
        val ipAddress = _ipAddress.text.toString()
        var response: String? = null

        // TODO: Implement your own authentication logic here.

        val thread = Thread(Runnable {
            val login = JSONObject()
            try {
                login.put("FullName", fullName)
                login.put("hangoutsemail", email)
                login.put("ghauthtoken", token)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            Log.d("OBJECT", login.toString())
            serverAddress = ipAddress
            val a = request(serverAddress!! + "/a/register", login)

            statusCode = Integer.valueOf(a[0])
            response = a[1].toString()
            Log.d("REPONSE", response)
        })

        thread.start()
        thread.join()

        // put away keyboard when login is pressed
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        android.os.Handler().postDelayed(
                {
                    // TODO: Put your logic here
                    val r = response
                    val status = statusCode
                    Log.d("RESPONSE", r)
                    if (status == 200) {
                        try {
                            val jsonObj = JSONObject(r)
                            val fullname = jsonObj.get("FullName").toString()
                            val hangoutsemail = jsonObj.get("HangoutsEmail").toString()
                            val token = jsonObj.get("GHAuthToken").toString()
                            val apikey = jsonObj.get("ApiKey").toString()
                            val sharedpreferences = getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE)
                            val editor = sharedpreferences.edit()
                            editor.putString("FullName", fullname)
                            editor.putString("HangoutsEmail", hangoutsemail)
                            editor.putString("GHAuthToken", token)
                            editor.putString("ApiKey", apikey)
                            editor.putString("IPAddress", ipAddress)
                            editor.commit()
                            Log.d("SHAREDPREFERENCES", sharedpreferences.getString("ApiKey", ""))
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }

                        onLoginSuccess()
                    } else if (status == 401) {
                        onLoginFailed("Invalid Auth Token.")
                    } else if (status == 400) {
                        onLoginFailed("Invalid Fields.")
                    }
                    //onLoginFailed();
                    progressDialog.dismiss()
                }, 3000)
    }

    private fun request(urlString: String, jsonObj: JSONObject): Array<String> {
        // TODO Auto-generated method stub

        val chaine = StringBuffer("")
        var code = -1
        try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.setRequestProperty("User-Agent", "")
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-type", "application/json")
            connection.doInput = true
            connection.connect()


            Log.d("REQUESTOUTPUT", "requesting")
            val b = jsonObj.toString().toByteArray()
            val outputStream = connection.outputStream
            outputStream.write(b)

            code = connection.responseCode

            val inputStream = connection.inputStream

            val rd = BufferedReader(InputStreamReader(inputStream))
            var line = ""
            while ((line = rd.readLine()) != null) {
                chaine.append(line)
            }

        } catch (e: IOException) {
            // writing exception to log
            e.printStackTrace()
        }

        Log.d("STATUSCODE", "" + code)

        val result = arrayOfNulls<String>(2)
        result[0] = "" + code
        result[1] = chaine.toString()

        return result
    }

    override fun onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true)
    }

    fun onLoginSuccess() {
        _loginButton.isEnabled = true
        val i = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(i)
        finish()
    }

    fun onLoginFailed(type: String) {
        _loginButton.isEnabled = true
        Toast.makeText(this, type, Toast.LENGTH_LONG).show()
    }

    fun validate(): Boolean {
        var valid = true

        val email = _emailText.text.toString()
        //String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.error = "enter a valid email address"
            valid = false
        } else {
            _emailText.error = null
        }
        /*
        if (password.isEmpty() || password.length() < 4 || password.length() > 15) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }
        */
        return valid
    }

    companion object {
        private val TAG = "LoginActivity"
    }
}