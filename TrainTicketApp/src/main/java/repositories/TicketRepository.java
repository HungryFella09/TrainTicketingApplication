package repositories;

import domain.Route;
import domain.Ticket;
import domain.Train;
import org.hibernate.Session;
import utils.HibernateUtils;

import java.util.Date;
import java.util.List;

public class TicketRepository implements TicketRepositoryInterface {
    @Override
    public long countByTrainAndDate(Train train, Date date) {
        try( Session session= HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("SELECT Count(t) FROM Ticket t JOIN t.booking b WHERE t.train=?1 AND b.dayOfTrip=?2", Long.class)
                    .setParameter(1, train)
                    .setParameter(2, date)
                    .getSingleResult();
        }
    }

    @Override
    public Ticket findOne(Long aLong) {
        return null;
    }

    @Override
    public List<Ticket> findAll() {
        return List.of();
    }

    @Override
    public Ticket save(Ticket entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(entity));
        return entity;
    }

    @Override
    public Ticket delete(Long aLong) {
        return null;
    }

    @Override
    public Ticket update(Ticket entity) {
        return null;
    }
}
