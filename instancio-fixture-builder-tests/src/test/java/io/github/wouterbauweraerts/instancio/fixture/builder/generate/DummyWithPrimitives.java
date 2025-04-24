package io.github.wouterbauweraerts.instancio.fixture.builder.generate;

import java.time.LocalDate;

import io.github.wouterbauweraerts.instancio.fixture.builder.dummy.ContactDetails;

public class DummyWithPrimitives {
    private final String name, firstName;
    private final LocalDate birthDate;
    private final int reliabilityScore;
    private final boolean boolField;
    private final Boolean boolWrapper;
    private final ContactDetails contactDetails;

    public DummyWithPrimitives(String name, String firstName, LocalDate birthDate, Integer reliabilityScore, boolean boolField, Boolean boolWrapper, ContactDetails contactDetails) {
        this.name = name;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.reliabilityScore = reliabilityScore;
        this.boolField = boolField;
        this.boolWrapper = boolWrapper;
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

    public boolean isBoolField() {
        return boolField;
    }

    public Boolean getBoolWrapper() {
        return boolWrapper;
    }

    public ContactDetails getContactDetails() {
        return contactDetails;
    }
}
