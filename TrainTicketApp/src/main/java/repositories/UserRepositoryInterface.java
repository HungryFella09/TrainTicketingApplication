package repositories;

import domain.Train;
import domain.User;

import java.util.Date;
import java.util.List;

public interface UserRepositoryInterface extends Repository<Long,User>{

    User findUserByUsername(String username);

    List<User> findAllUsersOnTrain(Train train, Date time);
}
