package io.zetaphase.hackerup

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import org.json.JSONException
import org.json.JSONObject

/**
 * Created by Dave Ho on 3/26/2017.
 */

class PopUser : Activity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.popup_user)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width = dm.widthPixels
        val height = dm.heightPixels

        window.setLayout(width.toInt(), height.toInt())

        val intent = intent
        val clickposition = Integer.valueOf(intent.getStringExtra("CLICKPOSITION"))!!
        val json = intent.getStringExtra("JSON")

        val user = MainActivity.nearbyUsers[clickposition]

        val fullName = findViewById(R.id.viewUserName) as TextView
        val userName = findViewById(R.id.viewUserUsername) as TextView
        val email = findViewById(R.id.viewUserEmail) as TextView
        val bio = findViewById(R.id.viewUserBio) as TextView
        val company = findViewById(R.id.viewUserCompany) as TextView
        val location = findViewById(R.id.viewUserLocation) as TextView
        val repoCount = findViewById(R.id.viewUserRepoCount) as TextView
        val hangoutsIcon = findViewById(R.id.viewUserHangouts) as ImageView
        val githubIcon = findViewById(R.id.viewUserGitHub) as ImageView


        try {
            val jsonObject = JSONObject(json)
            val _email = jsonObject.getString("HangoutsEmail")
            val _userName = jsonObject.getString("GitHubUsername")
            fullName.text = jsonObject.getString("FullName")
            userName.text = _userName
            email.text = _email
            bio.text = jsonObject.getString("GitHubBio")
            company.text = jsonObject.getString("Company")
            location.text = jsonObject.getString("HomeLocation")
            repoCount.text = jsonObject.getString("RepoCount")
            hangoutsIcon.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://hangouts.google.com/"))
                Toast.makeText(this@PopUser, "Send Message to " + _email, Toast.LENGTH_LONG)
                startActivity(browserIntent)
            }

            githubIcon.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/" + _userName))
                startActivity(browserIntent)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }


    }
}
