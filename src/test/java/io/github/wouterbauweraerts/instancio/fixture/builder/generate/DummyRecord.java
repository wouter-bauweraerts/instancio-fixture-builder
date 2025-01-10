package io.github.wouterbauweraerts.instancio.fixture.builder.generate;

import java.time.LocalDate;

import io.github.wouterbauweraerts.instancio.fixture.builder.dummy.ContactDetails;

public record DummyRecord(
        String name,
        String firstName,
        LocalDate birthDate,
        Integer reliabilityScore,
        ContactDetails contactDetails
) {
}
