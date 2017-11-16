package com.example.android.myapplication;


import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;

public class microphone extends AppCompatActivity {

    private int sampleRate = 8000, bufferSize, bufferElement2Rec = 1024, bytesPerElement = 2;
    private Button stop_record, stop, graphMic, play;
    boolean isRunning = false;
    private AudioRecord myAudioRecorder = null;
    private Thread recordingThread = null;
    private FileOutputStream os = null;

    private void startRecording() {

        myAudioRecorder = new AudioRecord(MediaRecorder.AudioSource.MIC,
                sampleRate,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSize);

        myAudioRecorder.startRecording();
        isRunning = true;
        recordingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                writeAudioDataToFile();
            }
        },"AudioRecorderThread");
        recordingThread.start();

        stop_record.setEnabled(false);
        stop.setEnabled(true);

        Toast.makeText(getApplicationContext(),"Recording Started", Toast.LENGTH_SHORT).show();
    }

    private byte[] short2byte(short[] sData) {
        int shortArrsize = sData.length;
        byte[] bytes = new byte[shortArrsize*2];

        for (int i = 0; i < shortArrsize; i++) {
            bytes[i * 2] = (byte) (sData[i] & 0x0FF);
            bytes[(i * 2) + 1] = (byte) (sData[i] >> 8);
            sData[i] = 0;
        }
        return bytes;
    }

    private void writeAudioDataToFile() {
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        short sData[] = new short[bufferElement2Rec];
        try {
            os = new FileOutputStream(filePath + "/record.pcm");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (isRunning) {
            myAudioRecorder.read(sData, 0, bufferElement2Rec);
            System.out.println("Short writing to file" + sData.toString());
            System.out.println("Audio File" + Arrays.toString(sData));
            try {
                byte bData[] = short2byte(sData);
                os.write(bData, 0, bufferElement2Rec * bytesPerElement);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        if(null != myAudioRecorder);
        {
            isRunning = false;
            myAudioRecorder.stop();
            myAudioRecorder.release();
            myAudioRecorder = null;
            recordingThread = null;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_microphone);

        bufferSize = AudioRecord.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);

        play = (Button) findViewById(R.id.play);
        stop_record = (Button) findViewById(R.id.stop_record);
        stop = (Button) findViewById(R.id.stop);
        graphMic = (Button) findViewById(R.id.graphMic);

        stop.setEnabled(false);
        play.setEnabled(false);

        stop_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecording();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecording();
                stop_record.setEnabled(true);
                stop.setEnabled(false);

                Toast.makeText(getApplicationContext(), "Audio Recorded", Toast.LENGTH_SHORT).show();
            }
        });
    }
}