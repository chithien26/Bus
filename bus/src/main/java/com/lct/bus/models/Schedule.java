package com.lct.bus.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "schedule")

@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    LocalTime arrivalTime;
    LocalDateTime createdDate;
    Boolean active;

    @ManyToOne
    @JoinColumn(name = "bus_trip_id")
    BusTrip busTrip;

    @ManyToOne
    @JoinColumn(name = "station_id")
    Station station;

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
}
