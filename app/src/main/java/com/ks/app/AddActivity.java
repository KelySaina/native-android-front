 package com.ks.app;

 import androidx.appcompat.app.AppCompatActivity;
 import android.os.Bundle;
 import android.util.Log;
 import android.view.View;
 import android.widget.Button;
 import android.widget.EditText;
 import android.widget.Toast;

 import com.google.android.material.textfield.TextInputEditText;
 import com.ks.app.operations.CRUDStudent;

 public class AddActivity extends AppCompatActivity {
     @Override
     protected void onCreate(Bundle savedInstanceState){
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_add);
     }

     @Override
     public void onStart(){
         super.onStart();
         Button btnSave = (Button)findViewById(R.id.buttonSave);
         Button btnBack = (Button)findViewById(R.id.buttonBack);

         btnSave.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 EditText EnumE = (EditText)findViewById(R.id.editTextMatricule);
                 EditText Enom = (EditText)findViewById(R.id.editTextName);
                 EditText EnoteM = (EditText)findViewById(R.id.editTextNoteM);
                 EditText EnoteP = (EditText)findViewById(R.id.editTextNotePhy);
                 String numE = EnumE.getText().toString();
                 String nom = Enom.getText().toString();
                 String noteM = EnoteM.getText().toString();
                 String noteP = EnoteP.getText().toString();

                 if(numE.isEmpty() || nom.isEmpty() || noteM.isEmpty() || noteP.isEmpty()){
                     Toast.makeText(AddActivity.this, "This is my Toast message!",
                             Toast.LENGTH_LONG).show();
                 }else {
                     String jsonData = "{\"numE\":\""+numE+"\",\"nom\":\""+nom+"\",\"noteM\":"+noteM+",\"noteP\":"+noteP+"}";
                     CRUDStudent create = new CRUDStudent();
                     Log.d("AddActivity", jsonData);
                     String result = create.create_student(jsonData);
                     Log.d("AddActivity", result);
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
