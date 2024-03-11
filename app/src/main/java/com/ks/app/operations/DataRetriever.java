package com.ks.app.operations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataRetriever {

    public DataRetriever(){

    }

    public static String fetchDataFromApi(String apiUrl) {
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method to GET
            connection.setRequestMethod("GET");

            // Get the response code
            int responseCode = connection.getResponseCode();

            // If the response code is HTTP_OK (200)
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;

                // Read the response line by line
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                reader.close();
            } else {
                // Handle error cases, if needed
                result.append("Error: ").append(responseCode);
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}



