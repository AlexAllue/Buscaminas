package com.example.allu.buscaminas;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Allu on 24/05/2015.
 */
public class MyOnClickListener implements View.OnClickListener {

    private final int position;
    public ArrayList<String> tablero;
    private Context context;
    private int casillas, nBombas;
    public MediaPlayer mediaPlayer;
    private String log="";


    public MyOnClickListener(int position,ArrayList<String> tablero,Context context,int casillas,int nBombas){
        this.position=position;
        this.tablero=tablero;
        this.context=context;
        this.casillas=casillas;
        this.nBombas=nBombas;
    }

    @Override
    public void onClick(View view) {

        ParrillaFrag.ParrillaListener parrillaListener=(ParrillaFrag.ParrillaListener)ParrillaFrag.parrillaActivity;
            parrillaListener.OnArticleSelected(position);
                ((Button) view).setText(tablero.get(position));
            if (tablero.get(position).matches("1")) ((Button) view).setTextColor(Color.BLUE);
            if (tablero.get(position).matches("2")) ((Button) view).setTextColor(Color.GREEN);
            if (tablero.get(position).matches("3")) ((Button) view).setTextColor(Color.RED);
            if (tablero.get(position).matches("4")) ((Button) view).setTextColor(Color.MAGENTA);
            if (tablero.get(position).matches("5")) ((Button) view).setTextColor(Color.YELLOW);
            if (tablero.get(position).matches("6")) ((Button) view).setTextColor(Color.CYAN);
            if (tablero.get(position).matches("7")) ((Button) view).setTextColor(Color.GRAY);
            if (tablero.get(position).matches("8")) ((Button) view).setTextColor(Color.BLACK);
        casillas--;
        if(casillas==nBombas){
            ((Button)view).setText(tablero.get(position));
            Toast.makeText(context.getApplicationContext(), "Has ganado! Enhorabuena.", Toast.LENGTH_LONG).show();
            mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.sound_win);
            mediaPlayer.start();
            new Handler().postDelayed(new Runnable(){
                public void run(){
                    mediaPlayer.stop();
                    ponerLog("Has ganado!! " + " Te han sobrado " /*(maximo - transcurrido)*/ + "s");
                    Intent intent = new Intent(context, ResultActivity.class);
                    intent.putExtra("log",log);
                    context.startActivity(intent);
                    ParrillaFrag.parrillaActivity.finish();
                };
            }, 3000);
        }

        ((Button)view).setEnabled(false);
    }

    public void ponerLog(String parte){
        log=log+parte+"\n";
    }

}
