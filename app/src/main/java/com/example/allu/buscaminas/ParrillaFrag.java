package com.example.allu.buscaminas;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Allu on 24/05/2015.
 */
public class ParrillaFrag extends Fragment {

    ButtonAdapter buttonAdapter;
    private String alias,parrilla,tiempo,numBombas;
    public String log="";
    private int longitud,nBombas;
    public ArrayList<String> tablero,apretado;
    ParrillaListener mlistener;
    public static FragmentActivity parrillaActivity;
    public static TextView tiempoText,bombasText,casillasText;
    public ProgressBar progreso;
    public int progresoNum=0;
    public static int casillas;
    Date now = new Date();
    String format = new SimpleDateFormat("HH:mm:ss").format(now);
    public GameControl gameControl;
    Calendar calFechaInicial = Calendar.getInstance();
    public Long inicio;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.parrilla_frag, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        this.parrillaActivity = getActivity();

        Intent intent = getActivity().getIntent();
        alias=intent.getStringExtra("alias");
        parrilla=intent.getStringExtra("parrilla");
        tiempo=intent.getStringExtra("tiempo");
        numBombas=intent.getStringExtra("bombas");

        longitud=Integer.parseInt(parrilla);
        casillas=longitud*longitud;

        tablero=new ArrayList<>();
        apretado = new ArrayList<>();

        nBombas=calcularNumeroBombas(numBombas);
        ponerBombas(nBombas);
        ponerNumeros();

        casillasText = (TextView) getView().findViewById(R.id.casillasText);
        tiempoText = (TextView) getView().findViewById(R.id.tiempoText);
        bombasText = (TextView) getView().findViewById(R.id.bombasText);

        casillasText.append(casillas+"");
        tiempoText.append(tiempo+"s");
        bombasText.append(nBombas+"");

        progreso = (ProgressBar) getView().findViewById(R.id.progressBar);
        progreso.setMax(casillas-nBombas);
        progreso.setProgress(progresoNum);



        Calendar calFechaFinal = Calendar.getInstance();
        calFechaFinal.setTime(new Date());


        String ponertiempo;
        if (tiempo.matches("0")) ponertiempo="Sin límite";
        else ponertiempo=tiempo+"s";
        ponerLog("Alias: " + alias + " Casillas: " + casillas + " Minas: " + numBombas + " NºMinas: " + nBombas + " Limite de Tiempo: " + ponertiempo + "\n");
        gameControl = new GameControl(tiempo,format,log,longitud,nBombas,tablero);
        final GridView gridview = (GridView)getView().findViewById(R.id.gridview);
        gridview.setNumColumns(longitud);
        gameControl.setInicioPartida(calFechaFinal.getTimeInMillis());
        gridview.setAdapter(buttonAdapter = new ButtonAdapter(getActivity().getApplicationContext(),longitud,tablero,gameControl));
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

    public interface ParrillaListener {
        public void OnArticleSelected(String position);
    }

    public void setParrillaListener(ParrillaListener listener) {
        this.mlistener=listener;
    }

    public void OnAttach(Activity activity){
        super.onAttach(activity);
        try{
            mlistener = (ParrillaListener) activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString()+"must implement OnArticleSelectedListener");
        }
    }





    public void ponerLog(String parte){
        log=log+parte+"\n";
    }

}
