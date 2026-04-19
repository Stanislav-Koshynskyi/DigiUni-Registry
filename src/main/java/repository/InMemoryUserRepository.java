package repository;

import entity.Role;
import entity.User;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class InMemoryUserRepository extends AbstractRepositorySaveByLong<User> implements UserRepository{

    public InMemoryUserRepository(Path file) {
        super(User.class, file);
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        return getData().values().stream().filter(d -> d.getLogin().equals(login)).findFirst();
    }
    @Override
    public boolean existsByLogin(String login) {
        return getData().values().stream().anyMatch(d -> d.getLogin().equals(login));
    }
    @Override
    public List<User> findByRole(Role role){
        return getData().values().stream().filter(u -> u.getRole().equals(role)).toList();
    }
}
