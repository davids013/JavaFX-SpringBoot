package com.ampliconix.javafxweaverexample;

import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    private String message = "It's gonna snow a lot. Brace yourselves, the winter is coming.";

    public String getWeatherForecast() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
