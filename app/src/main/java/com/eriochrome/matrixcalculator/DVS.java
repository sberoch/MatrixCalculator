package com.eriochrome.matrixcalculator;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.commons.math3.fraction.Fraction;
import org.apache.commons.math3.fraction.FractionConversionException;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.linear.SingularValueDecomposition;

import java.text.DecimalFormat;

public class DVS extends AppCompatActivity {

    RelativeLayout singularValuesRL;
    RelativeLayout uMatrixRL;
    RelativeLayout VtMatrixRL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dvs);
        Bundle b = getIntent().getExtras();

        double[][] mtx = (double[][])b.getSerializable("Matrix");
        RealMatrix matrix = new Array2DRowRealMatrix(mtx);
        singularValuesRL = findViewById(R.id.singularValuesRL);
        uMatrixRL = findViewById(R.id.uMatrixRL);
        VtMatrixRL = findViewById(R.id.VtMatrixRL);

        SingularValueDecomposition svd = new SingularValueDecomposition(matrix);
        RealMatrix U = svd.getU();
        RealMatrix Sigma = svd.getS();
        RealMatrix V = svd.getV();
        U = denormalize(U);
        V = denormalize(V);
        U = U.transpose();
        RealMatrix Vt = V.transpose();

        createValuesVector(Sigma, singularValuesRL);
        createVectorsDisplay(U, uMatrixRL);
        createVectorsDisplay(Vt, VtMatrixRL);
    }



    public void onClick(View view) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.button_touch);
        mp.start();
        this.finish();
    }

    private RealMatrix denormalize(RealMatrix M)
    {
        for (int i = 0; i < M.getColumnDimension(); i++)
        {
            RealVector vec = M.getColumnVector(i);
            double scale = 1.0;
            for (int j = 0; j < M.getRowDimension(); j++)
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


    public void createValuesVector(RealMatrix matrix, RelativeLayout relativeLayout) {
        TextView[] textViews = new TextView[matrix.getColumnDimension()];
        DecimalFormat df = new DecimalFormat("###.##");

        int widthTextView = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 70, getResources().getDisplayMetrics());
        int heightTextView = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 50, getResources().getDisplayMetrics());
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics());

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

            double tolerance = 1E-4;
            if (Math.abs(Math.floor(entry)- entry) < tolerance)
            {
                Fraction fractionEntry = new Fraction(entry, MatrixOperations.MAX_DENOMINATOR);
                textView.setText(fractionEntry.toString());
            }
            else
            {
                Double entrySquared = entry*entry;
                String sq = "\u221A";
                String singularValue = sq + "(" + df.format(entrySquared) + ")";
                textView.setText(singularValue);
            }

            relativeLayout.addView(textView);
            textViews[i] = textView;

            if (i > 0) {
                params.addRule(RelativeLayout.RIGHT_OF, textViews[i-1].getId());
                params.setMargins(margin, 0, 0, 0);
            }
        }


    }



    public void createVectorsDisplay(RealMatrix matrix, RelativeLayout relativeLayout) {
        int col = matrix.getColumnDimension();
        int row = matrix.getRowDimension();
        TextView[][] entries = new TextView[row][col];

        int widthTextView = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 70, getResources().getDisplayMetrics());
        int heightTextView = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 50, getResources().getDisplayMetrics());
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics());

        String sq = "\u221A";


        for (int i = 0; i < row; i++)
        {
            double norm = matrix.getRowVector(i).getNorm();
            for (int j = 0; j < col; j++)
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
                double entry = matrix.getEntry(i,j);

                try {
                    Fraction fractionEntry = new Fraction(entry, MatrixOperations.MAX_DENOMINATOR);
                    Fraction fractionSquaredNorm = new Fraction(norm*norm);
                    if ((entry == 0) || ((entry == 1) && (norm == 1))) {
                        textView.setText(fractionEntry.toString());
                    }
                    else
                    {
                        textView.setText(fractionEntry.toString() + "/" + sq + "(" + fractionSquaredNorm.toString() + ")" );
                    }
                } catch (FractionConversionException e) {
                    textView.setText(Double.toString(entry));
                }



                if (j > 0) {
                    params.addRule(RelativeLayout.BELOW, entries[i][j - 1].getId());
                    params.setMargins(0, margin, 0, 0);
                }
                if (i > 0) {
                    params.addRule(RelativeLayout.RIGHT_OF, entries[i-1][0].getId());
                    params.setMargins(margin, 0, 0,0);
                }
                if (j > 0 && i > 0) {
                    params.setMargins(margin, margin, 0,0);
                }
            }
        }
    }
}
