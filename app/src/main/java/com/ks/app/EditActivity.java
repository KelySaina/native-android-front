package com.ks.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ks.app.operations.CRUDStudent;

public class EditActivity extends AppCompatActivity {
    private String studentNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        EditText newNom = (EditText) findViewById(R.id.editTextNewName);
        EditText newNoteM = (EditText) findViewById(R.id.editTextNewNoteM);
        EditText newNoteP = (EditText) findViewById(R.id.editTextNewNotePhy);

        Intent intent = getIntent();

        studentNumber = intent.getStringExtra("studentNumber");

        String studentNom = intent.getStringExtra("studentNom");
        newNom.setText(studentNom);

        String studentNoteM = intent.getStringExtra("studentNoteM").toString();
        newNoteM.setText(studentNoteM);

        String studentNoteP = intent.getStringExtra("studentNoteP").toString();
        newNoteP.setText(studentNoteP);
    }

    @Override
    public void onStart(){
        super.onStart();
        Button btnSave = (Button)findViewById(R.id.buttonSave);
        Button btnBack = (Button)findViewById(R.id.buttonBack);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText Enom = (EditText)findViewById(R.id.editTextNewName);
                EditText EnoteM = (EditText)findViewById(R.id.editTextNewNoteM);
                EditText EnoteP = (EditText)findViewById(R.id.editTextNewNotePhy);
                String numE = studentNumber;
                String nom = Enom.getText().toString();
                String noteM = EnoteM.getText().toString();
                String noteP = EnoteP.getText().toString();

                if(numE.isEmpty() || nom.isEmpty() || noteM.isEmpty() || noteP.isEmpty()){
                    Toast.makeText(EditActivity.this, "This is my Toast message!",
                            Toast.LENGTH_LONG).show();
                }else {
                    String jsonData = "{\"numE\":\""+numE+"\",\"newNom\":\""+nom+"\",\"newNoteM\":"+noteM+",\"newNoteP\":"+noteP+"}";
                    CRUDStudent create = new CRUDStudent();
                    Log.d("EditActivity", jsonData);
                    String result = create.update_student(jsonData);
                    Log.d("EditActivity", result);
                    finish();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
