package io.github.wouterbauweraerts.instancio.fixture.builder.generator;

import static javax.lang.model.element.Modifier.PUBLIC;

import org.instancio.Select;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.MethodSpec;

public class WithMethodFactory {
    private WithMethodFactory() {
    }

    public static MethodSpec generateWithMethod(String withMethodName, String fieldName, String qualifiedTypeName, String builderClassName) {
        return MethodSpec.methodBuilder(withMethodName)
                .addModifiers(PUBLIC)
                .returns(ClassName.bestGuess(builderClassName))
                .addParameter(ClassName.bestGuess(qualifiedTypeName), fieldName)
                .addStatement("return set($T.field(\"%s\"), %s)".formatted(fieldName, fieldName), Select.class)
                .build();
    }
}
