package com.example.android.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;
import java.util.List;
import java.util.ArrayList;


public class Accelerometer1 extends AppCompatActivity implements SensorEventListener{

    private static final String TAG = "Accelerometer1";

    private SensorManager SM;
    Sensor accelerometer;

    private TextView Xtext , Ytext, Ztext;

    Button Record;
    private boolean buttonPressed;

    List<Float> x_values = new ArrayList<Float>();
    List<Float> y_values = new ArrayList<Float>();
    List<Float> z_values = new ArrayList<Float>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer1);

        Log.d(TAG, "onCreate: Initializing Sensor Services");
        SM = (SensorManager)getSystemService(SENSOR_SERVICE);

        accelerometer = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SM.registerListener(Accelerometer1.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        Log.d(TAG, "onCreate: Registered accelerometer listener");

        Xtext = (TextView)findViewById(R.id.Xtext);
        Ytext = (TextView)findViewById(R.id.Ytext);
        Ztext = (TextView)findViewById(R.id.Ztext);

    }

    @Override
    public void onSensorChanged(final SensorEvent sensorEvent) {
        Log.d(TAG, "onSensorChanged: X: " + sensorEvent.values[0] + " Y: " + sensorEvent.values[1] + " Z: " + sensorEvent.values[2]);


        Xtext.setText("X: " + sensorEvent.values[0]);
        Ytext.setText("Y: " + sensorEvent.values[1]);
        Ztext.setText("Z: " + sensorEvent.values[2]);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}

