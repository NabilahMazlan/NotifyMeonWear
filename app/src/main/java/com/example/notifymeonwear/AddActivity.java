package com.example.notifymeonwear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import android.content.Intent;

public class AddActivity extends AppCompatActivity {
    private EditText etName, etDescription, etSeconds;
    private Button btnAdd, btnCancel;
    private FirebaseFirestore firestore;
    int notificationId = 12345;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etName = findViewById(R.id.editTextName);
        etDescription = findViewById(R.id.editTextDescription);
        etSeconds = findViewById(R.id.editTextSeconds);
        btnAdd = findViewById(R.id.buttonAddTask);
        btnCancel = findViewById(R.id.buttonCancel);

        firestore = FirebaseFirestore.getInstance();
        final CollectionReference collection = firestore.collection("task");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: Task 3: Retrieve name and age from EditText and instantiate a new Student object
                //TODO: Task 4: Add student to database and go back to main screen

                String name = etName.getText().toString();
                String description = etDescription.getText().toString();
                int seconds = Integer.parseInt(etSeconds.getText().toString());
                TaskClass newTask = new TaskClass(seconds, name, description);

                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.SECOND, seconds);

                Intent intentNotification = new Intent(AddActivity.this, ScheduledNotification.class);
                intentNotification.putExtra("name", name);
                intentNotification.putExtra("desc", description);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(AddActivity.this, notificationId, intentNotification, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);



                collection.add(newTask)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(AddActivity.this, "Successfully added", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddActivity.this, "Not Successful", Toast.LENGTH_SHORT).show();

                            }
                        });

                finish();

            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
