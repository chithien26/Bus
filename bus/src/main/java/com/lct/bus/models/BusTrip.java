package com.lct.bus.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "bus_trip")
public class BusTrip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int tripNumber;
    private LocalTime departureTime;
    private LocalDateTime createdDate;
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route Route;

    @ManyToOne
    @JoinColumn(name = "bus_trip_id")
    private Vehicle vehicle;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public BusTrip() {
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

    public com.lct.bus.models.Route getRoute() {
        return Route;
    }

    public void setRoute(com.lct.bus.models.Route route) {
        Route = route;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
