package repositories;

import domain.Route;
import domain.Train;
import org.hibernate.Session;
import utils.HibernateUtils;

import java.util.List;

public class TrainRepository implements TrainRepositoryInterface{

    @Override
    public Train findOne(Long aLong) {
        return null;
    }

    @Override
    public List<Train> findAll() {
        try( Session session= HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Train ", Train.class).getResultList();
        }
    }

    @Override
    public Train save(Train entity) {
        return null;
    }

    @Override
    public Train delete(Long aLong) {
        return null;
    }

    @Override
    public Train update(Train entity) {
        return null;
    }

    @Override
    public List<Train> getAllByRoute(Route route) {
        try(Session session= HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Train t where t.route=?1",Train.class)
                    .setParameter(1,route)
                    .getResultList();
        }
    }
}
