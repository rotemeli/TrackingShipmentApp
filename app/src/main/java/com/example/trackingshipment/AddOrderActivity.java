package com.example.trackingshipment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AddOrderActivity extends AppCompatActivity {
    private DatabaseReference databaseOrders;

    private EditText orderDateEdtTxt;
    private EditText itemNumberEdtTxt;
    private EditText itemDescriptionEdtTxt;
    private EditText originCountryEdtTxt;
    private EditText departureDateEdtTxt;
    private EditText destinationCountryEdtTxt;
    private EditText estimatedArrivalDateEdtTxt;
    private EditText deliveryDateEdtTxt;
    private Spinner orderStatusSpinner;
    String orderStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initFields();

        loadOrderStatusSpinner();
        orderStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                orderStatus = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initFields() {
        orderDateEdtTxt = findViewById(R.id.orderDateEdtTxt);
        itemNumberEdtTxt = findViewById(R.id.itemNumberEdtTxt);
        itemDescriptionEdtTxt = findViewById(R.id.itemDescriptionEdtTxt);
        originCountryEdtTxt = findViewById(R.id.originCountryEdtTxt);
        departureDateEdtTxt = findViewById(R.id.departureDateEdtTxt);
        destinationCountryEdtTxt = findViewById(R.id.destinationCountryEdtTxt);
        estimatedArrivalDateEdtTxt = findViewById(R.id.estimatedArrivalDateEdtTxt);
        deliveryDateEdtTxt = findViewById(R.id.deliveryDateEdtTxt);
        orderStatusSpinner = findViewById(R.id.orderStatusSpinner);

        // Database Fields
        databaseOrders = FirebaseDatabase.getInstance().getReference("orders");

        orderDateEdtTxt.setOnClickListener(v -> showDateDialog(orderDateEdtTxt));
        departureDateEdtTxt.setOnClickListener(v -> showDateDialog(departureDateEdtTxt));
        estimatedArrivalDateEdtTxt.setOnClickListener(v -> showDateDialog(estimatedArrivalDateEdtTxt));
        deliveryDateEdtTxt.setOnClickListener(v -> showDateDialog(deliveryDateEdtTxt));
    }

    public void saveOrderClicked(View view) {
        String orderDate = orderDateEdtTxt.getText().toString();
        if (orderDate.isEmpty()) {
            Toast.makeText(this, "Enter an Order Date!", Toast.LENGTH_LONG).show();
            return;
        }
        String itemNumber = itemNumberEdtTxt.getText().toString();
        if (itemNumber.isEmpty()) {
            Toast.makeText(this, "Enter an Item Number!", Toast.LENGTH_LONG).show();
            return;
        }
        String itemDescription = itemDescriptionEdtTxt.getText().toString();
        if (itemDescription.isEmpty()) {
            Toast.makeText(this, "Enter an Item Description!", Toast.LENGTH_LONG).show();
            return;
        }
        String originCountry = originCountryEdtTxt.getText().toString();
        if (originCountry.isEmpty()) {
            Toast.makeText(this, "Enter an Origin Country", Toast.LENGTH_LONG).show();
            return;
        }
        String departureDate = departureDateEdtTxt.getText().toString();
        if (departureDate.isEmpty()) {
            Toast.makeText(this, "Enter an Departure Date!", Toast.LENGTH_LONG).show();
            return;
        }
        String destinationCountry = destinationCountryEdtTxt.getText().toString();
        if (destinationCountry.isEmpty()) {
            Toast.makeText(this, "Enter an Destination Country!", Toast.LENGTH_LONG).show();
            return;
        }
        String estimatedArrivalDate = estimatedArrivalDateEdtTxt.getText().toString();
        if (estimatedArrivalDate.isEmpty()) {
            Toast.makeText(this, "Enter an Estimated Arrival Date!", Toast.LENGTH_LONG).show();
            return;
        }
        String deliveryDate = deliveryDateEdtTxt.getText().toString();
        if (deliveryDate.isEmpty()) {
            Toast.makeText(this, "Enter an Delivery Date!", Toast.LENGTH_LONG).show();
            return;
        }

        Order order = new Order(orderDate, itemNumber, itemDescription, originCountry,
                departureDate, destinationCountry, estimatedArrivalDate, deliveryDate,
                orderStatus);

        // Creates a shipment for the specific order
        Intent intent = new Intent(AddOrderActivity.this, AddShipmentActivity.class);
        intent.putExtra("order_number", order.getOrderNumber());
        intent.putExtra("shipment_number", order.getShipmentNumber());
        startActivity(intent);

        addOrderToDb(order);
    }

    public void showDateDialog(EditText editText) {
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

    private void addOrderToDb(Order order) {
        String successMessage = "Order Successfully Saved\n" + "Order Number: " + order.getOrderNumber();
        String failMessage = "Failed to add order.";
        databaseOrders.child(order.getOrderNumber()).setValue(order)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, successMessage, Toast.LENGTH_LONG).show();

                    // Close this activity and return to the previous one
                    finish();
                }).addOnFailureListener(e -> Toast.makeText(this, failMessage, Toast.LENGTH_SHORT).show());
    }

    private void loadOrderStatusSpinner() {
        String[] orderStatusValues = new String[]{"Opened", "Ready To Ship", "Sent",
                "Reached The Destination", "Received"};

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, orderStatusValues);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        orderStatusSpinner.setAdapter(arrayAdapter);
    }
}