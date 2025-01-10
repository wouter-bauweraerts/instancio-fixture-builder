package io.github.wouterbauweraerts.instancio.fixture.builder.generator;

import java.io.IOException;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import com.palantir.javapoet.AnnotationSpec;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.TypeSpec;

import io.github.wouterbauweraerts.instancio.fixture.builder.generator.exception.FixtureBuilderGenerationException;
import io.github.wouterbauweraerts.instancio.fixture.builder.generator.util.GenerateFixtureBuilderUtils;

public class FixtureBuilderGenerator {
    private static final String BUILDER_FOR_TYPE = "builderForType";
    private static final String FIXTURE_CLASS_TYPE = "fixtureClass";

    private final ProcessingEnvironment processingEnv;
    private final BuilderMethodFactory methodFactory;
    private final NameFactory nameFactory;
    private final FixtureBuilderFactory fixtureBuilderFactory;
    private final JavaFileFactory javaFileFactory;
    private final GeneratedAnnotationFactory generatedAnnotationFactory;
    private final GenerateFixtureBuilderUtils utils;

    public FixtureBuilderGenerator(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
        methodFactory = FactoryProvider.builderMethodFactory(processingEnv);
        nameFactory = FactoryProvider.nameFactory();
        fixtureBuilderFactory = FactoryProvider.fixtureBuilderFactory();
        javaFileFactory = FactoryProvider.fileFactory();
        generatedAnnotationFactory = FactoryProvider.generatedAnnotationFactory();

        utils = new GenerateFixtureBuilderUtils(processingEnv);
    }

    public void generateFixtureBuilders(Element fixtureBuilderElement) {
        AnnotationMirror annotationMirror = utils.extractAnnotationMirror(fixtureBuilderElement);
        Element builderForClass = utils.getType(annotationMirror, BUILDER_FOR_TYPE);
        Element fixtureClassType = utils.getType(annotationMirror, FIXTURE_CLASS_TYPE);
        Element model = utils.extractInstancioModel(fixtureClassType);

        String builderClassName = nameFactory.fixtureBuilderName(builderForClass.getSimpleName().toString());

        AnnotationSpec generatedAnnotation = generatedAnnotationFactory.createAnnotation();

        MethodSpec selfMethod = methodFactory.generateSelf(builderClassName);
        MethodSpec buildMethod = methodFactory.generateBuild(model, builderForClass.getSimpleName().toString());
        List<MethodSpec> fieldMethods = methodFactory.generateFieldMethods(builderForClass, builderClassName);

        TypeSpec fixtureBuilderClassDefinition = fixtureBuilderFactory.createBuilderSpec(
                builderClassName,
                builderForClass,
                generatedAnnotation,
                selfMethod,
                buildMethod,
                fieldMethods
        );

        try {
            javaFileFactory.createJavaFile(
                    utils.extractPackageName(builderForClass),
                    utils.extractPackageName(fixtureClassType),
                    fixtureClassType.getSimpleName().toString(),
                    fixtureBuilderClassDefinition,
                    model.getSimpleName().toString()
            ).writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            throw FixtureBuilderGenerationException.writeException(e.getMessage());
        }
    }
}
