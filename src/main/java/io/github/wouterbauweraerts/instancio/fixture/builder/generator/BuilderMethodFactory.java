package io.github.wouterbauweraerts.instancio.fixture.builder.generator;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.TypeName;

import io.github.wouterbauweraerts.instancio.fixture.builder.generator.parameter.ParamType;

class BuilderMethodFactory {

    private final BuildMethodFactory buildMethodFactory;
    private final SelfMethodFactory selfMethodFactory;
    private final WithMethodFactory withMethodFactory;
    private final IgnoreMethodFactory ignoreMethodFactory;
    private final NameFactory nameFactory;

    BuilderMethodFactory(BuildMethodFactory buildMethodFactory, SelfMethodFactory selfMethodFactory, WithMethodFactory withMethodFactory, IgnoreMethodFactory ignoreMethodFactory, NameFactory nameFactory) {
        this.buildMethodFactory = buildMethodFactory;
        this.selfMethodFactory = selfMethodFactory;
        this.withMethodFactory = withMethodFactory;
        this.ignoreMethodFactory = ignoreMethodFactory;
        this.nameFactory = nameFactory;
    }

    List<MethodSpec> generateFieldMethods(ProcessingEnvironment processingEnv, Element typeToBuild, String builderClassName) {
        Types typeUtils = processingEnv.getTypeUtils();
        Elements elementUtils = processingEnv.getElementUtils();

        Map<String, ParamType> fields = typeToBuild.getEnclosedElements().stream()
                .filter(e -> e.getKind().equals(ElementKind.FIELD))
                .collect(Collectors.toMap(
                        element -> element.getSimpleName().toString(),
                        e -> extractParameterTypeName(e, elementUtils, typeUtils)
                ));

        return fields.entrySet()
                .stream()
                .flatMap(e -> generateBuilderMethodsForField(e.getKey(), e.getValue(), builderClassName))
                .toList();
    }

    private static ParamType extractParameterTypeName(Element e, Elements elementUtils, Types typeUtils) {
        boolean isPrimitiveType = e.asType().getKind()
                .isPrimitive();
        if (isPrimitiveType) {
            return ParamType.of(TypeName.get(e.asType()));
        }
        return ParamType.of(
                typeUtils.asElement(e.asType()).toString()
//                elementUtils.getName(
//                        typeUtils.asElement(e.asType())
//                                .getSimpleName()
//                                .toString()
//                ).toString()
        );
    }

    private Stream<MethodSpec> generateBuilderMethodsForField(String fieldName, ParamType paramType, String builderClassName) {
        String withMethodName = nameFactory.fieldMethodWithPrefix("with", fieldName);
        String ignoreMethodName = nameFactory.fieldMethodWithPrefix("ignore", fieldName);

        return Stream.of(
                withMethodFactory.generateWithMethod(withMethodName, fieldName, paramType, builderClassName),
                ignoreMethodFactory.generateIgnoreMethod(ignoreMethodName, withMethodName, builderClassName, paramType.isPrimitive())
        ).filter(Objects::nonNull);
    }

    public MethodSpec generateSelf(String builderClassName) {
        return selfMethodFactory.generateSelf(builderClassName);
    }

    public MethodSpec generateBuild(Element model, String typeToBuild) {
        return buildMethodFactory.generateBuild(model, typeToBuild);
    }
}
