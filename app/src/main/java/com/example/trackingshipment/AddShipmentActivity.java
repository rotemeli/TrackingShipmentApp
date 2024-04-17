package com.example.trackingshipment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AddShipmentActivity extends AppCompatActivity {
    private DatabaseReference databaseShipments;
    private TextView orderNumberTextView;
    private TextView shipmentNumberTextView;
    private EditText shipmentDateEdtTxt;
    private EditText shipmentTimeEdtTxt;
    private EditText shipmentStatusEdtTxt;
    private Button saveShipmentBtn;

    private String orderNumber, shipmentNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shipment);

        orderNumber = getIntent().getStringExtra("order_number");
        shipmentNumber = getIntent().getStringExtra("shipment_number");

        initFields();
    }

    private void initFields() {
        databaseShipments = FirebaseDatabase.getInstance().getReference("shipments");

        orderNumberTextView = findViewById(R.id.orderNumberTextView);
        shipmentNumberTextView = findViewById(R.id.shipmentNumberTextView);
        shipmentDateEdtTxt = findViewById(R.id.shipmentDateEdtTxt);
        shipmentTimeEdtTxt = findViewById(R.id.shipmentTimeEdtTxt);
        shipmentStatusEdtTxt = findViewById(R.id.shipmentStatusEdtTxt);
        saveShipmentBtn = findViewById(R.id.saveShipmentBtn);

        shipmentDateEdtTxt.setOnClickListener(v -> showDateDialog(shipmentDateEdtTxt));

        orderNumberTextView.setText("Order Number: " + orderNumber);
        shipmentNumberTextView.setText("Shipment Number: " + shipmentNumber);
    }

    private void showDateDialog(EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePicker, year1, month1, day) -> {
            @SuppressLint("DefaultLocale") String selectedDate = String.format("%d/%02d/%02d", day, month1 + 1, year1);
            editText.setText(selectedDate);
        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

    public void saveShipmentClicked(View view) {
        String shipmentDate = shipmentDateEdtTxt.getText().toString();
        String shipmentTime = shipmentTimeEdtTxt.getText().toString();
        String shipmentStatus = shipmentStatusEdtTxt.getText().toString();
    }
}