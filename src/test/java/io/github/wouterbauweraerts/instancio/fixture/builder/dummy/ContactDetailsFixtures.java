package io.github.wouterbauweraerts.instancio.fixture.builder.dummy;

import static org.instancio.Select.allStrings;

import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.Random;

import io.github.wouterbauweraerts.instancio.fixture.builder.AbstractFixtureBuilder;

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
