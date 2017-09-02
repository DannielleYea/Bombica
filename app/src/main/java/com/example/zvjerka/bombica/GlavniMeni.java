package com.example.zvjerka.bombica;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GlavniMeni extends AppCompatActivity {

    Button neww;
    Intent game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glavni_meni);

        neww = (Button) findViewById(R.id.nw);
        game = new Intent(this, game.class);

        neww.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(game);
            }
        });
    }
}
