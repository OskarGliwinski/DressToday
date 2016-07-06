package com.carmelobymelo.dresstoday;
/**
 * @author Oskar Gliwinski
 */

import com.carmelobymelo.dresstoday.models.AerisWeatherModel;
import com.carmelobymelo.dresstoday.models.ForecastIOModel;
import com.carmelobymelo.dresstoday.models.WeatherModel;
import com.carmelobymelo.dresstoday.models.WundergroundModel;

import java.util.List;

public class Formula {

    private static int RESULT = 0;
    private static int workingServices;

    public static int resultFormula(List<WundergroundModel> wundergroundList,
                             List<ForecastIOModel> forecastioList,
                             List<AerisWeatherModel> aerisweatherList,
                             WeatherModel averageWeather,
                             int forecastDuration) {

        workingServices = checkWorkingServices(wundergroundList, forecastioList, aerisweatherList);

        if (workingServices < 3) {
            return 0;
        }

        averageWeather(wundergroundList, forecastioList, aerisweatherList, averageWeather, forecastDuration);

        if (averageWeather.getFeelsLike() <= -10) {
            return 1;
        }

        if (averageWeather.getFeelsLike() > -10 && averageWeather.getFeelsLike() < -5) {
            RESULT = 2;
            //average wind speed > 12 km/h or rain intensity > 1 mm
            if (averageWeather.getWindSpeed() > 12 || averageWeather.getPrecipitationMM() > 1) {
                RESULT--;
            }
            return RESULT;
        }

        if (averageWeather.getFeelsLike() >= -5 && averageWeather.getFeelsLike() < -1) {
            RESULT = 3;
            //average wind speed > 12 km/h or cloudiness > 85% or rain intensity > 1 mm
            if (averageWeather.getWindSpeed() > 12
                    || averageWeather.getHumidity() > 85
                    || averageWeather.getPrecipitationMM() > 1) {
                RESULT--;
            }
            return RESULT;
        }

        if (averageWeather.getFeelsLike() >= -1 && averageWeather.getFeelsLike() < 3) {
            RESULT = 4;
            if (averageWeather.getWindSpeed() > 12
                    || averageWeather.getHumidity() > 80
                    || averageWeather.getPrecipitationMM() > 1) {
                RESULT--;
            }
            return RESULT;
        }

        if (averageWeather.getFeelsLike() >= 3 && averageWeather.getFeelsLike() < 6) {
            RESULT = 5;
            if (averageWeather.getWindSpeed() > 12
                    || averageWeather.getHumidity() > 80
                    || averageWeather.getPrecipitationMM() > 1) {
                RESULT--;
            }
            return RESULT;
        }

        if (averageWeather.getFeelsLike() >= 6 && averageWeather.getFeelsLike() < 9) {
            RESULT = 6;
            if (averageWeather.getWindSpeed() > 13
                    || averageWeather.getHumidity() > 80
                    || averageWeather.getPrecipitationMM() > 1) {
                RESULT--;
            }
            return RESULT;
        }

        if (averageWeather.getFeelsLike() >= 9 && averageWeather.getFeelsLike() < 12) {
            RESULT = 7;
            if (averageWeather.getWindSpeed() > 13
                    || averageWeather.getHumidity() > 85
                    || averageWeather.getPrecipitationMM() > 1) {
                RESULT--;
            }
            return RESULT;
        }

        if (averageWeather.getFeelsLike() >= 12 && averageWeather.getFeelsLike() < 15) {
            RESULT = 8;
            if (averageWeather.getWindSpeed() > 13
                    || averageWeather.getHumidity() > 85
                    || averageWeather.getPrecipitationMM() > 1
                    || averageWeather.getCloudiness() > 90) {
                RESULT--;
            }
            return RESULT;
        }

        if (averageWeather.getFeelsLike() >= 15 && averageWeather.getFeelsLike() < 17) {
            RESULT = 9;
            if (averageWeather.getWindSpeed() > 14
                    || averageWeather.getHumidity() > 85
                    || averageWeather.getPrecipitationMM() > 1
                    || averageWeather.getCloudiness() > 90) {
                RESULT--;
            }
            return RESULT;
        }

        if (averageWeather.getFeelsLike() >= 17 && averageWeather.getFeelsLike() < 19) {
            RESULT = 10;
            if (averageWeather.getWindSpeed() > 14
                    || averageWeather.getHumidity() > 85
                    || averageWeather.getPrecipitationMM() > 1
                    || averageWeather.getCloudiness() > 90) {
                RESULT--;
            }
            return RESULT;
        }

        if (averageWeather.getFeelsLike() >= 19 && averageWeather.getFeelsLike() < 21) {
            RESULT = 11;
            if ((averageWeather.getWindSpeed() > 15
                    || averageWeather.getHumidity() > 85
                    || averageWeather.getPrecipitationMM() > 1) && averageWeather.getCloudiness() > 60) {
                RESULT--;
            }
        }

        if (averageWeather.getFeelsLike() >= 21 && averageWeather.getFeelsLike() < 23) {
            RESULT = 12;
            if ((averageWeather.getWindSpeed() > 17
                    || averageWeather.getHumidity() > 90
                    || averageWeather.getPrecipitationMM() > 1) && averageWeather.getCloudiness() > 60) {
                RESULT--;
            }
            return RESULT;
        }

        if (averageWeather.getFeelsLike() >= 23 && averageWeather.getFeelsLike() < 26) {
            RESULT = 13;
            if ((averageWeather.getWindSpeed() > 20
                    || averageWeather.getHumidity() > 90
                    || averageWeather.getPrecipitationMM() > 1) && averageWeather.getCloudiness() > 70) {
                RESULT--;
            }
        }

        if (averageWeather.getFeelsLike() >= 26 && averageWeather.getFeelsLike() < 30) {
            RESULT = 14;
            if ((averageWeather.getWindSpeed() > 23
                    || averageWeather.getHumidity() > 90
                    || averageWeather.getPrecipitationMM() > 1) && averageWeather.getCloudiness() > 80) {
                RESULT--;
            }
            return RESULT;
        }

        if (averageWeather.getFeelsLike() >= 30 && averageWeather.getFeelsLike() < 35) {
            RESULT = 15;
            if ((averageWeather.getWindSpeed() > 25
                    || averageWeather.getHumidity() > 90
                    || averageWeather.getPrecipitationMM() > 1) && averageWeather.getCloudiness() > 85) {
                RESULT--;
            }
            return RESULT;
        }

        if (averageWeather.getFeelsLike() >= 35) {
            RESULT = 16;
            if ((averageWeather.getWindSpeed() > 25
                    || averageWeather.getHumidity() > 90
                    || averageWeather.getPrecipitationMM() > 1) && averageWeather.getCloudiness() > 90) {
                RESULT--;
            }
            return RESULT;
        }

        return RESULT;
    }

    public static void averageWeather(List<WundergroundModel> wundergroundList,
                                      List<ForecastIOModel> forecastioList,
                                      List<AerisWeatherModel> aerisweatherList,
                                      WeatherModel averageWeather,
                                      int forecastDuration) {

        for (int i = 0; i < forecastDuration + 1; i++) {

            System.out.println("cloud forecast io=" + forecastioList.get(i).getCloudiness());
            System.out.println("cloud wunder =" + wundergroundList.get(i).getCloudiness());
            System.out.println("cloud ae=" + aerisweatherList.get(i).getCloudiness());

            averageWeather.setTemp(averageWeather.getTemp()
                    + wundergroundList.get(i).getTemp()
                    + forecastioList.get(i).getTemp()
                    + aerisweatherList.get(i).getTemp());
            averageWeather.setFeelsLike(averageWeather.getFeelsLike()
                    + wundergroundList.get(i).getFeelsLike()
                    + forecastioList.get(i).getFeelsLike()
                    + aerisweatherList.get(i).getFeelsLike());
            averageWeather.setWindSpeed(averageWeather.getWindSpeed()
                    + wundergroundList.get(i).getWindSpeed()
                    + forecastioList.get(i).getWindSpeed()
                    + aerisweatherList.get(i).getWindSpeed());
            averageWeather.setHumidity(averageWeather.getHumidity()
                    + wundergroundList.get(i).getHumidity()
                    + forecastioList.get(i).getHumidity()
                    + aerisweatherList.get(i).getHumidity());
            averageWeather.setCloudiness(averageWeather.getCloudiness()
                    + wundergroundList.get(i).getCloudiness()
                    + forecastioList.get(i).getCloudiness()
                    + aerisweatherList.get(i).getCloudiness());
            averageWeather.setProbabilityOfPrecipitation(averageWeather.getProbabilityOfPrecipitation()
                    + wundergroundList.get(i).getProbabilityOfPrecipitation()
                    + forecastioList.get(i).getProbabilityOfPrecipitation()
                    + aerisweatherList.get(i).getProbabilityOfPrecipitation());
            averageWeather.setPrecipitationMM(averageWeather.getPrecipitationMM()
                    + wundergroundList.get(i).getPrecipitationMM()
                    + forecastioList.get(i).getPrecipitationMM()
                    + aerisweatherList.get(i).getPrecipitationMM());

            System.out.println("total after i=" +i + " = " + averageWeather.getCloudiness());
        }

        averageWeather.setTemp(averageWeather.getTemp() / ((forecastDuration + 1) * workingServices));
        averageWeather.setFeelsLike(averageWeather.getFeelsLike() / ((forecastDuration + 1) * workingServices));
        averageWeather.setWindSpeed(averageWeather.getWindSpeed() / ((forecastDuration + 1) * workingServices));
        averageWeather.setHumidity(averageWeather.getHumidity() / ((forecastDuration + 1) * workingServices));
        averageWeather.setCloudiness(averageWeather.getCloudiness() / ((forecastDuration + 1) * workingServices));
        averageWeather.setProbabilityOfPrecipitation(averageWeather.getProbabilityOfPrecipitation() / ((forecastDuration + 1) * workingServices));
        averageWeather.setPrecipitationMM(averageWeather.getPrecipitationMM() / ((forecastDuration + 1) * workingServices));

        //TODO
        if (averageWeather.getHumidity() > 100) averageWeather.setHumidity(97);
        if (averageWeather.getCloudiness() > 100) averageWeather.setCloudiness(99);
        if (averageWeather.getProbabilityOfPrecipitation() > 100) averageWeather.setProbabilityOfPrecipitation(100);
    }

    static int checkWorkingServices(List<WundergroundModel> wundergroundList,
                                    List<ForecastIOModel> forecastioList,
                                    List<AerisWeatherModel> aerisweatherList) {
        int workingServices = 0;
        if (!wundergroundList.isEmpty()) {
            workingServices++;
        } else {
            System.err.println("Wunderground service is off");
        }
        if (!forecastioList.isEmpty()) {
            workingServices++;
        } else {
            System.err.println("ForecastIO service is off");
        }
        if (!aerisweatherList.isEmpty()) {
            workingServices++;
        } else {
            System.err.println("AerisWeather service is off");
        }
        return workingServices;
    }

}
