package com.lct.bus.dto;

import com.lct.bus.models.BusTrip;
import com.lct.bus.models.Station;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class ScheduleDTO {
    private int id;
    private LocalTime arrivalTime;
    private Station station;
    private BusTrip busTrip;
    private LocalDateTime createdDate;
    private Boolean active;

    public BusTrip getBusTrip() {
        return busTrip;
    }

    public void setBusTrip(BusTrip busTrip) {
        this.busTrip = busTrip;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
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
