package com.ks.app;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button)findViewById(R.id.btnClick);

        btn.setOnClickListener(new OnClickListener(){
            @Override
            public  void  onClick(View arg0){
                setContentView(R.layout.activity_add);

            }
        });

    }
}