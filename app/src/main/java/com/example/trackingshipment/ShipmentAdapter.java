package com.example.trackingshipment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ShipmentAdapter extends RecyclerView.Adapter<ShipmentAdapter.ShipmentViewHolder> {
    private List<Shipment> shipmentList;


    public ShipmentAdapter(List<Shipment> shipmentList) {
        this.shipmentList = new ArrayList<>(shipmentList);
    }

    @NonNull
    @Override
    public ShipmentAdapter.ShipmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shipment_item, parent, false);
        return new ShipmentAdapter.ShipmentViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ShipmentAdapter.ShipmentViewHolder holder, int position) {
        Shipment shipment = shipmentList.get(position);

        // Displaying other details
        holder.orderNumberTextView.setText("Order Number: " + shipment.getOrderNumber());
        holder.shipmentNumberTextView.setText("Shipment Number: " + shipment.getShipmentNumber());
        holder.shipmentDateTextView.setText("Shipment Date: " + shipment.getShipmentDate());
        holder.shipmentTimeTextView.setText("Shipment Time: " + shipment.getShipmentTime());
        holder.shipmentStatusTextView.setText("Status: " + shipment.getShipmentStatus());
    }

    @Override
    public int getItemCount() {
        return shipmentList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateShipmentsData(List<Shipment> newShipments) {
        this.shipmentList.clear();
        this.shipmentList.addAll(newShipments);
        notifyDataSetChanged();
    }

    protected static class ShipmentViewHolder extends RecyclerView.ViewHolder {
        TextView orderNumberTextView, shipmentNumberTextView, shipmentDateTextView,
                shipmentTimeTextView, shipmentStatusTextView;

        public ShipmentViewHolder(View itemView) {
            super(itemView);
            orderNumberTextView = itemView.findViewById(R.id.orderNumberTextView);
            shipmentNumberTextView = itemView.findViewById(R.id.shipmentNumberTextView);
            shipmentDateTextView = itemView.findViewById(R.id.shipmentDateTextView);
            shipmentTimeTextView = itemView.findViewById(R.id.shipmentTimeTextView);
            shipmentStatusTextView = itemView.findViewById(R.id.shipmentStatusTextView);
        }
    }
}
