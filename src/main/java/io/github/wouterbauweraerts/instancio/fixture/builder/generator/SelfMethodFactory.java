package io.github.wouterbauweraerts.instancio.fixture.builder.generator;

import static javax.lang.model.element.Modifier.PUBLIC;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.MethodSpec;

class SelfMethodFactory {
    MethodSpec generateSelf(String returnType) {
        return MethodSpec.methodBuilder("self")
                .addAnnotation(Override.class)
                .addModifiers(PUBLIC)
                .returns(ClassName.bestGuess(returnType))
                .addCode("return this;\n")
                .build();
    }
}
