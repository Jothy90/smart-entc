package org.smart.entc.listener;

/**
 * Created by john on 4/12/15.
 */

import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.LoggerFactory;
import org.smart.entc.repo.DBConnector;
import org.smart.entc.repo.DataLayer;
import org.smart.entc.repo.object.Node;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

public class MQTTServletContextListener
        implements ServletContextListener, MqttCallback {

    MqttClient myClient;
    MqttConnectOptions connOpt;
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MQTTServletContextListener.class);

    static final String BROKER_URL = "tcp://192.248.10.70:1883";
    static final String M2MIO_DOMAIN = "Department";
    static final String M2MIO_DOMAIN_SERVER = "Server";
    static final int SAMPLE_LENGTH = 10;
    static final double PERCENTAGE = .6;

    /**
     * connectionLost
     * This callback is invoked upon losing the MQTT connection.
     */
    @Override
    public void connectionLost(Throwable t) {
        System.out.println("Connection lost!");
        runClient();
        //TODO:code to reconnect to the broker would go here if desired
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {

        System.out.println("-------------------------------------------------");
        System.out.println("| Topic:" + s);
        System.out.println("| Message: " + new String(mqttMessage.getPayload()));
        System.out.println("-------------------------------------------------");

        String nodeName = s.replace(M2MIO_DOMAIN + "/", "");
        Node node = DataLayer.getNodeByName(nodeName);
        if (node == null) {
            node = new Node();
            node.setName(nodeName);
            node.setType(1);
            DataLayer.add(node);
        }
        String msg = new String(mqttMessage.getPayload());
        msg = msg.replace("4:2", "4:0");
        msg = msg.replace("\r", "");
        msg = msg.replace("\n", "");
        String[] readings = msg.split(" ");
        for (String ss : readings) {
            String[] reading = ss.split(":");
            switch (Integer.parseInt(reading[0])) {
                case 1:
                    //Temperature
                    int newTemp = Integer.parseInt(reading[1]);
                    if (newTemp > 45) {
                        msg.replace("1:" + newTemp, "1:" + node.getTemperature());
                    } else {
                        node.setTemperature(newTemp);
                        DataLayer.updateNodeTemperature(node);
                    }
                    break;
                case 2:
                    //Humidity
                    node.setHumidity(Integer.parseInt(reading[1]));
                    DataLayer.updateNodeHumidity(node);
                    break;
                case 3:
                    //Light
                    node.setLight(Integer.parseInt(reading[1]));
                    DataLayer.updateNodeLight(node);
                    break;
                case 4:
                    //People Count
                    node.setPeopleCount(node.getPeopleCount() + Integer.parseInt(reading[1]));
                    DataLayer.updatePeopleCount(node);
                    break;
                case 5:
                    //Noise
                    node.setNoise(Integer.parseInt(reading[1]));
                    DataLayer.updateNodeNoise(node);
                    break;
            }

        }

        //Publish for Applications
        int pubQoS = 0;
        MqttMessage message = new MqttMessage(msg.getBytes());
        message.setQos(pubQoS);
        message.setRetained(false);

        String myTopic = M2MIO_DOMAIN_SERVER + "/" + nodeName;
        MqttTopic topic = myClient.getTopic(myTopic);

        // Publish the message
        System.out.println("Publishing to topic \"" + topic + "\" qos " + pubQoS);

        try {
            // publish message to broker
            topic.publish(message);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!(msg.contains("6:") || msg.contains("7:"))) {
            //Publish for Android Applications
            int pubQoSA = 0;
            MqttMessage messageA = new MqttMessage(msg.getBytes());
            messageA.setQos(pubQoSA);
            messageA.setRetained(false);

            String myTopicA = "Android" + "/" + nodeName;
            MqttTopic topicA = myClient.getTopic(myTopicA);

            // Publish the message
            System.out.println("Publishing to topic \"" + topicA + "\" qos " + pubQoSA);

            try {
                // publish message to broker
                topicA.publish(messageA);

            } catch (Exception e) {
                e.printStackTrace();
            }
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
            DBConnector.closeStaticConnection();
            myClient.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ServletContextListener destroyed");
    }

    //Run this before web application is started
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        final MQTTServletContextListener smc = new MQTTServletContextListener();
        smc.runClient();
        System.out.println("ServletContextListener started");
        new Thread() {
            public void run() {
                smc.performActivity();
            }
        }.start();
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
        String myTopicENTC1 = M2MIO_DOMAIN + "/ENTC1";
        String myTopicMadam = M2MIO_DOMAIN + "/Madam";
        String myTopicMicro = M2MIO_DOMAIN + "/Micro";
        String[] myTopics = new String[]{myTopicENTC1, myTopicMadam, myTopicMicro};
        // subscribe to topic if subscriber

        try {
            int[] subQoS = new int[]{0, 0, 0};
            myClient.subscribe(myTopics, subQoS);
        } catch (Exception e) {
            LOGGER.error("Exception occurred in Perform runClient() Subscribe" + e);
            e.printStackTrace();
        }
    }

    private void performActivity() {
        Map<String, LinkedList<Integer>> activityMap = new HashMap<String, LinkedList<Integer>>();
        while (true) {
            try {
                List<Node> nodes = DataLayer.getNodeList();

                for (Node node : nodes) {
                    LinkedList<Integer> list = activityMap.get(node.getName());
                    if (list == null) {
                        list = new LinkedList<Integer>();
                        activityMap.put(node.getName(), list);
                    }
                    boolean change = false;
                    switch (node.getType()) {
                        case 1:
                            int act;
                            if (node.getPeopleCount() == 0 && node.getNoise() <= 2) {
                                act = 0;
                            } else if (node.getPeopleCount() == 1) {
                                act = 1;
                            } else if (node.getPeopleCount() > 1 && node.getNoise() > 1) {
                                act = 2;
                            } else {
                                break;
                            }
                            if (list.size() >= SAMPLE_LENGTH) {
                                list.removeFirst();
                                change = true;
                            }
                            list.add(act);
                            break;
                        case 2:
                            if (node.getPeopleCount() <= 2 && node.getNoise() <= 2) {
                                act = 0;
                            } else if (node.getPeopleCount() >= 10) {

                                if (node.getNoise() >= 5 && node.getNoise() <= 6) {
                                    act = 1;
                                } else if (node.getNoise() <= 4 || node.getNoise() >6) {
                                    act = 2;
                                } else {
                                    break;
                                }

                            } else if (node.getPeopleCount() < 10) {
                                if (node.getNoise() >=5 && node.getNoise() <= 6) {
                                    act = 1;
                                } else if (node.getNoise() <= 4 || node.getNoise() >6) {
                                    act = 2;
                                } else {
                                    break;
                                }
                            } else {
                                break;
                            }
                            if (list.size() >= SAMPLE_LENGTH) {
                                list.removeFirst();
                                change = true;
                            }
                            list.add(act);
                            break;
                        case 3:
                            if (node.getPeopleCount() <= 2 && node.getNoise() <= 2) {
                                act = 0;
                            } else if (node.getPeopleCount() >= 10) {

                                if (node.getNoise() >=4) {
                                    act = 1;
                                } else if (node.getNoise() <= 4) {
                                    act = 2;
                                } else {
                                    break;
                                }

                            } else if (node.getPeopleCount() < 10) {
                                if (node.getNoise() >= 4) {
                                    act = 1;
                                } else if (node.getNoise() <= 3) {
                                    act = 2;
                                } else {
                                    break;
                                }
                            } else {
                                break;
                            }
                            if (list.size() >= SAMPLE_LENGTH) {
                                list.removeFirst();
                                change = true;
                            }
                            list.add(act);
                            break;
                            /*if (node.getPeopleCount() <= 2 && node.getNoise() <= 2) {
                                act = 0;
                            } else if (node.getPeopleCount() >= 2 && node.getNoise() >= 4) {
                                act = 1;
                            } else if (node.getPeopleCount() >= 2 && node.getNoise() <= 3) {
                                act = 2;
                            } else {
                                break;
                            }
                            if (list.size() >= SAMPLE_LENGTH) {
                                list.removeFirst();
                                change = true;
                            }
                            list.add(act);
                            break;*/
                    }
                    if (change) {
                        int newActivity = -99;
                        if (Collections.frequency(list, 0) > SAMPLE_LENGTH * PERCENTAGE) {
                            newActivity = 0;
                        } else if (Collections.frequency(list, 1) > SAMPLE_LENGTH * PERCENTAGE) {
                            newActivity = 1;
                        } else if (Collections.frequency(list, 2) > SAMPLE_LENGTH * PERCENTAGE) {
                            newActivity = 2;
                        }

                        if (newActivity != -99 && newActivity != node.getActivity()) {
                            LOGGER.debug("Activity change for " + node.getName() + " " + node.getActivity() + " to " + newActivity);
                            node.setActivity(newActivity);
                            DataLayer.updateActivity(node);

                            //Publish for Applications
                            int pubQoS = 0;
                            MqttMessage message = new MqttMessage(("0:" + newActivity).getBytes());
                            message.setQos(pubQoS);
                            message.setRetained(false);

                            String myTopic = M2MIO_DOMAIN_SERVER + "/" + node.getName();
                            MqttTopic topic = myClient.getTopic(myTopic);

                            // Publish the message
                            System.out.println("Publishing to topic \"" + topic + "\" qos " + pubQoS);

                            try {
                                // publish message to broker
                                topic.publish(message);

                            } catch (Exception e) {
                                LOGGER.error("Exception occurred in Perform performActivity() Publish" + e);
                                e.printStackTrace();
                            }

                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error("Exception occurred in Perform performActivity() Main try block as" + e);
                e.printStackTrace();
            }


            try {
                Thread.sleep(500);
            } catch (Exception e) {
                LOGGER.error("Exception occurred in Perform performActivity() Thread Sleep" + e);
                e.printStackTrace();
            }
        }
    }
}