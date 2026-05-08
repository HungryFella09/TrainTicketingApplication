package repositories;

import domain.Route;
import domain.Station;

import java.util.List;

public interface RouteRepositoryInterface extends Repository<Long, Route>{

    List<Route> routesOfStation(Station station);

}
