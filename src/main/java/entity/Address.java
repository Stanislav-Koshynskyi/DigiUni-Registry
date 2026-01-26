package entity;

public record Address (String city,String street, String buildNumber) {
    public Address {
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("city can not be empty");
        }
        if (street == null || street.isBlank()) {
            throw new IllegalArgumentException("street can not be empty");
        }
        if (buildNumber == null || buildNumber.isBlank()) {
            throw new IllegalArgumentException("buildNumber can not be empty");
        }

    }
}
