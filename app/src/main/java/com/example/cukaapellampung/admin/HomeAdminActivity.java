package com.example.cukaapellampung.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.cukaapellampung.R;
import com.example.cukaapellampung.session.PrefSetting;
import com.example.cukaapellampung.session.SessionManager;
import com.example.cukaapellampung.users.LoginActivity;

public class HomeAdminActivity extends AppCompatActivity {

    CardView cardexit, cardDataCuka, cardInputCuka, cardProfile;

    SessionManager session;
    SharedPreferences prefs;
    PrefSetting prefSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        prefSetting = new PrefSetting(this);
        prefs = prefSetting.getSharePreference();

        session = new SessionManager(HomeAdminActivity.this);

        prefSetting.isLogin(session, prefs);

        cardexit = (CardView) findViewById(R.id.cardexit);
        cardDataCuka = (CardView) findViewById(R.id.cardDataCuka);
        cardInputCuka= (CardView) findViewById(R.id.cardInputCuka);
        cardProfile = (CardView) findViewById(R.id.cardProfile);

        cardexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogin(false);
                session.setSessid(0);
                Intent i = new Intent(HomeAdminActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        cardDataCuka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeAdminActivity.this, ActivityDataCuka.class);
                startActivity(i);
                finish();
            }
        });

        cardInputCuka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeAdminActivity.this, InputDataCuka.class);
                startActivity(i);
                finish();
            }
        });

        cardProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeAdminActivity.this, Profile.class);
                startActivity(i);
                finish();
            }
        });

    }
}
