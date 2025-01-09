package io.github.wouterbauweraerts.instancio.fixture.builder.generator;

import static org.assertj.core.api.Assertions.assertThat;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

class MethodNameFactoryTest {
    MethodNameFactory methodNameFactory = new MethodNameFactory();

    @Test
    void fieldMethodWithPrefix_returnsExpected() {
        String prefix = Instancio.create(String.class).toLowerCase();
        String fieldName = Instancio.create(String.class).toLowerCase();
        String capitalizedFieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

        assertThat(methodNameFactory.fieldMethodWithPrefix(prefix, fieldName)).isEqualTo(prefix + capitalizedFieldName);
    }
}