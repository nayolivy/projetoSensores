package com.example.xubuntu_developer.projetosensores;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;


public class sensors extends ActionBarActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private long lastUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new Clicado());

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();
    }

    private class Clicado implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            sortear();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sensors, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];


        //TextView textView = (TextView) findViewById(R.id.text_view);
        //textView.setText(x + ", " + y + ", " + z);

        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = System.currentTimeMillis();

        if (accelationSquareRoot >= 2) //
        {
            //Toast.makeText(this, "O celular foi balançado", Toast.LENGTH_SHORT)
            //        .show();

            if (actualTime - lastUpdate < 200) {
                return;
            }
            lastUpdate = actualTime;


            sortear();


        }
    }
    public void sortear(){

        Random radom  = new Random();

        Set<Integer> numeros = new TreeSet< Integer>();

        while (numeros.size() < 6) {
            numeros.add(radom.nextInt(60) + 1);
        }

        Iterator<Integer> it = numeros.iterator();


        TextView textView = (TextView) findViewById(R.id.text_view);
        textView.setText(Integer.toString(it.next()));


        TextView textView1= (TextView) findViewById(R.id.textView1);
        textView1.setText(Integer.toString(it.next()));


        TextView textView2= (TextView) findViewById(R.id.textView2);
        textView2.setText(Integer.toString(it.next()));


        TextView textView3= (TextView) findViewById(R.id.textView3);
        textView3.setText(Integer.toString(it.next()));


        TextView textView4= (TextView) findViewById(R.id.textView4);
        textView4.setText(Integer.toString(it.next()));




    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        // register this class as a listener for the orientation and
        // accelerometer sensors
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (sensor != null) {
            sensorManager.registerListener(this,
                    sensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, "Sensor not available!", Toast.LENGTH_SHORT)
                    .show();
        }

    }
    @Override
    protected void onPause() {
        // unregister listener
        super.onPause();
        sensorManager.unregisterListener(this);
    }


}
