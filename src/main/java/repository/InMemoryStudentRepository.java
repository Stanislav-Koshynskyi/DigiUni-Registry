package repository;

import entity.*;

import java.time.Year;
import java.util.List;
import java.util.Map;

public class InMemoryStudentRepository extends AbstractPersonRepository<Student> implements StudentRepository {
    public InMemoryStudentRepository() {
    }
    @Override
    public List<Student> findByFormOfEducation(FormOfEducation formOfEducation) {
        Map<Long, Student> data = getData();
        return data.values().stream().filter(p -> p.getFormOfEducation().equals(formOfEducation)).toList();
    }
    @Override
    public List<Student> findByStudentStatus(StudentStatus studentStatus) {
        Map<Long, Student> data = getData();
        return data.values().stream().filter(p -> p.getStudentStatus().equals(studentStatus)).toList();
    }
    @Override
    public List<Student> findByRecordBookNumber(String recordBookNumber) {
        Map<Long, Student> data = getData();
        return data.values().stream().filter(p -> p.getRecordBookNumber().equals(recordBookNumber)).toList();
    }
    @Override
    public boolean existsByRecordBookNumber(String recordBookNumber) {
        Map<Long, Student> data = getData();
        return data.values().stream().anyMatch(p -> p.getRecordBookNumber().equals(recordBookNumber));
    }
    @Override
    public List<Student> findByYearOfAdmission(Year yearOfAdmission) {
        Map<Long, Student> data = getData();
        return data.values().stream().filter(p -> p.getYearOfAdmission().equals(yearOfAdmission)).toList();
    }

    @Override
    public List<Student> findByCourse(int courseMin, int courseMax) {
        Map<Long, Student> data = getData();
        return data.values().stream().filter(p -> p.getCourse() >= courseMin && p.getCourse() <= courseMax).toList();
    }

    @Override
    public List<Student> findByGroup(StudentGroup studentGroup) {
        Map<Long, Student> data = getData();
        return data.values().stream().filter(p -> p.getGroup().equals(studentGroup)).toList();
    }

    @Override
    public List<Student> findByUniversity(University university) {
        Map<Long, Student> data = getData();
        return data.values().stream().filter(
                p -> p.getGroup().getDepartment().getFaculty()
                        .getUniversity().equals(university)
        ).toList();
    }

    @Override
    public List<Student> findByDepartment(Department department) {
        Map<Long, Student> data = getData();
        return data.values().stream().filter(
                p -> p.getGroup().getDepartment().equals(department)
        ).toList();
    }

    @Override
    public List<Student> findByFaculty(Faculty faculty) {
        Map<Long, Student> data = getData();
        return data.values().stream().filter(
                p -> p.getGroup().getDepartment().getFaculty().equals(faculty)
        ).toList();
    }
}