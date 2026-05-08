package repositories;

import domain.Booking;
import utils.HibernateUtils;

import java.util.List;

public class BookingRepository implements BookingRepositoryInterface {

    @Override
    public Booking findOne(Long aLong) {
        return null;
    }

    @Override
    public List<Booking> findAll() {
        return List.of();
    }

    @Override
    public Booking save(Booking entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(entity));
        return entity;
    }

    @Override
    public Booking delete(Long aLong) {
        return null;
    }

    @Override
    public Booking update(Booking entity) {
        return null;
    }
}
