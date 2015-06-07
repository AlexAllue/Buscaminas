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

import java.sql.Time;
import java.text.SimpleDateFormat;
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
    private int longitud, nBombas,casillas;
    public MediaPlayer mediaPlayer;
    private String log="";
    private GameControl gamecontrol;


    public MyOnClickListener(int position,Context context,GameControl gameControl){
        this.position=position;
        this.context=context;
        this.gamecontrol=gameControl;

        tablero=gameControl.getTablero();
        longitud=gameControl.getLongitud();
        nBombas=gameControl.getnBombas();
        casillas=gameControl.getCasillas();
    }

    @Override
    public void onClick(View view) {

        if(!gamecontrol.haTerminado()) {
            gamecontrol.getApretado().add("" + position);

            casillas = gamecontrol.restarCasilla();

            ParrillaFrag.getCasillasRestantes().setText("Casillas: "+gamecontrol.getCasillas());
            if(!gamecontrol.tiempoMaximo.matches("SinTiempo")){
            ParrillaFrag.getTiempoRestante().setText("Tiempo: "+gamecontrol.calcularTiempoRestante()+"s");}
            ParrillaFrag.getProgreso().setProgress(gamecontrol.getProgreso());

            Date now = new Date();
            String format = new SimpleDateFormat("HH:mm:ss").format(now);

            ParrillaFrag.ParrillaListener parrillaListener = (ParrillaFrag.ParrillaListener) ParrillaFrag.parrillaActivity;
            parrillaListener.OnArticleSelected("Casilla seleccionada = " + "(" + ((position % longitud) + 1) + "," + ((position / longitud) + 1) + ")\n" + gamecontrol.getInicioTirada() + "\n" + format);
            gamecontrol.setInicioTirada(format);
            ((Button) view).setText(tablero.get(position));


            if (gamecontrol.calcularFinalPartida()) {
                gamecontrol.terminar();
                Toast.makeText(context.getApplicationContext(), "Has perdido! Se ha agotado el tiempo.", Toast.LENGTH_LONG).show();
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.sound_lose);
                mediaPlayer.start();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mediaPlayer.stop();
                        ponerLog("Has agotado el tiempo!! " + " Te han quedado " + (casillas + 1) + " Casillas por descubrir");
                        Intent intent = new Intent(context.getApplicationContext(), ResultActivity.class);
                        intent.putExtra("log", gamecontrol.getLogInicial() + log);
                        intent.putExtra("game","Tiempo agotado");
                        intent.putExtra("alias",gamecontrol.getAlias());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.getApplicationContext().startActivity(intent);
                        ParrillaFrag.parrillaActivity.finish();
                    }

                    ;
                }, 3000);
            }

            //Si se ha apretado una bomba
            if (tablero.get(position).matches("B")) {
                gamecontrol.terminar();
                ((Button) view).setText("");
                view.setBackgroundResource(R.drawable.icon_red);
                Toast.makeText(context.getApplicationContext(), "Has perdido! Has descubierto una bomba.", Toast.LENGTH_LONG).show();
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.sound_lose);
                mediaPlayer.start();

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mediaPlayer.stop();
                        ponerLog("Has perdido!! Bomba en la casilla " + ((position % longitud) + 1) + "," + ((position / longitud) + 1) + " Te han quedado " + casillas + " Casillas por descubrir");
                        Intent intent = new Intent(context.getApplicationContext(), ResultActivity.class);
                        intent.putExtra("log", gamecontrol.getLogInicial() + log);
                        intent.putExtra("game","Derrota");
                        intent.putExtra("alias",gamecontrol.getAlias());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.getApplicationContext().startActivity(intent);
                        ParrillaFrag.parrillaActivity.finish();
                    }

                    ;
                }, 3000);

            } else { //Si no es bomba
                ((Button) view).setText(tablero.get(position));
                if (tablero.get(position).matches("1")) ((Button) view).setTextColor(Color.BLUE);
                if (tablero.get(position).matches("2")) ((Button) view).setTextColor(Color.GREEN);
                if (tablero.get(position).matches("3")) ((Button) view).setTextColor(Color.RED);
                if (tablero.get(position).matches("4")) ((Button) view).setTextColor(Color.MAGENTA);
                if (tablero.get(position).matches("5")) ((Button) view).setTextColor(Color.YELLOW);
                if (tablero.get(position).matches("6")) ((Button) view).setTextColor(Color.CYAN);
                if (tablero.get(position).matches("7")) ((Button) view).setTextColor(Color.GRAY);
                if (tablero.get(position).matches("8")) ((Button) view).setTextColor(Color.BLACK);


            }
            if (casillas == nBombas) {
                gamecontrol.terminar();
                ((Button) view).setText(tablero.get(position));
                Toast.makeText(context.getApplicationContext(), "Has ganado! Enhorabuena.", Toast.LENGTH_LONG).show();
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.sound_win);
                mediaPlayer.start();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mediaPlayer.stop();
                        if(gamecontrol.tiempoMaximo.matches("SinTiempo")){ponerLog("Has ganado!! Jugabas sin tiempo");}
                        else ponerLog("Has ganado!! " + " Te han sobrado " +gamecontrol.calcularTiempoRestante() + "s");
                        Intent intent = new Intent(context.getApplicationContext(), ResultActivity.class);
                        intent.putExtra("log", gamecontrol.getLogInicial() + log);
                        intent.putExtra("game","Victoria");
                        intent.putExtra("alias",gamecontrol.getAlias());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.getApplicationContext().startActivity(intent);
                        ParrillaFrag.parrillaActivity.finish();
                    }

                    ;
                }, 3000);
            }

            ((Button) view).setEnabled(false);
        }
    }

    public void ponerLog(String parte){

        log=log+parte+"\n";
    }

}
