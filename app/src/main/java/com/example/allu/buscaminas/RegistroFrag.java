package com.example.allu.buscaminas;


import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Allu on 02/06/2015.
 */
public class RegistroFrag extends Fragment {

    public TextView txtDetalle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        txtDetalle =(TextView)getView().findViewById(R.id.registroText);

        if (savedInstanceState != null) {
            String text = savedInstanceState.getString("reg");
            txtDetalle.setText(text);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registro_frag, container, false);
        return view;
    }

    public void showText(String item) {
        txtDetalle.setText(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putString("reg", txtDetalle.getText().toString());
        super.onSaveInstanceState(outState);
    }
}
