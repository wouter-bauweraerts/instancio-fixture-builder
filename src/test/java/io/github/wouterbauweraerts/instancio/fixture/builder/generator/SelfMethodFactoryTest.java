package io.github.wouterbauweraerts.instancio.fixture.builder.generator;

import static javax.lang.model.element.Modifier.PUBLIC;
import static org.assertj.core.api.Assertions.assertThat;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import com.palantir.javapoet.AnnotationSpec;

class SelfMethodFactoryTest {
    @Test
    void generateReturnsExpectedMethodSpec() {
        AnnotationSpec overrideAnnotationSpec = AnnotationSpec.builder(Override.class).build();
        String returnType = Instancio.create(String.class);

        assertThat(SelfMethodFactory.generate(returnType))
                .returns(returnType, ms -> ms.returnType().toString())
                .returns("return this;\n", ms -> ms.code().toString())
                .returns(true, ms -> ms.annotations().contains(overrideAnnotationSpec))
                .returns(true, ms -> ms.modifiers().contains(PUBLIC));
    }
}