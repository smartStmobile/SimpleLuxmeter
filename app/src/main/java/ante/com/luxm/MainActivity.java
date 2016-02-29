package ante.com.luxm;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import ante.com.simpleluxmeter.R;


public class MainActivity extends AppCompatActivity implements SensorEventListener {


    private static final String MOONLESS = "Moonless,overcast night sky ";
    private static final String MOONLESS_WITH_AIRGLOW = "Moonless clear night sky with airglow ";
    private static final String FULL_MOON_CLEAR = "Fool moon on a clear night";
    private static final String CIVIL_TWILIGHT_CLEAR_SKY = "Dark limit of civil twilight under a clear sky ";
    private static final String LIVING_ROOM = "Family living room lights ";
    private static final String DARK_OVERCAST_DAY = "Very dark overcast day ";
    private static final String OFFICE_HALLWAY = "Office building hallway/toilet lighting ";
    private static final String OFFICE_LIGHTNING = "Office lighting,Sunrise or sunset on a clear day. ";
    private static final String SUNRISE_SUNSET_CLEAR = "Sunrise or sunset on a clear day. ";
    private static final String OVERCAST_DAY = "Overcast day;Typical TV studio lightning ";
    private static final String FULL_DAYLIGHT = "Full daylight (not direct sun)";
    private static final String DIRECT_SUNLIGHT = "Direct sunlight";
    private static ProgressBar progressBar;
    private static float min = 20;
    private static float max = 0;
    private SensorManager sensorManager;
    private Sensor sensorLight;
    private TextView textViewLight, textViewExplanation, textmin, textMax;
    private ImageView imageView;
    private String luminance = "";
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Toast.makeText(getApplicationContext(),String.valueOf(MANUFACTURER.equalsIgnoreCase("LGE")), Toast.LENGTH_LONG).show();


        try {
            // Toast.makeText(MainActivity.this,"getting sensor type.light",Toast.LENGTH_SHORT).show();
            sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            Toast.makeText(MainActivity.this, "Sensor: " + " " + sensorLight.getName() + sensorLight.getVendor() + "\n" +
                    " max power: " + String.valueOf(sensorLight.getPower()), Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Can't find Sensor type:Light", Toast.LENGTH_LONG).show();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    doStuff();
                }
            }, 2000);


        }

        textViewLight = (TextView) findViewById(R.id.textViewLight);
        textmin = (TextView) findViewById(R.id.textMin);
        textMax = (TextView) findViewById(R.id.textMax);
        textViewExplanation = (TextView) findViewById(R.id.textViewExplanation);
        imageView = (ImageView) findViewById(R.id.imageView);

        imageView.setImageResource(R.drawable.allsun);

        // imageView.setVisibility(View.INVISIBLE);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);


    }

    private void doStuff() {
        //Toast.makeText(this, "Delayed Toast!", Toast.LENGTH_SHORT).show();
        finish();

    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorLight, SensorManager.SENSOR_DELAY_NORMAL);
        // Resume the AdView.
        //  mAdView.resume();
    }

    @Override
    public void onPause() {

        super.onPause();
        sensorManager.unregisterListener(this, sensorLight);

    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        sensorManager.unregisterListener(this, sensorLight);


    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {


            if (event.values[0] < 110) {


                if (event.values[0] >= 0.0001 && event.values[0] < 0.002) {
                    luminance = MOONLESS;
                    progressBar.setProgress(0);


                } else if (event.values[0] >= 0.002 && event.values[0] < 0.27) {
                    luminance = MOONLESS_WITH_AIRGLOW;
                    progressBar.setProgress(3);

                } else if (event.values[0] >= 0.27 && event.values[0] < 1.1) {
                    luminance = FULL_MOON_CLEAR;
                    progressBar.setProgress(5);

                } else if (event.values[0] >= 2.5 && event.values[0] < 45) {
                    luminance = CIVIL_TWILIGHT_CLEAR_SKY;
                    progressBar.setProgress(10);

                } else if (event.values[0] >= 45 && event.values[0] < 60) {
                    luminance = LIVING_ROOM;
                    progressBar.setProgress(25);

                } else if (event.values[0] >= 60 && event.values[0] < 90) {
                    luminance = OFFICE_HALLWAY;
                    progressBar.setProgress(30);

                } else if (event.values[0] >= 90 && event.values[0] < 110) {
                    luminance = DARK_OVERCAST_DAY;
                    progressBar.setProgress(40);

                }
            } else {
                if (event.values[0] >= 110 && event.values[0] < 310) {
                    luminance = OFFICE_LIGHTNING;
                    progressBar.setProgress(50);
                } else if (event.values[0] >= 310 && event.values[0] < 500) {
                    luminance = OFFICE_LIGHTNING;
                    progressBar.setProgress(60);
                    //imageView.setImageResource(R.drawable.overcastday);
                } else if (event.values[0] >= 500 && event.values[0] < 1100) {
                    luminance = OVERCAST_DAY;
                    progressBar.setProgress(70);
                    // imageView.setImageResource(R.drawable.sun);
                } else if (event.values[0] >= 1100 && event.values[0] < 9000) {
                    luminance = OVERCAST_DAY;
                    progressBar.setProgress(80);
                    // imageView.setImageResource(R.drawable.sun);
                } else if (event.values[0] >= 9000 && event.values[0] < 27000) {
                    luminance = FULL_DAYLIGHT;
                    progressBar.setProgress(90);
                    //imageView.setImageResource(R.drawable.sun);
                } else if (event.values[0] >= 27000 && event.values[0] < 100000) {
                    luminance = DIRECT_SUNLIGHT;
                    progressBar.setProgress(100);
                }
            }
        }

        if (event.values[0] < min) {
            min = event.values[0];
        }
        if (event.values[0] > max) {
            max = event.values[0];
        }


        textViewLight.setText(String.valueOf(event.values[0]));
        textViewExplanation.setText(luminance);
        textMax.setText("max: " + String.valueOf(max));
        textmin.setText("min: " + String.valueOf(min));


    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
