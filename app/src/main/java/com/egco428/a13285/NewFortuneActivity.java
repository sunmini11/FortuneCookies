package com.egco428.a13285;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.shapes.Shape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class NewFortuneActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private long lastUpdate;
    private boolean check = false;
    private int count = 1;
    Button button;
    ImageView imageView;
    TextView resultView;
    TextView timestampView;
    Intent intent;
    String message[] = {"You will get A", "You're lucky", "Don't panic", "Something surprise you today", "Work harder"};
    protected List<FortuneResults> data = new ArrayList<>();
    ArrayAdapter<FortuneResults> adapter;
    private DataSource dataSource;
    FortuneResults result;
    private boolean saveButton = false;
    String currentDateTime;
    String nameImg;
    int n;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    //private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_fortune);
        button = (Button) findViewById(R.id.shakeButton);
        imageView = (ImageView) findViewById(R.id.cookieDisplay);
        resultView = (TextView) findViewById(R.id.resultDisplay);
        timestampView = (TextView) findViewById(R.id.timestampDisplay);


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //set back button
        Toast.makeText(getApplicationContext(), "Press SHAKE button", Toast.LENGTH_SHORT).show();

        final Button button = (Button) findViewById(R.id.shakeButton);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(saveButton){
                        intent = new Intent();
                        intent.putExtra(MainActivity.imgname, nameImg);
                        intent.putExtra(MainActivity.message, message[n]);
                        intent.putExtra(MainActivity.timestamp, currentDateTime);
                        setResult(RESULT_OK, intent);
                        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Shake your phone", Toast.LENGTH_SHORT).show();
                        check = true;
                    }
                }
            });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event);
        }
    }

    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelerationSquareRoot = (x * x + y * y + z * z) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = System.currentTimeMillis();
        if (accelerationSquareRoot >= 4 && check) {
            button.setText("Shaking");

            if (count >= 4) {
                Random rand = new Random();

                n = rand.nextInt(5);
                nameImg = "opened_cookie_" + n;
                button.setText("Save");
                int res = getResources().getIdentifier(nameImg, "drawable", getPackageName());
                imageView.setImageResource(res);
                resultView.setText("Result: " + message[n]);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
                currentDateTime = dateFormat.format(new Date());
                timestampView.setText("Date: " + currentDateTime);

                saveButton = true;
            }

            if (actualTime - lastUpdate < 700) {
                return;
            }

            lastUpdate = actualTime;
            count++;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


}

