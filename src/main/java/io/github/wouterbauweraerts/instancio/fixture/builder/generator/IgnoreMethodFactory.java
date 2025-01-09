package io.github.wouterbauweraerts.instancio.fixture.builder.generator;

import static javax.lang.model.element.Modifier.PUBLIC;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.MethodSpec;

public class IgnoreMethodFactory {
    private IgnoreMethodFactory() {
    }

    public static MethodSpec generateIgnoreMethod(String ignoreMethodName, String withMethodName, String builderClassName) {
        return MethodSpec.methodBuilder(ignoreMethodName)
                .addModifiers(PUBLIC)
                .returns(ClassName.bestGuess(builderClassName))
                .addCode("return %s(null);%n".formatted(withMethodName))
                .build();
    }
}
