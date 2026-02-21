package repository;

import entity.User;

import java.util.Optional;

public class InMemoryUserRepository extends AbstractRepositoryByLong<User> implements UserRepository{

    @Override
    public Optional<User> findUserByLogin(String login) {
        getData().values().stream().filter(d -> d.getLogin().equals(login)).findFirst();
    }
    @Override
    public boolean existsByLogin(String login) {
        return getData().values().stream().anyMatch(d -> d.getLogin().equals(login));
    }
}
