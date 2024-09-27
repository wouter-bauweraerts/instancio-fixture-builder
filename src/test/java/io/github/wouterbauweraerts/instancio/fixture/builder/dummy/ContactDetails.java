package io.github.wouterbauweraerts.instancio.fixture.builder.dummy;

public class ContactDetails {
    private final String phoneNumber;
    private final String mobileNumber;
    private final String email;

    public ContactDetails(String phoneNumber, String mobileNumber, String email) {
        this.phoneNumber = phoneNumber;
        this.mobileNumber = mobileNumber;
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getEmail() {
        return email;
    }
}
