package com.example.trackingshipment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addOrderBtn = findViewById(R.id.addOrderBtn);
        Button viewOrdersBtn = findViewById(R.id.viewOrdersBtn);
        Button viewShipmentsBtn = findViewById(R.id.viewShipmentsBtn);

        addOrderBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddOrderActivity.class);
            startActivity(intent);
        });

        viewOrdersBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewOrSearchOrdersActivity.class);
            startActivity(intent);
        });

        viewShipmentsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewOrSearchShipmentsActivity.class);
            startActivity(intent);
        });
    }
}
