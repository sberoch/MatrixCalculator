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




    // (3x3) - Vector con Textviews, Matriz ya operada
    public static void matrixRepresentation(TextView[] vec, RealMatrix mat) {
        int j = 0, k = 0;
        for (int i = 0; i < 9; i++) {
            double entry = mat.getEntry(j,k);
            Fraction fractionEntry = new Fraction(entry);
            vec[i].setText(fractionEntry.toString());
            k++;
            if (k == 3) {
                k = 0;
                j++;
            }
        }
    }

    public static void matrixRepresentationAndNormalizationCol(TextView[] vec, RealMatrix mat) {
        int j = 0, k = 0;
        for (int i = 0; i < 9; i++) {
            double entry = mat.getEntry(j,k);
            double norm = mat.getColumnVector(k).getNorm();
            Fraction fractionEntry = new Fraction(entry);
            Fraction fractionSquaredNorm = new Fraction(norm*norm);
            if (entry == 0) {
                vec[i].setText(fractionEntry.toString());
            }
            else
                vec[i].setText(fractionEntry.toString() + "/(" + fractionSquaredNorm.toString() + ")^(1/2)" );

            k++;
            if (k == 3) {
                k = 0;
                j++;
            }
        }
    }

    public static void matrixRepresentationAndNormalizationFil(TextView[] vec, RealMatrix mat) {
        int j = 0, k = 0;
        for (int i = 0; i < 9; i++) {
            double entry = mat.getEntry(j,k);
            double norm = mat.getRowVector(j).getNorm();
            Fraction fractionEntry = new Fraction(entry);
            Fraction fractionSquaredNorm = new Fraction(norm*norm);
            if (entry == 0) {
                vec[i].setText(fractionEntry.toString());
            }
            else
            {
                vec[i].setText(fractionEntry.toString() + "/(" + fractionSquaredNorm.toString() + ")^(1/2)" );
            }
            k++;
            if (k == 3) {
                k = 0;
                j++;
            }
        }
    }

    public static void matrixRepresentationSigma(TextView[] vec, RealMatrix mat) {
        DecimalFormat df = new DecimalFormat("###.##");
        int j = 0, k = 0;
        for (int i = 0; i < 9; i++) {
            double entry = mat.getEntry(j,k);
            double tolerance = 1E-4;
            if (Math.abs(Math.floor(entry)- entry) < tolerance)
            {
                Fraction fractionEntry = new Fraction(entry);
                vec[i].setText(fractionEntry.toString());
            }
            else
            {
                Double entrySquared = entry*entry;
                String singularValue = "(" + df.format(entrySquared) + ")^(1/2)";
                vec[i].setText(singularValue);
            }
            k++;
            if (k == 3) {
                k = 0;
                j++;
            }
        }
    }

}
