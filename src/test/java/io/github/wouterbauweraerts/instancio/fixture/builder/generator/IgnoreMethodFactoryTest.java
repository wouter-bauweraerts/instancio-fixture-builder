package io.github.wouterbauweraerts.instancio.fixture.builder.generator;

import static javax.lang.model.element.Modifier.PUBLIC;
import static org.assertj.core.api.Assertions.assertThat;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

class IgnoreMethodFactoryTest {
    private static final String METHOD_BODY_PATTERN = "return %s(null);%n";

    @Test
    void generte_generatesExpectedMethod() {

        String ignoreMethodName = Instancio.create(String.class);
        String withMethodName = Instancio.create(String.class);
        String builderClassname = Instancio.create(String.class);

        assertThat(IgnoreMethodFactory.generateIgnoreMethod(ignoreMethodName, withMethodName, builderClassname))
                .returns(true, ms -> ms.modifiers().contains(PUBLIC))
                .returns(builderClassname, ms -> ms.returnType().toString())
                .returns(METHOD_BODY_PATTERN.formatted(withMethodName), ms -> ms.code().toString());
    }
}