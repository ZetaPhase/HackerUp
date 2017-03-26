package io.zetaphase.hackerup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log

import org.json.JSONObject

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by Dave Ho on 3/14/2017.
 */

class SplashScreen : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        Handler().postDelayed({
            val i = Intent(this@SplashScreen, LoginActivity::class.java)
            startActivity(i)
            finish()
        }, SPLASH_TIME_OUT.toLong())
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

    companion object {

        private val SPLASH_TIME_OUT = 3000
    }
}