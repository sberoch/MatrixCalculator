package com.eriochrome.matrixcalculator;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.commons.math3.fraction.Fraction;
import org.apache.commons.math3.linear.RealMatrix;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            Keyboard firstFragment = new Keyboard();
            firstFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.button_touch);

        switch (item.getItemId()) {
            case R.id.menuItemHelp:
                mp.start();
                Intent i = new Intent(this, Help.class);
                startActivity(i);
                return true;
            case R.id.menuItemRate:
                mp.start();
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void createMatrixDinamically(RealMatrix matrix, RelativeLayout relativeLayout, Context context, int width, int height, int marginSp, int textSize) {

        TextView[][] entries = new TextView[matrix.getRowDimension()][matrix.getColumnDimension()];

        int widthTextView = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, width, context.getResources().getDisplayMetrics());
        int heightTextView = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, height, context.getResources().getDisplayMetrics());
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, marginSp, context.getResources().getDisplayMetrics());

        for (int i=0; i<matrix.getRowDimension(); i++) {
            for (int j = 0; j < matrix.getColumnDimension(); j++) {

                TextView textView = new TextView(context);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(widthTextView , heightTextView);
                textView.setLayoutParams(params);
                textView.setTextSize(textSize);
                textView.setId(10*(i+1) + j+1);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }

                double entry = matrix.getEntry(i,j);
                Fraction fractionEntry = new Fraction(entry);
                textView.setText(fractionEntry.toString());

                relativeLayout.addView(textView);
                entries[i][j] = textView;
                if (j > 0) {
                    params.addRule(RelativeLayout.RIGHT_OF, entries[i][j - 1].getId());
                    params.setMargins(margin, 0, 0, 0);
                }
                if (i > 0) {
                    params.addRule(RelativeLayout.BELOW, entries[i-1][0].getId());
                    params.setMargins(0, margin, 0,0);
                }
                if (j > 0 && i > 0) {
                    params.setMargins(margin, margin, 0,0);
                }
            }
        }
    }



}
