package ui;

import entity.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;

public interface InputReader {
    public String readString(String prompt);
    public String readPassword( String prompt);
    public String readHide(String prompt);
    public String readStringWithMaxLengthProbablyBlank(String prompt, int maxLength);

    public int readInt(String prompt);
    public int readIntInRange(String prompt, int min, int max) ;
    public Integer readIntInRangeProbablyNull(String prompt,Integer min, Integer max);
    public Long readLong(String prompt);

    public BigDecimal readBigDecimal(String prompt);
    public Contact readContact();
    public Contact readContactProbablyNull();
    public LocalDate readBirthDate();

    public LocalDate readEmploymentDate();

    public Year readYearOfAdmission();
    public FormOfEducation readFormOfEducation();
    public FullName readfullName();
    public AcademicDegree readAcademicDegree() ;
    public AcademicRank readAcademicRank() ;
    public <T> T readChoose(List<T> list, String promt);
    public <T> T readChooseProbablyNull(List<T> list, String promt, String promptForZero);
    public String readProbablyBlank(String prompt);
    public String readStringWithMaxLength(String prompt, int maxLength);
    void println(String message);
    default void println(Object obj) {println(obj != null ? obj.toString() : "null");}
}
