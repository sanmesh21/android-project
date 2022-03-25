package com.example.android.customerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    EditText inputemail, inputpswd;
    TextView newUser;
    Button logincheck;
    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private ProgressDialog loadingBar;
    private FirebaseAuth fAuth;
    public static final String TAG = "TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputemail = findViewById(R.id.Email);
        inputpswd = findViewById(R.id.Password);
        logincheck = findViewById(R.id.Sign);
        newUser= findViewById(R.id.ID);
        loadingBar = new ProgressDialog(this);
        fAuth = FirebaseAuth.getInstance();

        logincheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputemail.getText().toString().isEmpty()) {
                    inputemail.setError("Required");
                    return;
                } else {
                    inputemail.setError(null);
                }
                if (inputpswd.getText().toString().isEmpty()) {
                    inputpswd.setError("Required");
                    return;
                } else {
                    inputpswd.setError(null);
                }
                LoginUser();
            }
        });
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCust_registeration();
            }
        });
    }
    private void openCust_registeration() {
        Intent intent = new Intent(MainActivity.this, Cust_Register.class);
        startActivity(intent);
        finish();
    }

    private void LoginUser() {
        String mail = inputemail.getText().toString();
        String pswd = inputpswd.getText().toString();

        if (!mail.matches(emailpattern))
        {
            inputemail.setError("Enter Correct Email-Id");
            return;
        }
        else if (pswd.isEmpty() || pswd.length() < 6)
        {
            inputpswd.setError("Enter password more than 6 digits");
            return;
        }
        else
            {
                loadingBar.setTitle("Login Account");
                loadingBar.setMessage("Please wait, while we are checking credentials");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
        }
        fAuth.createUserWithEmailAndPassword(mail, pswd).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                //send verification link
                FirebaseUser user = fAuth.getCurrentUser();
                assert user != null;
                user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(MainActivity.this, "Verification mail has been sent", Toast.LENGTH_SHORT).show();
                        openCust_menu();
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: Email not sent" + e.getMessage());
                            }
                        });

            }

        });
    }

    private void openCust_menu() {
        Intent intent = new Intent(MainActivity.this, Cust_menu.class);
        startActivity(intent);
    }

}