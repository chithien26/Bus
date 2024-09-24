package com.lct.bus.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String license_plates;
    private int capacity;
    private LocalDateTime createdDate;
    private Boolean active;

    @OneToMany(mappedBy = "vehicle")
    private Set<BusTrip> busTrips = new HashSet<>();

    public Vehicle() {
    }

    public Set<BusTrip> getBusTrips() {
        return busTrips;
    }

    public void setBusTrips(Set<BusTrip> busTrips) {
        this.busTrips = busTrips;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLicense_plates() {
        return license_plates;
    }

    public void setLicense_plates(String license_plates) {
        this.license_plates = license_plates;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
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
