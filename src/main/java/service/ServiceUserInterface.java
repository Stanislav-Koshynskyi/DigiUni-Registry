package service;

import entity.Role;
import entity.User;

import java.util.List;
import java.util.Optional;

public interface ServiceUserInterface {
    Optional<User> findUserByLogin(String username);
    Optional<User> findUserById(Long id);
    User save(User user);
    User update(User user);
    void delete(User user);
    List<User> findAllUsers();


    List<User> findByRole(Role role);
}
