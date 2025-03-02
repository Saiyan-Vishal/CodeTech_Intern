package org.example;

import org.json.JSONObject;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class API {
    private static final String API_KEY = "1fe756a53fc5944e0f45387e177d6848";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(API::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Weather App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTextField cityField = new JTextField(15);
        JButton fetchButton = new JButton("Get Weather");
        JTextArea resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);

        fetchButton.addActionListener(e -> {
            String city = cityField.getText().trim();
            if (!city.isEmpty()) {
                String weatherData = fetchWeatherData(city);
                resultArea.setText(weatherData);
            } else {
                resultArea.setText("Please enter a city name.");
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Enter City: "));
        inputPanel.add(cityField);
        inputPanel.add(fetchButton);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static String fetchWeatherData(String city) {
        try {
            String urlString = BASE_URL + city + "&appid=" + API_KEY + "&units=metric";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return parseWeatherData(response.toString());
        } catch (Exception e) {
            return "Error fetching weather data.";
        }
    }

    private static String parseWeatherData(String jsonResponse) {
        JSONObject json = new JSONObject(jsonResponse);
        String city = json.getString("name");
        JSONObject main = json.getJSONObject("main");
        double temperature = main.getDouble("temp");
        double humidity = main.getDouble("humidity");
        String weatherDescription = json.getJSONArray("weather").getJSONObject(0).getString("description");

        return "City: " + city + "\n" +
                "Temperature: " + temperature + "°C\n" +
                "Humidity: " + humidity + "%\n" +
                "Condition: " + weatherDescription;
    }
}
