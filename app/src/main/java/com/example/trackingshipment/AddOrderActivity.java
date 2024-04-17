package com.example.trackingshipment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Calendar;

public class AddOrderActivity extends AppCompatActivity {
    private DatabaseReference databaseOrders;
    private DatabaseReference databaseShipments;
    private TextView orderNumberTxtView;
    private EditText orderDateEdtTxt;
    private EditText itemNumberEdtTxt;
    private EditText itemDescriptionEdtTxt;
    private EditText originCountryEdtTxt;
    private EditText departureDateEdtTxt;
    private EditText destinationCountryEdtTxt;
    private EditText estimatedArrivalDateEdtTxt;
    private EditText deliveryDateEdtTxt;
    private EditText orderStatusEdtTxt;
    private TextView shipmentNumberTxtView;
    private Button saveOrderBtn;

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
    }

    private void initFields() {
//        orderNumberTxtView = findViewById(R.id.orderNumberTxtView);
        orderDateEdtTxt = findViewById(R.id.orderDateEdtTxt);
        itemNumberEdtTxt = findViewById(R.id.itemNumberEdtTxt);
        itemDescriptionEdtTxt = findViewById(R.id.itemDescriptionEdtTxt);
        originCountryEdtTxt = findViewById(R.id.originCountryEdtTxt);
        departureDateEdtTxt = findViewById(R.id.departureDateEdtTxt);
        destinationCountryEdtTxt = findViewById(R.id.destinationCountryEdtTxt);
        estimatedArrivalDateEdtTxt = findViewById(R.id.estimatedArrivalDateEdtTxt);
        deliveryDateEdtTxt = findViewById(R.id.deliveryDateEdtTxt);
        orderStatusEdtTxt = findViewById(R.id.orderStatusEdtTxt);
//        shipmentNumberTxtView = findViewById(R.id.shipmentNumberTxtView);
        saveOrderBtn = findViewById(R.id.saveOrderBtn);

        // Database Fields
        databaseOrders = FirebaseDatabase.getInstance().getReference("orders");
        databaseShipments = FirebaseDatabase.getInstance().getReference("shipments");

        orderDateEdtTxt.setOnClickListener(v -> showDateDialog(orderDateEdtTxt));
        departureDateEdtTxt.setOnClickListener(v -> showDateDialog(departureDateEdtTxt));
        estimatedArrivalDateEdtTxt.setOnClickListener(v -> showDateDialog(estimatedArrivalDateEdtTxt));
        deliveryDateEdtTxt.setOnClickListener(v -> showDateDialog(deliveryDateEdtTxt));
    }

    public void saveOrderClicked(View view) {
//        String orderNumber = orderNumberTxtView.getText().toString();
        String orderDate = orderDateEdtTxt.getText().toString();
        String itemNumber = itemNumberEdtTxt.getText().toString();
        String itemDescription = itemDescriptionEdtTxt.getText().toString();
        String originCountry = originCountryEdtTxt.getText().toString();
        String departureDate = departureDateEdtTxt.getText().toString();
        String destinationCountry = destinationCountryEdtTxt.getText().toString();
        String estimatedArrivalDate = estimatedArrivalDateEdtTxt.getText().toString();
        String deliveryDate = deliveryDateEdtTxt.getText().toString();
        String orderStatus = orderStatusEdtTxt.getText().toString();

        // TODO
        // Check the variables
        // Toast.makeText(this, "Enter an Order Number", Toast.LENGTH_LONG).show();

        Order order = new Order(orderDate, itemNumber, itemDescription, originCountry,
                departureDate, destinationCountry, estimatedArrivalDate, deliveryDate,
                orderStatus);

        Intent intent = new Intent(AddOrderActivity.this, AddShipmentActivity.class);
        intent.putExtra("order_number", order.getOrderNumber());
        intent.putExtra("shipment_number", order.getShipmentNumber());
        startActivity(intent);

        String shipmentStatus = "In the warehouse of the origin country";
        Shipment shipment = new Shipment(order.getOrderNumber(), order.getShipmentNumber(),
                order.getDepartureDate(), LocalDateTime.now().toString(), shipmentStatus);

        addOrderAndShipmentToDb(order, shipment);
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

    private void addOrderAndShipmentToDb(Order order, Shipment shipment) {
        String successMessage = "Order Successfully Saved\n" + "Order Number: " + order.getOrderNumber();
        String failMessage = "Failed to add order.";
        databaseOrders.child(order.getOrderNumber()).setValue(order)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, successMessage, Toast.LENGTH_LONG).show();

                    // Add the shipment after added the order
                    databaseShipments.child(shipment.getShipmentNumber()).setValue(shipment);

                    // Close this activity and return to the previous one
                    finish();
                }).addOnFailureListener(e -> Toast.makeText(this, failMessage, Toast.LENGTH_SHORT).show());
    }
}