package io.github.wouterbauweraerts.instancio.fixture.builder.generator;

import static javax.lang.model.element.Modifier.PUBLIC;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.instancio.Select;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.MethodSpec;

public class BuilderMethodGenerator {
    public static List<MethodSpec> generate(ProcessingEnvironment processingEnv, Element typeToBuild, String builderClassName) {
        Types typeUtils = processingEnv.getTypeUtils();
        Elements elementUtils = processingEnv.getElementUtils();

        Map<String, String> fields = typeToBuild.getEnclosedElements().stream()
                .filter(e -> e.getKind().equals(ElementKind.FIELD))
                .collect(Collectors.toMap(
                        element -> element.getSimpleName().toString(),
                        e -> elementUtils.getName(typeUtils.asElement(e.asType()).toString()).toString()
                ));

        return fields.entrySet()
                .stream()
                .flatMap(e -> generateBuilderMethodsForField(e.getKey(), e.getValue(), builderClassName))
                .toList();
    }

    private static Stream<MethodSpec> generateBuilderMethodsForField(String fieldName, String qualifiedTypeName, String builderClassName) {
        String withMethodName = "with" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        String ignoreMethodName = "ignore" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);

        return Stream.of(
                generateWithMethod(withMethodName, fieldName, qualifiedTypeName, builderClassName),
                generateIgnoreMethod(ignoreMethodName, withMethodName, builderClassName)
        );
    }

    private static MethodSpec generateWithMethod(String withMethodName, String fieldName, String qualifiedTypeName, String builderClassName) {
        return MethodSpec.methodBuilder(withMethodName)
                .addModifiers(PUBLIC)
                .returns(ClassName.bestGuess(builderClassName))
                .addParameter(ClassName.bestGuess(qualifiedTypeName), fieldName)
                .addStatement("return set($T.field(\"%s\"), %s)".formatted(fieldName, fieldName), Select.class)
                .build();
    }

    private static MethodSpec generateIgnoreMethod(String ignoreMethodName, String withMethodName, String builderClassName) {
        return MethodSpec.methodBuilder(ignoreMethodName)
                .addModifiers(PUBLIC)
                .returns(ClassName.bestGuess(builderClassName))
                .addCode("return %s(null);%n".formatted(withMethodName))
                .build();
    }
}
