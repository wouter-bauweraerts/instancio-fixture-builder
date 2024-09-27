package io.github.wouterbauweraerts.instancio.fixture.builder.dummy;

import static org.instancio.Select.allStrings;
import static org.instancio.Select.field;

import java.time.LocalDate;

import org.instancio.Instancio;
import org.instancio.Model;

import io.github.wouterbauweraerts.instancio.fixture.builder.AbstractFixtureBuilder;

public class PersonFixtures {
    private static final Model<Person> PERSON_MODEL = Instancio.of(Person.class)
            .generate(field(Person::getBirthDate), gen -> gen.temporal().localDate().past())
            .supply(field(Person::getContactDetails), ContactDetailsFixtures::contactDetails)
            .toModel();

    public static PersonFixtureBuilder fixtureBuilder() {
        return new PersonFixtureBuilder();
    }

    public static class PersonFixtureBuilder extends AbstractFixtureBuilder<Person, PersonFixtureBuilder> {

        @Override
        public Person build() {
            return buildInternal(PERSON_MODEL);
        }

        @Override
        public PersonFixtureBuilder self() {
            return this;
        }

        public PersonFixtureBuilder withName(String name) {
            return setField(Person::getName, name);
        }

        public PersonFixtureBuilder withFirstName(String firstName) {
            return setField(Person::getFirstName, firstName);
        }

        public PersonFixtureBuilder withBirthDate(LocalDate birthDate) {
            return setField(Person::getBirthDate, birthDate);
        }

        public PersonFixtureBuilder withReliabilityScore(Integer reliabilityScore) {
            return setField(Person::getReliabilityScore, reliabilityScore);
        }

        public PersonFixtureBuilder withContactDetails(ContactDetails contactDetails) {
            return setField(Person::getContactDetails, contactDetails);
        }

        public PersonFixtureBuilder ignoreName() {
            return ignoreField(Person::getName);
        }

        public PersonFixtureBuilder ignoreFirstName() {
            return ignoreField(Person::getFirstName);
        }

        public PersonFixtureBuilder ignoreBirthDate() {
            return ignoreField(Person::getBirthDate);
        }

        public PersonFixtureBuilder ignoreReliabilityScore() {
            return ignoreField(Person::getReliabilityScore);
        }

        public PersonFixtureBuilder ignoreContactDetails() {
            return ignoreField(Person::getContactDetails);
        }

        public PersonFixtureBuilder ignoreFullName() {
            return ignore(allStrings());
        }
    }
}
