package com.lct.bus.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.core.serializer.Serializer;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "station")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;
    private String name;
    private String address;
    private Double longitude;
    private Double latitude;
    private LocalDateTime createdDate;
    private Boolean active;

    @JsonIgnore
    @OneToMany(mappedBy = "station", fetch = FetchType.LAZY)
    private Set<RouteStation> routeStationSet = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "station", fetch = FetchType.LAZY)
    private Set<Schedule> schedules = new HashSet<>();

    @JsonIgnore
    public Station() {
    }

    public Set<RouteStation> getRouteStationSet() {
        return routeStationSet;
    }

    public void setRouteStationSet(Set<RouteStation> routeStationSet) {
        this.routeStationSet = routeStationSet;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
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
