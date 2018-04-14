package com.eriochrome.matrixcalculator;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularMatrixException;

public class LU extends AppCompatActivity {

    RelativeLayout lRelativeLayout;
    RelativeLayout uRelativeLayout;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lu);
        Bundle b = getIntent().getExtras();
        int cols = b.getInt("Cols");
        int rows = b.getInt("Rows");

        double[][] mtx;
        if (Build.VERSION.SDK_INT >= 21)
        {
            mtx = (double[][])b.getSerializable("Matrix");
        }
        else
        {
            Object[] objmtx = (Object[]) b.getSerializable("Matrix");
            mtx = new double[rows][cols];
            for (int i = 0; i < rows; i++)
            {
                mtx[i] = (double[]) objmtx[i];
            }
        }

        RealMatrix matrix = new Array2DRowRealMatrix(mtx);
        lRelativeLayout = findViewById(R.id.lRelativeLayout);
        uRelativeLayout = findViewById(R.id.uRelativeLayout);


        TextView errorHandler = findViewById(R.id.errorTextViewLU);
        TextView l = findViewById(R.id.l);
        TextView u = findViewById(R.id.u);

        try {
            if (!matrix.isSquare()){
                errorHandler.setText(getResources().getString(R.string.notSquare));
                u.setVisibility(View.INVISIBLE);
                l.setVisibility(View.INVISIBLE);
            } else {
                LUDecomposition luDecomposition = new LUDecomposition(matrix);
                luDecomposition.getSolver().getInverse(); //Para triggerear la exception
                RealMatrix L = luDecomposition.getL();
                RealMatrix U = luDecomposition.getU();
                u.setVisibility(View.VISIBLE);
                l.setVisibility(View.VISIBLE);
                MainActivity main = new MainActivity();
                main.createMatrixDinamically(L, lRelativeLayout, this, 55,35,14,14);
                main.createMatrixDinamically(U, uRelativeLayout, this, 55,35,14,14);
            }
        } catch (SingularMatrixException e) {
            errorHandler.setText(getResources().getString(R.string.singularMatrix));
            u.setVisibility(View.INVISIBLE);
            l.setVisibility(View.INVISIBLE);
        }

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5220294257716774/8778214085");

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
