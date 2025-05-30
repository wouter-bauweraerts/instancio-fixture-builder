package io.github.wouterbauweraerts.instancio.fixture.builder.generate;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Random;

import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.instancio.fixture.builder.dummy.ContactDetails;
import io.github.wouterbauweraerts.instancio.fixture.builder.dummy.ContactDetailsFixtures;

class DummyWithPrimitivesFixtureBuilderTest {
    @Test
    void generatedFixtureBuilderCreatesExpectedInstance() {
        String name = "Doe";
        String firstName = "John";
        LocalDate birthDate = LocalDate.now();
        Integer reliabilityScore = new Random().nextInt();
        ContactDetails contactDetails = ContactDetailsFixtures.contactDetails();
        boolean isValid = new Random().nextBoolean();

        assertThat(
                new DummyFixtureBuilder()
                        .withName(name)
                        .withFirstName(firstName)
                        .withBirthDate(birthDate)
                        .withReliabilityScore(reliabilityScore)
                        .withContactDetails(contactDetails)
                        .withIsValid(isValid)
                        .build()
        ).usingRecursiveComparison()
                .isEqualTo(new Dummy(name, firstName, birthDate, reliabilityScore, contactDetails, isValid));
    }
}
