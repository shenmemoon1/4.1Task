package com.bignerdranch.android.task41;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.TimeUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView counter;
    private Timer timer;
    private TimerTask timerTask;
    private Double time = 0.0;
    private String Time;
    private boolean isStart = false;
    private EditText taskEdit;
    private TextView spendTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        counter = (TextView) findViewById(R.id.counter);
        taskEdit = (EditText) findViewById(R.id.taskEdit);
        spendTime = (TextView) findViewById(R.id.spend_time);
        timer = new Timer();
        if (savedInstanceState != null){
            time = savedInstanceState.getDouble(Time);
            String getTime = getTimerText();
            counter.setText(getTime);
        }
    }

    public void timeClick(View view){
        switch (view.getId()){
            case R.id.startBtn:
                if (isStart == false){
                    isStart = true;
                    startTimer();
                }else{
                    isStart = false;
                }
//                Toast.makeText(this,"start your timer", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pauseBtn:
//                Toast.makeText(this,"pause your timer", Toast.LENGTH_SHORT).show();
                if (isStart == true){
                    isStart =false;
                    timerTask.cancel();
                }

                break;
            case R.id.stopBtn:
                String getVal = String.valueOf(taskEdit.getText());
                if (timerTask != null){
                    spendTime.setText("You spent " + counter.getText() +" " + getVal + " last time!");
                    timerTask.cancel();
                    time = 0.0;
                    isStart = false;
                    counter.setText(formatTime(0,0,0));

                }
//                Toast.makeText(this,"stop your timer", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble(Time,time);
    }

    private void startTimer() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        counter.setText(getTimerText());
                    }
                });

            }
        };
        timer.scheduleAtFixedRate(timerTask,0,1000);
    }

    public String getTimerText() {
        int rounded = (int)Math.round(time);

//        time convert
        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);

//        return format time
        return formatTime(seconds,minutes,hours);
    }

    private String formatTime(int seconds, int minutes, int hours) {
        return String.format("%02d",hours) + ":" + String.format("%02d",minutes) + ":" + String.format("%02d",seconds);
    }

}
