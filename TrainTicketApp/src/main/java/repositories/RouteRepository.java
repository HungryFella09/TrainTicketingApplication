package repositories;

import domain.Route;
import domain.Station;
import org.hibernate.Session;
import utils.HibernateUtils;

import javax.management.relation.Role;
import java.util.List;

public class RouteRepository implements RouteRepositoryInterface {

    @Override
    public Route findOne(Long aLong) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery("from Route where id=:idM ", Route.class)
                    .setParameter("idM", aLong)
                    .getSingleResultOrNull();
        }
    }

    @Override
    public List<Route> findAll() {
        try( Session session= HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Route ", Route.class).getResultList();
        }
    }

    @Override
    public Route save(Route entity) {
        return null;
    }

    @Override
    public Route delete(Long aLong) {
        return null;
    }

    @Override
    public Route update(Route entity) {
        return null;
    }

    @Override
    public List<Route> routesOfStation(Station station) {
        try( Session session= HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("SELECT r FROM Route r JOIN r.stationRoutes sR WHERE sR.station=?1", Route.class)
                    .setParameter(1, station)
                    .getResultList();
        }
    }
}
