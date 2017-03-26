package io.zetaphase.hackerup;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dave Ho on 3/26/2017.
 */

public class PopUser extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup_user);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width), (int) (height));

        Intent intent = getIntent();
        int clickposition = Integer.valueOf(intent.getStringExtra("CLICKPOSITION"));
        String json = intent.getStringExtra("JSON");

        User user = MainActivity.nearbyUsers.get(clickposition);

        TextView fullName = (TextView) findViewById(R.id.viewUserName);
        final TextView userName = (TextView) findViewById(R.id.viewUserUsername);
        TextView email = (TextView) findViewById(R.id.viewUserEmail);
        TextView bio = (TextView) findViewById(R.id.viewUserBio);
        TextView company = (TextView) findViewById(R.id.viewUserCompany);
        TextView location = (TextView) findViewById(R.id.viewUserLocation);
        TextView repoCount = (TextView) findViewById(R.id.viewUserRepoCount);
        ImageView hangoutsIcon = (ImageView) findViewById(R.id.viewUserHangouts);
        ImageView githubIcon = (ImageView) findViewById(R.id.viewUserGitHub);


        try {
            final JSONObject jsonObject = new JSONObject(json);
            final String _email = jsonObject.getString("HangoutsEmail");
            final String _userName = jsonObject.getString("GitHubUsername");
            fullName.setText(jsonObject.getString("FullName"));
            userName.setText(_userName);
            email.setText(_email);
            bio.setText(jsonObject.getString("GitHubBio"));
            company.setText(jsonObject.getString("Company"));
            location.setText(jsonObject.getString("HomeLocation"));
            repoCount.setText(jsonObject.getString("RepoCount"));
            hangoutsIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://hangouts.google.com/"));
                    Toast.makeText(PopUser.this, "Send Message to " + _email, Toast.LENGTH_LONG);
                    startActivity(browserIntent);
                }
            });

            githubIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/" + _userName));
                    startActivity(browserIntent);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
