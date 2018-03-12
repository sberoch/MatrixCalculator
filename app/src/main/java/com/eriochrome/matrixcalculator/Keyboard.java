package com.eriochrome.matrixcalculator;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class Keyboard extends Fragment{

    Button button0, button1, button2, button3, button4, button5, button6,
            button7, button8, button9, button_minus,
            button_div, button_delete, button_dot, button_ok;

    ImageButton button_erase;


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.button_touch);
            mp.start();

            EditText focusedEditText;
            if ((EditText)getActivity().getCurrentFocus() != null) {
                 focusedEditText = (EditText)getActivity().getCurrentFocus();
            } else {
                 focusedEditText = getActivity().findViewById(R.id.editText11);
            }

            switch (view.getId()) {
                case R.id.button_ok:
                    OperationsFragment opFragment = new OperationsFragment();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, opFragment);
                    transaction.commit();
                    break;

                case R.id.button0:
                    focusedEditText.setText(focusedEditText.getText() + "0");
                    break;

                case R.id.button1:
                    focusedEditText.setText(focusedEditText.getText() + "1");
                    break;

                case R.id.button2:
                    focusedEditText.setText(focusedEditText.getText() + "2");
                    break;

                case R.id.button3:
                    focusedEditText.setText(focusedEditText.getText() + "3");
                    break;

                case R.id.button4:
                    focusedEditText.setText(focusedEditText.getText() + "4");
                    break;

                case R.id.button5:
                    focusedEditText.setText(focusedEditText.getText() + "5");
                    break;

                case R.id.button6:
                    focusedEditText.setText(focusedEditText.getText() + "6");
                    break;

                case R.id.button7:
                    focusedEditText.setText(focusedEditText.getText() + "7");
                    break;

                case R.id.button8:
                    focusedEditText.setText(focusedEditText.getText() + "8");
                    break;

                case R.id.button9:
                    focusedEditText.setText(focusedEditText.getText() + "9");
                    break;


                case R.id.button_minus:
                    focusedEditText.setText(focusedEditText.getText() + "-");
                    break;

                case R.id.button_div:
                    focusedEditText.setText(focusedEditText.getText() + "/");
                    break;


                case R.id.button_dot:
                    focusedEditText.setText(focusedEditText.getText() + ".");
                    break;

                case R.id.button_delete:
                    focusedEditText.setText("");
                    break;

                case R.id.button_erase:
                    int length = focusedEditText.getText().length();
                    if (length > 0) {
                        focusedEditText.getText().delete(length - 1, length);
                    }
                    break;



            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.keyboard, container, false);

        button0 = rootView.findViewById(R.id.button0);
        button0.setOnClickListener(onClickListener);

        button1 = rootView.findViewById(R.id.button1);
        button1.setOnClickListener(onClickListener);

        button2 = rootView.findViewById(R.id.button2);
        button2.setOnClickListener(onClickListener);

        button3 = rootView.findViewById(R.id.button3);
        button3.setOnClickListener(onClickListener);

        button4 = rootView.findViewById(R.id.button4);
        button4.setOnClickListener(onClickListener);

        button5 = rootView.findViewById(R.id.button5);
        button5.setOnClickListener(onClickListener);

        button6 = rootView.findViewById(R.id.button6);
        button6.setOnClickListener(onClickListener);

        button7 = rootView.findViewById(R.id.button7);
        button7.setOnClickListener(onClickListener);

        button8 = rootView.findViewById(R.id.button8);
        button8.setOnClickListener(onClickListener);

        button9 = rootView.findViewById(R.id.button9);
        button9.setOnClickListener(onClickListener);

        button_minus = rootView.findViewById(R.id.button_minus);
        button_minus.setOnClickListener(onClickListener);

        button_div = rootView.findViewById(R.id.button_div);
        button_div.setOnClickListener(onClickListener);

        button_delete = rootView.findViewById(R.id.button_delete);
        button_delete.setOnClickListener(onClickListener);

        button_dot = rootView.findViewById(R.id.button_dot);
        button_dot.setOnClickListener(onClickListener);

        button_ok = rootView.findViewById(R.id.button_ok);
        button_ok.setOnClickListener(onClickListener);

        button_erase = rootView.findViewById(R.id.button_erase);
        button_erase.setOnClickListener(onClickListener);

        return rootView;

    }



}
