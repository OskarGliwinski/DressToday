package com.carmelobymelo.dresstoday.models;

/**
 * @author Oskar Gliwinski
 */
public class WundergroundModel extends WeatherModel {

//    private int windSpeed; //km/h
//    private int humidity; //in %
//    private int cloudiness; //in %
//    private int probabilityOfPrecipitation; //in %
//    private int precipitationMM; //in mm
//    private int snowPrecip; //in mm

    private double snowPrecip;

    public double getSnowPrecip() {
        return snowPrecip;
    }

    public void setSnowPrecip(double snowPrecip) {
        this.snowPrecip = snowPrecip;
    }
}
