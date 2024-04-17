package com.example.trackingshipment;

import java.util.Date;
import java.util.UUID;

public class Order {
    private String orderNumber;
    private String orderDate;
    private String itemNumber;
    private String itemDescription;
    private String originCountry;
    private String departureDate;
    private String destinationCountry;
    private String estimatedArrivalDate;
    private String deliveryDate;
    private String orderStatus;
    private String shipmentNumber;

    public Order() {
        // Default constructor required for calls to DataSnapshot.getValue(Order.class)
    }

    public Order(String orderDate, String itemNumber,
                 String itemDescription, String originCountry, String departureDate,
                 String destinationCountry, String estimatedArrivalDate, String deliveryDate,
                 String orderStatus) {
        this.orderNumber = UUID.randomUUID().toString();
        this.orderDate = orderDate;
        this.itemNumber = itemNumber;
        this.itemDescription = itemDescription;
        this.originCountry = originCountry;
        this.departureDate = departureDate;
        this.destinationCountry = destinationCountry;
        this.estimatedArrivalDate = estimatedArrivalDate;
        this.deliveryDate = deliveryDate;
        this.orderStatus = orderStatus;
        this.shipmentNumber = UUID.randomUUID().toString();
    }

    // Getters
    public String getOrderNumber() {
        return orderNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public String getEstimatedArrivalDate() {
        return estimatedArrivalDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getShipmentNumber() {
        return shipmentNumber;
    }
}
