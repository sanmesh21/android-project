package com.example.android.customerapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Cust_Register extends AppCompatActivity {
    Button register;
    EditText name, password, email;
    FirebaseFirestore db;
    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public static final String TAG = "TAG";
    public static final String TAG1 = "TAG";
    private FirebaseAuth fAuth;
    private String hashMap;
    private ProgressDialog loadingBar;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_register);
        name = findViewById(R.id.Username);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        register = findViewById(R.id.Register);
        db = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        loadingBar = new ProgressDialog(this);

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().isEmpty()) {
                    name.setError("Required");
                    return;
                } else {
                    name.setError(null);
                }
                if (email.getText().toString().isEmpty()) {
                    email.setError("Required");
                    return;
                } else {
                    email.setError(null);
                }
                if (password.getText().toString().isEmpty()) {
                    password.setError("Required");
                    return;
                } else {
                    password.setError(null);
                }

                String mail = email.getText().toString();
                String pswd = password.getText().toString();

                if (!mail.matches(emailpattern)) {
                    email.setError("Enter Correct Email-Id");
                } else if (pswd.isEmpty() || pswd.length() < 6) {
                    password.setError("Enter password more than 6 digits");
                }else {
                    loadingBar.setTitle("Login Account");
                    loadingBar.setMessage("Please wait, while we are checking credentials");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    String getname = name.getText().toString();
                    String getmail = email.getText().toString();
                    String getpswd = password.getText().toString();

                    fAuth.createUserWithEmailAndPassword(getmail,getpswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                hashMap = fAuth.getCurrentUser().getUid();
                                fAuth.getCurrentUser().toString();
                                Map<String, Object> items = new HashMap<>();
                                items.put("Name", getname);
                                items.put("Email", getmail);
                                items.put("Password", getpswd);
                                Toast.makeText(Cust_Register.this, "Registered", Toast.LENGTH_SHORT).show();
                                db.collection("Customers").add(items)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Log.d(TAG, "onSuccess:User profile is created for" + getmail);
                                                openCust_menu();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG1, "onfailure:Failed to create" + e.toString());
                                            }
                                        });
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }else {
                                Toast.makeText(Cust_Register.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            register.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(getApplicationContext(), Cust_menu.class));
                                }
                            });
                        }
                    });
                }
            }
        });
    }
    private void openCust_menu() {
        Intent intent = new Intent(Cust_Register.this, Cust_menu.class);
        startActivity(intent);
        finish();
    }
}
