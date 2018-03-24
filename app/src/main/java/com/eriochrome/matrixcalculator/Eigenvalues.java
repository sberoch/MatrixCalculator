package com.eriochrome.matrixcalculator;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import org.apache.commons.math3.fraction.Fraction;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class Eigenvalues extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eigenvalues);
        Bundle b = getIntent().getExtras();

        double[][] mtx = (double[][]) b.getSerializable("Matrix");
        RealMatrix matrix = new Array2DRowRealMatrix(mtx);

        int col = matrix.getColumnDimension();
        int row = matrix.getRowDimension();
        TextView[] eigenvalues = new TextView[col];
        eigenvalues[0] = findViewById(R.id.lambda1);
        eigenvalues[1] = findViewById(R.id.lambda2);
        eigenvalues[2] = findViewById(R.id.lambda3);


        TextView[][] vectors = new TextView[row][col];
        vectors[0][0] = findViewById(R.id.p11);
        vectors[0][1] = findViewById(R.id.p12);
        vectors[0][2] = findViewById(R.id.p13);
        vectors[1][0] = findViewById(R.id.p21);
        vectors[1][1] = findViewById(R.id.p22);
        vectors[1][2] = findViewById(R.id.p23);
        vectors[2][0] = findViewById(R.id.p31);
        vectors[2][1] = findViewById(R.id.p32);
        vectors[2][2] = findViewById(R.id.p33);


        EigenDecomposition eigenDecomposition = new EigenDecomposition(matrix);
        if (eigenDecomposition.hasComplexEigenvalues())
        {
            ScrollView sv = findViewById(R.id.eigenScrollView);
            sv.setVisibility(View.INVISIBLE);
            TextView errorHandler = findViewById(R.id.errorTextEigen);
            errorHandler.setText(getResources().getString(R.string.eigenComplex));
        } else
            {
            for (int i = 0; i < col; i++)
            {
                Fraction eigenvalue = new Fraction(eigenDecomposition.getRealEigenvalue(i));
                eigenvalues[i].setText(eigenvalue.toString());
            }
            for (int i = 0; i < col; i++)
            {
                try
                {
                    RealVector evec = eigenDecomposition.getEigenvector(i);
                    Double scale = 0.0;
                    for (int k = 0; k < col; k++) {
                        if (evec.getEntry(k) != 0) {
                            scale = 1 / evec.getEntry(k);
                        }
                    }
                    for (int j = 0; j < col; j++)
                    {
                        Fraction value = new Fraction(evec.getEntry(j) * scale);
                        vectors[i][j].setText(value.toString());
                    }
                }
                catch (Exception e)
                {
                    for (int j = 0; j < col; j++) {
                        vectors[i][j].setVisibility(View.INVISIBLE);
                    }
                }
            }
        }
    }

    public void onClick(View view)
    {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.button_touch);
        mp.start();
        this.finish();
    }

}
