package com.eriochrome.matrixcalculator;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.apache.commons.math3.analysis.function.Sin;
import org.apache.commons.math3.fraction.Fraction;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.DiagonalMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.linear.SingularValueDecomposition;

public class DVS extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dvs);
        Bundle b = getIntent().getExtras();

        double[][] mtx = (double[][])b.getSerializable("Matrix");
        RealMatrix matrix = new Array2DRowRealMatrix(mtx);
        int dim  = matrix.getColumnDimension();

        TextView[] vecU = new TextView[dim*dim];
        vecU[0] = findViewById(R.id.u11);
        vecU[1] = findViewById(R.id.u12);
        vecU[2] = findViewById(R.id.u13);
        vecU[3] = findViewById(R.id.u21);
        vecU[4] = findViewById(R.id.u22);
        vecU[5] = findViewById(R.id.u23);
        vecU[6] = findViewById(R.id.u31);
        vecU[7] = findViewById(R.id.u32);
        vecU[8] = findViewById(R.id.u33);

        TextView[] vecSigma = new TextView[dim*dim];
        vecSigma[0] = findViewById(R.id.s11);
        vecSigma[1] = findViewById(R.id.s12);
        vecSigma[2] = findViewById(R.id.s13);
        vecSigma[3] = findViewById(R.id.s21);
        vecSigma[4] = findViewById(R.id.s22);
        vecSigma[5] = findViewById(R.id.s23);
        vecSigma[6] = findViewById(R.id.s31);
        vecSigma[7] = findViewById(R.id.s32);
        vecSigma[8] = findViewById(R.id.s33);

        TextView[] vecVt = new TextView[dim*dim];
        vecVt[0] = findViewById(R.id.vt11);
        vecVt[1] = findViewById(R.id.vt12);
        vecVt[2] = findViewById(R.id.vt13);
        vecVt[3] = findViewById(R.id.vt21);
        vecVt[4] = findViewById(R.id.vt22);
        vecVt[5] = findViewById(R.id.vt23);
        vecVt[6] = findViewById(R.id.vt31);
        vecVt[7] = findViewById(R.id.vt32);
        vecVt[8] = findViewById(R.id.vt33);

        SingularValueDecomposition svd = new SingularValueDecomposition(matrix);
        RealMatrix U = svd.getU();
        RealMatrix Sigma = svd.getS();
        RealMatrix V = svd.getV();
        U = denormalize(U);
        V = denormalize(V);
        RealMatrix Vt = V.transpose();
        MatrixOperations.matrixRepresentationAndNormalizationCol(vecU, U);
        MatrixOperations.matrixRepresentationSigma(vecSigma, Sigma);
        MatrixOperations.matrixRepresentationAndNormalizationFil(vecVt, Vt);
    }



    public void onClick(View view) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.button_touch);
        mp.start();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private RealMatrix denormalize(RealMatrix M)
    {
        for (int i = 0; i < 3; i++)
        {
            RealVector vec = M.getColumnVector(i);
            double scale = 1.0;
            for (int j = 0; j < 3; j++)
            {
                if (vec.getEntry(j) != 0)
                {
                    scale = 1 / vec.getEntry(j);
                }
            }
            vec = vec.mapMultiply(scale);
            M.setColumnVector(i, vec);
        }
        return M;
    }
}
