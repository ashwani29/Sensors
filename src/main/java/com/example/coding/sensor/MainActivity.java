package com.example.coding.sensor;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import static java.lang.Math.abs;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ConstraintLayout  constraintLayout= (ConstraintLayout) findViewById(R.id.constraint);

        final SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensor = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for(Sensor s : sensor){
            Log.e("TAG", s.getName());
        }


        final SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float [] arr= event.values;
                int x,y,z;
                x= (int) (abs(arr[0]/9.8)*255);

                y= (int) (abs(arr[1]/9.8)*255);

                z= (int) (abs(arr[2]/9.8)*255);
                constraintLayout.setBackgroundColor(Color.rgb(x,y,z));

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        final Sensor acc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        Button button = (Button) findViewById(R.id.b);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).setTitle("Do u want to register listener")
                        .setMessage("Please Select").setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sensorManager.registerListener(sensorEventListener, acc, SensorManager.SENSOR_DELAY_NORMAL);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setCancelable(false)
                        .create();

                alertDialog.show();
            }
        });


    }
}
