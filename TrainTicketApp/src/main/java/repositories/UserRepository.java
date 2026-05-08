package repositories;

import domain.User;
import exceptions.RepositoryException;
import org.hibernate.Session;
import utils.HibernateUtils;

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
    public User delete(Long aLong) {
        return null;
    }

    @Override
    public User update(User entity) {
        return null;
    }
}
