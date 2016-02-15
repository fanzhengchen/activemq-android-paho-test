package com.example.mark.myapplication;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    private Logger log = LoggerFactory.getLogger(MainActivity.class);

    @Bind(R.id.mqtt)
    TextView mqttBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        log.debug("fuck");

    }

    @OnClick(R.id.mqtt)
    public void testMqtt() {
        MqttService mqttService = new MqttService("192.168.1.127", 1883);
        mqttService.run("fzc", "mqtt", "I made it!!");
    }
}
