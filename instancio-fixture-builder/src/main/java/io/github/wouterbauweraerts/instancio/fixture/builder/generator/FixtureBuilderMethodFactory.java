package io.github.wouterbauweraerts.instancio.fixture.builder.generator;

import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.MethodSpec;

public class FixtureBuilderMethodFactory {
    public MethodSpec generateFixtureBuilder(String builderClassName) {
        return MethodSpec.methodBuilder("fixtureBuilder")
                .addModifiers(PUBLIC, STATIC)
                .returns(ClassName.bestGuess(builderClassName))
                .addCode("return new $T();", ClassName.bestGuess(builderClassName))
                .build();
    }
}
