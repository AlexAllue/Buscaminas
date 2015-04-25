package com.example.allu.buscaminas;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;


/**
 * Created by Allu on 15/04/2015.
 */
public class GameActivity extends ActionBarActivity {
    private GridView gridview;
    private ArrayList<Integer> bombas;
    private String alias,parrilla,tiempo,numBombas,log="";
    private boolean atrasSalir;
    private int longitud,nBombas,casillas;
    public ArrayList<String> tablero;
    public Calendar calFechaInicial;
    public TextView tiempoText,bombasText,casillasText;
    public ProgressBar progreso;
    public int progresoNum=0;
    public MediaPlayer mediaPlayer;
    public long ttt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.icon);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff5bc610")));

        Intent intent = getIntent();
        alias=intent.getStringExtra("alias");
        parrilla=intent.getStringExtra("parrilla");
        tiempo=intent.getStringExtra("tiempo");
        numBombas=intent.getStringExtra("bombas");

        longitud=Integer.parseInt(parrilla);
        casillas=longitud*longitud;
        tablero=new ArrayList<>();
        nBombas=calcularNumeroBombas(numBombas);
        ponerBombas(nBombas);
        ponerNumeros();
        String ponertiempo;
        if (tiempo.matches("0")) ponertiempo="Sin límite";
        else ponertiempo=tiempo+"s";
        ponerLog("Alias: " + alias + " Casillas: " + casillas + " Minas: " + numBombas + " NºMinas: " + nBombas + " Limite de Tiempo: " + ponertiempo);



        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setNumColumns(longitud);

        gridview.setAdapter(new ButtonAdapter(this));

        calFechaInicial = Calendar.getInstance();
        calFechaInicial.setTime(new Date());
        ttt=calFechaInicial.getTimeInMillis();



        casillasText = (TextView) findViewById(R.id.casillasText);
        tiempoText = (TextView) findViewById(R.id.tiempoText);
        bombasText = (TextView) findViewById(R.id.bombasText);

        casillasText.append(casillas+"");
        tiempoText.append(tiempo+"s");
        bombasText.append(nBombas+"");

        progreso = (ProgressBar) findViewById(R.id.progressBar);
        progreso.setMax(casillas-nBombas);
        progreso.setProgress(progresoNum);

    }

    public int calcularNumeroBombas(String numBombas){
        int total=longitud*longitud;
        if(numBombas.matches("15%"))total=(15*total)/100;
        if(numBombas.matches("25%"))total=(25*total)/100;
        if(numBombas.matches("35%"))total=(35*total)/100;
        return total;
    }

    public void ponerBombas(int nBombas){
        for (int i=0;i<nBombas;i++){
            tablero.add("B");
        }
        for(int j=0;j<longitud*longitud-nBombas;j++){
            tablero.add("");
        }
        Collections.shuffle(tablero);
    }
    public void ponerNumeros(){

        String [] cambio= new String[tablero.size()];
        cambio= tablero.toArray(cambio);
        for(int i=0;i<longitud*longitud;i++){
            int numero=0;
            if(!cambio[i].matches("B")){
                if(i%longitud<longitud-1&&cambio[i+1].matches("B"))numero++;
                if(i%longitud>0&&cambio[i-1].matches("B"))numero++;
                if(i<longitud*longitud-longitud&&cambio[i+longitud].matches("B"))numero++;
                if(i<longitud*longitud-longitud&&i%longitud<longitud-1&&cambio[i+longitud+1].matches("B"))numero++;
                if(i<longitud*longitud-longitud&&i%longitud>0&&cambio[i+longitud-1].matches("B"))numero++;
                if(i>longitud&&cambio[i-longitud].matches("B"))numero++;
                if(i>longitud&&i%longitud<longitud-1&&cambio[i-longitud+1].matches("B"))numero++;
                if(i>longitud&&i%longitud>0&&cambio[i-longitud-1].matches("B"))numero++;

                if(numero!=0) cambio[i]=numero+"";
            }

        }
        tablero= new ArrayList<>(Arrays.asList(cambio));
    }

    public class ButtonAdapter extends BaseAdapter {
        private Context mContext;

        public ButtonAdapter(Context c) {
            mContext = c;
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

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            Button btn;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                btn = new Button(mContext);




                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                    DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
                    int screenWidth = (int) (metrics.widthPixels*0.65);
                    btn.setLayoutParams(new GridView.LayoutParams(screenWidth/longitud,screenWidth/longitud));
                    btn.setPadding(0, 0, 0, 0);
                }
                    else{
                    DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
                    int screenWidth = metrics.widthPixels;
                    btn.setLayoutParams(new GridView.LayoutParams(screenWidth/longitud,screenWidth/longitud));
                    btn.setPadding(0, 0, 0, 0);
                }

            } else {
                btn = (Button) convertView;


            }
            //btn.setText(tablero.get(position));
            btn.setTextColor(Color.BLACK);
            btn.setBackgroundResource(R.drawable.button);
            btn.setOnClickListener(new MyOnClickListener(position));
            btn.setId(position);
            return btn;
        }



    }



    class MyOnClickListener implements View.OnClickListener{
        private final int position;

        public MyOnClickListener(int position){
            this.position=position;
        }

        public void onClick(View view){



            Calendar calFechaFinal = Calendar.getInstance();
            calFechaFinal.setTime(new Date());

            String total=""+(calFechaFinal.getTimeInMillis()-ttt);

            final int hola = Integer.parseInt(tiempo);
            final int adios = Integer.parseInt(total)/1000;
            casillas--;
            progresoNum++;
            progreso.setProgress(progresoNum);
            casillasText.setText(getResources().getString(R.string.casillas)+casillas);
            tiempoText.setText(getResources().getString(R.string.descubrir)+(hola-adios)+"s");
            if(adios>hola&&hola!=0){
                Toast.makeText(getApplicationContext(), "Has perdido! Se ha agotado el tiempo.", Toast.LENGTH_LONG).show();
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_lose);
                mediaPlayer.start();
                new Handler().postDelayed(new Runnable(){
                    public void run(){
                        mediaPlayer.stop();
                        ponerLog("Has agotado el tiempo!! " + " Te han quedado " + (casillas + 1) + " Casillas por descubrir");
                        Intent intent = new Intent(GameActivity.this, ResultActivity.class);
                        intent.putExtra("log",log);
                        intent.putExtra("resultado", "Lose");
                        startActivity(intent);
                        finish();
                    };
                }, 3000);


            }
            if(tablero.get(position).matches("B")){
                view.setBackgroundResource(R.drawable.icon_red);
                Toast.makeText(getApplicationContext(), "Has perdido! Has descubierto una bomba.", Toast.LENGTH_LONG).show();
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_lose);
                mediaPlayer.start();

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mediaPlayer.stop();
                        ponerLog("Has perdido!! Bomba en la casilla " + ((position % longitud) + 1) + "," + ((position / longitud) + 1) + " Te han quedado " + casillas + " Casillas por descubrir");
                        Intent intent = new Intent(GameActivity.this, ResultActivity.class);
                        intent.putExtra("log", log);
                        intent.putExtra("resultado", "Lose");
                        startActivity(intent);
                        finish();
                    }

                    ;
                }, 3000);

            }else{
                ((Button)view).setText(tablero.get(position));
                if(tablero.get(position).matches("1"))((Button) view).setTextColor(Color.BLUE);
                if(tablero.get(position).matches("2"))((Button) view).setTextColor(Color.GREEN);
                if(tablero.get(position).matches("3"))((Button) view).setTextColor(Color.RED);
                if(tablero.get(position).matches("4"))((Button) view).setTextColor(Color.MAGENTA);
                if(tablero.get(position).matches("5"))((Button) view).setTextColor(Color.YELLOW);
                if(tablero.get(position).matches("6"))((Button) view).setTextColor(Color.CYAN);
                if(tablero.get(position).matches("7"))((Button) view).setTextColor(Color.GRAY);
                if(tablero.get(position).matches("8"))((Button) view).setTextColor(Color.BLACK);


            }
            if(casillas==nBombas){
                ((Button)view).setText(tablero.get(position));
                Toast.makeText(getApplicationContext(), "Has ganado! Enhorabuena.", Toast.LENGTH_LONG).show();
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_win);
                mediaPlayer.start();
                new Handler().postDelayed(new Runnable(){
                    public void run(){
                        mediaPlayer.stop();
                        ponerLog("Has ganado!! " + " Te han sobrado " + (hola - adios) + "s");
                        Intent intent = new Intent(GameActivity.this, ResultActivity.class);
                        intent.putExtra("log",log);
                        intent.putExtra("resultado", "Win");
                        startActivity(intent);
                        finish();
                    };
                }, 3000);
            }



                    ((Button) view).setEnabled(false);

        }



    }


    @Override
    public void onBackPressed() {
        if (atrasSalir) {
            super.onBackPressed();
            return;
        }
        this.atrasSalir = true;
        Toast.makeText(this, "Si pulsas el botón atrás otra vez cerraras la aplicación", Toast.LENGTH_LONG).show();
    }

    public void ponerLog(String parte){
        log=log+parte+"\n";
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putSerializable("fechainicial",calFechaInicial);
        outState.putInt("casillasRestantes", casillas);
        outState.putString("tiempo", tiempoText.getText().toString());
        outState.putString("casillas", casillasText.getText().toString());
        outState.putString("bombas", bombasText.getText().toString());
        outState.putStringArrayList("tablero",tablero);
        outState.putInt("progress",progresoNum);
        outState.putLong("ttt",ttt);


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        casillas=savedInstanceState.getInt("casillasRestantes");
        casillasText.setText(savedInstanceState.getString("casillas"));
        tiempoText.setText(savedInstanceState.getString("tiempo"));
        bombasText.setText(savedInstanceState.getString("bombas"));
        tablero=savedInstanceState.getStringArrayList("tablero");
        progresoNum=(savedInstanceState.getInt("progress"));
        ttt=savedInstanceState.getLong("ttt");

    }

}
