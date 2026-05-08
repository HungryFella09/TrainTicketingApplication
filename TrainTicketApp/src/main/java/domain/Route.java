package domain;

import jakarta.persistence.*;

import java.util.List;


@jakarta.persistence.Entity
@Table(name = "routes")
public class Route extends Entity<Long> {

    private String name;

//    @OneToMany(mappedBy="route", cascade = CascadeType.ALL, orphanRemoval = true)

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="route_id", referencedColumnName = "id")
    private List<StationRoute> stationRoutes;

    public Route() {
        super();
    }

    public Route(Long aLong, String name, List<StationRoute> stationRoutes) {
        super(aLong);
        this.name = name;
        this.stationRoutes = stationRoutes;
    }

    public List<StationRoute> getStationRoutes() {
        return stationRoutes;
    }

    public void setStationRoutes(List<StationRoute> stationRoutes) {
        this.stationRoutes = stationRoutes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Route{" +
                "name='" + name + '\'' +
                ", stationRoutes=" + stationRoutes +
                '}';
    }
}
