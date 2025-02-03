package io.github.wouterbauweraerts.instancio.fixture.builder.generator;

import static javax.lang.model.element.Modifier.PUBLIC;

import org.instancio.Select;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.ParameterSpec;

import io.github.wouterbauweraerts.instancio.fixture.builder.generator.parameter.ParamType;

class WithMethodFactory {
    MethodSpec generateWithMethod(String withMethodName, String fieldName, ParamType paramType, String builderClassName) {
        ParameterSpec parameter = ParameterSpec.builder(
                paramType.isPrimitive() ? paramType.typeName() : ClassName.bestGuess(paramType.fullyQualifiedName()),
                fieldName
        ).build();
        return MethodSpec.methodBuilder(withMethodName)
                .addModifiers(PUBLIC)
                .returns(ClassName.bestGuess(builderClassName))
                .addParameter(parameter)
                .addStatement("return set($T.field(\"%s\"), %s)".formatted(fieldName, fieldName), Select.class)
                .build();
    }

    MethodSpec generateInheritedWithMethod(String withMethodName, String fieldName, ParamType paramType, String builderClassName, String parentClassName) {
        ParameterSpec parameter = ParameterSpec.builder(
                paramType.isPrimitive() ? paramType.typeName() : ClassName.bestGuess(paramType.fullyQualifiedName()),
                fieldName
        ).build();
        return MethodSpec.methodBuilder(withMethodName)
                .addModifiers(PUBLIC)
                .returns(ClassName.bestGuess(builderClassName))
                .addParameter(parameter)
                .addStatement("return set($T.fields(f -> \"%s\".equals(f.getName()) && f.getDeclaringClass().getSimpleName().equals(\"%s\")), %s)".formatted(fieldName, parentClassName, fieldName), Select.class)
                .build();
    }

//    static TargetSelector idSelector() {
//        return Select.fields(f -> "id".equals(f.getName())
//                && f.getDeclaringClass().getSimpleName().equals("AbstractPersistable"));
//    }
}
