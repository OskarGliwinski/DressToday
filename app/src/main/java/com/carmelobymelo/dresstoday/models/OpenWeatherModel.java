package com.carmelobymelo.dresstoday.models;

/**
 * @author Oskar Gliwinski
 */

public class OpenWeatherModel {

    private String description;
    private String descriptionMultiLang;
    private double temp;
    private double windSpeed; //org m/s, change to km/h
    private double humidity;
    private double cloudiness;

    public boolean isRaining() {
        return description.toLowerCase().contains("rain");
    }

    public boolean isSnowing() {
        return description.toLowerCase().contains("snow");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionMultiLang() {
        return descriptionMultiLang;
    }

    public void setDescriptionMultiLang(String descriptionMultiLang) {
        this.descriptionMultiLang = descriptionMultiLang;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(double cloudiness) {
        this.cloudiness = cloudiness;
    }
}
