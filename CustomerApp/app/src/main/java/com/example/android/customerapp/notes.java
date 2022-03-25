package com.example.android.customerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class notes extends AppCompatActivity {
    private static final String TAG ="notes";
    TextView note;
    private TextView textViewData;

    private static final String KEY_TITLE = "note";
    private FirebaseFirestore db;
    Button clicknote;
    private RecyclerView mFireStoreList;

    private DocumentReference noteref = db.collection("Admin Notes").document("Notes");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        clicknote = findViewById(R.id.clicknote);
        db = FirebaseFirestore.getInstance();
        textViewData = findViewById(R.id.mFireStoreList);

        clicknote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadNote();
            }
        });


    }


    public void loadNote() {
        noteref.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String AdminNote = documentSnapshot.getString(KEY_TITLE);
                            textViewData.setText("Note:"+note);

                        }else{
                            Toast.makeText(notes.this, "Document Does not Exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(notes.this, "Error", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());

                    }
                });
    }
}