package com.ampliconix.javafxweaverexample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

//@Component
@RestController
@RequestMapping("/")
@FxmlView("main-stage.fxml")
public class MyController {

    @FXML
    private Label label;
    @FXML
    private TextArea responseText;
    private WeatherService weatherService;
    private byte counter = 0;

    @Autowired
    public MyController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public void loadWeatherForecast(ActionEvent actionEvent) {
//        this.label.setText(weatherService.getWeatherForecast());
        label.setText("");
    }

    @GetMapping("hello")
    public String getRequest() {
        final String message = "Hello world!";
        System.out.println(message);
        return message;
    }

    @GetMapping("print")
    public String printText(@RequestParam String text, HttpServletRequest request) {
        long time = System.currentTimeMillis();
        final AtomicReference<String> response = new AtomicReference<>(" ");
        counter++;
        final String result = "Request #" + counter + " from port " + request.getRemotePort() +
                "\r\nMessage: '" + text + "'";
        final Thread t1 = new Thread(() ->
                Platform.runLater(() -> {
                    response.set(responseText.getText());
                    label.setText(result);
                }));
        t1.start();
        while (!t1.getState().equals(Thread.State.TERMINATED)) {};
        final long lastTime = System.currentTimeMillis();
        System.out.println("Request #" + counter + " from port " + request.getRemotePort() + ":\t'" + text + "'");
        time = lastTime - time;
        System.out.println("Transition time (ms):\t" + time);
        if (request.getHeader("Timestamp") != null) {
            final String timestamp = request.getHeader("Timestamp");
            final Date respDate = new Date(lastTime);
            final SimpleDateFormat sdf =
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            final Date reqDate = sdf.parse(timestamp, new ParsePosition(0));
            System.out.println("Request time:\t" + sdf.format(reqDate));
            System.out.println("Response time:\t" + sdf.format(respDate));
            System.out.println("Waiting time (ms):\t" + (lastTime - reqDate.getTime()));
        }
        return "'" + text + "' => '" + response + "'.\tTransition time (ms):\t" + time;
    }
}
