package com.example.mark.myapplication;

import android.support.annotation.NonNull;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mark on 16-2-15.
 */
public class MqttService {
    private final static Logger LOG = LoggerFactory.getLogger(MqttService.class);
    private String clientID;
    private String topic;
    private String content;
    private String ipAddress;
    private String url;
    private int qos = 1; // quality 0 1 2
    private int port;

    public MqttService(@NonNull String ipAddress, int port) {
        url = "tcp://" + ipAddress + ":" + port;
        this.ipAddress = ipAddress;
    }

    public void run(@NonNull String clientID, @NonNull String topic, String content) {
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            MqttClient mqttClient = new MqttClient(url, clientID, persistence);
            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {

                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    LOG.debug("topic {} message {}", topic, message);
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {

                }
            });
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setCleanSession(true);
            connectOptions.setUserName("admin");
            connectOptions.setPassword(new char[]{'a', 'd', 'm', 'i', 'n'});
            mqttClient.connect(connectOptions);


            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            LOG.debug("Publish Message: {}", message);
            mqttClient.subscribe(topic, qos);
            mqttClient.publish(topic, message);
            //mqttClient.disconnect();
        } catch (MqttException e) {
//            System.out.println("reason " + me.getReasonCode());
//            System.out.println("msg " + me.getMessage());
//            System.out.println("loc " + me.getLocalizedMessage());
//            System.out.println("cause " + me.getCause());
//            System.out.println("excep " + me);
            LOG.debug("reson {}", e.getReasonCode());
            LOG.debug("msg {}", e.getMessage());
            LOG.debug("loc {}", e.getLocalizedMessage());
            LOG.debug("cause {}", e.getCause());
            LOG.debug("excep {}", e);
        }
    }
}
