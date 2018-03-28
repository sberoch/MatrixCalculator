package com.eriochrome.matrixcalculator;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularMatrixException;

public class Inverse extends AppCompatActivity {

    RelativeLayout invRelativeLayout;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inverse);
        Bundle b = getIntent().getExtras();

        double[][] mtx = (double[][])b.getSerializable("Matrix");
        RealMatrix matrix = new Array2DRowRealMatrix(mtx);
        invRelativeLayout = (RelativeLayout)findViewById(R.id.invRelativeLayout);

        TextView errorHandler = findViewById(R.id.errorTextView);
        ImageView corchete1 = findViewById(R.id.corchete1);
        ImageView corchete2 = findViewById(R.id.corchete2);



        try {
            if (!matrix.isSquare()){
                errorHandler.setText(getResources().getString(R.string.notSquare));
            } else {
                RealMatrix inverseMatrix = new LUDecomposition(matrix).getSolver().getInverse();
                corchete1.setVisibility(View.VISIBLE);
                corchete2.setVisibility(View.VISIBLE);
                MainActivity main = new MainActivity();
                main.createMatrixDinamically(inverseMatrix, invRelativeLayout, this, 45,40,14, 14);
            }
        } catch (SingularMatrixException e) {
            errorHandler.setText(getResources().getString(R.string.singularMatrix));
        }

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd.loadAd(new AdRequest.Builder().build());

    }





    public void onClick(View view) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.button_touch);
        mp.start();
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
        this.finish();
    }
}
