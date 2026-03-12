package util;
import entity.*;
import repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;

public class BaseForTest {
    public static void seed( UniversityRepository universityRepository, FacultyRepository facultyRepository, DepartmentRepository departmentRepository, TeacherRepository teacherRepository, StudentGroupRepository groupRepository, StudentRepository studentRepository){
        Address address = new Address("Kyiv", "Hryhoriya Skovorody", "2");
        University university = new University("Kyiv-Mohyla Academy","NaUKMA",address);
        universityRepository.save(university);

        Contact facultyContact = new Contact("+380441234567","fi@naukma.edu.ua");

// ---------- TEACHERS ----------

        Teacher t1 = new Teacher("T-HP-1",
                new FullName("Severus","Snape","Prince"),
                LocalDate.of(1970,1,9),
                new Contact("+380971111111","snape@naukma.edu.ua"),
                AcademicDegree.DOCTOR_OF_SCIENCES,
                AcademicRank.PROFESSOR,
                LocalDate.of(2005,9,1),
                BigDecimal.valueOf(90000));

        Teacher t2 = new Teacher("T-HP-2",
                new FullName("Minerva","McGonagall",""),
                LocalDate.of(1965,10,4),
                new Contact("+380972222222","mcgonagall@naukma.edu.ua"),
                AcademicDegree.DOCTOR_OF_SCIENCES,
                AcademicRank.PROFESSOR,
                LocalDate.of(2003,3,12),
                BigDecimal.valueOf(95000));

        Teacher t3 = new Teacher("T-BB-1",
                new FullName("Barbara","Roberts","Millicent"),
                LocalDate.of(1985,2,9),
                new Contact("+380973333333","barbie@naukma.edu.ua"),
                AcademicDegree.CANDIDATE_OF_SCIENCES,
                AcademicRank.ASSOCIATE_PROFESSOR,
                LocalDate.of(2015,9,1),
                BigDecimal.valueOf(60000));

        Teacher t4 = new Teacher("T-SPN-1",
                new FullName("Dean","Winchester",""),
                LocalDate.of(1980,1,24),
                new Contact("+380974444444","dean@naukma.edu.ua"),
                AcademicDegree.NONE,
                AcademicRank.SENIOR_RESEARCHER,
                LocalDate.of(2018,6,1),
                BigDecimal.valueOf(50000));

        Teacher t5 = new Teacher("T-SPN-2",
                new FullName("Sam","Winchester",""),
                LocalDate.of(1983,5,2),
                new Contact("+380975555555","sam@naukma.edu.ua"),
                AcademicDegree.DOCTOR_OF_PHILOSOPHY,
                AcademicRank.ASSOCIATE_PROFESSOR,
                LocalDate.of(2016,2,10),
                BigDecimal.valueOf(65000));

        Teacher t6 = new Teacher("T-VD-1",
                new FullName("Elena","Gilbert",""),
                LocalDate.of(1990,6,22),
                new Contact("+380976666666","elena@naukma.edu.ua"),
                AcademicDegree.CANDIDATE_OF_SCIENCES,
                AcademicRank.ASSOCIATE_PROFESSOR,
                LocalDate.of(2019,9,1),
                BigDecimal.valueOf(55000));

        Teacher t7 = new Teacher("T-SC-1",
                new FullName("Sidney","Prescott",""),
                LocalDate.of(1987,3,15),
                new Contact("+380977777777","sidney@naukma.edu.ua"),
                AcademicDegree.NONE,
                AcademicRank.SENIOR_RESEARCHER,
                LocalDate.of(2020,1,1),
                BigDecimal.valueOf(48000));



// ---------- FACULTY ----------

        Faculty faculty = new Faculty(
                "NAUKMA-FI",
                "Faculty of Informatics",
                "FI",
                t2,
                facultyContact,
                university
        );

        facultyRepository.save(faculty);


// ---------- DEPARTMENT ----------

        Department department = new Department(
                "NAUKMA-CS",
                "Computer Science",
                "CS",
                faculty,
                t1,
                "315"
        );

        departmentRepository.save(department);


// ---------- GROUP ----------

        StudentGroup group = new StudentGroup("CS-101",department);
        group.setHeadOfGroup(t3);
        groupRepository.save(group);


// ---------- STUDENTS ----------

        Student s1 = new Student("RB-001",
                "S-HP-1",
                new FullName("Harry","Potter",""),
                LocalDate.of(2000,7,31),
                new Contact("+380981111111","harry@student.naukma.edu.ua"),
                FormOfEducation.BUDGET,
                StudentStatus.STUDIES,
                Year.of(2022),
                3,
                group);

        Student s2 = new Student("RB-002",
                "S-HP-2",
                new FullName("Hermione","Granger",""),
                LocalDate.of(2000,9,19),
                new Contact("+380982222222","hermione@student.naukma.edu.ua"),
                FormOfEducation.BUDGET,
                StudentStatus.STUDIES,
                Year.of(2022),
                3,
                group);

        Student s3 = new Student("RB-003",
                "S-HP-3",
                new FullName("Ron","Weasley",""),
                LocalDate.of(2000,3,1),
                new Contact("+380983333333","ron@student.naukma.edu.ua"),
                FormOfEducation.CONTRACT,
                StudentStatus.STUDIES,
                Year.of(2022),
                3,
                group);

        Student s4 = new Student("RB-004",
                "S-SPN-1",
                new FullName("Castiel","Angel",""),
                LocalDate.of(1999,11,5),
                new Contact("+380984444444","castiel@student.naukma.edu.ua"),
                FormOfEducation.BUDGET,
                StudentStatus.STUDIES,
                Year.of(2021),
                4,
                group);

        Student s5 = new Student("RB-005",
                "S-VD-1",
                new FullName("Damon","Salvatore",""),
                LocalDate.of(1998,6,18),
                new Contact("+380985555555","damon@student.naukma.edu.ua"),
                FormOfEducation.CONTRACT,
                StudentStatus.STUDIES,
                Year.of(2020),
                5,
                group);

        Student s6 = new Student("RB-006",
                "S-VD-2",
                new FullName("Stefan","Salvatore",""),
                LocalDate.of(1998,11,1),
                new Contact("+380986666666","stefan@student.naukma.edu.ua"),
                FormOfEducation.BUDGET,
                StudentStatus.STUDIES,
                Year.of(2020),
                5,
                group);

        Student s7 = new Student("RB-007",
                "S-BB-1",
                new FullName("Ken","Carson",""),
                LocalDate.of(2001,2,14),
                new Contact("+380987777777","ken@student.naukma.edu.ua"),
                FormOfEducation.CONTRACT,
                StudentStatus.STUDIES,
                Year.of(2023),
                2,
                group);

        Student s8 = new Student("RB-008",
                "S-FNAF-1",
                new FullName("Michael","Afton",""),
                LocalDate.of(1999,4,20),
                new Contact("+380988888888","michael@student.naukma.edu.ua"),
                FormOfEducation.BUDGET,
                StudentStatus.STUDIES,
                Year.of(2021),
                4,
                group);

        Student s9 = new Student("RB-009",
                "S-SC-1",
                new FullName("Billy","Loomis",""),
                LocalDate.of(1999,8,10),
                new Contact("+380989999999","billy@student.naukma.edu.ua"),
                FormOfEducation.CONTRACT,
                StudentStatus.STUDIES,
                Year.of(2021),
                4,
                group);

        Student s10 = new Student("RB-010",
                "S-SC-2",
                new FullName("Stu","Macher",""),
                LocalDate.of(2000,1,1),
                new Contact("+380980000000","stu@student.naukma.edu.ua"),
                FormOfEducation.BUDGET,
                StudentStatus.STUDIES,
                Year.of(2022),
                3,
                group);

        group.addStudent(s1);
        group.addStudent(s2);
        group.addStudent(s3);
        group.addStudent(s4);
        group.addStudent(s5);
        group.addStudent(s6);
        group.addStudent(s7);
        group.addStudent(s8);
        group.addStudent(s9);
        group.addStudent(s10);

        group.setGroupLeader(s2);

        // ---------- SAVE DATA ----------

        teacherRepository.save(t1);
        teacherRepository.save(t2);
        teacherRepository.save(t3);
        teacherRepository.save(t4);
        teacherRepository.save(t5);
        teacherRepository.save(t6);
        teacherRepository.save(t7);

        studentRepository.save(s1);
        studentRepository.save(s2);
        studentRepository.save(s3);
        studentRepository.save(s4);
        studentRepository.save(s5);
        studentRepository.save(s6);
        studentRepository.save(s7);
        studentRepository.save(s8);
        studentRepository.save(s9);
        studentRepository.save(s10);
    }
}
