package com.example.android.customerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class one_order extends AppCompatActivity {
    EditText name, mail, address,Cno,MilkType,MilkQnty;
    Button order;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_order);
        name=findViewById(R.id.custName);
        mail=findViewById(R.id.custMail);
        address=findViewById(R.id.custAdd);
        Cno=findViewById(R.id.custPhone);
        MilkType=findViewById(R.id.custreq);
        MilkQnty=findViewById(R.id.custQunty);
        order=findViewById(R.id.order);
        db = FirebaseFirestore.getInstance();

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().isEmpty()) {
                    name.setError("Required");
                    return;
                } else {
                    name.setError(null);
                }
                if (address.getText().toString().isEmpty()) {
                    address.setError("Required");
                    return;
                } else {
                    address.setError(null);
                }
                if (mail.getText().toString().isEmpty()) {
                    mail.setError("Required");
                    return;
                } else {
                    mail.setError(null);
                }
                if (Cno.getText().toString().isEmpty()) {
                    Cno.setError("Required");
                    return;
                } else {
                    Cno.setError(null);
                }
                if (MilkType.getText().toString().isEmpty()) {
                    MilkType.setError("Required");
                    return;
                } else {
                    MilkType.setError(null);
                }
                if (MilkQnty.getText().toString().isEmpty()) {
                    MilkQnty.setError("Required");
                    return;
                } else {
                    MilkQnty.setError(null);
                }
                PlaceOrder();
                openpayment();
            }
        });
    }

    private void openpayment() {
        Intent intent = new Intent(this,payment.class);
        startActivity(intent);
    }

    private void PlaceOrder() {
        Map<String, String> items = new HashMap<>();
        items.put("Name", name.getText().toString().trim());
        items.put("Email", mail.getText().toString().trim());
        items.put("Address", address.getText().toString());
        items.put("Contact_No", Cno.getText().toString());
        items.put("Milk_Type", MilkType.getText().toString());
        items.put("Milk_Qnty", MilkQnty.getText().toString());

        db.collection("Once Order").add(items)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        name.setText("Name");
                        mail.setText("Email");
                        address.setText("Address");
                        Cno.setText("Contact_no");
                        MilkType.setText("Milk_Type");
                        MilkQnty.setText("Milk_Qnty");
                        Toast.makeText(one_order.this,"Order Placed", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}