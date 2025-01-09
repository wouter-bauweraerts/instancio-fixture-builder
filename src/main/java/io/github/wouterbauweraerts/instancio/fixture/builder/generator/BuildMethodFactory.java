package io.github.wouterbauweraerts.instancio.fixture.builder.generator;

import static javax.lang.model.element.Modifier.PUBLIC;

import javax.lang.model.element.Element;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.MethodSpec;

class BuildMethodFactory {
    private BuildMethodFactory() {
    }

    static MethodSpec generate(Element model, String returnType) {
        return MethodSpec.methodBuilder("build")
                .addModifiers(PUBLIC)
                .addAnnotation(Override.class)
                .returns(ClassName.bestGuess(returnType))
                .addCode("return buildInternal(%s);%n".formatted(model.getSimpleName()))
                .build();
    }
}
