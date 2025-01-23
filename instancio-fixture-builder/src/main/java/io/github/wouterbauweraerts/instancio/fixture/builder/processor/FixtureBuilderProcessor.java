package io.github.wouterbauweraerts.instancio.fixture.builder.processor;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import com.google.auto.service.AutoService;

import io.github.wouterbauweraerts.instancio.fixture.builder.GenerateFixtureBuilder;
import io.github.wouterbauweraerts.instancio.fixture.builder.generator.FixtureBuilderGenerator;

@SuppressWarnings("unused")
@SupportedAnnotationTypes("io.github.wouterbauweraerts.instancio.fixture.builder.GenerateFixtureBuilder")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
@AutoService(Processor.class)
public class FixtureBuilderProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        FixtureBuilderGenerator fixtureBuilderGenerator = new FixtureBuilderGenerator(processingEnv);
        for (TypeElement annotation : annotations) {
            if (annotation.getQualifiedName().contentEquals(GenerateFixtureBuilder.class.getCanonicalName())) {
                Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
                Map<Boolean, ? extends List<? extends Element>> isProcessableMap = annotatedElements.stream()
                        .collect(Collectors.partitioningBy(e -> {
                            ElementKind kind = e.getKind();
                            return kind.equals(ElementKind.CLASS) || kind.equals(ElementKind.RECORD);
                        }));

                isProcessableMap.get(false).forEach(e -> processingEnv.getMessager().printMessage(
                        Diagnostic.Kind.ERROR,
                        "@GenerateFixtureBuilder can only be applied to classes or records",
                        e
                ));

                List<? extends Element> processableElements = isProcessableMap.get(true);

                if (processableElements.isEmpty()) {
                    return false;
                }

                processableElements.forEach(fixtureBuilderGenerator::generateFixtureBuilders);

                return true;
            }
        }
        return false;
    }
}
