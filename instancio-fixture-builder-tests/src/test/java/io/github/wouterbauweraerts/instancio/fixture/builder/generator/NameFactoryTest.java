package io.github.wouterbauweraerts.instancio.fixture.builder.generator;

import static io.github.wouterbauweraerts.instancio.fixture.builder.generator.NameFactory.FIXTURE_BUILDER_SUFFIX;
import static org.assertj.core.api.Assertions.assertThat;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

class NameFactoryTest {
    NameFactory nameFactory = new NameFactory();

    @Test
    void fieldMethodWithPrefix_returnsExpected() {
        String prefix = Instancio.create(String.class).toLowerCase();
        String fieldName = Instancio.create(String.class).toLowerCase();
        String capitalizedFieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

        assertThat(nameFactory.fieldMethodWithPrefix(prefix, fieldName)).isEqualTo(prefix + capitalizedFieldName);
    }

    @Test
    void fixtureBuilderName_returnsExpected() {
        String fixtureBuilderName = Instancio.create(String.class);
        assertThat(nameFactory.fixtureBuilderName(fixtureBuilderName)).isEqualTo(fixtureBuilderName + FIXTURE_BUILDER_SUFFIX);
    }
}