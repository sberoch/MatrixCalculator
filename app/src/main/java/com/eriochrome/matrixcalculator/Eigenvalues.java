package com.eriochrome.matrixcalculator;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.apache.commons.math3.fraction.Fraction;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.NonSquareMatrixException;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class Eigenvalues extends AppCompatActivity
{

    RelativeLayout eigenvaluesRL;
    RelativeLayout eigenvectorsRL;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eigenvalues);
        Bundle b = getIntent().getExtras();

        double[][] mtx = (double[][]) b.getSerializable("Matrix");
        RealMatrix matrix = new Array2DRowRealMatrix(mtx);
        eigenvaluesRL = findViewById(R.id.eigenvaluesRL);
        eigenvectorsRL = findViewById(R.id.eigenvectorsRL);


        try {

            EigenDecomposition eigenDecomposition = new EigenDecomposition(matrix);
            if (eigenDecomposition.hasComplexEigenvalues())
            {
                ScrollView sv = findViewById(R.id.eigenScrollView);
                sv.setVisibility(View.INVISIBLE);
                TextView errorHandler = findViewById(R.id.errorTextEigen);
                errorHandler.setText(getResources().getString(R.string.eigenComplex));
            } else
            {
                createEigenvaluesVector(eigenDecomposition.getD(), eigenvaluesRL);
                createEigenvectorsDisplay(eigenDecomposition, eigenvectorsRL);

            }
        } catch (NonSquareMatrixException e) {
            ScrollView sv = findViewById(R.id.eigenScrollView);
            sv.setVisibility(View.INVISIBLE);
            TextView errorHandler = findViewById(R.id.errorTextEigen);
            errorHandler.setText(getResources().getString(R.string.notSquare));
        }
    }



    public void onClick(View view)
    {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.button_touch);
        mp.start();
        this.finish();
    }




    private void createEigenvaluesVector(RealMatrix matrix, RelativeLayout relativeLayout) {
        TextView[] textViews = new TextView[matrix.getColumnDimension()];

        int widthTextView = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 45, getResources().getDisplayMetrics());
        int heightTextView = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 50, getResources().getDisplayMetrics());
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 40, getResources().getDisplayMetrics());

        for (int i = 0; i < matrix.getColumnDimension(); i++) {
            TextView textView = new TextView(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(widthTextView , heightTextView);
            textView.setLayoutParams(params);
            textView.setTextSize(14);
            textView.setId(i+1); //Cualquier cosa
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
            double entry = matrix.getEntry(i,i);
            Fraction fractionEntry = new Fraction(entry);
            textView.setText(fractionEntry.toString());

            relativeLayout.addView(textView);
            textViews[i] = textView;

            if (i > 0) {
                params.addRule(RelativeLayout.RIGHT_OF, textViews[i-1].getId());
                params.setMargins(margin, 0, 0, 0);
            }
        }


    }


    private void createEigenvectorsDisplay(EigenDecomposition eigenDecomposition, RelativeLayout relativeLayout) {
        int dim = eigenDecomposition.getD().getColumnDimension();

        int widthTextView = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 40, getResources().getDisplayMetrics());
        int heightTextView = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 50, getResources().getDisplayMetrics());
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics());
        int marginBetweenVectors = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 65, getResources().getDisplayMetrics());
        int heightNoDiago = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 60, getResources().getDisplayMetrics());
        int widthNoDiago = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 300, getResources().getDisplayMetrics());


        TextView[][] entries = new TextView[dim][dim];

        int i = 0;
        boolean diagonalizable = true;

        while (i < dim && diagonalizable)
        {
            try
            {
                RealVector evec = eigenDecomposition.getEigenvector(i);
                Double scale = 0.0;
                for (int k = 0; k < dim; k++) {
                    if (evec.getEntry(k) != 0) {
                        scale = 1 / evec.getEntry(k);
                    }
                }
                for (int j = 0; j < dim; j++)
                {
                    TextView textView = new TextView(this);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(widthTextView , heightTextView);
                    textView.setLayoutParams(params);
                    textView.setTextSize(14);
                    textView.setId(10*(i+1) + j+1); //Cualquier cosa
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    }
                    entries[i][j] = textView;
                    relativeLayout.addView(textView);

                    Fraction value = new Fraction(evec.getEntry(j) * scale);
                    textView.setText(value.toString());

                    if (j > 0) {
                        params.addRule(RelativeLayout.BELOW, entries[i][j - 1].getId());
                        params.setMargins(0, margin, 0, 0);
                    }
                    if (i > 0) {
                        params.addRule(RelativeLayout.RIGHT_OF, entries[i-1][0].getId());
                        params.setMargins(marginBetweenVectors, 0, 0,0);
                    }
                    if (j > 0 && i > 0) {
                        params.setMargins(marginBetweenVectors, margin, 0,0);
                    }


                }

            }
            catch (Exception e)
            {
                relativeLayout.removeAllViews();
                diagonalizable = false;
                TextView textView = new TextView(this);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(widthNoDiago , heightNoDiago);
                textView.setLayoutParams(params);
                textView.setTextSize(14);
                relativeLayout.addView(textView);
                textView.setText(getResources().getString(R.string.noDiago));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
            }
            i++;
        }
    }


}
