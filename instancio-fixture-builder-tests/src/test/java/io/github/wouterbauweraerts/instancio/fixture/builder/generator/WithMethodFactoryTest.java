package io.github.wouterbauweraerts.instancio.fixture.builder.generator;

import static javax.lang.model.element.Modifier.PUBLIC;
import static org.assertj.core.api.Assertions.assertThat;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.TypeName;

import io.github.wouterbauweraerts.instancio.fixture.builder.generator.parameter.ParamType;

class WithMethodFactoryTest {
    private static final String METHOD_BODY_PATTERN = "return set(org.instancio.Select.field(\"%s\"), %s);%n";

    WithMethodFactory withMethodFactory = new WithMethodFactory();

    @Test
    void generate_nonPrimitiveType_generatesExpectedMethod() {
        String withMethodName = Instancio.create(String.class);
        String fieldName = Instancio.create(String.class);
        String qualifiedTypeName = Instancio.create(String.class);
        ParamType paramType = ParamType.of(qualifiedTypeName);
        String builderClassname = Instancio.create(String.class);

        MethodSpec actual = withMethodFactory.generateWithMethod(withMethodName, fieldName, paramType, builderClassname);
        assertThat(actual)
                .returns(true, ms -> ms.modifiers().contains(PUBLIC))
                .returns(builderClassname, ms -> ms.returnType().toString())
                .returns(
                        true,
                        ms -> ms.parameters().stream().anyMatch(
                                param -> param.type().toString().equals(qualifiedTypeName)
                                        && param.name().equals(fieldName))
                )
                .returns(METHOD_BODY_PATTERN.formatted(fieldName, fieldName), ms -> ms.code().toString());
    }

    @Test
    void generate_primitiveType_generatesExpectedMethod() {
        String withMethodName = Instancio.create(String.class);
        String fieldName = Instancio.create(String.class);
        ParamType paramType = ParamType.of(Instancio.create(TypeName.class));
        String builderClassname = Instancio.create(String.class);

        MethodSpec actual = withMethodFactory.generateWithMethod(withMethodName, fieldName, paramType, builderClassname);
        assertThat(actual)
                .returns(true, ms -> ms.modifiers().contains(PUBLIC))
                .returns(builderClassname, ms -> ms.returnType().toString())
                .returns(
                        true,
                        ms -> ms.parameters().stream().anyMatch(
                                param -> param.type().toString().equals(paramType.typeName().toString())
                                        && param.name().equals(fieldName))
                )
                .returns(METHOD_BODY_PATTERN.formatted(fieldName, fieldName), ms -> ms.code().toString());
    }

}