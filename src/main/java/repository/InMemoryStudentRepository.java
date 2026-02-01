package repository;

import entity.*;

import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryStudentRepository extends AbstractPersonRepository<Student> implements StudentRepository {
    public InMemoryStudentRepository() {
    }

    public List<Student> findByFormOfEducation(FormOfEducation formOfEducation) {
        Map<Long, Student> data = getData();
        return data.values().stream().filter(p -> p.getFormOfEducation().equals(formOfEducation)).toList();
    }

    public List<Student> findByStudentStatus(StudentStatus studentStatus) {
        Map<Long, Student> data = getData();
        return data.values().stream().filter(p -> p.getStudentStatus().equals(studentStatus)).toList();
    }

    public Optional<Student> findByRecordBookNumber(String recordBookNumber) {
        Map<Long, Student> data = getData();
        return data.values().stream().filter(p -> p.getRecordBookNumber().equals(recordBookNumber)).findFirst();
    }

    public boolean existsByRecordBookNumber(String recordBookNumber) {
        Map<Long, Student> data = getData();
        return data.values().stream().anyMatch(p -> p.getRecordBookNumber().equals(recordBookNumber));
    }

    public List<Student> findByYearOfAdmission(Year yearOfAdmission) {
        Map<Long, Student> data = getData();
        return data.values().stream().filter(p -> p.getYearOfAdmission().equals(yearOfAdmission)).toList();
    }

    public List<Student> findByCourse(int course) {
        Map<Long, Student> data = getData();
        return data.values().stream().filter(p -> p.getCourse() == course).toList();
    }

    public List<Student> findByGroup(StudentGroup studentGroup) {
        Map<Long, Student> data = getData();
        return data.values().stream().filter(p -> p.getGroup().equals(studentGroup)).toList();
    }
}