package repositories;

import domain.Booking;
import domain.Route;
import domain.Train;
import org.hibernate.Session;
import utils.HibernateUtils;

import java.util.List;

public class BookingRepository implements BookingRepositoryInterface {

    @Override
    public Booking findOne(Long aLong) {
        return null;
    }

    @Override
    public List<Booking> findAll() {
        try( Session session= HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Booking ", Booking.class).getResultList();
        }
    }

    @Override
    public Booking save(Booking entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(entity));
        return entity;
    }

    @Override
    public void delete(Booking entity) {

    }

    @Override
    public Booking update(Booking entity) {
        return null;
    }

    @Override
    public List<Booking> bookingsOfTrain(Train train) {
        try( Session session= HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("SELECT b FROM Booking b JOIN b.tickets t WHERE t.train=?1", Booking.class)
                    .setParameter(1, train)
                    .getResultList();
        }
    }
}
