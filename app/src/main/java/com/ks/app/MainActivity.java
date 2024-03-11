package com.ks.app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.ks.app.operations.DataRetriever;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private final Executor executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAdd = findViewById(R.id.buttonAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                TextView txt = findViewById(R.id.bannerText);
                txt.setText("Loading Data...");
                // Move network operation to executor
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        final String data = fetchDataFromApi();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txt.setText(data);
                            }
                        });
                    }
                });
            }
        });
    }

    private String fetchDataFromApi() {
        DataRetriever dr = new DataRetriever();
        return dr.fetchDataFromApi("https://merry-allowed-rhino.ngrok-free.app");
    }
}
