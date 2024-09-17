package com.lct.bus.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "route")
public class Route {
    @Id
    private int id;
    @NotNull
    @Size(min = 1, max = 100)
    private String name;
    private LocalTime firstTrip;
    private LocalTime lastTrip;
    private Double fare;
    private LocalDateTime createdDate;
    private Boolean active;

    public Route() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalTime getFirstTrip() {
        return firstTrip;
    }

    public void setFirstTrip(LocalTime firstTrip) {
        this.firstTrip = firstTrip;
    }

    public LocalTime getLastTrip() {
        return lastTrip;
    }

    public void setLastTrip(LocalTime lastTrip) {
        this.lastTrip = lastTrip;
    }

    public Double getFare() {
        return fare;
    }

    public void setFare(Double fare) {
        this.fare = fare;
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
