package com.stift.housecontrol.mess;

import org.springframework.stereotype.Component;

@Component
public class CurrentMeasurements {

    private float feeledTemperature;
    private float temperature;
    private float absHummidty;
    private float relHummidity;
    private float windSpeed;

    private boolean rain;

    private float helligkeit1;

    public float getFeeledTemperature() {
        return feeledTemperature;
    }

    public void setFeeledTemperature(float feeledTemperature) {
        this.feeledTemperature = feeledTemperature;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getAbsHummidty() {
        return absHummidty;
    }

    public void setAbsHummidty(float absHummidty) {
        this.absHummidty = absHummidty;
    }

    public float getRelHummidity() {
        return relHummidity;
    }

    public void setRelHummidity(float relHummidity) {
        this.relHummidity = relHummidity;
    }

    public float getHelligkeit1() {
        return helligkeit1;
    }

    public void setHelligkeit1(float helligkeit1) {
        this.helligkeit1 = helligkeit1;
    }

    public boolean isRain() {
        return rain;
    }

    public void setRain(boolean rain) {
        this.rain = rain;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }
}
