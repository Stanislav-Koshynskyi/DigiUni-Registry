package entity;

public record Address (String city,String street, String buildNumber) {
    public Address {
        if (city == null || city.isEmpty()) {
            throw new IllegalArgumentException("city can not be empty");
        }
        if (street == null || street.isEmpty()) {
            throw new IllegalArgumentException("street can not be empty");
        }
        if (buildNumber == null || buildNumber.isEmpty()) {
            throw new IllegalArgumentException("buildNumber can not be empty");
        }

    }
}
