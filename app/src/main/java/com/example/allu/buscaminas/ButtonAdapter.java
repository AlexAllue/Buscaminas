package com.example.allu.buscaminas;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Allu on 24/05/2015.
 */
public class ButtonAdapter extends BaseAdapter {
    private Context mContext;
    public ArrayList<String> tablero;
    private MyOnClickListener clickListener;
    private int longitud;

    public GameControl gameControl;


    public ButtonAdapter(Context c) {
        mContext = c;
    }

    public ButtonAdapter(Context c,GameControl gc) {
        mContext = c;
        gameControl=gc;
        tablero=gameControl.getTablero();
        longitud=gameControl.getLongitud();
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
            if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                DisplayMetrics metrics = mContext.getApplicationContext().getResources().getDisplayMetrics();
                int screenWidth = (int) (metrics.heightPixels*0.7);
                btn.setLayoutParams(new GridView.LayoutParams(screenWidth/longitud,screenWidth/longitud));
                btn.setPadding(0, 0, 0, 0);
            }else{
                DisplayMetrics metrics = mContext.getApplicationContext().getResources().getDisplayMetrics();
                int screenWidth = metrics.widthPixels;
                btn.setLayoutParams(new GridView.LayoutParams(screenWidth/longitud,screenWidth/longitud));
                btn.setPadding(0, 0, 0, 0);
            }


            if(mContext.getResources().getConfiguration().isLayoutSizeAtLeast(Configuration.SCREENLAYOUT_SIZE_LARGE)){
                btn.setTextSize(500/longitud);
            }

        } else {
            btn = (Button) convertView;
        }

        //btn.setText(tablero.get(position));
        btn.setTextColor(Color.BLACK);
        btn.setBackgroundResource(R.drawable.button);
        btn.setOnClickListener(clickListener=new MyOnClickListener(position,mContext,gameControl));
        btn.setId(position);

        if(gameControl.getApretado().contains(""+position)){
            btn.setText(tablero.get(position));
            btn.setEnabled(false);
            if(tablero.get(position).matches("1"))btn.setTextColor(Color.BLUE);
            if(tablero.get(position).matches("2"))btn.setTextColor(Color.GREEN);
            if(tablero.get(position).matches("3"))btn.setTextColor(Color.RED);
            if(tablero.get(position).matches("4"))btn.setTextColor(Color.MAGENTA);
            if(tablero.get(position).matches("5"))btn.setTextColor(Color.YELLOW);
            if(tablero.get(position).matches("6"))btn.setTextColor(Color.CYAN);
            if(tablero.get(position).matches("7"))btn.setTextColor(Color.GRAY);
            if(tablero.get(position).matches("8"))btn.setTextColor(Color.BLACK);
        }

        return btn;

    }

}
