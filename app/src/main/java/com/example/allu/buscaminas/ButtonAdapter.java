package com.example.allu.buscaminas;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by Allu on 24/05/2015.
 */
public class ButtonAdapter extends BaseAdapter {
    private Context mContext;
    private int longitud;
    public ArrayList<String> tablero;
    private MyOnClickListener clickListener;
    private int casillas,nBombas;

    public ButtonAdapter(Context c) {
        mContext = c;
    }

    public ButtonAdapter(Context c,int l,ArrayList<String> t,int ca,int nb) {
        mContext = c;
        longitud=l;
        tablero=t;
        casillas=ca;
        nBombas=nb;

    }

    public int getCount() {
        return longitud*longitud;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Button btn;

        if (convertView == null) {
            btn = new Button(mContext);
            //tamaño de casillas segun la pantalla

        } else {
            btn = (Button) convertView;
        }

        //btn.setText(tablero.get(position));
        btn.setTextColor(Color.BLACK);
        btn.setBackgroundResource(R.drawable.button);
        btn.setOnClickListener(clickListener=new MyOnClickListener(position,tablero,mContext,casillas,nBombas));
        btn.setId(position);

        return btn;

    }

}
