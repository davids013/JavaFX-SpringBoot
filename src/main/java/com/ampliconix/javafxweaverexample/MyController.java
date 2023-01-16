package com.ampliconix.javafxweaverexample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

//@Component
@RestController
@RequestMapping("/")
@FxmlView("main-stage.fxml")
public class MyController {

    @FXML
    private Label weatherLabel;
    private WeatherService weatherService;
    private byte counter = 0;

    @Autowired
    public MyController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public void loadWeatherForecast(ActionEvent actionEvent) {
        this.weatherLabel.setText(weatherService.getWeatherForecast());
    }

    @GetMapping("hello")
    public String getRequest() {
        final String message = "Hello world!";
        System.out.println(message);
        return message;
    }

    @GetMapping("print")
    public void printText(@RequestParam String text, HttpServletRequest request) {
        long time = System.currentTimeMillis();
//        final LocalDateTime ldt = LocalDateTime.now();
        final String result = ++counter + " " + text;
        final Thread t1 = new Thread(() ->
                Platform.runLater(() ->
                        weatherLabel.setText(result)));
        t1.start();
//        while (!t1.getState().equals(Thread.State.TERMINATED)) {};
        final long lastTime = System.currentTimeMillis();
        time = lastTime - time;
        System.out.println(result);
        System.out.println("Transition (ms):\t" + time);
        final String timestamp = request.getHeader("Timestamp");
        final Date respDate = new Date(lastTime);
        final SimpleDateFormat sdf =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        final Date reqDate = sdf.parse(timestamp, new ParsePosition(0));
        System.out.println("Request time:\t" + sdf.format(reqDate));
        System.out.println("Response time:\t" + sdf.format(respDate));
        System.out.println("Waiting time (ms):\t" + (lastTime - reqDate.getTime()));

    }
}
