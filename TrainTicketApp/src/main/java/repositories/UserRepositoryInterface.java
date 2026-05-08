package repositories;

import domain.User;

public interface UserRepositoryInterface extends Repository<Long,User>{

    User findUserByUsername(String username);
}
