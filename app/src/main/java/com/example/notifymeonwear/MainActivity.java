package com.example.notifymeonwear;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ListView lvFirestore;
    private ArrayList<TaskClass> alFirestore;
    private ArrayAdapter<TaskClass> aaFirestore;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvFirestore = (ListView)findViewById(R.id.listView);

        firestore = FirebaseFirestore.getInstance();
        final CollectionReference collection = firestore.collection("task");

        collection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                alFirestore = new ArrayList<TaskClass>();

                for (QueryDocumentSnapshot doc : value) {
                    if (doc.get("name") != null) {
                        TaskClass newInventory = doc.toObject(TaskClass.class);
                        newInventory.setId(doc.getId());
                        alFirestore.add(newInventory);

                    }
                }

                aaFirestore = new CustomAdapter(getApplicationContext(), R.layout.custom_listvoew, alFirestore);
                lvFirestore.setAdapter(aaFirestore);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.add) {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivityForResult(intent,1);
            return true;

        }
        return super.onOptionsItemSelected(item);

    }
}
