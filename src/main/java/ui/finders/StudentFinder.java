package ui.finders;

import entity.*;
import entity.find_criteria.*;
import service.ServiceStudentInterface;
import ui.InputReader;

import java.util.*;

public class StudentFinder {
    private final UniversityFinderInterface universityFinder;
    private final FacultyFinderInterface facultyFinder;
    private final DepartmentFinderInterface departmentFinder;
    private final StudentGroupFinderInterface studentGroupFinder;
    private final ServiceStudentInterface serviceStudent;
    private final InputReader inputReader;

    public StudentFinder(InputReader inputReader, UniversityFinderInterface universityFinder, FacultyFinderInterface facultyFinder,
                         DepartmentFinderInterface departmentFinder, StudentGroupFinderInterface studentGroupFinder,
                         ServiceStudentInterface serviceStudent
    ) {
        this.inputReader = inputReader;
        this.universityFinder = universityFinder;
        this.facultyFinder = facultyFinder;
        this.departmentFinder = departmentFinder;
        this.studentGroupFinder = studentGroupFinder;
        this.serviceStudent = serviceStudent;

    }

    public Optional<Student> chooseStudent(List<Student> students) {
        if (students == null || students.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(inputReader.readChoose(students, "Choose student"));
        }
    }

    public List<Student> findByFormOfEducation() {
        FormOfEducation formOfEducation = inputReader.readChoose(
                Arrays.stream(FormOfEducation.values()).toList(), "Choose form of education"
        );
        return serviceStudent.findByFormOfEducation(formOfEducation);
    }

    public List<Student> findByRecordBook() {
        String recordBook = inputReader.readString("Enter record book number");
        return serviceStudent.findByRecordBookNumber(recordBook);
    }

    public List<Student> findByStudentStatus() {
        StudentStatus status = inputReader.readChoose(
                Arrays.stream(StudentStatus.values()).toList(), "Choose student status"
        );
        return serviceStudent.findByStudentStatus(status);
    }

    public List<Student> findByCourse() {
        int courseMin = inputReader.readInt("Enter minium course");
        int courseMax = inputReader.readInt("Enter maxium course");
        return serviceStudent.findByCourse(courseMin, courseMax);
    }

    public List<Student> findByStudentGroup() {
        Optional<StudentGroup> studentGroup = studentGroupFinder.findAndSelect();
        if (studentGroup.isEmpty()) {
            return Collections.emptyList();
        } else {
            return serviceStudent.findByStudentGroup(studentGroup.get());
        }
    }

    public List<Student> findByUniqueCode() {
        String uniqueCode = inputReader.readString("Enter unique code");
        return serviceStudent.findByUniqueCode(uniqueCode);
    }

    public List<Student> findByName() {
        String name = inputReader.readString("Enter name");
        return serviceStudent.findByName(name);
    }

    public List<Student> findBySurname() {
        String surname = inputReader.readString("Enter surname");
        return serviceStudent.findBySurname(surname);
    }

    public List<Student> findByPatronymic() {
        String patronymic = inputReader.readString("Enter patronymic");
        return serviceStudent.findByPatronymic(patronymic);
    }

    public List<Student> findByUniversity() {
        Optional<University> university = universityFinder.findAndSelect();
        if (university.isEmpty()) {
            return Collections.emptyList();
        } else {
            return serviceStudent.findByUniversity(university.get());
        }
    }

    public List<Student> findByFaculty() {
        Optional<Faculty> faculty = facultyFinder.findAndSelect();
        if (faculty.isEmpty()) {
            return Collections.emptyList();
        } else {
            return serviceStudent.findByFaculty(faculty.get());
        }
    }

    public List<Student> findByDepartment() {
        Optional<Department> department = departmentFinder.findAndSelect();
        if (department.isEmpty()) {
            return Collections.emptyList();
        } else {
            return serviceStudent.findByDepartment(department.get());
        }
    }

    public Optional<Student> findByEmail() {
        String email = inputReader.readString("Enter email");
        return serviceStudent.findByEmail(email);
    }

    public Optional<Student> findByPhone() {
        String phone = inputReader.readString("Enter phone number");
        return serviceStudent.findByPhone(phone);
    }

    public List<Student> findByIds() {
        IdsFind find = inputReader.readChoose(
                Arrays.stream(IdsFind.values()).toList(), "Choose criteria"
        );
        switch (find) {
            case UNIQUE_CODE -> {
                return findByUniqueCode();
            }
            case RECORD_BOOK -> {
                return findByRecordBook();
            }
            case null, default -> {
                return Collections.emptyList();
            }
        }
    }

    public Optional<Student> findByContact() {
        ContactFind find = inputReader.readChoose(
                Arrays.stream(ContactFind.values()).toList(), "Choose criteria");
        switch (find) {
            case PHONE -> {
                return findByPhone();
            }
            case EMAIL -> {
                return findByEmail();
            }
            case null, default -> {
                return Optional.empty();
            }
        }
    }

    public List<Student> findByOrgStructure() {
        OrgStructure find = inputReader.readChoose(
                Arrays.stream(OrgStructure.values()).toList(), "Choose criteria"
        );
        switch (find) {
            case UNIVERSITY -> {
                return findByUniversity();
            }
            case FACULTY -> {
                return findByFaculty();
            }
            case DEPARTMENT -> {
                return findByDepartment();
            }
            case STUDENT_GROUP -> {
                return findByStudentGroup();
            }
            case null, default -> {
                return Collections.emptyList();
            }
        }
    }

    public Optional<Student> findAndSelect() {
        Optional<Student> studentOptional = Optional.empty();
        while (true) {
            System.out.println("Select Student");
            StudentFind find = inputReader.readChoose(
                    Arrays.stream(StudentFind.values()).toList(), "Choose criteria"
            );
            switch (find) {
                case NAME -> {
                    studentOptional = chooseStudent(findByName());
                }
                case CANCEL -> {
                    return Optional.empty();
                }
                case SHOW_ALL -> {
                    studentOptional = chooseStudent(serviceStudent.findAll());
                }
                case COURSE -> {
                    studentOptional = chooseStudent(findByCourse());
                }
                case SURNAME -> {
                    studentOptional = chooseStudent(findBySurname());
                }
                case PATRONYMIC -> {
                    studentOptional = chooseStudent(findByPatronymic());
                }
                case STUDENT_STATUS -> {
                    studentOptional = chooseStudent(findByStudentStatus());
                }
                case FORM_OF_EDUCATION -> {
                    studentOptional = chooseStudent(findByFormOfEducation());
                }
                case IDS -> {
                    studentOptional = chooseStudent(findByIds());
                }
                case Contact -> {
                    studentOptional = findByContact();
                }
                case ORG_STRUCTURE -> {
                    studentOptional = chooseStudent(findByOrgStructure());
                }
                case ADVANCED -> {
                    studentOptional = chooseStudent(advancedSearch());
                }

                case null, default -> {
                    studentOptional = Optional.empty();
                }
            }
            if (studentOptional.isPresent()) {
                return studentOptional;
            } else {
                System.out.println("Student not found. Try again");
            }
        }
    }

    private List<Student> filterByFormOfEducation(List<Student> students) {
        FormOfEducation form = inputReader.readChoose(
                Arrays.stream(FormOfEducation.values()).toList(), "Choose form of education"
        );
        return students.stream().filter(
                s -> s.getFormOfEducation().equals(form)
        ).toList();
    }

    private List<Student> filterByStudentStatus(List<Student> students) {
        StudentStatus studentStatus = inputReader.readChoose(
                Arrays.stream(StudentStatus.values()).toList(), "Choose student status"
        );
        return students.stream().filter(
                s -> s.getStudentStatus().equals(studentStatus)
        ).toList();
    }

    private List<Student> filterBySurname(List<Student> students) {
        String surname = inputReader.readString("Enter surname");
        return students.stream().filter(
                s -> s.getFullName().surname().equals(surname)
        ).toList();
    }

    private List<Student> filterByPatronymic(List<Student> students) {
        String patronymic = inputReader.readString("Enter patronymic");
        return students.stream().filter(
                s -> s.getFullName().patronymic().equals(patronymic)
        ).toList();
    }

    private List<Student> filterByName(List<Student> students) {
        String name = inputReader.readString("Enter name");
        return students.stream().filter(
                s -> s.getFullName().name().equals(name)
        ).toList();
    }

    private List<Student> filterByUniversity(List<Student> students) {
        Optional<University> optionalUniversity = universityFinder.findAndSelect();
        if (optionalUniversity.isEmpty()) {
            return Collections.emptyList();
        } else {
            return students.stream().filter(
                    s -> s.getGroup().getDepartment().getFaculty()
                            .getUniversity().equals(optionalUniversity.get())
            ).toList();
        }
    }

    public List<Student> filterByFaculty(List<Student> students) {
        Optional<Faculty> optionalFaculty = facultyFinder.findAndSelect();
        if (optionalFaculty.isEmpty()) {
            return Collections.emptyList();
        } else {
            return students.stream().filter(
                    s -> s.getGroup().getDepartment().
                            getFaculty().equals(optionalFaculty.get())
            ).toList();
        }
    }

    public List<Student> filterByDepartment(List<Student> students) {
        Optional<Department> optionalDepartment = departmentFinder.findAndSelect();
        if (optionalDepartment.isEmpty()) {
            return Collections.emptyList();
        } else {
            return students.stream().filter(
                    s -> s.getGroup().getDepartment().equals(optionalDepartment.get())
            ).toList();
        }
    }

    public List<Student> filterByStudentGroup(List<Student> students) {
        Optional<StudentGroup> optionalStudentGroup = studentGroupFinder.findAndSelect();
        if (optionalStudentGroup.isEmpty()) {
            return Collections.emptyList();
        } else {
            return students.stream().filter(
                    s -> s.getGroup().equals(optionalStudentGroup.get())
            ).toList();
        }
    }
    private List<Student> filterByCourse(List<Student> students) {
        int minCourse = inputReader.readInt("Enter minimum course");
        int maxCourse = inputReader.readInt("Enter maximum course");
        if (maxCourse < minCourse) {
            return Collections.emptyList();
        }
        else {
            return students.stream().filter(
                    s -> (s.getCourse() >= minCourse && s.getCourse() <= maxCourse)
            ).toList();
        }
    }
    private List<Student>filterByUniqueCode(List<Student> students) {
        String uniqueCode = inputReader.readString("Enter unique code");
        return  students.stream().filter(
                s -> s.getUniqueCode().equals(uniqueCode)
        ).toList();
    }
    private List<Student> filterByRecordBook(List<Student> students) {
        String recordBook = inputReader.readString("Enter record book");
        return students.stream().filter(
                s -> s.getRecordBookNumber().equals(recordBook)
        ).toList();
    }
    public List<Student> advancedSearch() {
        Set<StudentForAdvancedFind> finds = new LinkedHashSet<>();
        while (true) {
            int i = 1;
            for (StudentForAdvancedFind find : StudentForAdvancedFind.values()) {
                System.out.println(i++ + " : " + find);
            }
            System.out.println(i++ + ": Reset setting");
            System.out.println(i++ + ": Start searching");
            System.out.println(i + ": Cancel");
            System.out.println("Active filters: " + finds);
            int choose = inputReader.readIntInRange("Choose param or start search",
                    1, i);
            if (choose <= i - 3) {
                finds.add(StudentForAdvancedFind.values()[choose - 1]);
            } else if (choose == i - 2) {
                finds = new LinkedHashSet<>();
            } else if (choose == i - 1) {
                return advancedFilter(finds.stream().toList());
            } else {
                return new ArrayList<>();
            }

        }
    }

    private List<Student> advancedFilter(List<StudentForAdvancedFind> finds) {
        List<Student> students = serviceStudent.findAll();
        for (StudentForAdvancedFind find : finds) {
            switch (find) {
                case ORG_STRUCTURE ->{
                    students = filterByOrgStructure(students);
                }
                case IDS ->{
                    students = filterByIds(students);
                }
                case STUDENT_STATUS -> {
                    students = filterByStudentStatus(students);
                }
                case PATRONYMIC ->  {
                    students = filterByPatronymic(students);
                }
                case SURNAME ->   {
                    students = filterBySurname(students);
                }
                case COURSE ->   {
                    students = filterByCourse(students);
                }
                case NAME ->   {
                    students = filterByName(students);
                }
                case FORM_OF_EDUCATION -> {
                    students = filterByFormOfEducation(students);
                }
                case null, default -> {
                    students = Collections.emptyList();
                }
            }
        }
        return students;
    }

    private List<Student> filterByIds(List<Student> students) {
        IdsFind find = inputReader.readChoose(
                Arrays.stream(IdsFind.values()).toList(), "Choose criteria"
        );
        switch (find) {
            case RECORD_BOOK ->  {
                return filterByRecordBook(students);
            }
            case UNIQUE_CODE ->   {
                return filterByUniqueCode(students);
            }
            case CANCEL -> {
                return students;
            }
            case null, default ->  {
                return Collections.emptyList();
            }
        }
    }

    private List<Student> filterByOrgStructure(List<Student> students) {
        OrgStructure orgStructure = inputReader.readChoose(
                Arrays.stream(OrgStructure.values()).toList(), "choose criteria"
        );
        switch (orgStructure) {
            case UNIVERSITY -> {
                return filterByUniversity(students);
            }
            case STUDENT_GROUP ->  {
                return filterByStudentGroup(students);
            }
            case FACULTY ->  {
                return filterByFaculty(students);
            }
            case DEPARTMENT ->  {
                return filterByDepartment(students);
            }
            case CANCEL -> {
                return students;
            }
            case null, default ->  {
                return Collections.emptyList();
            }
        }
    }


}
/*
    FormOfEducation formOfEducation;
    private String recordBookNumber;
    private StudentStatus studentStatus;
    private int course;
    private StudentGroup group;
    private String uniqueCode;
    private FullName fullName;
    private Contact contact;
 */