package txy.cmp.illuminometer;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener{

    private boolean mSensorFlag = false;
    public static final String TAG = "MainActivity";
    public static SensorManager mSensorManager;
    public static Sensor mSensor;
    public static TextView mTextView;
    public static Button startBtn,stopBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mTextView = (TextView) findViewById(R.id.number);
        startBtn = (Button) findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Start test illumination",Toast.LENGTH_SHORT).show();
                if(!mSensorFlag) {
                    enable();
                    mSensorFlag = true;
                }else{
                    Toast.makeText(MainActivity.this,"Illuminometer already open !",Toast.LENGTH_SHORT).show();
                }
            }
        });
        stopBtn = (Button) findViewById(R.id.stopBtn);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Stop test illumination",Toast.LENGTH_SHORT).show();
                disable();
                mSensorFlag = false;
            }
        });


    }

    public void enable(){

        if (mSensor == null) {
            Log.w(TAG, "Cannot detect sensors. Not enabled");
            return;
        }
            mSensorManager.registerListener(this, mSensor, mSensorManager.SENSOR_DELAY_UI);

    }

    public void disable(){

        if (mSensor == null) {
            Log.w(TAG, "Cannot detect sensors. Not enabled");
            return;
        }
        mSensorManager.unregisterListener(this);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {

        if(mSensorFlag){
            mSensorManager.unregisterListener(this);
            mSensorFlag = false;
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_LIGHT) {
            mTextView.setText(event.values[0] + "");
        }
    }
}
