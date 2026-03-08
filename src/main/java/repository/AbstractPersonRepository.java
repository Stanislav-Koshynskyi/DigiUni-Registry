package repository;

import entity.Person;
import entity.Student;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractPersonRepository <P extends Person> extends AbstractRepositoryByLong<P> implements PersonRepository <P> {
    public AbstractPersonRepository() {
    }

    public AbstractPersonRepository(long id) {
        super(id);
    }

    public List<P> findByUniqueCode(String uniqueCode) {
        Map<Long, P> data = getData();
        return data.values().stream().filter(p -> p.getUniqueCode().equals(uniqueCode)).toList();
    }

    public boolean existsByUniqueCode(String uniqueCode) {
        Map<Long, P> data = getData();
        return data.values().stream().anyMatch(p -> p.getUniqueCode().equals(uniqueCode));
    }

    public List<P> findByName(String name) {
        Map<Long, P> data = getData();
        return data.values().stream().filter(p -> p.getFullName().name().equalsIgnoreCase(name)).toList();
    }

    public List<P> findBySurname(String surname) {
        Map<Long, P> data = getData();
        return data.values().stream().filter(p -> p.getFullName().surname().equalsIgnoreCase(surname)).toList();
    }

    public List<P> findByPatronymic(String patronymic) {
        Map<Long, P> data = getData();
        return data.values().stream().filter(p -> p.getFullName().patronymic().equalsIgnoreCase(patronymic)).toList();
    }

    public Optional<P> findByEmail(String email) {
        Map<Long, P> data = getData();
        return data.values().stream().filter(p -> p.getContact().email().equals(email)).findFirst();
    }

    public boolean existsByEmail(String email) {
        Map<Long, P> data = getData();
        return data.values().stream().anyMatch(p -> p.getContact().email().equals(email));
    }

    public Optional<P> findByPhone(String phone) {
        Map<Long, P> data = getData();
        return data.values().stream().filter(p -> p.getContact().phone().equals(phone)).findFirst();
    }

    public boolean existsByPhone(String phone) {
        Map<Long, P> data = getData();
        return data.values().stream().anyMatch(p -> p.getContact().phone().equals(phone));
    }

    public List<P> findByBirthDate(LocalDate birthDate) {
        Map<Long, P> data = getData();
        return data.values().stream().filter(p -> p.getBirthDate().equals(birthDate)).toList();
    }
}
