package com.example.trackingshipment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button saveShipmentBtn;
    private Spinner shipmentStatusSpinner;

    private String orderNumber, shipmentNumber, shipmentStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shipment);

        orderNumber = getIntent().getStringExtra("order_number");
        shipmentNumber = getIntent().getStringExtra("shipment_number");

        initFields();
        loadShipmentStatusSpinner();

        shipmentStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedTeam = (String) parent.getItemAtPosition(position);
                shipmentStatus = selectedTeam;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initFields() {
        databaseShipments = FirebaseDatabase.getInstance().getReference("shipments");

        orderNumberTextView = findViewById(R.id.orderNumberTextView);
        shipmentNumberTextView = findViewById(R.id.shipmentNumberTextView);
        shipmentDateEdtTxt = findViewById(R.id.shipmentDateEdtTxt);
        shipmentTimeEdtTxt = findViewById(R.id.shipmentTimeEdtTxt);
        saveShipmentBtn = findViewById(R.id.saveShipmentBtn);

        shipmentStatusSpinner = findViewById(R.id.shipmentStatusSpinner);

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
        if (shipmentDate.isEmpty()) {
            Toast.makeText(this, "Enter an Shipment Date!", Toast.LENGTH_LONG).show();
            return;
        }
        String shipmentTime = shipmentTimeEdtTxt.getText().toString();
        if (shipmentTime.isEmpty()) {
            Toast.makeText(this, "Enter an Shipment Time!", Toast.LENGTH_LONG).show();
            return;
        }

        Shipment shipment = new Shipment(orderNumber, shipmentNumber, shipmentDate, shipmentTime, shipmentStatus);

        addShipmentToDb(shipment);
    }

    private void addShipmentToDb(Shipment shipment) {
        String successMessage = "Shipment Successfully Saved\n" + "Order Number: " + shipment.getShipmentNumber();
        String failMessage = "Failed to add order.";
        databaseShipments.child(shipment.getOrderNumber()).setValue(shipment)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, successMessage, Toast.LENGTH_LONG).show();
                    // Close this activity and return to the previous one
                    finish();
                }).addOnFailureListener(e -> Toast.makeText(this, failMessage, Toast.LENGTH_SHORT).show());
    }

    private void loadShipmentStatusSpinner() {
        String[] shipmentStatusValues = new String[]{"Shipping with the local shipping company",
                "Left the country of origin", "Received by the airline",
                "Found in the warehouse of the country of origin",
                "Arrived at the post office of the destination country"};

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, shipmentStatusValues);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        shipmentStatusSpinner.setAdapter(arrayAdapter);
    }
}