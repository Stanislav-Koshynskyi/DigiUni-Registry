package repository;

import entity.Person;
import entity.Student;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PersonRepository<P extends Person> extends Repository<P, Long> {
    List<P> findByUniqueCode(String uniqueCode);

    boolean existsByUniqueCode(String uniqueCode);

    List<P> findByName(String name);

    List<P> findBySurname(String surname);

    List<P> findByPatronymic(String patronymic);

    Optional<P> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<P> findByPhone(String phone);

    boolean existsByPhone(String phone);

    List<P> findByBirthDate(LocalDate birthDate);
}
