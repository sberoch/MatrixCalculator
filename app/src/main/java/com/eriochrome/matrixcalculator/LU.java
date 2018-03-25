package com.eriochrome.matrixcalculator;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularMatrixException;

public class LU extends AppCompatActivity {

    RelativeLayout lRelativeLayout;
    RelativeLayout uRelativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lu);
        Bundle b = getIntent().getExtras();

        double[][] mtx = (double[][])b.getSerializable("Matrix");
        RealMatrix matrix = new Array2DRowRealMatrix(mtx);
        lRelativeLayout = findViewById(R.id.lRelativeLayout);
        uRelativeLayout = findViewById(R.id.uRelativeLayout);


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
                u.setVisibility(View.INVISIBLE);
                l.setVisibility(View.INVISIBLE);
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
                MainActivity main = new MainActivity();
                main.createMatrixDinamically(L, lRelativeLayout, this, 35,15,8,10);
                main.createMatrixDinamically(U, uRelativeLayout, this, 35,15,8,10);
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
        this.finish();
    }

}
