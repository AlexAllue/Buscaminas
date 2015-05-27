package com.example.allu.buscaminas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Allu on 24/05/2015.
 */
public class LogFrag extends Fragment {

    public TextView txtDetalle;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.log_frag, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        txtDetalle =(TextView)getView().findViewById(R.id.textView);

        if (savedInstanceState != null) {
            String text = savedInstanceState.getString("log");
            txtDetalle.setText(text);
        }

    }

    public void mostrarLog(String texto) {

        txtDetalle.append(texto+"\n");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putString("log", txtDetalle.getText().toString());
        super.onSaveInstanceState(outState);
    }
}
