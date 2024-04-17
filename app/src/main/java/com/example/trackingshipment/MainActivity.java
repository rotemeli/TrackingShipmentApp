package com.example.trackingshipment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference databaseOrders;
    private Button addOrderBtn;
    private Button viewOrdersBtn;
    private Button viewShipmentsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting the reference to the database
        databaseOrders = FirebaseDatabase.getInstance().getReference("orders");

        addOrderBtn = findViewById(R.id.addOrderBtn);
        viewOrdersBtn = findViewById(R.id.viewOrdersBtn);
        viewShipmentsBtn = findViewById(R.id.viewShipmentsBtn);

        addOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddOrderActivity.class);
                startActivity(intent);
            }
        });

        viewOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewOrSearchOrdersActivity.class);
                startActivity(intent);
            }
        });

        viewShipmentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewOrSearchShipmentsActivity.class);
                startActivity(intent);
            }
        });
    }
}
