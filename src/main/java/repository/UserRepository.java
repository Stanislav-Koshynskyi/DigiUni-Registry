package repository;

import entity.User;

import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {
    Optional<User> findUserByLogin(String login);
    boolean existsByLogin(String login);

}
