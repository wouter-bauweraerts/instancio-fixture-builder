package io.github.wouterbauweraerts.instancio.fixture.builder.generator;

import static javax.lang.model.element.Modifier.PUBLIC;
import static org.assertj.core.api.Assertions.assertThat;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

class IgnoreMethodFactoryTest {
    private static final String METHOD_BODY_PATTERN = "return %s(null);%n";

    IgnoreMethodFactory factory = new IgnoreMethodFactory();

    @Test
    void generate_generatesExpectedMethod() {

        String ignoreMethodName = Instancio.create(String.class);
        String withMethodName = Instancio.create(String.class);
        String builderClassname = Instancio.create(String.class);

        assertThat(factory.generateIgnoreMethod(ignoreMethodName, withMethodName, builderClassname, false))
                .returns(true, ms -> ms.modifiers().contains(PUBLIC))
                .returns(builderClassname, ms -> ms.returnType().toString())
                .returns(METHOD_BODY_PATTERN.formatted(withMethodName), ms -> ms.code().toString());
    }

    @Test
    void generate_whenIsPrimitive_returnsNull() {

        String ignoreMethodName = Instancio.create(String.class);
        String withMethodName = Instancio.create(String.class);
        String builderClassname = Instancio.create(String.class);

        assertThat(factory.generateIgnoreMethod(ignoreMethodName, withMethodName, builderClassname, false))
                .returns(true, ms -> ms.modifiers().contains(PUBLIC))
                .returns(builderClassname, ms -> ms.returnType().toString())
                .returns(METHOD_BODY_PATTERN.formatted(withMethodName), ms -> ms.code().toString());
    }
}