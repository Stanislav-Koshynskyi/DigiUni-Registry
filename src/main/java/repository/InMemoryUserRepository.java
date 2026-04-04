package repository;

import entity.User;

import java.io.File;
import java.util.Optional;

public class InMemoryUserRepository extends AbstractRepositorySaveByLong<User> implements UserRepository{

    public InMemoryUserRepository(File file) {
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
}
