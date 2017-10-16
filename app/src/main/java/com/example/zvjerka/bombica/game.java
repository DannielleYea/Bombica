package com.example.zvjerka.bombica;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.util.concurrent.TimeUnit;


public class game extends AppCompatActivity implements View.OnTouchListener {

    Button[] tipke = new Button[10];
    TextView[] znamenke = new TextView[10];
    TextView preostalo;
    String s;
    int[] sifra = new int[7];
    Thread[] dretve;
    vibra D_vib;
    CountDownTimer CDT;
    long do_kraja = 55000;
    Vibrator vib;
    int pogodeni = -1;
    long zadnji = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tipke[0] = (Button) findViewById(R.id.T0);
        tipke[1] = (Button) findViewById(R.id.T1);
        tipke[2] = (Button) findViewById(R.id.T2);
        tipke[3] = (Button) findViewById(R.id.T3);
        tipke[4] = (Button) findViewById(R.id.T4);
        tipke[5] = (Button) findViewById(R.id.T5);
        tipke[6] = (Button) findViewById(R.id.T6);
        tipke[7] = (Button) findViewById(R.id.T7);
        tipke[8] = (Button) findViewById(R.id.T8);
        tipke[9] = (Button) findViewById(R.id.T9);

        znamenke[0] = (TextView) findViewById(R.id.X0);
        znamenke[1] = (TextView) findViewById(R.id.X1);
        znamenke[2] = (TextView) findViewById(R.id.X2);
        znamenke[3] = (TextView) findViewById(R.id.X3);
        znamenke[4] = (TextView) findViewById(R.id.X4);
        znamenke[5] = (TextView) findViewById(R.id.X5);
        znamenke[6] = (TextView) findViewById(R.id.X6);

        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        dretve = new Thread[7];
        for(int i = 0 ; i < 7 ; i++)
            dretve[i] = new pozicija(i);

        preostalo = (TextView) findViewById(R.id.textView11);
        for(int i = 0 ; i < 10; i++)
            tipke[i].setOnTouchListener(this);

        rand();
    }

    @Override
    public void onBackPressed(){
        for(int i = 0 ; i < 7 ; i++)
            dretve[i].interrupt();

        D_vib.interrupt();

        CharSequence text = "BackButtonPressed";
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();

        finish();
    }

    @Override
    protected void onStart(){
        super.onStart();

        for(int i = 0; i < 7 ; i++)
            dretve[i].start();

    }

    @Override
    protected void onResume(){
        super.onResume();
        D_vib = new vibra();

        CDT = null;

        CDT = new CountDownTimer(do_kraja, 10) {
            boolean radi = true;

            @Override
            public void onTick(long mili) {
                s = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toSeconds(mili), (mili - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(mili)))/10);
                preostalo.setText(s);
                if(mili < 10000 && radi){
                    D_vib.start();
                    radi = false;
                }
                do_kraja = mili;
            }

            @Override
            public void onFinish() {
                for(int y = 0 ; y < 7; y++)
                    dretve[y].interrupt();
                D_vib.interrupt();
                for(int i = 0; i < 10; i++)
                    tipke[i].setClickable(false);
                finish();
            }
        }.start();
    }

    @Override
    public boolean onTouch (View v, MotionEvent event){

        if((System.currentTimeMillis() - zadnji ) < 350 )
            return false;
        zadnji = System.currentTimeMillis();

        switch(v.getId()){
            case R.id.T0:
                obrada(0);
                return true;

            case R.id.T1:
                obrada(1);
                return true;

            case R.id.T2:
                obrada(2);
                return true;

            case R.id.T3:
                obrada(3);
                return true;

            case R.id.T4:
                obrada(4);
                return true;

            case R.id.T5:
                obrada(5);
                return true;

            case R.id.T6:
                obrada(6);
                return true;

            case R.id.T7:
                obrada(7);
                return true;

            case R.id.T8:
                obrada(8);
                return true;

            case R.id.T9:
                obrada(9);
                return true;
        }
        return false;
    }

    private void rand(){
        for(int i = 0 ; i < 7 ; i++)
            sifra[i] = (int)(Math.random() * 10);
    }

    private void obrada(int x){

        if (pogodeni == -1) pogodeni++;

        if(x == sifra[pogodeni]){
            znamenke[pogodeni].setText("" + x);
            znamenke[pogodeni].setTextColor(Color.parseColor("#009900"));
            pogodeni++;

            if(pogodeni == 7) {
                CDT.cancel();

                for(int i = 0; i < 10 ; i++)
                    tipke[i].setClickable(false);
                // fuckcija koja hendla pobjedu



            }

        }
        else{
            znamenke[pogodeni].setText("" + x);
            znamenke[pogodeni].setTextColor(Color.parseColor("#FF0000"));
        }


    }

    class vibra extends Thread{

        public vibra(){
        }

        public void run() {
            while(!Thread.interrupted()) {
                while (do_kraja > 250){
                    vib.vibrate(200);
                    }
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException("Thread interrupted..."+e);
                }
            }
        Thread.currentThread().interrupt();
        }
    }

    class pozicija extends Thread{
        private int poz;
        public pozicija(int x){
            this.poz = x;
        }

        public synchronized void run(){
            while(!Thread.interrupted()) {
                while(pogodeni < poz) {
                    runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                            znamenke[poz].setText("" + ((int) (Math.random() * 10)));
                        }
                });
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException("Thread interrupted..."+e);
                    }
                }
            }
        Thread.currentThread().interrupt();
        }
    }
}
