package io.github.wouterbauweraerts.instancio.fixture.builder.dummy.inheritance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.util.Random;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.instancio.fixture.builder.dummy.ContactDetailsFixtures;
import io.github.wouterbauweraerts.instancio.fixture.builder.generate.Dummy;
import io.github.wouterbauweraerts.instancio.fixture.builder.generate.DummyFixtureBuilder;

class MultiLevelInheritanceChildFixtureBuilderTest {
    @Test
    void canInstantiateInstanceWithoutModifications() {
        assertThat(new MultiLevelInheritanceChildFixtureBuilder().build()).isNotNull()
                .hasNoNullFieldsOrProperties();
    }

    @Test
    void canInstantiateInstanceWithModifications() {
        String randomString = Instancio.create(String.class);
        LocalDate date = Instancio.create(LocalDate.class);

        MultiLevelInheritanceChild instance = new MultiLevelInheritanceChildFixtureBuilder()
                .ignoreParentField()
                .withBaseClassField(randomString)
                .withChildField(date)
                .build();

        assertThat(instance).returns(randomString, MultiLevelInheritanceChild::getBaseClassField)
                .returns(date, MultiLevelInheritanceChild::getChildField)
                .returns(null, MultiLevelInheritanceChild::getParentField);
    }

    @Test
    void canMakeShallowCopyOfObjectInstance() {
        MultiLevelInheritanceChild original = new MultiLevelInheritanceChildFixtureBuilder().build();
        MultiLevelInheritanceChild copy = MultiLevelInheritanceChildFixtureBuilder.toFixtureBuilder(original).build();

        assertAll(() -> {
            assertThat(copy).isEqualTo(original);
            assertThat(copy).isNotSameAs(original);

            assertThat(copy.getChildField()).isSameAs(original.getChildField());
            assertThat(copy.getParentField()).isSameAs(original.getParentField());
            assertThat(copy.getBaseClassField()).isSameAs(original.getBaseClassField());
        });
    }

    @Test
    void canModifyShallowCopyWithoutChangingTheOriginal_noInheritance() {
        String doe = "Doe";
        String john = "John";
        String jane = "Jane";
        LocalDate johnDate = LocalDate.now().minusYears(25);
        LocalDate janeDate = LocalDate.now().minusYears(22);
        int johnReliability = 15;
        int janeReliability = 16;
        boolean isValid = new Random().nextBoolean();


        Dummy original = new Dummy(doe, john, johnDate, johnReliability, ContactDetailsFixtures.contactDetails(), isValid);

        Dummy copy = DummyFixtureBuilder.toFixtureBuilder(original)
                .withFirstName(jane)
                .withBirthDate(janeDate)
                .withReliabilityScore(janeReliability)
                .ignoreContactDetails()
                .build();

        assertThat(copy).isNotSameAs(original)
                .isNotEqualTo(original);

        assertThat(original.getName()).isEqualTo(doe);
        assertThat(original.getFirstName()).isEqualTo(john);
        assertThat(original.getBirthDate()).isEqualTo(johnDate);
        assertThat(original.getReliabilityScore()).isEqualTo(johnReliability);
        assertThat(original.getContactDetails()).isNotNull();

        assertThat(copy.getName()).isEqualTo(doe);
        assertThat(copy.getFirstName()).isEqualTo(jane);
        assertThat(copy.getBirthDate()).isEqualTo(janeDate);
        assertThat(copy.getReliabilityScore()).isEqualTo(janeReliability);
        assertThat(copy.getContactDetails()).isNull();
        assertThat(copy.isValid()).isEqualTo(isValid);
    }

    @Test
    void canModifyShallowCopyWithoutChangingTheOriginal() {
        final String originalString = "The Original Value";
        final String newString = "The New Value";
        final LocalDate originalDate = LocalDate.now().minusDays(15);
        final LocalDate newDate = LocalDate.now().plusYears(2);
        final Integer newInt = 42;

        MultiLevelInheritanceChild original = new MultiLevelInheritanceChild(originalString, null, originalDate);

        MultiLevelInheritanceChild copy = MultiLevelInheritanceChildFixtureBuilder.toFixtureBuilder(original)
                .withChildField(newDate)
                .withParentField(newInt)
                .withBaseClassField(newString)
                .build();

        assertThat(copy).isNotSameAs(original)
                .isNotEqualTo(original);

        assertThat(original.getChildField()).isEqualTo(originalDate);
        assertThat(original.getBaseClassField()).isEqualTo(originalString);
        assertThat(original.getParentField()).isNull();

        assertThat(copy.getBaseClassField()).isEqualTo(newString);
        assertThat(copy.getParentField()).isEqualTo(newInt);
        assertThat(copy.getChildField()).isEqualTo(newDate);
    }
}