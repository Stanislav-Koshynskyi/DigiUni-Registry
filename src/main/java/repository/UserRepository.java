package repository;

import entity.Role;
import entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {
    Optional<User> findUserByLogin(String login);
    boolean existsByLogin(String login);

    List<User> findByRole(Role role);
}
