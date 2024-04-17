package com.example.trackingshipment;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Order> ordersList;

    public OrderAdapter(List<Order> ordersList) {
        this.ordersList = ordersList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = ordersList.get(position);

        // Displaying other details
        holder.orderNumberTextView.setText("Order #: " + order.getOrderNumber());
        holder.itemNumberTextView.setText("Item Number: " + order.getItemNumber());
        holder.itemDescriptionTextView.setText("Item: " + order.getItemDescription());
        holder.originCountryTextView.setText("Origin: " + order.getOriginCountry());
        holder.destinationCountryTextView.setText("Destination: " + order.getDestinationCountry());
        holder.orderStatusTextView.setText("Status: " + order.getOrderStatus());
        holder.shipmentNumberTextView.setText("Shipment #: " + order.getShipmentNumber());


        // Date details
        if (order.getOrderDate() != null) {
            holder.orderDateTextView.setText("Order Date: " + order.getOrderDate());
        } else {
            holder.orderDateTextView.setText("Order Date: N/A");
        }

        if (order.getDepartureDate() != null) {
            holder.departureDateTextView.setText("Departure: " + order.getDepartureDate());
        } else {
            holder.departureDateTextView.setText("Departure: N/A");
        }

        if (order.getEstimatedArrivalDate() != null) {
            holder.estimatedArrivalDateTextView.setText("Estimated Arrival: " + order.getEstimatedArrivalDate());
        } else {
            holder.estimatedArrivalDateTextView.setText("Estimated Arrival: N/A");
        }

        if (order.getDeliveryDate() != null) {
            holder.deliveryDateTextView.setText("Delivery Date: " + order.getDeliveryDate());
        } else {
            holder.deliveryDateTextView.setText("Delivery Date: N/A");
        }
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderNumberTextView, itemDescriptionTextView, orderStatusTextView, orderDateTextView,
                originCountryTextView, departureDateTextView, destinationCountryTextView,
                estimatedArrivalDateTextView, deliveryDateTextView, shipmentNumberTextView,
                itemNumberTextView;

        public OrderViewHolder(View itemView) {
            super(itemView);
            orderNumberTextView = itemView.findViewById(R.id.orderNumberTextView);
            itemDescriptionTextView = itemView.findViewById(R.id.itemDescriptionTextView);
            orderStatusTextView = itemView.findViewById(R.id.orderStatusTextView);
            orderDateTextView = itemView.findViewById(R.id.orderDateTextView);
            originCountryTextView = itemView.findViewById(R.id.originCountryTextView);
            departureDateTextView = itemView.findViewById(R.id.departureDateTextView);
            destinationCountryTextView = itemView.findViewById(R.id.destinationCountryTextView);
            estimatedArrivalDateTextView = itemView.findViewById(R.id.estimatedArrivalDateTextView);
            deliveryDateTextView = itemView.findViewById(R.id.deliveryDateTextView);
            shipmentNumberTextView = itemView.findViewById(R.id.shipmentNumberTextView);
            itemNumberTextView = itemView.findViewById(R.id.itemNumberTextView);
        }
    }
}
