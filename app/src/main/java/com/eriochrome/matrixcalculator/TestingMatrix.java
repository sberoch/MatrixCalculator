package com.eriochrome.matrixcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class TestingMatrix extends AppCompatActivity {

    RelativeLayout rLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_matrix);

        rLayout = findViewById(R.id.rLayout);
        int cols =1, rows=4;
        EditText[] entries = addEditTextDynamically(rLayout, cols, rows);
    }


    private EditText[] addEditTextDynamically(RelativeLayout mParentLayout, int cols, int rows){
        EditText[] entries = new EditText[cols*rows];

        for (int i=0; i<3; i++){
            EditText myEditText = new EditText(mParentLayout.getContext()); //Context
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            myEditText.setLayoutParams(params);
            myEditText.setId(i+1);
            mParentLayout.addView(myEditText);
            entries[i] = myEditText;
            if (i > 0) {
                params.addRule(RelativeLayout.RIGHT_OF, entries[i - 1].getId());
            }
        }

        return entries;
    }
}
