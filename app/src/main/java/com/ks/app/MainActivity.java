package com.ks.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ks.app.models.Student;
import com.ks.app.operations.CRUDStudent;
import com.ks.app.operations.DataRetriever;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private final Executor executor = Executors.newSingleThreadExecutor();
    private static final String TAG = "MainActivity";

    private Student[] students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onStart(){
        super.onStart();
        Button btnRef = (Button) findViewById(R.id.buttonRefresh);
        Button btnAdd = (Button) findViewById(R.id.buttonAdd);

        TextView bannerText = findViewById(R.id.bannerText);
        bannerText.setText("Loading data...");

        // Example data (replace this with your actual data retrieval logic)
        DataRetriever dr = new DataRetriever();
        dr.fetchDataFromApi("https://merry-allowed-rhino.ngrok-free.app/listEtudiants")
                .thenApplyAsync(data -> {
                    students = dr.toArray(data);
                    // Update UI with student data on the main thread
                    runOnUiThread(() -> {
                        if (students != null && students.length > 0) {
                            bannerText.setText(students.toString());
                            updateUIWithStudentData(students);
                            bannerText.setText("");
                        } else {
                            bannerText.setText("Failed to fetch students data.\nCheck your internet connection");
                        }
                    });

                    // Return the result to the next stage of the CompletableFuture chain
                    return students;
                })
                .exceptionally(throwable -> {
                    // Handle exceptions here, if needed
                    Log.e(TAG, "Error fetching or processing data", throwable);
                    return null;
                });

        btnRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(add_intent);
            }
        });
    }

    public void refresh(){
        ListView l = (ListView) findViewById(R.id.listView);
        l.setAdapter(null);
        TextView bannerText = findViewById(R.id.bannerText);
        bannerText.setText("Loading data...");
        DataRetriever dr = new DataRetriever();
        dr.fetchDataFromApi("https://merry-allowed-rhino.ngrok-free.app/listEtudiants")
                .thenApplyAsync(data -> {
                    students = dr.toArray(data);
                    // Update UI with student data on the main thread
                    runOnUiThread(() -> {
                        if (students != null && students.length > 0) {
                            bannerText.setText(students.toString());
                            updateUIWithStudentData(students);
                            bannerText.setText("");
                        } else {
                            bannerText.setText("Failed to fetch students data.\nCheck your internet connection");
                        }
                    });

                    // Return the result to the next stage of the CompletableFuture chain
                    return students;
                })
                .exceptionally(throwable -> {
                    // Handle exceptions here, if needed
                    Log.e(TAG, "Error fetching or processing data", throwable);
                    return null;
                });
    }

    private void updateUIWithStudentData(Student[] students) {
        ListView listView = findViewById(R.id.listView);

        // Extract student names
        String[] studentNames = new String[students.length];
        for (int i = 0; i < students.length; i++) {
            studentNames[i] = students[i].getNom()+"\n#"+students[i].getNumEt()+"\n\nMathematique:"+students[i].getNoteMath()+"         Physique:"+students[i].getNotePhy();
        }

        // Create an ArrayAdapter to bind data to the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                studentNames
        );

        // Set the adapter to the ListView
        listView.setAdapter(adapter);

        // Optional: Set an item click listener for the ListView
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Student selectedStudent = students[position];

            // Get the student number (numEt) from the selected student
            String selectedStudentNumEt = selectedStudent.getNumEt();

            // Log or use the student number as needed

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Edit-Delete");
            builder.setMessage("Edit or Delete Student #"+selectedStudentNumEt+" records");

            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Perform action on positive button click (e.g., confirm deletion)
                    CRUDStudent delete = new CRUDStudent();
                    String resultDel = delete.delete_student(selectedStudentNumEt);
                    Log.d(TAG, resultDel);
                    refresh();
                }
            });

            builder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Dismiss the dialog (optional)
                    String selectedStudentNom = selectedStudent.getNom();
                    String selectedStudentNoteM = String.valueOf(selectedStudent.getNoteMath());
                    String selectedStudentNoteP = String.valueOf(selectedStudent.getNotePhy());
                    Intent edit_intent = new Intent(MainActivity.this, EditActivity.class);
                    edit_intent.putExtra("studentNumber", selectedStudentNumEt);
                    edit_intent.putExtra("studentNom", selectedStudentNom);
                    edit_intent.putExtra("studentNoteM", selectedStudentNoteM);
                    edit_intent.putExtra("studentNoteP", selectedStudentNoteP);
                    startActivity(edit_intent);
                }
            });

            builder.setCancelable(true);

            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }
}
