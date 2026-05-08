package domain;


import jakarta.persistence.*;

@jakarta.persistence.Entity
@Table(name = "station-route")
public class StationRoute extends Entity<Long>{

    @Column(name = "hour_of_arrival")
    private Integer hourOfArrival;

    @Column(name = "hour_of_departure")
    private Integer hourOfDeparture;
    
    @ManyToOne
    @JoinColumn(name="station_id", nullable=false)
    private Station station;

    @Column(name="route_id")
    private Long routeId;


    public StationRoute() {}

    public StationRoute(Long id, Integer hourOfArrival, Integer hourOfDeparture, Station station, Long routeId) {
        super(id);
        this.hourOfArrival = hourOfArrival;
        this.hourOfDeparture = hourOfDeparture;
        this.station = station;
        this.routeId = routeId;
    }

    public StationRoute(Integer hourOfArrival, Integer hourOfDeparture, Station station, Long routeId) {
        this.hourOfArrival = hourOfArrival;
        this.hourOfDeparture = hourOfDeparture;
        this.station = station;
        this.routeId = routeId;
    }

    public Integer getHourOfArrival() {
        return hourOfArrival;
    }

    public void setHourOfArrival(Integer hourOfArrival) {
        this.hourOfArrival = hourOfArrival;
    }

    public Integer getHourOfDeparture() {
        return hourOfDeparture;
    }

    public void setHourOfDeparture(Integer hourOfDeparture) {
        this.hourOfDeparture = hourOfDeparture;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    @Override
    public String toString() {
        return "StationRoute{" +
                "hourOfArrival=" + hourOfArrival +
                ", hourOfDeparture=" + hourOfDeparture +
                ", station=" + station +
                ", route=" + routeId +
                '}';
    }
}
