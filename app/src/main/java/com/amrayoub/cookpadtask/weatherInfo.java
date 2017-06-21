package com.amrayoub.cookpadtask;

/**
 * Created by Amr Ayoub on 6/21/2017.
 */

public class weatherInfo {
    private String weather_clouds;
    private String weather_desc;
    private String weather_ico;
    private String temp;
    private String temp_min;
    private String temp_max;
    private String pressure;
    private String humidity;
    private String wind_speed;
    private String wind_deg;

    public String getWeather_clouds() {
        return weather_clouds;
    }

    public void setWeather_clouds(String weather_clouds) {
        this.weather_clouds = weather_clouds;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(String wind_speed) {
        this.wind_speed = wind_speed;
    }

    public String getWind_deg() {
        return wind_deg;
    }

    public void setWind_deg(String wind_deg) {
        this.wind_deg = wind_deg;
    }

    public String getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(String temp_min) {
        this.temp_min = temp_min;
    }

    public String getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(String temp_max) {
        this.temp_max = temp_max;
    }

    public String getWeather_desc() {
        return weather_desc;
    }

    public void setWeather_desc(String weather_desc) {
        this.weather_desc = weather_desc;
    }

    public String getWeather_ico() {
        return weather_ico;
    }

    public void setWeather_ico(String weather_ico) {
        this.weather_ico = weather_ico;
    }
}
