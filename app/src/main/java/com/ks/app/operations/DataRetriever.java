package com.ks.app.operations;

import android.util.Log;

import com.ks.app.models.Student;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DataRetriever {

    private static final String HOST = "https://merry-allowed-rhino.ngrok-free.app";
    private final Executor executor = Executors.newSingleThreadExecutor();

    public DataRetriever() {
    }

    public CompletableFuture<String> fetchDataFromApi(String apiUrl) {
        return CompletableFuture.supplyAsync(() -> {
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

                return result.toString();
            } catch (IOException e) {
                Log.e("DataRetriever", "Error fetching data", e);
                return null;
            }
        }, executor);
    }

    public CompletableFuture<Student[]> toArrayAsync(String jsonString) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                Student[] students = objectMapper.readValue(jsonString, Student[].class);
                return students;
            } catch (IOException e) {
                Log.e("DataRetriever", "Error deserialization", e);
                return null;
            }
        }, executor);
    }

    public interface DataFetchCallback {
        void onDataFetched(String result);
    }

    public CompletableFuture<String> sendDataToApi(String apiUrl, String jsonData) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // Set request method to POST
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // Write JSON data to the API
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                // Get the response code
                int responseCode = connection.getResponseCode();

                // If the response code is HTTP_OK (200)
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    return "Data sent successfully";
                } else {
                    return "Error: " + responseCode;
                }
            } catch (Exception e) {
                return "Error sending data: "+e;
            }
        });
    }

    public Student[] toArray(String jsonString) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Student[] students = objectMapper.readValue(jsonString, Student[].class);

            return students;

        } catch (Exception e) {
            Log.e("MainActivity", "Error deserialization", e);
            return null;
        }
    }

}



