package io.github.wouterbauweraerts.instancio.fixture.builder.dummy;

import java.time.LocalDate;

public class Person {
    private final String name, firstName;
    private final LocalDate birthDate;
    private final Integer reliabilityScore;
    private final ContactDetails contactDetails;

    public Person(String name, String firstName, LocalDate birthDate, Integer reliabilityScore, ContactDetails contactDetails) {
        this.name = name;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.reliabilityScore = reliabilityScore;
        this.contactDetails = contactDetails;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Integer getReliabilityScore() {
        return reliabilityScore;
    }

    public ContactDetails getContactDetails() {
        return contactDetails;
    }
}
