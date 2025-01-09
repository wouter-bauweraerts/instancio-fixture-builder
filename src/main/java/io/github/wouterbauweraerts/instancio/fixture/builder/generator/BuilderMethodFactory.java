package io.github.wouterbauweraerts.instancio.fixture.builder.generator;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import com.palantir.javapoet.MethodSpec;

class BuilderMethodFactory {

    private final BuildMethodFactory buildMethodFactory;
    private final SelfMethodFactory selfMethodFactory;
    private final WithMethodFactory withMethodFactory;
    private final IgnoreMethodFactory ignoreMethodFactory;
    private final MethodNameFactory methodNameFactory;

    BuilderMethodFactory(BuildMethodFactory buildMethodFactory, SelfMethodFactory selfMethodFactory, WithMethodFactory withMethodFactory, IgnoreMethodFactory ignoreMethodFactory, MethodNameFactory methodNameFactory) {
        this.buildMethodFactory = buildMethodFactory;
        this.selfMethodFactory = selfMethodFactory;
        this.withMethodFactory = withMethodFactory;
        this.ignoreMethodFactory = ignoreMethodFactory;
        this.methodNameFactory = methodNameFactory;
    }

    List<MethodSpec> generateFieldMethods(ProcessingEnvironment processingEnv, Element typeToBuild, String builderClassName) {
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

    private Stream<MethodSpec> generateBuilderMethodsForField(String fieldName, String qualifiedTypeName, String builderClassName) {
        String withMethodName = methodNameFactory.fieldMethodWithPrefix("with", fieldName);
        String ignoreMethodName = methodNameFactory.fieldMethodWithPrefix("ignore", fieldName);

        return Stream.of(
                withMethodFactory.generateWithMethod(withMethodName, fieldName, qualifiedTypeName, builderClassName),
                ignoreMethodFactory.generateIgnoreMethod(ignoreMethodName, withMethodName, builderClassName)
        );
    }

    public MethodSpec generateSelf(String builderClassName) {
        return selfMethodFactory.generateSelf(builderClassName);
    }

    public MethodSpec generateBuild(Element model, String typeToBuild) {
        return buildMethodFactory.generateBuild(model, typeToBuild);
    }
}
