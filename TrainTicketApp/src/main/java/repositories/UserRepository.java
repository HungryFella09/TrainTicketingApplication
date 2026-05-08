package repositories;

import domain.Train;
import domain.User;
import exceptions.RepositoryException;
import org.hibernate.Session;
import utils.HibernateUtils;

import java.util.Date;
import java.util.List;

public class UserRepository implements UserRepositoryInterface {
    @Override
    public User findUserByUsername(String username) {
        try(Session session= HibernateUtils.getSessionFactory().openSession()) {
            List<User> users =
                    session.createQuery("from User u where u.username=?1",User.class)
                            .setParameter(1,username)
                            .getResultList();

            if(users.isEmpty()){
                throw new RepositoryException("User not found");
            }

            return users.get(0);
        }
    }

    @Override
    public List<User> findAllUsersOnTrain(Train train, Date time) {
        try( Session session= HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("SELECT u FROM Booking b JOIN b.tickets t JOIN User u on u.id=b.userId WHERE b.dayOfTrip=?1 AND t.train=?2", User.class)
                    .setParameter(1, time)
                    .setParameter(2, train)
                    .getResultList();
        }
    }

    @Override
    public User findOne(Long aLong) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public User save(User entity) {
        return null;
    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public User update(User entity) {
        return null;
    }
}
