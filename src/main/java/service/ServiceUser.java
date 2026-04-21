package service;

import entity.Role;
import entity.User;
import lombok.extern.slf4j.Slf4j;
import repository.UserRepository;
import security.PasswordCoder;

import java.util.List;
import java.util.Optional;
@Slf4j
public class ServiceUser implements ServiceUserInterface {
    private final UserRepository userRepository;
    PasswordCoder passwordCoder;
    public ServiceUser(UserRepository userRepository, PasswordCoder passwordCoder) {
        this.userRepository = userRepository;
        this.passwordCoder = passwordCoder;
    }
    @Override
    public Optional<User> findUserByLogin(String username) {
        return userRepository.findUserByLogin(username);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        if (userRepository.existsByLogin(user.getLogin())) {
            log.warn("Trying to create user with login {}, but already exists", user.getLogin());
            throw new IllegalArgumentException("User with this login already exists!");
        }
        user.setPassword(passwordCoder.encodePassword(user.getPassword()));
        User result = userRepository.save(user);
        log.info("Created user with login {}, id: {}", user.getLogin(), result.getId());
        return  result;
    }
    @Override
    public User update(User user){
        return userRepository.save(user);
    }
    @Override
    public void delete(User user) {
        log.info("Deleting user with id {}", user.getId());
        userRepository.delete(user);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findByRole(Role role) {
        return userRepository.findByRole(role);
    }
}
