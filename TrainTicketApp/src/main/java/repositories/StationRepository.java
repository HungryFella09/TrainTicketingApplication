package repositories;

import domain.Station;
import org.hibernate.Session;
import utils.HibernateUtils;

import java.util.List;

public class StationRepository implements StationRepositoryInterface {


    @Override
    public Station findOne(Long aLong) {
        return null;
    }

    @Override
    public List<Station> findAll() {
        try( Session session= HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Station ", Station.class).getResultList();
        }
    }

    @Override
    public Station save(Station entity) {
        return null;
    }

    @Override
    public Station delete(Long aLong) {
        return null;
    }

    @Override
    public Station update(Station entity) {
        return null;
    }
}
