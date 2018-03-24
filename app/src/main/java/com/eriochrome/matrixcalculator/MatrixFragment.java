package com.eriochrome.matrixcalculator;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import static java.lang.Double.parseDouble;

public class MatrixFragment extends Fragment{

    RelativeLayout rLayout;
    private int cols;
    private int rows;
    private EditText[][] entries;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.matrix_fragment, container, false);

        return viewRoot;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rLayout = getActivity().findViewById(R.id.rLayout);
        setCols(3);
        setRows(3);
        setEntries(addEditTextDynamically(rLayout, getCols(), getRows()));


    }

    public EditText[][] addEditTextDynamically(RelativeLayout mParentLayout, int cols, int rows){
        EditText[][] entries = new EditText[rows][cols];

        // Relative values.
        int widthTextView = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 45, getResources().getDisplayMetrics());
        int heigthTextView = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 45, getResources().getDisplayMetrics());
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics());

        for (int i=0; i<rows; i++) {
            for (int j=0; j< cols; j++){
                EditText myEditText = new EditText(mParentLayout.getContext()); //Context

                myEditText.setHint("0");
                myEditText.setHintTextColor(getResources().getColor(R.color.colorHint));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    myEditText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(widthTextView, heigthTextView);
                myEditText.setLayoutParams(params);
                myEditText.setTextSize(14);
                myEditText.setId(10*(i+1) + j+1);
                mParentLayout.addView(myEditText);
                entries[i][j] = myEditText;
                if (j > 0) {
                    params.addRule(RelativeLayout.RIGHT_OF, entries[i][j - 1].getId());
                    params.setMargins(margin, 0, 0, 0);
                }
                if (i > 0) {
                    params.addRule(RelativeLayout.BELOW, entries[i-1][0].getId());
                    params.setMargins(0, margin, 0,0);
                }
                if (j > 0 && i > 0) {
                    params.setMargins(margin, margin, 0,0);
                }
            }
        }

        return entries;

        //Github test
    }


    public double[][] createMatrixFromFragment() {
        double[][] matrix = new double[getRows()][getCols()];
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                matrix[i][j] = editTextToDouble(getEntries()[i][j]);
            }
        }
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
        if ((!string.equals(string.split("/")[0]))&&(array.length > 1)) {
            double result = Double.parseDouble(array[0]) / Double.parseDouble(array[1]);
            return Double.toString(result);
        }
        else return string;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public EditText[][] getEntries() {
        return entries;
    }

    public void setEntries(EditText[][] entries) {
        this.entries = entries;
    }
}
