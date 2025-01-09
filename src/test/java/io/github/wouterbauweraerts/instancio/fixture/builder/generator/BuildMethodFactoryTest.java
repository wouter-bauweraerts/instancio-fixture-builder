package io.github.wouterbauweraerts.instancio.fixture.builder.generator;

import static javax.lang.model.element.Modifier.PUBLIC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.lang.model.element.Element;
import javax.lang.model.element.Name;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import com.palantir.javapoet.AnnotationSpec;

class BuildMethodFactoryTest {
    private static final String METHOD_BODY_PATTERN = "return buildInternal(%s);%n";

    @Test
    void generte_generatesExpectedMethod() {
        AnnotationSpec overrideAnnotationsSpec = AnnotationSpec.builder(Override.class).build();
        String returnType = Instancio.create(String.class);
        String simpleName = Instancio.create(String.class);

        Element element = mock(Element.class);
        Name name = mock(Name.class);

        when(element.getSimpleName()).thenReturn(name);
        when(name.toString()).thenReturn(simpleName);

        assertThat(BuildMethodFactory.generate(element, returnType))
                .returns(true, ms -> ms.modifiers().contains(PUBLIC))
                .returns(true, ms -> ms.annotations().contains(overrideAnnotationsSpec))
                .returns(returnType, ms -> ms.returnType().toString())
                .returns(METHOD_BODY_PATTERN.formatted(simpleName), ms -> ms.code().toString());
    }
}