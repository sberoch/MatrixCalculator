package com.eriochrome.matrixcalculator;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.commons.math3.fraction.Fraction;
import org.apache.commons.math3.linear.NonSquareMatrixException;
import org.apache.commons.math3.linear.RealMatrix;
import org.w3c.dom.Text;

import java.text.DecimalFormat;


public class MatrixOperations {

    public static final int MAX_DENOMINATOR = 1000;

    public static double matrixDeterminant(double[][] matrix, boolean isSquare) throws Exception {


        double temporary[][];
        double result = 0;


        if (isSquare) {
            if (matrix.length == 1) {
                result = matrix[0][0];
                return (result);
            }

            if (matrix.length == 2) {
                result = ((matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]));
                return (result);
            }

            for (int i = 0; i < matrix[0].length; i++) {
                temporary = new double[matrix.length - 1][matrix[0].length - 1];

                for (int j = 1; j < matrix.length; j++) {
                    for (int k = 0; k < matrix[0].length; k++) {
                        if (k < i) {
                            temporary[j - 1][k] = matrix[j][k];
                        } else if (k > i) {
                            temporary[j - 1][k - 1] = matrix[j][k];
                        }
                    }
                }

                result += matrix[0][i] * Math.pow(-1, (double) i) * matrixDeterminant(temporary, isSquare);
            }
        }
        else throw new Exception();

        return (result);
    }


}
