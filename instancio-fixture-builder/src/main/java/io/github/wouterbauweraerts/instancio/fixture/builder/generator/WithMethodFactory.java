package io.github.wouterbauweraerts.instancio.fixture.builder.generator;

import static javax.lang.model.element.Modifier.PUBLIC;

import org.instancio.Select;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.ParameterSpec;

import io.github.wouterbauweraerts.instancio.fixture.builder.generator.parameter.ParamType;

class WithMethodFactory {
    MethodSpec generateWithMethod(String withMethodName, String fieldName, String declaringClass, ParamType paramType, String builderClassName) {
        ClassName declaringClassName = ClassName.bestGuess(declaringClass);
        ClassName returnTypeClassName = ClassName.bestGuess(builderClassName);

        ParameterSpec parameter = ParameterSpec.builder(
                paramType.isPrimitive() ? paramType.typeName() : ClassName.bestGuess(paramType.fullyQualifiedName()),
                fieldName
        ).build();
        return MethodSpec.methodBuilder(withMethodName)
                .addModifiers(PUBLIC)
                .returns(returnTypeClassName)
                .addParameter(parameter)
                .addStatement("return set($T.field($T.class, \"%s\"), %s)".formatted(fieldName, fieldName), Select.class, declaringClassName)
                .build();
    }
}
