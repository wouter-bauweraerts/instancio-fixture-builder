package io.github.wouterbauweraerts.instancio.fixture.builder.generator;

import static javax.lang.model.element.Modifier.PUBLIC;

import java.util.List;

import javax.lang.model.element.Element;

import com.palantir.javapoet.AnnotationSpec;
import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.ParameterizedTypeName;
import com.palantir.javapoet.TypeSpec;

import io.github.wouterbauweraerts.instancio.fixture.builder.AbstractFixtureBuilder;

class FixtureBuilderFactory {
    TypeSpec createBuilderSpec(
            String builderClassName,
            Element typeToBuild,
            AnnotationSpec generatedAnnotation,
            MethodSpec self,
            MethodSpec build,
            List<MethodSpec> builderMethods) {
        return TypeSpec.classBuilder(builderClassName)
                .addModifiers(PUBLIC)
                .addAnnotation(generatedAnnotation)
                .superclass(
                        ParameterizedTypeName.get(
                                ClassName.get(AbstractFixtureBuilder.class),
                                ClassName.bestGuess(typeToBuild.getSimpleName().toString()),
                                ClassName.bestGuess(builderClassName)
                        )
                )
                .addMethod(self)
                .addMethod(build)
                .addMethods(builderMethods)
                .build();
    }
}
