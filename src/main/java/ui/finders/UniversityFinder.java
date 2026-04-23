package ui.finders;

import entity.University;
import entity.find_criteria.UniversityFind;
import service.ServiceUniversityInterface;
import ui.InputReader;

import java.util.List;
import java.util.Optional;

public class UniversityFinder implements  UniversityFinderInterface {
    private final ServiceUniversityInterface serviceUniversity;
    private final InputReader inputReader;
    public UniversityFinder(ServiceUniversityInterface serviceUniversity,InputReader inputReader) {
        this.serviceUniversity = serviceUniversity;
        this.inputReader = inputReader;
    }
    public Optional<University> findByFullName() {
        String name = inputReader.readString("Enter name: ");
        return serviceUniversity.findByFullName(name);
    }
    public List<University> findByCity() {
        String city = inputReader.readString("Enter city: ");
        return serviceUniversity.findByCity(city);
    }
    public Optional<University> findByShortName() {
        String name = inputReader.readString("Enter name: ");
        return serviceUniversity.findByShortName(name);
    }
    public Optional<University> chooseUniversity(List<University> universityList) {
        if  (universityList.isEmpty()) {
            inputReader.println("No university found");
            return Optional.empty();
        }

        return Optional.of(inputReader.readChoose(universityList, "Choose university: "));
    }
    public Optional<University> findAndSelect(){
        while (true) {
            inputReader.println("Select an university");
            List<UniversityFind>  universityFinds = List.of(UniversityFind.values());
            UniversityFind find = inputReader.readChoose(universityFinds, "Choose criteria: ");
            Optional<University> university;
            switch (find) {
                case ALL:
                    university = chooseUniversity(serviceUniversity.findAll());
                    if (university.isPresent()) return  university;
                    else inputReader.println("No university found");
                    break;
                case CANCEL:
                    return Optional.empty();
                case NAME:
                    university = findByFullName();
                    if (university.isPresent()) return  university;
                    else inputReader.println("No university found");
                    break;
                case SHORT_NAME:
                    university = findByShortName();
                    if (university.isPresent()) return  university;
                    else inputReader.println("No university found");
                    break;
                case CITY:
                    List<University> universities = findByCity();
                    if (universities.isEmpty()) {
                        inputReader.println("No university found");
                        break;
                    }
                    else return chooseUniversity(universities);
                default:
                    inputReader.println("Invalid selection");
                    return Optional.empty();
            }
        }


    }

}
