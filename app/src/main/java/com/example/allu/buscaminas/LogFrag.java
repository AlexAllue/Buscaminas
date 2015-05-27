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

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.log_frag, container, false);
    }

    public void mostrarLog(String texto) {
        TextView txtDetalle =
                (TextView)getView().findViewById(R.id.textView);

        txtDetalle.append(texto+"\n");
    }
}
