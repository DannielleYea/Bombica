package com.example.zvjerka.bombica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class end_screen extends AppCompatActivity {
    private long vrijeme;
    private TextView dod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        double width = displayMetrics.widthPixels;
        double height = displayMetrics.heightPixels;

        getWindow().setLayout(400, 800);

        Intent podaci = getIntent();

        vrijeme = podaci.getLongExtra("VRIJEME", 0);

        dod = (TextView) findViewById(R.id.do_kraja);

        dod.setText( String.format("%02d:%02d", TimeUnit.MILLISECONDS.toSeconds(vrijeme), (vrijeme - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(vrijeme)))/10));
    }
}
