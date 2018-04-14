package com.eriochrome.matrixcalculator;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

public class OperationsFragment extends Fragment {

    Button button_backOp, button_eigen, button_ana, button_inv, button_dvs, button_lu;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.operations_fragment, container, false);

        final MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.button_touch);

        button_backOp = viewRoot.findViewById(R.id.button_backOp);
        button_backOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mp.start();
                Keyboard newFragment = new Keyboard();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.commit();
            }
        });


        button_eigen = viewRoot.findViewById(R.id.button_eigen);
        button_eigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mp.start();
                MatrixFragment mtxFrg = (MatrixFragment) getFragmentManager().findFragmentById(R.id.matrix_fragment);
                double[][] mtx = mtxFrg.createMatrixFromFragment();
                RealMatrix matrix = new Array2DRowRealMatrix(mtx);

                Intent i = new Intent(getActivity(), Eigenvalues.class);
                Bundle b = new Bundle();
                b.putSerializable("Matrix", mtx);
                b.putInt("Rows", matrix.getRowDimension());
                b.putInt("Cols", matrix.getColumnDimension());
                i.putExtras(b);
                startActivity(i);

            }
        });

        button_inv = viewRoot.findViewById(R.id.button_inv);
        button_inv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mp.start();
                MatrixFragment mtxFrg = (MatrixFragment) getFragmentManager().findFragmentById(R.id.matrix_fragment);
                double[][] mtx = mtxFrg.createMatrixFromFragment();
                RealMatrix matrix = new Array2DRowRealMatrix(mtx);


                Intent i = new Intent(getActivity(), Inverse.class);
                Bundle b = new Bundle();
                b.putSerializable("Matrix", mtx);
                b.putInt("Rows", matrix.getRowDimension());
                b.putInt("Cols", matrix.getColumnDimension());
                i.putExtras(b);
                startActivity(i);
            }
        });

        button_ana = viewRoot.findViewById(R.id.button_ana);
        button_ana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mp.start();
                MatrixFragment mtxFrg = (MatrixFragment) getFragmentManager().findFragmentById(R.id.matrix_fragment);
                double[][] mtx = mtxFrg.createMatrixFromFragment();
                RealMatrix matrix = new Array2DRowRealMatrix(mtx);

                Intent i = new Intent(getActivity(), MatrixAnalysis.class);
                Bundle b = new Bundle();
                b.putSerializable("Matrix", mtx);
                b.putInt("Rows", matrix.getRowDimension());
                b.putInt("Cols", matrix.getColumnDimension());
                i.putExtras(b);
                startActivity(i);
            }
        });

        button_dvs = viewRoot.findViewById(R.id.button_svd);
        button_dvs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mp.start();
                MatrixFragment mtxFrg = (MatrixFragment) getFragmentManager().findFragmentById(R.id.matrix_fragment);
                double[][] mtx = mtxFrg.createMatrixFromFragment();
                RealMatrix matrix = new Array2DRowRealMatrix(mtx);

                Intent i = new Intent(getActivity(), DVS.class);
                Bundle b = new Bundle();
                b.putSerializable("Matrix", mtx);
                b.putInt("Rows", matrix.getRowDimension());
                b.putInt("Cols", matrix.getColumnDimension());
                i.putExtras(b);
                startActivity(i);
            }
        });

        button_lu = viewRoot.findViewById(R.id.button_lu);
        button_lu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mp.start();
                MatrixFragment mtxFrg = (MatrixFragment) getFragmentManager().findFragmentById(R.id.matrix_fragment);
                double[][] mtx = mtxFrg.createMatrixFromFragment();
                RealMatrix matrix = new Array2DRowRealMatrix(mtx);

                Intent i = new Intent(getActivity(), LU.class);
                Bundle b = new Bundle();
                b.putSerializable("Matrix", mtx);
                b.putInt("Rows", matrix.getRowDimension());
                b.putInt("Cols", matrix.getColumnDimension());
                i.putExtras(b);
                startActivity(i);
            }
        });




        return viewRoot;
    }
}