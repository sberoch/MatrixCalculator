package com.eriochrome.matrixcalculator;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import org.apache.commons.math3.fraction.Fraction;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RRQRDecomposition;
import org.apache.commons.math3.linear.RealMatrix;

import java.text.DecimalFormat;

public class MatrixAnalysis extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix_analysis);
        DecimalFormat df = new DecimalFormat("###.#");

        Bundle b = getIntent().getExtras();
        double[][] mtx = (double[][])b.getSerializable("Matrix");
        RealMatrix matrix = new Array2DRowRealMatrix(mtx);
        int col = matrix.getColumnDimension();
        int row = matrix.getRowDimension();


        //Rango
        EigenDecomposition auxMat = new EigenDecomposition(matrix);
        double[] eigenvalues = auxMat.getRealEigenvalues();
        int rank = 0;
        for (int i = 0; i<eigenvalues.length; i++) {
            if (eigenvalues[i] != 0) {
                rank++;
            }
        }
        TextView rank_textView = findViewById(R.id.rank_textView);
        rank_textView.setText(Integer.toString(rank));


        //Trace
        double trace = matrix.getTrace();
        TextView trace_textView = findViewById(R.id.trace_textView);
        Fraction traceFrac = new Fraction(trace);
        trace_textView.setText(traceFrac.toString());

        //Transposed --- (3x3)
        RealMatrix transposedMatrix = matrix.transpose();
        TextView[] vec = new TextView[row*col];
        vec[0] = findViewById(R.id.t11);
        vec[1] = findViewById(R.id.t12);
        vec[2] = findViewById(R.id.t13);
        vec[3] = findViewById(R.id.t21);
        vec[4] = findViewById(R.id.t22);
        vec[5] = findViewById(R.id.t23);
        vec[6] = findViewById(R.id.t31);
        vec[7] = findViewById(R.id.t32);
        vec[8] = findViewById(R.id.t33);
        MatrixOperations.matrixRepresentation(vec, transposedMatrix);

        //Determinant
        double det = MatrixOperations.matrixDeterminant(matrix.getData());
        TextView det_textView = findViewById(R.id.det_textView);
        Fraction detFrac = new Fraction(det);
        det_textView.setText(detFrac.toString());



    }



    public void onClick(View view) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.button_touch);
        mp.start();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
