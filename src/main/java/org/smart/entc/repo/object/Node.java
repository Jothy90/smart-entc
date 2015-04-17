package org.smart.entc.repo.object;

/**
 * Created by john on 4/13/15.
 */
public class Node {
    private int id;
    private String name;
    private int temperature;
    private int humidity;
    private int noise;
    private int light;
    private int peopleCount;
    private int type;
    private int activity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getNoise() {
        return noise;
    }

    public void setNoise(int noise) {
        this.noise = noise;
    }

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }

    public int getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(int peopleCount) {
        this.peopleCount = peopleCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", noise=" + noise +
                ", light=" + light +
                ", peopleCount=" + peopleCount +
                ", type=" + type +
                ", activity=" + activity +
                '}';
    }
}
