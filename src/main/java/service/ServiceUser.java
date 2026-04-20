package service;

import entity.Role;
import entity.User;
import repository.UserRepository;
import security.PasswordCoder;

import java.util.List;
import java.util.Optional;

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
        if (userRepository.existsByLogin(user.getLogin()))
            throw new IllegalArgumentException("User with this login already exists!");

        user.setPassword(passwordCoder.encodePassword(user.getPassword()));
        return  userRepository.save(user);
    }
    @Override
    public User update(User user){
        return userRepository.save(user);
    }
    @Override
    public void delete(User user) {
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
