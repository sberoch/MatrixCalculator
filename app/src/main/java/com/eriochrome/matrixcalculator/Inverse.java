package com.eriochrome.matrixcalculator;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularMatrixException;

public class Inverse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inverse);
        Bundle b = getIntent().getExtras();

        double[][] mtx = (double[][])b.getSerializable("Matrix");
        RealMatrix matrix = new Array2DRowRealMatrix(mtx);
        int col = matrix.getColumnDimension();
        int row = matrix.getRowDimension();


        TextView[] vec = new TextView[row*col];
        vec[0] = findViewById(R.id.i11);
        vec[1] = findViewById(R.id.i12);
        vec[2] = findViewById(R.id.i13);
        vec[3] = findViewById(R.id.i21);
        vec[4] = findViewById(R.id.i22);
        vec[5] = findViewById(R.id.i23);
        vec[6] = findViewById(R.id.i31);
        vec[7] = findViewById(R.id.i32);
        vec[8] = findViewById(R.id.i33);

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
                MatrixOperations.matrixRepresentation(vec, inverseMatrix);
            }
        } catch (SingularMatrixException e) {
            errorHandler.setText(getResources().getString(R.string.singularMatrix));
        }







    }

    public void onClick(View view) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.button_touch);
        mp.start();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
