package com.example.android.customerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Month_order extends AppCompatActivity {
    EditText name, mail, address, Cno, MilkType, MilkQnty;
    Button order;
    FirebaseFirestore db;
    TextView date;
    ImageView calender;
    int y, m, d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_order);
        name=findViewById(R.id.custName);
        mail=findViewById(R.id.custMail);
        address=findViewById(R.id.custAdd);
        Cno=findViewById(R.id.custPhone);
        MilkType=findViewById(R.id.custreq);
        MilkQnty=findViewById(R.id.custQunty);
        order=findViewById(R.id.order);
        db = FirebaseFirestore.getInstance();
        date= findViewById(R.id.date);
        calender= findViewById(R.id.calender);
        final Calendar c=Calendar.getInstance();
        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                y=c.get(Calendar.YEAR);
                m=c.get(Calendar.MONTH);
                d=c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog g= new DatePickerDialog(Month_order.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        date.setText(i+"/"+i1+"/"+i2);
                    }
                } ,y,m,d);
                g.show();
            }
        });
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
                if (date.getText().toString().isEmpty()) {
                    date.setError("Required");
                    return;
                } else {
                    date.setError(null);
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
        items.put("Date", date.getText().toString());

        db.collection("Monthly Order").add(items)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        name.setText("Name");
                        mail.setText("Email");
                        address.setText("Address");
                        Cno.setText("Contact_no");
                        MilkType.setText("Milk_Type");
                        MilkQnty.setText("Milk_Qnty");
                        Toast.makeText(Month_order.this,"Order Placed", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}