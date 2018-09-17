package edu.jru.deleon.deleonfitnessapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.TimerTask;

public class Pedometer extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    Sensor stepSensor;
    TextView txtStep,calories,distance,speed;
    boolean running = false;
    double CaloriesBurned;
    double distanceComp;
   double speedComp;
    long steps = 0;
     double walkingFactor = 0.57;
     double CaloriesBurnedPerMile;
     double strip;
     double stepCountMile; // step/mile
     double conversionFactor;
     double weight = 67.0; // kg
     double height = 178.0; // cm

    FloatingActionButton btnHome;

     long startTime;
     long currTime;


    boolean isPaused=false;
    boolean isCanceled=false;

    MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometer);

        txtStep = (TextView)findViewById(R.id.txtStep);
        calories = (TextView)findViewById(R.id.caloriesValue);
        distance =(TextView)findViewById(R.id.distanceValue);
        speed = (TextView)findViewById(R.id.speedValue);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        TextView txtAverageSpeed = (TextView) findViewById(R.id.speed);
        TextView speedValue = (TextView) findViewById(R.id.speedValue);
        btnHome = (FloatingActionButton)findViewById(R.id.btnHomePedo);

        mp = MediaPlayer.create(Pedometer.this,R.raw.ding);

        CaloriesBurnedPerMile = walkingFactor * (weight * 2.2);
        strip = height * 0.415;
        stepCountMile = 160934.4 / strip;
        conversionFactor = CaloriesBurnedPerMile / stepCountMile;

        startTime = 0;
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Pedometer.this,MainActivity.class);
                startActivity(intent);
            }
        });


        Context context = findViewById(android.R.id.content).getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formview = inflater.inflate(R.layout.inputweight, null, false);
        final EditText editTextWeight = (EditText) formview.findViewById(R.id.txtWeight);
        final EditText editTextHeight = (EditText) formview.findViewById(R.id.txtHeight);

        new AlertDialog.Builder(context)
                .setView(formview)
                .setTitle("Weight and Height")
                .setPositiveButton("ENTER",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                try {
                                    weight = Double.parseDouble(editTextWeight.getText().toString());
                                    height = Double.parseDouble(editTextHeight.getText().toString());
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                //  String studentFirstname = editTextStudentFirstname.getText().toString();
                                //  String studentEmail = editTextStudentEmail.getText().toString();



                                dialog.cancel();

                            }

                        }).show();





        if (stepSensor != null) {
            txtAverageSpeed.setVisibility(View.VISIBLE);
            speedValue.setVisibility(View.VISIBLE);
            //   sensorManager.registerListener(this,stepSensor,SensorManager.SENSOR_DELAY_UI);
            Toast.makeText(this, "SENSOR DETECTED!", Toast.LENGTH_SHORT).show();}
        else{
            Toast.makeText(this, "SENSOR NOT AVAILABLE IN DEVICE!\n CHRONOMETER IS ACTIVATED", Toast.LENGTH_LONG).show();
            txtAverageSpeed.setVisibility(View.GONE);
            speedValue.setVisibility(View.GONE);
        }

    }

    public void togglePedometer(View v) {
        Button toggle = (Button) findViewById(R.id.toggle);
        Button reset = (Button)findViewById(R.id.reset);


        if (stepSensor != null) {

            //   sensorManager.registerListener(this,stepSensor,SensorManager.SENSOR_DELAY_UI);
            if(!running){
                running = true;
                if(startTime == 0) {
                    startTime = System.currentTimeMillis();
                }
                toggle.setText("STOP");
                try{
                    if (mp.isPlaying()){
                        mp.reset();
                        mp.release();
                        mp = MediaPlayer.create(Pedometer.this,R.raw.ding);
                    } mp.start();
                }catch (Exception e){e.printStackTrace();}
                sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);
            }
            else {
                toggle.setText("START");
                running = false;
                sensorManager.unregisterListener(this,stepSensor);
            }
        } else { if(toggle.getText().equals("START")) {

            try{
                if (mp.isPlaying()){
                    mp.reset();
                    mp.release();
                    mp = MediaPlayer.create(Pedometer.this,R.raw.ding);
                } mp.start();
            }catch (Exception e){e.printStackTrace();}
                   toggle.setText("STOP");
                    long millisInFuture = 99999000;
                    long interval = 1000;
                isCanceled = false;
            reset.setEnabled(false);

                    CountDownTimer countDownTimer = new CountDownTimer(millisInFuture, interval) {
                        @Override
                        public void onTick(long l) {
                            if (isPaused || isCanceled) {
                                cancel();}
                            else {

                                steps++;
                                startTime++;
                                txtStep.setText(Long.toString(steps));
                                CaloriesBurned = steps * conversionFactor; //in cals
                                distanceComp = (steps * strip) / 100000; // in km
                                currTime = System.currentTimeMillis();
                                speedComp = distanceComp * 1000000 / (currTime - startTime);
                                distance.setText(Math.round(distanceComp * 1000d) / 1000d + " km");
                                speed.setText(Math.round(speedComp * 1000d) / 1000d + " m/s");
                                calories.setText(Math.round(CaloriesBurned * 1000d) / 1000d + " cals");
                            }
                        }

                        @Override
                        public void onFinish() {

                        }
                    }.start();
                }else
                {
                    toggle.setText("START");
                    isCanceled = true;
                    toggle.setEnabled(false);
                    reset.setEnabled(true);

                }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
      /*  if (stepSensor != null) {
            //   sensorManager.registerListener(this,stepSensor,SensorManager.SENSOR_DELAY_UI);
            Toast.makeText(this, "SENSOR DETECTED!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "SENSOR NOT DETECTED!", Toast.LENGTH_SHORT).show();
        }*/
    }

        @Override
    protected void onPause() {
        super.onPause();
      //  running = false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        float[] values = event.values;
        int value = -1;

        if (values.length > 0) {
            value = (int) values[0];
        }


        if (sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            steps++;
            txtStep.setText(Long.toString(steps));
            CaloriesBurned = steps * conversionFactor; //in cals
            distanceComp = (steps * strip) / 100000; // in km
            currTime = System.currentTimeMillis();
            speedComp = distanceComp*1000000/(currTime - startTime);
            distance.setText(Math.round(distanceComp*1000d)/1000d+" km");
            speed.setText(Math.round(speedComp*1000d)/1000d+" m/s");
            calories.setText(Math.round(CaloriesBurned*1000d)/1000d+" cals");
        }
    }
    public void resetPedometer(View v){

        steps = 0;
        distanceComp = 0;
        speedComp=0;
        CaloriesBurned = 0;
        distance.setText(distanceComp+" km");
        speed.setText(speedComp+" m/s");
        calories.setText(CaloriesBurned+" cals");
        txtStep.setText(Long.toString(steps));
        isCanceled = true;

        Button toggle = (Button) findViewById(R.id.toggle);

       if(toggle.getText()=="START") {
           toggle.setText("START");
           toggle.setEnabled(true);
           isCanceled = true;
       }




    }
        public void alertDialog(View view) {

       Context context = view.getRootView().getContext();


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formview = inflater.inflate(R.layout.inputweight, null, false);


        final EditText editTextWeight = (EditText) formview.findViewById(R.id.txtWeight);
        final EditText editTextHeight = (EditText) formview.findViewById(R.id.txtHeight);

        new AlertDialog.Builder(context)
                .setView(formview)
                .setTitle("Weight and Height")
                .setPositiveButton("ENTER",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                try {
                                    weight = Double.parseDouble(editTextWeight.getText().toString());
                                    height = Double.parseDouble(editTextHeight.getText().toString());
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                              //  String studentFirstname = editTextStudentFirstname.getText().toString();
                              //  String studentEmail = editTextStudentEmail.getText().toString();



                                dialog.cancel();

                            }

                        }).show();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
