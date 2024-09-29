package com.lct.bus.dto;


import com.lct.bus.models.Route;
import com.lct.bus.models.Schedule;
import com.lct.bus.models.Vehicle;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class BusTripDTO {
    private int id;
    private int tripNumber;
    private LocalTime departureTime;
    private LocalDateTime createdDate;
    private Boolean active;

    private Route route;
    private Vehicle vehicle;

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }



    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTripNumber() {
        return tripNumber;
    }

    public void setTripNumber(int tripNumber) {
        this.tripNumber = tripNumber;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
