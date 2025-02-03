package io.github.wouterbauweraerts.instancio.fixture.builder.generator;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

import javax.lang.model.element.Element;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.TypeName;

class ToFixtureBuilderFactory {
    public MethodSpec generateToFixtureBuilder(String builderClassName, Element expectedParameterType) {
        return MethodSpec.methodBuilder("toFixtureBuilder")
                .addModifiers(PUBLIC)
                .addModifiers(STATIC)
                .addParameter(TypeName.get(expectedParameterType.asType()), "obj", FINAL)
                .returns(ClassName.bestGuess(builderClassName))
                .addCode("return null;") // TODO implement
                .build();
    }
}
