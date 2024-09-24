package com.lct.bus.dto;


import lombok.Data;

import java.time.LocalTime;

public class RouteDTO {
    private Integer id;
    private String name;
    private LocalTime firstTrip;
    private LocalTime lastTrip;
    private Double fare;
    private Boolean active;


    public RouteDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
