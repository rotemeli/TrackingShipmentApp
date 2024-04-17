package com.example.trackingshipment;

import java.util.Date;

public class Shipment {
    private String orderNumber;
    private String shipmentNumber;
    private Date shipmentDate;
    private String shipmentTime;
    private String shipmentStatus;

    public Shipment() {
        // Default constructor for Firebase
    }

    public Shipment(String orderNumber, String shipmentNumber, Date shipmentDate, String shipmentTime, String shipmentStatus) {
        this.orderNumber = orderNumber;
        this.shipmentNumber = shipmentNumber;
        this.shipmentDate = shipmentDate;
        this.shipmentTime = shipmentTime;
        this.shipmentStatus = shipmentStatus;
    }

    // Getters
    public String getOrderNumber() {
        return orderNumber;
    }

    public String getShipmentNumber() {
        return shipmentNumber;
    }

    public Date getShipmentDate() {
        return shipmentDate;
    }

    public String getShipmentTime() {
        return shipmentTime;
    }

    public String getShipmentStatus() {
        return shipmentStatus;
    }
}

