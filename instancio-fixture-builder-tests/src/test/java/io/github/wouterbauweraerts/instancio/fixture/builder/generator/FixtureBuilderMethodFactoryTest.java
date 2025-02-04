package io.github.wouterbauweraerts.instancio.fixture.builder.generator;

import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.palantir.javapoet.MethodSpec;

class FixtureBuilderMethodFactoryTest {
    FixtureBuilderMethodFactory factory = new FixtureBuilderMethodFactory();

    @Test
    void generatesExpectedMethodSpec() {
        MethodSpec methodSpec = factory.generateFixtureBuilder("DummyFixtureBuilder");

        assertThat(methodSpec)
                .returns(true, ms -> ms.annotations().isEmpty())
                .returns("DummyFixtureBuilder", ms -> ms.returnType().toString())
                .returns(true, ms -> ms.modifiers().contains(PUBLIC))
                .returns(true, ms -> ms.modifiers().contains(STATIC))
                .returns("return new DummyFixtureBuilder();", ms -> ms.code().toString());
    }
}