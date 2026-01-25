package entity;

import java.util.Objects;

public class University {
    private Long id;
    private String fullName;
    private String shortName;
    private Address address;

    public University() {
    }

    public University(String universityFullName, String universityShortName, Address address) {
        setFullName(universityFullName);
        setShortName(universityShortName);
        setAddress(address);
    }


    public University(Long universityId, String universityFullName, String universityShortName, Address address) {
        this(universityFullName, universityShortName, address);
        this.id = universityId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("universityFullName cannot be empty");
        }
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {

        if (shortName == null || shortName.isBlank()) {
            throw new IllegalArgumentException("universityShortName cannot be empty");
        }
        if (shortName.length() > 15) {
            throw new IllegalArgumentException("universityShortName must be short");
        }
        this.shortName = shortName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        if (address == null) {
            throw new IllegalArgumentException("address cannot be null");
        }
        this.address = address;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;
        University other = (University) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "university[fullName: " + fullName + ", shortName: " + shortName + ", address: " + address + "]";
    }
}

