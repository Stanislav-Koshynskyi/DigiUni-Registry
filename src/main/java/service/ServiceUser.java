package service;

import entity.User;
import repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class ServiceUser implements ServiceUserInterface {
    private final UserRepository userRepository;
    public ServiceUser(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        return  userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
