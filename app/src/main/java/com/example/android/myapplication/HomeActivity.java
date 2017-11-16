package com.example.android.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class HomeActivity extends AppCompatActivity {

    public Button accelerometer;
    public Button microphone;

    public void init() {
        microphone = (Button) findViewById(R.id.microphone);
        microphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toy = new Intent(HomeActivity.this, microphone.class);

                startActivity(toy);
            }
        });

        accelerometer = (Button) findViewById(R.id.accelerometer);
        accelerometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toy = new Intent(HomeActivity.this, Accelerometer1.class);

                startActivity(toy);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }
}