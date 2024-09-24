package com.lct.bus.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "route")
    private Set<BusTrip> busTrips = new HashSet<>();

    @OneToMany(mappedBy = "route")
    private Set<RouteStation> routeStationSet = new HashSet<>();

    @OneToMany(mappedBy = "route")
    private Set<Favourite> favourites = new HashSet<>();


    public Route() {
    }

    public Set<RouteStation> getRouteStationSet() {
        return routeStationSet;
    }

    public void setRouteStationSet(Set<RouteStation> routeStationSet) {
        this.routeStationSet = routeStationSet;
    }

    public Set<Favourite> getFavourites() {
        return favourites;
    }

    public void setFavourites(Set<Favourite> favourites) {
        this.favourites = favourites;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotNull @Size(min = 1, max = 100) String getName() {
        return name;
    }

    public void setName(@NotNull @Size(min = 1, max = 100) String name) {
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


    public Set<BusTrip> getBusTrips() {
        return busTrips;
    }

    public void setBusTrips(Set<BusTrip> busTrips) {
        this.busTrips = busTrips;
    }
}
