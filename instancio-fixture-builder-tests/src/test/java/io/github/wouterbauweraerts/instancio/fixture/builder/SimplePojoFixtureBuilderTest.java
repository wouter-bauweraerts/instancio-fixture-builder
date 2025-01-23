package io.github.wouterbauweraerts.instancio.fixture.builder;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.instancio.fixture.builder.dummy.ContactDetails;
import io.github.wouterbauweraerts.instancio.fixture.builder.dummy.ContactDetailsFixtures;

class SimplePojoFixtureBuilderTest {
    @Test
    void allFieldsOfCertainTypeCanBeIgnoredAtOnce() {
        assertThat(ContactDetailsFixtures.fixtureBuilder().ignoreAll().build())
                .returns(null, ContactDetails::getEmail)
                .returns(null, ContactDetails::getPhoneNumber)
                .returns(null, ContactDetails::getMobileNumber);
    }

    @Test
    void emailFieldCanBeIgnored() {
        assertThat(ContactDetailsFixtures.fixtureBuilder().ignoreEmail().build())
                .returns(null, ContactDetails::getEmail)
                .satisfies(cd -> {
                    assertThat(cd.getPhoneNumber()).isNotNull().isInstanceOf(String.class).isNotBlank();
                    assertThat(cd.getMobileNumber()).isNotNull().isInstanceOf(String.class).isNotBlank();
                });
    }

    @Test
    void phoneFieldCanBeIgnored() {
        assertThat(ContactDetailsFixtures.fixtureBuilder().ignorePhoneNumber().build())
                .returns(null, ContactDetails::getPhoneNumber)
                .satisfies(cd -> {
                    assertThat(cd.getEmail()).isNotNull().isInstanceOf(String.class).isNotBlank();
                    assertThat(cd.getMobileNumber()).isNotNull().isInstanceOf(String.class).isNotBlank();
                });
    }

    @Test
    void mobileFieldCanBeIgnored() {
        assertThat(ContactDetailsFixtures.fixtureBuilder().ignoreMobileNumber().build())
                .returns(null, ContactDetails::getMobileNumber)
                .satisfies(cd -> {
                    assertThat(cd.getEmail()).isNotNull().isInstanceOf(String.class).isNotBlank();
                    assertThat(cd.getPhoneNumber()).isNotNull().isInstanceOf(String.class).isNotBlank();
                });
    }

    @Test
    void fieldsCanBeSetToSpecificValue() {
        String email = "test@example.com";
        String phoneNumber = "123-456-789";
        String mobileNumber = "987-654-321";

        assertThat(
                ContactDetailsFixtures.fixtureBuilder()
                        .withEmail(email)
                        .withPhoneNumber(phoneNumber)
                        .withMobileNumber(mobileNumber)
                        .build()
        ).returns(email, ContactDetails::getEmail)
        .returns(phoneNumber, ContactDetails::getPhoneNumber)
        .returns(mobileNumber, ContactDetails::getMobileNumber);
    }
}