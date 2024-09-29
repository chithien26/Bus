package com.lct.bus.dto;

import com.lct.bus.models.Route;
import com.lct.bus.models.Station;

public class RouteStationDTO {
    private int id;
    private Route route;
    private Station station;
    private int order;
    private Double distToNext;
    private Boolean active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Double getDistToNext() {
        return distToNext;
    }

    public void setDistToNext(Double distToNext) {
        this.distToNext = distToNext;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
