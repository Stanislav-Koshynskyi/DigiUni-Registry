package entity;

public record Contact(String phone, String email) {
    public Contact {
        if (email == null || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,255}$")){
            throw new IllegalArgumentException("Invalid email address");
        }
        if (phone == null || !phone.matches("^\\+?\\d{10,13}$")){
            throw new IllegalArgumentException("Invalid phone number");
        }

    }
}
