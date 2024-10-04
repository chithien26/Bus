package com.lct.bus.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "route")
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Route {
    @Id
    int id;
    @Column(name = "route_number")
    Integer routeNumber;
    @NotNull
    @Size(min = 1, max = 100)
    String name;
    LocalTime firstTrip;
    LocalTime lastTrip;
    Double fare;
    LocalDateTime createdDate;
    Boolean active;

    @JsonIgnore
    @OneToMany(mappedBy = "route")
    Set<BusTrip> busTrips = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "route")
    Set<RouteStation> routeStationSet = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "route")
    Set<Favourite> favourites = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(Integer routeNumber) {
        this.routeNumber = routeNumber;
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
}
