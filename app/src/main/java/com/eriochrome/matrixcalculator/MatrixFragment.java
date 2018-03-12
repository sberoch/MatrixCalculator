package com.eriochrome.matrixcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;

import java.nio.DoubleBuffer;

import static java.lang.Double.parseDouble;

public class MatrixFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.matrix_fragment, container, false);

        return viewRoot;
    }

    public double[][] createMatrixFromFragment() {
        double[][] matrix = new double[3][3];

        Activity act = getActivity();
        matrix[0][0] = editTextToDouble((EditText)act.findViewById(R.id.editText11));
        matrix[0][1] = editTextToDouble((EditText)act.findViewById(R.id.editText12));
        matrix[0][2] = editTextToDouble((EditText)act.findViewById(R.id.editText13));
        matrix[1][0] = editTextToDouble((EditText)act.findViewById(R.id.editText21));
        matrix[1][1] = editTextToDouble((EditText)act.findViewById(R.id.editText22));
        matrix[1][2] = editTextToDouble((EditText)act.findViewById(R.id.editText23));
        matrix[2][0] = editTextToDouble((EditText)act.findViewById(R.id.editText31));
        matrix[2][1] = editTextToDouble((EditText)act.findViewById(R.id.editText32));
        matrix[2][2] = editTextToDouble((EditText)act.findViewById(R.id.editText33));


        return matrix;
    }

    public double editTextToDouble(EditText edit) {
        double result = 0;
        String editstr = edit.getText().toString();

        //0 is default
        if(editstr.isEmpty()) {
            editstr = "0";
        }


        try {
            editstr = catchDiv(editstr);
            result = parseDouble(editstr);
            if (editstr.length() > 4){
                throw new IllegalArgumentException("Invalid value");
            }
        } catch (NumberFormatException e) {
            result = 0;
        } catch (IllegalArgumentException e) {
            result = 0;
        }
        return result;
    }


    public String catchDiv (String string) {
        String[] array = string.split("/");
        if ((string != string.split("/")[0])&&(array.length > 1)) {
            double result = Double.parseDouble(array[0]) / Double.parseDouble(array[1]);
            return Double.toString(result);
        }
        else return string;
    }
}
