package io.github.wouterbauweraerts.instancio.fixture.builder.generator.util;

import static java.util.Objects.nonNull;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

import com.palantir.javapoet.TypeName;

import io.github.wouterbauweraerts.instancio.fixture.builder.InstancioModel;
import io.github.wouterbauweraerts.instancio.fixture.builder.generator.exception.FixtureBuilderGenerationException;
import io.github.wouterbauweraerts.instancio.fixture.builder.generator.parameter.ParamType;

public class GenerateFixtureBuilderUtils {
    static final String GENERATE_FIXTURE_BUILDER_QUALIFIED_NAME = "io.github.wouterbauweraerts.instancio.fixture.builder.GenerateFixtureBuilder";

    private final ProcessingEnvironment processingEnv;

    public GenerateFixtureBuilderUtils(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
    }

    public AnnotationMirror extractAnnotationMirror(Element element) {
        return processingEnv.getElementUtils().getAllAnnotationMirrors(element).stream()
                .filter(am -> am.getAnnotationType().toString().equals(GENERATE_FIXTURE_BUILDER_QUALIFIED_NAME))
                .findFirst().orElseThrow(FixtureBuilderGenerationException::unableToReadAnnotation);
    }

    public Element getType(AnnotationMirror mirror, String keyValue) {
        return processingEnv.getElementUtils().getElementValuesWithDefaults(mirror).entrySet().stream()
                .filter(e -> e.getKey().getSimpleName().contentEquals(keyValue))
                .map(e -> e.getValue().getValue())
                .map(it -> processingEnv.getTypeUtils().asElement((TypeMirror) it))
                .findFirst()
                .orElseThrow(() -> FixtureBuilderGenerationException.missingValue(keyValue));
    }

    public Element extractInstancioModel(Element fixtureClassElement) {
        return fixtureClassElement.getEnclosedElements().stream()
                .filter(e -> nonNull(e.getAnnotation(InstancioModel.class)))
                .findFirst()
                .orElseThrow(FixtureBuilderGenerationException::modelNotFound);
    }

    public String extractPackageName(Element classElement) {
        return processingEnv.getElementUtils().getPackageOf(classElement).getQualifiedName().toString();
    }

    public ParamType extractParamType(Element element) {
        boolean isPrimitiveType = element.asType().getKind().isPrimitive();

        if (isPrimitiveType) {
            return ParamType.of(
                    TypeName.get(element.asType())
            );
        }

        return ParamType.of(
                processingEnv.getTypeUtils().asElement(element.asType()).toString()
        );
    }

    public Element getSuperclass(Element element) {
        TypeMirror superclass = processingEnv.getTypeUtils().directSupertypes(element.asType()).get(0);
        return processingEnv.getTypeUtils().asElement(superclass);
    }
}
