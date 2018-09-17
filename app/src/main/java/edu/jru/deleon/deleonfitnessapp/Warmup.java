package edu.jru.deleon.deleonfitnessapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Warmup extends AppCompatActivity {

    TextView txtTime,txtExer;
    Button btnStart,btnReset;
    ToggleButton btnPause;
    ImageView imgDemo;
    FloatingActionButton btnHome;
   private boolean isPaused= false;
    private boolean isCanceled=false;
    private  long remTime= 0;

    int[] demo= {R.drawable.warmup1,R.drawable.warmup2,R.drawable.warmup3};
    String []exer = {"Neck Stretches","Shoulder Stretches","Arm Stretches"};
    int loopReference=0;
    MediaPlayer mp,cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warmup);

        txtTime = (TextView) findViewById(R.id.txtTime);
        txtExer = (TextView) findViewById(R.id.txtExer);
        btnPause = (ToggleButton) findViewById(R.id.btnPause);
        btnReset = (Button) findViewById(R.id.btnReset);
        btnStart = (Button) findViewById(R.id.btnStart);
        imgDemo = (ImageView) findViewById(R.id.imgWarmup);
        btnHome = (FloatingActionButton)findViewById(R.id.btnHomeWarm);

       // final MediaPlayer mp = MediaPlayer.create(this,R.raw.timesup);
       // final MediaPlayer cdsound = MediaPlayer.create(this,R.raw.countdowntimer);

        btnPause.setEnabled(false);
        btnReset.setEnabled(false);
        mp = MediaPlayer.create(Warmup.this,R.raw.timesup);
        cd = MediaPlayer.create(Warmup.this,R.raw.ding);


                btnStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        isCanceled = false;
                        btnStart.setEnabled(false);
                        btnPause.setEnabled(false);
                        btnReset.setEnabled(false);

                        long cdMillis = 3000;
                        long cdInterval = 1000;
                        final CountDownTimer cdtimer = new CountDownTimer(cdMillis, cdInterval) {
                            @Override
                            public void onTick(long l) {
                                if (isPaused || isCanceled) {
                                    cancel();

                                } else {
                                    txtTime.setText("" + l / 1000);
                                }

                            }

                            @Override
                            public void onFinish() {
                                txtTime.setText("START");
                                try{
                                    if (cd.isPlaying()){
                                        cd.reset();
                                        cd.release();
                                        cd = MediaPlayer.create(Warmup.this,R.raw.ding);
                                    } cd.start();
                                }catch (Exception e){e.printStackTrace();}
                                btnStart.setEnabled(false);
                                btnPause.setEnabled(true);
                                btnReset.setEnabled(true);

                                long millisInfuture = 11000; // 10 sec
                                long countDownInterval = 1000; // 1 sec
                                CountDownTimer countDownTimer = new CountDownTimer(millisInfuture, countDownInterval) {
                                    @Override
                                    public void onTick(long l) {


                                        if (isPaused || isCanceled) {
                                            cancel();

                                        } else {

                                                txtTime.setText("" + l / 1000);
                                                remTime = l;

                                        }
                                    }

                                    @Override
                                    public void onFinish() {

                          try{
                              if (mp.isPlaying()){
                                  mp.reset();
                                  mp.release();
                                  mp = MediaPlayer.create(Warmup.this,R.raw.timesup);
                              } mp.start();
                          }catch (Exception e){e.printStackTrace();}




                                        btnStart.setEnabled(true);
                                        txtTime.setText("DONE!");
                                        btnReset.setEnabled(false);
                                        btnPause.setEnabled(false);
                                        loopReference++;



                                        if(loopReference<3) {

                                            Bitmap bitmap = BitmapFactory.decodeResource
                                                    (getResources(), demo[loopReference]); // your bitmap
                                            ByteArrayOutputStream bs = new ByteArrayOutputStream();
                                            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bs);
                                            imgDemo.setImageBitmap(bitmap);

                                            txtExer.setText("" + exer[loopReference]);
                                        }else{
                                            loopReference=0;
                                            Bitmap bitmap = BitmapFactory.decodeResource
                                                    (getResources(), demo[loopReference]); // your bitmap
                                            ByteArrayOutputStream bs = new ByteArrayOutputStream();
                                            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bs);
                                            imgDemo.setImageBitmap(bitmap);

                                            txtExer.setText("" + exer[loopReference]);

                                        }


                                    }

                                }.start();


                            }
                        }.start();


                    }

                });

                btnPause.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //cdsound.start();
                        if (btnPause.isChecked()) {
                            isPaused = true;
                            btnReset.setEnabled(false);
                        } else {
                            isPaused = false;
                            btnReset.setEnabled(true);
                            long millisInfuture = remTime; // 10 sec
                            long countDownInterval = 1000; // 1 sec
                            new CountDownTimer(millisInfuture, countDownInterval) {
                                @Override
                                public void onTick(long l) {

                                    if (isPaused || isCanceled) {
                                        cancel();

                                    } else {


                                        txtTime.setText("" + l / 1000);
                                        remTime = l;

                                    }
                                }

                                @Override
                                public void onFinish() {

                                    try{
                                        if (mp.isPlaying()){
                                            mp.reset();
                                            mp.release();
                                            mp = MediaPlayer.create(Warmup.this,R.raw.timesup);
                                        } mp.start();
                                    }catch (Exception e){e.printStackTrace();}

                                    btnStart.setEnabled(true);
                                    txtTime.setText("DONE!");
                                    btnReset.setEnabled(false);
                                    btnPause.setEnabled(false);
                                    loopReference++;



                                    if(loopReference<=3) {

                                        Bitmap bitmap = BitmapFactory.decodeResource
                                                (getResources(), demo[loopReference]); // your bitmap
                                        ByteArrayOutputStream bs = new ByteArrayOutputStream();
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bs);
                                        imgDemo.setImageBitmap(bitmap);

                                        txtExer.setText("" + exer[loopReference]);
                                    }else{
                                        loopReference=0;
                                        Bitmap bitmap = BitmapFactory.decodeResource
                                                (getResources(), demo[loopReference]); // your bitmap
                                        ByteArrayOutputStream bs = new ByteArrayOutputStream();
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bs);
                                        imgDemo.setImageBitmap(bitmap);

                                        txtExer.setText("" + exer[loopReference]);

                                    }



                                }
                            }.start();

                        }
                    }
                });
                btnReset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isCanceled = true;
                        txtTime.setText("0");
                        remTime = 0;
                        btnStart.setEnabled(true);
                        btnPause.setEnabled(false);
                        btnReset.setEnabled(false);

                        if(mp.isPlaying()){
                            mp.stop();
                            mp.release();
                            mp = MediaPlayer.create(Warmup.this,R.raw.timesup);
                        }
                        if(cd.isPlaying()){
                            cd.stop();
                            cd.release();
                            cd = MediaPlayer.create(Warmup.this,R.raw.ding);
                        }


                    }
                });


        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mp.isPlaying()){
                    mp.stop();
                    mp.release();
                    mp = MediaPlayer.create(Warmup.this,R.raw.timesup);
                }
                if(cd.isPlaying()){
                    cd.stop();
                    cd.release();
                    cd = MediaPlayer.create(Warmup.this,R.raw.ding);
                }
                Intent intent = new Intent(Warmup.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }



}
