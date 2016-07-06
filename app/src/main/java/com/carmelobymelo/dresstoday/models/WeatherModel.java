package com.carmelobymelo.dresstoday.models;

/**
 * @author Oskar Gliwinski
 */
public class WeatherModel {

    private double temp;
    private double feelsLike;
    private double windSpeed;
    private double humidity;
    private double cloudiness;
    private double probabilityOfPrecipitation;
    private double precipitationMM;

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public double getProbabilityOfPrecipitation() {
        return probabilityOfPrecipitation;
    }

    public void setProbabilityOfPrecipitation(double probabilityOfPrecipitation) {
        this.probabilityOfPrecipitation = probabilityOfPrecipitation;
    }

    public double getPrecipitationMM() {
        return precipitationMM;
    }

    public void setPrecipitationMM(double precipitationMM) {
        this.precipitationMM = precipitationMM;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(double cloudiness) {
        this.cloudiness = cloudiness;
    }

}
