package io.github.wouterbauweraerts.instancio.fixture.builder.generate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import io.github.wouterbauweraerts.instancio.fixture.builder.dummy.ContactDetails;
import io.github.wouterbauweraerts.instancio.fixture.builder.dummy.ContactDetailsFixtures;

class DummyRecordFixtureBuilderTest {
    @Test
    void generatedFixtureBuilderCreatesExpectedInstance() {
        String name = "Doe";
        String firstName = "John";
        LocalDate birthDate = LocalDate.now();
        Integer reliabilityScore = new Random().nextInt();
        ContactDetails contactDetails = ContactDetailsFixtures.contactDetails();

        assertThat(
                new DummyRecordFixtureBuilder()
                        .withName(name)
                        .withFirstName(firstName)
                        .withBirthDate(birthDate)
                        .withReliabilityScore(reliabilityScore)
                        .withContactDetails(contactDetails)
                        .build()
        ).usingRecursiveComparison()
                .isEqualTo(new DummyRecord(name, firstName, birthDate, reliabilityScore, contactDetails));
    }

    @TestFactory
    Stream<DynamicTest> hasExpectedParameterlessMethods() {
        return Stream.of(
                "self",
                "build",
                "ignoreFirstName",
                "ignoreName",
                "ignoreBirthDate",
                "ignoreContactDetails",
                "ignoreReliabilityScore",
                "fixtureBuilder"
        ).map(name -> DynamicTest.dynamicTest(name, () -> {
            assertAll(() -> {
                AtomicReference<Method> methodReference = new AtomicReference<>();

                assertThatCode(() -> methodReference.set(getMethod(DummyRecordFixtureBuilder.class, name))).doesNotThrowAnyException();
                assertThat(methodReference.get()).isNotNull();
            });
        }));
    }

    @TestFactory
    Stream<DynamicTest> hasExpectedMethodsWithParameters() {
        return Map.of(
                "withName", new Class<?>[]{String.class},
                "withFirstName", new Class<?>[]{String.class},
                "withBirthDate", new Class<?>[]{LocalDate.class},
                "withReliabilityScore", new Class<?>[]{Integer.class},
                "withContactDetails", new Class<?>[]{ContactDetails.class},
                "toFixtureBuilder", new Class[]{DummyRecord.class}
        ).entrySet().stream().map(entry -> DynamicTest.dynamicTest(entry.getKey(), () -> {
            assertAll(() -> {
                AtomicReference<Method> methodReference = new AtomicReference<>();

                assertThatCode(() -> methodReference.set(getMethod(DummyRecordFixtureBuilder.class, entry.getKey(), entry.getValue()))).doesNotThrowAnyException();
                assertThat(methodReference.get()).isNotNull();
            });
        }));
    }

    private Method getMethod(Class<?> clazz, String name, Class<?>... parameterTypes) throws NoSuchMethodException {
        return clazz.getMethod(name, parameterTypes);
    }
}
