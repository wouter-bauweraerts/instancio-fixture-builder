# Implementation example

## POJO classes

### Person

```java
public class Person {
    private final String name, firstName;
    private final LocalDate birthDate;
    private final Integer reliabilityScore;
    private final ContactDetails contactDetails;

    // Constructors and getters omitted...
}
```

### ContactDetails

```java
public class ContactDetails {
    private final String phoneNumber;
    private final String mobileNumber;
    private final String email;

    // Constructors and getters omitted...
}
```

## Fixture Classes
### ContactDetailsFixtures

```java
public class ContactDetailsFixtures {
    private static final Model<ContactDetails> CONTACT_DETAILS_MODEL = Instancio.of(ContactDetails.class).toModel();

    public static ContactDetailsFixtureBuilder fixtureBuilder() {
        return new ContactDetailsFixtureBuilder();
    }

    public static ContactDetails contactDetails() {
        return Instancio.create(CONTACT_DETAILS_MODEL);
    }

    public static class ContactDetailsFixtureBuilder extends AbstractFixtureBuilder<ContactDetails, ContactDetailsFixtureBuilder> {
        @Override
        public ContactDetails build() {
            return buildInternal(CONTACT_DETAILS_MODEL);
        }

        @Override
        public ContactDetailsFixtureBuilder self() {
            return this;
        }

        public ContactDetailsFixtureBuilder withEmail(String email) {
            return setField(ContactDetails::getEmail, email);
        }


        public ContactDetailsFixtureBuilder withPhoneNumber(String phoneNumber) {
            return setField(ContactDetails::getPhoneNumber, phoneNumber);
        }

        public ContactDetailsFixtureBuilder withMobileNumber(String mobileNumber) {
            return setField(ContactDetails::getMobileNumber, mobileNumber);
        }


        public ContactDetailsFixtureBuilder ignoreEmail() {
            return ignoreField(ContactDetails::getEmail);
        }

        public ContactDetailsFixtureBuilder ignorePhoneNumber() {
            return ignoreField(ContactDetails::getPhoneNumber);
        }

        public ContactDetailsFixtureBuilder ignoreMobileNumber() {
            return ignoreField(ContactDetails::getMobileNumber);
        }

        public ContactDetailsFixtureBuilder ignoreAll() {
            return ignore(allStrings());
        }
    }

}

```

### PersonFixtures
```java
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
    }
}

```

## Example Usage
This is how a complete person could be created using only the Fixture Builder:

```java
Person person = PersonFixtures.fixtureBuilder()
                        .withName(name)
                        .withFirstName(firstName)
                        .withBirthDate(dateOfBirth)
                        .withReliabilityScore(reliabilityScore)
                        .withContactDetails(ContactDetailsFixtures.contactDetails())
                        .build();
```