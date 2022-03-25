package com.example.android.customerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Cust_menu extends AppCompatActivity {
    TextView NotesCheck;
    TextView Morder;
    TextView order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_menu);

        Morder=findViewById(R.id.order1);
        order=findViewById(R.id.order2);
        NotesCheck=findViewById(R.id.NotesCheck);
        NotesCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNotes();
            }
        });
        Morder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMorder();
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openOneorder();
            }
        });
    }
    private void openNotes() {
        Intent intent1 = new Intent(Cust_menu.this, notes.class);
        startActivity(intent1);
    }
    private void openMorder() {
        Intent intent2 = new Intent(Cust_menu.this, Month_order.class);
        startActivity(intent2);
    }
    private void openOneorder() {
        Intent intent3 = new Intent(Cust_menu.this, one_order.class);
        startActivity(intent3);
    }
}