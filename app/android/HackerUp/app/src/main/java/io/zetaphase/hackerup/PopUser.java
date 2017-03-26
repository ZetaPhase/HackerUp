package io.zetaphase.hackerup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dave Ho on 3/26/2017.
 */

public class PopUser extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup_user);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*0.8), (int) (height*0.8));

        Intent intent = getIntent();
        int clickposition = Integer.valueOf(intent.getStringExtra("CLICKPOSITION"));
        String json =intent.getStringExtra("JSON");

        User user = MainActivity.nearbyUsers.get(clickposition);

        TextView fullName = (TextView) findViewById(R.id.viewUserName);
        TextView userName = (TextView) findViewById(R.id.viewUserUsername);
        TextView email = (TextView) findViewById(R.id.viewUserEmail);
        TextView bio = (TextView) findViewById(R.id.viewUserBio);
        TextView company = (TextView) findViewById(R.id.viewUserCompany);
        TextView location = (TextView) findViewById(R.id.viewUserLocation);
        TextView repoCount = (TextView) findViewById(R.id.viewUserRepoCount);
        ImageView hangoutsIcon = (ImageView) findViewById(R.id.viewUserHangouts);
        ImageView githubIcon = (ImageView) findViewById(R.id.viewUserGitHub);

        try {
            JSONObject jsonObject = new JSONObject(json);
            fullName.setText(jsonObject.getString("FullName"));
            userName.setText(jsonObject.getString("GitHubUsername"));
            email.setText(jsonObject.getString("HangoutsEmail"));
            bio.setText(jsonObject.getString("GitHubBio"));
            company.setText(jsonObject.getString("Company"));
            location.setText(jsonObject.getString("HomeLocation"));
            repoCount.setText(jsonObject.getString("RepoCount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
