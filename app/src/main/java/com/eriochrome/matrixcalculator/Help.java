package com.eriochrome.matrixcalculator;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        TextView t1 = findViewById(R.id.link1);
        t1.setMovementMethod(LinkMovementMethod.getInstance());
        TextView t2 = findViewById(R.id.link2);
        t2.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void onClick(View view) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.button_touch);
        mp.start();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
