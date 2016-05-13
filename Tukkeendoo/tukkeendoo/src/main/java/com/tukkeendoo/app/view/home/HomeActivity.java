package com.tukkeendoo.app.view.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.tukkeendoo.app.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String message = getIntent().getExtras().getString("message");
        TextView welcome = (TextView) findViewById(R.id.text_welcome);
        welcome.setText(message);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        startActivity(new Intent(this, LoginActivity.class));
//        finish();
    }
}
