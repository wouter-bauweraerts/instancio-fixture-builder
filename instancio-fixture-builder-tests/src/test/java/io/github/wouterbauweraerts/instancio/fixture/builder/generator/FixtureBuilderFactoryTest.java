package io.github.wouterbauweraerts.instancio.fixture.builder.generator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;

import org.instancio.Instancio;
import org.instancio.Model;
import org.junit.jupiter.api.Test;

import com.palantir.javapoet.AnnotationSpec;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.TypeSpec;

class FixtureBuilderFactoryTest {
    private static final Model<MethodSpec> METHOD_SPEC_MODEL = Instancio.of(MethodSpec.class)
            .ignore(field(MethodSpec::defaultValue))
            .set(field(MethodSpec::modifiers), Set.of(Modifier.PUBLIC))
            .toModel();
    public static final String SUPERCLASS_PATTERN = "io.github.wouterbauweraerts.instancio.fixture.builder.AbstractFixtureBuilder<%s, %s>";

    FixtureBuilderFactory factory = new FixtureBuilderFactory();

    @Test
    void createBuilderSpec() {
        String builderClassName = Instancio.create(String.class);
        String simpleName = Instancio.create(String.class);
        Element element = mock(Element.class);
        Name elementName = mock(Name.class);

        AnnotationSpec generatedAnnotaion = Instancio.create(AnnotationSpec.class);
        MethodSpec self = Instancio.create(METHOD_SPEC_MODEL);
        MethodSpec build = Instancio.create(METHOD_SPEC_MODEL);
        MethodSpec toFixtureBuilder = Instancio.create(METHOD_SPEC_MODEL);
        List<MethodSpec> builderMethods = Instancio.ofList(METHOD_SPEC_MODEL).create();

        when(element.getSimpleName()).thenReturn(elementName);
        when(elementName.toString()).thenReturn(simpleName);

        TypeSpec typeSpec = factory.createBuilderSpec(builderClassName, element, generatedAnnotaion, self, build, toFixtureBuilder, builderMethods);

        assertThat(typeSpec).isNotNull()
                .isInstanceOf(TypeSpec.class)
                .returns(builderClassName, TypeSpec::name)
                .returns(SUPERCLASS_PATTERN.formatted(simpleName, builderClassName), e -> e.superclass().toString());

        assertThat(typeSpec.methodSpecs()).containsExactlyInAnyOrderElementsOf(
                Stream.concat(Stream.of(self, build, toFixtureBuilder), builderMethods.stream()).toList()
        );
    }
}