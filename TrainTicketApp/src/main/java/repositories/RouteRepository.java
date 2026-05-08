package repositories;

import domain.Route;
import domain.Station;
import org.hibernate.Session;
import utils.HibernateUtils;

import javax.management.relation.Role;
import java.util.List;
import java.util.Objects;

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
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(entity));
        return entity;
    }

    @Override
    public void delete(Route entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.remove(entity));
    }

    @Override
    public Route update(Route entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            if (!Objects.isNull(session.find(Route.class, entity.getId()))) {
                session.merge(entity);
                session.flush();
            }
        });
        return entity;
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
