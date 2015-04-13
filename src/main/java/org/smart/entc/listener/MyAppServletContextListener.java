package org.smart.entc.listener;

/**
 * Created by john on 4/12/15.
 */

import org.eclipse.paho.client.mqttv3.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyAppServletContextListener
        implements ServletContextListener, MqttCallback {

    MqttClient myClient;
    MqttConnectOptions connOpt;

    static final String BROKER_URL = "tcp://192.248.10.70:1883";
    static final String M2MIO_DOMAIN = "Department";
    static final String M2MIO_DOMAIN_SERVER = "Server";
    static final String M2MIO_STUFF = "ENTC1";

    /**
     * connectionLost
     * This callback is invoked upon losing the MQTT connection.
     */
    @Override
    public void connectionLost(Throwable t) {
        System.out.println("Connection lost!");
        // code to reconnect to the broker would go here if desired
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {

        System.out.println("-------------------------------------------------");
        System.out.println("| Topic:" + s);
        System.out.println("| Message: " + new String(mqttMessage.getPayload()));
        System.out.println("-------------------------------------------------");

        //TODO:MAIN ALGORITHM
        int noOfPeople = 0;
        String[] readings = new String(mqttMessage.getPayload()).split(" ");
        for (String ss : readings) {
            String[] reading = ss.split(":");
            switch (Integer.parseInt(reading[0])) {
                case 1:

                    break;
                case 2:

                    break;
                case 3:
                    break;
                case 4:
                    noOfPeople = noOfPeople + Integer.parseInt(reading[1]);
                    break;
                case 5:
                    break;
            }
        }


        //Publish for Applications
        int pubQoS = 0;
        MqttMessage message = mqttMessage; //new MqttMessage(pubMsg.getBytes());
        message.setQos(pubQoS);
        message.setRetained(false);

        String myTopic = M2MIO_DOMAIN_SERVER + "/" + M2MIO_STUFF;
        MqttTopic topic = myClient.getTopic(myTopic);

        // Publish the message
        System.out.println("Publishing to topic \"" + topic + "\" qos " + pubQoS);

        try {
            // publish message to broker
            topic.publish(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        try {
            // wait to ensure subscribed messages are delivered

            Thread.sleep(5000);

            myClient.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ServletContextListener destroyed");
    }

    //Run this before web application is started
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        MyAppServletContextListener smc = new MyAppServletContextListener();
        smc.runClient();
        System.out.println("ServletContextListener started");
    }

    public void runClient() {
        // setup MQTT Client
        String clientID = "myclientid_" + (int) (Math.random() * 100);
        System.out.println(clientID);
        connOpt = new MqttConnectOptions();

        connOpt.setCleanSession(true);
        connOpt.setKeepAliveInterval(30);

        // Connect to Broker
        try {
            myClient = new MqttClient(BROKER_URL, clientID);
            myClient.connect(connOpt);
            myClient.setCallback(this);
        } catch (MqttException e) {
            System.out.println("Exception in MQTT");
            e.printStackTrace();
            System.exit(-1);
        }

        System.out.println("Connected to " + BROKER_URL);

        // setup topic
        // topics on m2m.io are in the form <domain>/<stuff>/<thing>
        String myTopic = M2MIO_DOMAIN + "/" + M2MIO_STUFF;
        MqttTopic topic = myClient.getTopic(myTopic);

        // subscribe to topic if subscriber

        try {
            int subQoS = 0;
            myClient.subscribe(myTopic, subQoS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}