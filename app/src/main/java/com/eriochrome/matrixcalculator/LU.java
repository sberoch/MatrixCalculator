package com.eriochrome.matrixcalculator;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularMatrixException;

public class LU extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lu);
        Bundle b = getIntent().getExtras();

        double[][] mtx = (double[][])b.getSerializable("Matrix");
        RealMatrix matrix = new Array2DRowRealMatrix(mtx);

        TextView[] vecL = new TextView[9];
        vecL[0] = findViewById(R.id.l11);
        vecL[1] = findViewById(R.id.l12);
        vecL[2] = findViewById(R.id.l13);
        vecL[3] = findViewById(R.id.l21);
        vecL[4] = findViewById(R.id.l22);
        vecL[5] = findViewById(R.id.l23);
        vecL[6] = findViewById(R.id.l31);
        vecL[7] = findViewById(R.id.l32);
        vecL[8] = findViewById(R.id.l33);

        TextView[] vecU = new TextView[9];
        vecU[0] = findViewById(R.id.ulu11);
        vecU[1] = findViewById(R.id.ulu12);
        vecU[2] = findViewById(R.id.ulu13);
        vecU[3] = findViewById(R.id.ulu21);
        vecU[4] = findViewById(R.id.ulu22);
        vecU[5] = findViewById(R.id.ulu23);
        vecU[6] = findViewById(R.id.ulu31);
        vecU[7] = findViewById(R.id.ulu32);
        vecU[8] = findViewById(R.id.ulu33);

        TextView errorHandler = findViewById(R.id.errorTextViewLU);
        TextView corchete3 = findViewById(R.id.corchete3);
        TextView corchete4 = findViewById(R.id.corchete4);
        TextView corchete5 = findViewById(R.id.corchete5);
        TextView corchete6 = findViewById(R.id.corchete6);
        TextView l = findViewById(R.id.l);
        TextView u = findViewById(R.id.u);

        try {
            if (!matrix.isSquare()){
                errorHandler.setText(getResources().getString(R.string.notSquare));
            } else {
                LUDecomposition luDecomposition = new LUDecomposition(matrix);
                luDecomposition.getSolver().getInverse(); //Para triggerear la exception
                RealMatrix L = luDecomposition.getL();
                RealMatrix U = luDecomposition.getU();
                u.setVisibility(View.VISIBLE);
                l.setVisibility(View.VISIBLE);
                corchete3.setVisibility(View.VISIBLE);
                corchete4.setVisibility(View.VISIBLE);
                corchete5.setVisibility(View.VISIBLE);
                corchete6.setVisibility(View.VISIBLE);
                MatrixOperations.matrixRepresentation(vecL, L);
                MatrixOperations.matrixRepresentation(vecU, U);
            }
        } catch (SingularMatrixException e) {
            errorHandler.setText(getResources().getString(R.string.singularMatrix));
            u.setVisibility(View.INVISIBLE);
            l.setVisibility(View.INVISIBLE);
            corchete3.setVisibility(View.INVISIBLE);
            corchete4.setVisibility(View.INVISIBLE);
            corchete5.setVisibility(View.INVISIBLE);
            corchete6.setVisibility(View.INVISIBLE);
        }



    }



    public void onClick(View view) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.button_touch);
        mp.start();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

}
