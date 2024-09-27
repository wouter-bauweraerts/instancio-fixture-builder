package io.github.wouterbauweraerts.instancio.fixture.builder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Random;

import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.instancio.fixture.builder.dummy.ContactDetails;
import io.github.wouterbauweraerts.instancio.fixture.builder.dummy.ContactDetailsFixtures;
import io.github.wouterbauweraerts.instancio.fixture.builder.dummy.Person;
import io.github.wouterbauweraerts.instancio.fixture.builder.dummy.PersonFixtures;

class ComplexPojoFixtureBuilderTest {
    @Test
    void randomObjectCanBeGeneratedWithAllFieldsPopulated() {
        assertThat(PersonFixtures.fixtureBuilder().build()).satisfies(
                p -> {
                    Arrays.stream(p.getClass().getDeclaredMethods())
                            .forEach(method -> {
                                try {
                                    assertThat(method.invoke(p)).isNotNull();
                                } catch (Throwable t) {
                                    fail("Could not invoke method: " + method, t);
                                }
                            });
                }
        );
    }

    @Test
    void allFieldsCanBeSetToSpecificValues() {
        Random random = new Random();

        String name = "Doe";
        String firstName = "John";
        LocalDate dateOfBirth = LocalDate.now().minusYears(random.nextInt(75));
        int reliabilityScore = random.nextInt(50);
        ContactDetails contactDetails = ContactDetailsFixtures.contactDetails();

        assertThat(
                PersonFixtures.fixtureBuilder()
                        .withName(name)
                        .withFirstName(firstName)
                        .withBirthDate(dateOfBirth)
                        .withReliabilityScore(reliabilityScore)
                        .withContactDetails(contactDetails)
                        .build()
        ).returns(name, Person::getName)
                .returns(firstName, Person::getFirstName)
                .returns(dateOfBirth, Person::getBirthDate)
                .returns(reliabilityScore, Person::getReliabilityScore)
                .returns(contactDetails, Person::getContactDetails);
    }

    @Test
    void nameCanBeIgnored() {
        assertThat(PersonFixtures.fixtureBuilder().ignoreName().build())
                .satisfies(p -> {
                    assertThat(p.getName()).isNull();
                    assertThat(p.getFirstName()).isNotNull();
                    assertThat(p.getBirthDate()).isNotNull().isBefore(LocalDate.now());
                    assertThat(p.getReliabilityScore()).isNotNull();
                    assertThat(p.getContactDetails()).isNotNull();
                });
    }

    @Test
    void firstNameCanBeIgnored() {
        assertThat(PersonFixtures.fixtureBuilder().ignoreFirstName().build())
                .satisfies(p -> {
                    assertThat(p.getName()).isNotNull();
                    assertThat(p.getFirstName()).isNull();
                    assertThat(p.getBirthDate()).isNotNull().isBefore(LocalDate.now());
                    assertThat(p.getReliabilityScore()).isNotNull();
                    assertThat(p.getContactDetails()).isNotNull();
                });
    }

    @Test
    void birthDateCanBeIgnored() {
        assertThat(PersonFixtures.fixtureBuilder().ignoreBirthDate().build())
                .satisfies(p -> {
                    assertThat(p.getName()).isNotNull();
                    assertThat(p.getFirstName()).isNotNull();
                    assertThat(p.getBirthDate()).isNull();
                    assertThat(p.getReliabilityScore()).isNotNull();
                    assertThat(p.getContactDetails()).isNotNull();
                });
    }

    @Test
    void reliabilityScoreCanBeIgnored() {
        assertThat(PersonFixtures.fixtureBuilder().ignoreReliabilityScore().build())
                .satisfies(p -> {
                    assertThat(p.getName()).isNotNull();
                    assertThat(p.getFirstName()).isNotNull();
                    assertThat(p.getBirthDate()).isNotNull().isBefore(LocalDate.now());
                    assertThat(p.getReliabilityScore()).isNull();
                    assertThat(p.getContactDetails()).isNotNull();
                });
    }

    @Test
    void contactDetailsCanBeIgnored() {
        assertThat(PersonFixtures.fixtureBuilder().ignoreContactDetails().build())
                .satisfies(p -> {
                    assertThat(p.getName()).isNotNull();
                    assertThat(p.getFirstName()).isNotNull();
                    assertThat(p.getBirthDate()).isNotNull().isBefore(LocalDate.now());
                    assertThat(p.getReliabilityScore()).isNotNull();
                    assertThat(p.getContactDetails()).isNull();
                });
    }
}