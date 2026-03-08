package ui.finders;

import entity.AcademicDegree;
import entity.AcademicRank;
import entity.Teacher;
import entity.find_criteria.ContactFind;
import entity.find_criteria.TeacherFind;
import entity.find_criteria.TeacherForAdvancedFind;
import service.ServiceTeacherInterface;
import ui.InputReader;

import java.util.*;

public class TeacherFinder {
    private ServiceTeacherInterface serviceTeacher;
    private InputReader inputReader;

    public TeacherFinder(ServiceTeacherInterface serviceTeacher, InputReader inputReader) {
        this.serviceTeacher = serviceTeacher;
        this.inputReader = inputReader;
    }

    public Optional<Teacher> findAndSelect(){
        Optional<Teacher> teacherOptional = Optional.empty();
        while (true) {
            System.out.println("Select teacher");
            TeacherFind find = inputReader.readChoose(
                    Arrays.stream(TeacherFind.values()).toList(), "Choose criteria"
            );
            switch (find) {
                case NAME -> teacherOptional = chooseTeacher(findByName());
                case SURNAME -> teacherOptional = chooseTeacher(findBySurname());
                case SALARY ->  teacherOptional = chooseTeacher(findBySalary());
                case Patronymic -> teacherOptional = chooseTeacher(findByPatronymic());
                case ACADEMIC_DEGREE ->  teacherOptional = chooseTeacher(findByAcademicDegree());
                case ACADEMIC_RANK ->   teacherOptional = chooseTeacher(findByAcademicRank());
                case UNIQUE_CODE -> teacherOptional = findByUniqueCode();
                case ADVANCED -> teacherOptional = chooseTeacher(advancedSearch());
                case SHOW_ALL -> teacherOptional = chooseTeacher(serviceTeacher.findAll());
                case CONTACT -> teacherOptional = findByContact();
                case CANCEL -> {
                    return Optional.empty();
                }
                case null, default -> teacherOptional = Optional.empty();
            }
            if (teacherOptional.isPresent()) {
                return teacherOptional;
            } else {
                System.out.println("Teacher not found. Try again");
            }
        }
    }
    public List<Teacher> findByName() {
        String name = inputReader.readString("Enter name");
        return serviceTeacher.findByName(name);
    }
    private List<Teacher> filterByName(List<Teacher> teachers) {
        String name = inputReader.readString("Enter name");
        return teachers.stream().filter(
                t -> t.getFullName().name().equals(name)
        ).toList();
    }

    public List<Teacher> findBySurname() {
        String surname = inputReader.readString("Enter surname");
        return serviceTeacher.findBySurname(surname);
    }
    private List<Teacher> filterBySurname(List<Teacher> teachers) {
        String surname = inputReader.readString("Enter surname");
        return teachers.stream().filter(
                t -> t.getFullName().surname().equals(surname)
        ).toList();
    }


    public List<Teacher> findByPatronymic() {
        String patronymic = inputReader.readString("Enter patronymic");
        return serviceTeacher.findByPatronymic(patronymic);
    }
    private List<Teacher> filterByPatronymic(List<Teacher> teachers) {
        String patronymic = inputReader.readString("Enter patronymic");
        return teachers.stream().filter(
                t -> t.getFullName().patronymic().equals(patronymic)
        ).toList();
    }

    public Optional<Teacher> findByContact() {
        ContactFind criteria = inputReader.readChoose(
                Arrays.stream(ContactFind.values()).toList(), "Choose criteria");
        Optional<Teacher> teacher;
        switch (criteria) {
            case EMAIL -> teacher = findByEmail();
            case PHONE -> teacher = findByPhone();
            case null, default -> teacher = Optional.empty();
        }
        return teacher;
    }

    public Optional<Teacher> findByEmail() {
        String email = inputReader.readString("Enter email");
        return serviceTeacher.findByEmail(email);
    }

    public Optional<Teacher> findByPhone() {
        String phone = inputReader.readString("Enter phone");
        return serviceTeacher.findByPhone(phone);
    }

    public List<Teacher> findByAcademicRank() {
        AcademicRank rank = inputReader.readChoose(
                Arrays.stream(AcademicRank.values()).toList(), "Choose rank"
        );
        return serviceTeacher.findByAcademicRank(rank);
    }
    private List<Teacher> filterByAcademicRank(List<Teacher> teachers) {
        AcademicRank rank = inputReader.readChoose(
                Arrays.stream(AcademicRank.values()).toList(), "Choose rank"
        );
        return teachers.stream().filter(
                teacher -> teacher.getAcademicRank().equals(rank)
        ).toList();
    }

    public List<Teacher> findByAcademicDegree() {
        AcademicDegree degree = inputReader.readChoose(
                Arrays.stream(AcademicDegree.values()).toList(), "Choose degree"
        );
        return serviceTeacher.findByAcademicDegree(degree);
    }
    private List<Teacher> filterByDegree(List<Teacher> teachers) {
        AcademicDegree degree = inputReader.readChoose(
                Arrays.stream(AcademicDegree.values()).toList(), "Choose degree"
        );
        return teachers.stream().filter(
                t -> t.getAcademicDegree().equals(degree)
        ).toList();
    }

    public Optional<Teacher> chooseTeacher(List<Teacher> teachers) {
        if (teachers.isEmpty())
            return Optional.empty();

        return Optional.ofNullable(
                inputReader.readChoose(
                        teachers,"Choose teacher"
                )
        );
    }
    public Optional<Teacher> findByUniqueCode() {
        String code = inputReader.readString("Enter unique code");
        return serviceTeacher.findByUniqueCode(code);
    }
    public List<Teacher> findBySalary(){
        Integer salaryMin = inputReader.readInt("Enter minium salary");
        Integer salaryMax = inputReader.readInt("Enter maximum salary");
        return serviceTeacher.findBySalary(salaryMin, salaryMax);
    }
    private List<Teacher> filterBySalary(List<Teacher> teachers) {
        Integer salaryMin = inputReader.readInt("Enter minium salary");
        Integer salaryMax = inputReader.readInt("Enter maximum salary");
        return teachers.stream().filter(
                t -> t.getSalary().intValue() >= salaryMin
                && t.getSalary().intValue() <= salaryMax
        ).toList();
    }
    public List<Teacher> advancedSearch(){
        Set<TeacherForAdvancedFind> finds = new LinkedHashSet<>();
        while (true) {
            int i = 1;
            for (TeacherForAdvancedFind find : TeacherForAdvancedFind.values()) {
                System.out.println(i++ + " : " + find);
            }
            System.out.println(i++ + ": Reset setting");
            System.out.println(i++ + ": Start searching");
            System.out.println(i + ": Cancel");
            System.out.println("Active filters: " + finds);
            int choose = inputReader.readIntInRange("Choose param or start search",
                    1, i);
            if (choose <= i - 3) {
                finds.add(TeacherForAdvancedFind.values()[choose - 1]);
            } else if (choose == i - 2) {
                finds = new LinkedHashSet<>();
            } else if (choose == i - 1) {
                return advancedFilter(finds.stream().toList());
            } else {
                return new ArrayList<>();
            }
        }
    }
    private List<Teacher> advancedFilter(List<TeacherForAdvancedFind> criteria){
        List<Teacher> teachers = serviceTeacher.findAll();
        for (TeacherForAdvancedFind find : criteria) {
            switch (find) {
                case NAME ->  teachers = filterByName(teachers);
                case SURNAME ->   teachers = filterBySurname(teachers);
                case SALARY ->    teachers = filterBySalary(teachers);
                case Patronymic ->  teachers = filterByPatronymic(teachers);
                case ACADEMIC_RANK ->   teachers = filterByAcademicRank(teachers);
                case ACADEMIC_DEGREE ->    teachers = filterByDegree(teachers);
                case null, default -> {
                    teachers = Collections.emptyList();
                }
            }
        }
        return teachers;
    }


}
/*
    NAME("By name"),
    SURNAME("By surname"),
    Patronymic("By patronymic"),
    ACADEMIC_RANK("By academic rank"),
    ACADEMIC_DEGREE("By academic degree"),
    SALARY("By salary"),
    SHOW_ALL("Show all"),
    ADVANCED("Advanced search"),
    CANCEL("Cancel");
 */