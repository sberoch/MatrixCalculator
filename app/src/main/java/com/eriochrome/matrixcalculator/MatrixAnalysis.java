package com.eriochrome.matrixcalculator;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import org.apache.commons.math3.fraction.Fraction;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.NonSquareMatrixException;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularValueDecomposition;

import java.text.DecimalFormat;

public class MatrixAnalysis extends AppCompatActivity {

    RelativeLayout relativeLayout;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix_analysis);
        DecimalFormat df = new DecimalFormat("###.#");

        Bundle b = getIntent().getExtras();
        double[][] mtx = (double[][])b.getSerializable("Matrix");
        RealMatrix matrix = new Array2DRowRealMatrix(mtx);
        relativeLayout = findViewById(R.id.transpRelativeLayout);


        //Rango
        SingularValueDecomposition auxMat = new SingularValueDecomposition(matrix);
        double[] singularValues = auxMat.getSingularValues();
        int rank = 0;
        for (int i = 0; i<singularValues.length; i++) {
            if (singularValues[i] != 0) {
                rank++;
            }
        }
        TextView rank_textView = findViewById(R.id.rank_textView);
        rank_textView.setText(Integer.toString(rank));


        //Trace
        try {
            double trace = matrix.getTrace();
            TextView trace_textView = findViewById(R.id.trace_textView);
            Fraction traceFrac = new Fraction(trace);
            trace_textView.setText(traceFrac.toString());

        } catch (NonSquareMatrixException e) {
            TextView trace_textView = findViewById(R.id.trace_textView);
            trace_textView.setText(getResources().getString(R.string.notSquare));
        }

        //Transposed
        RealMatrix transposedMatrix = matrix.transpose();
        MainActivity main = new MainActivity();
        main.createMatrixDinamically(transposedMatrix, relativeLayout, this, 45, 40, 14,14);


        //Determinant
        try {
            boolean isSquare = matrix.isSquare();
            double det = MatrixOperations.matrixDeterminant(matrix.getData(), isSquare);
            TextView det_textView = findViewById(R.id.det_textView);
            Fraction detFrac = new Fraction(det);
            det_textView.setText(detFrac.toString());

        } catch (Exception e) {
            TextView det_textView = findViewById(R.id.det_textView);
            det_textView.setText(getResources().getString(R.string.notSquare));
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
