package io.github.wouterbauweraerts.instancio.fixture.builder.generator;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

import com.palantir.javapoet.MethodSpec;

import io.github.wouterbauweraerts.instancio.fixture.builder.generator.parameter.ParamType;
import io.github.wouterbauweraerts.instancio.fixture.builder.generator.util.GenerateFixtureBuilderUtils;

class BuilderMethodFactory {

    private final BuildMethodFactory buildMethodFactory;
    private final SelfMethodFactory selfMethodFactory;
    private final WithMethodFactory withMethodFactory;
    private final IgnoreMethodFactory ignoreMethodFactory;
    private final NameFactory nameFactory;

    private final GenerateFixtureBuilderUtils utils;

    BuilderMethodFactory(BuildMethodFactory buildMethodFactory, SelfMethodFactory selfMethodFactory, WithMethodFactory withMethodFactory, IgnoreMethodFactory ignoreMethodFactory, NameFactory nameFactory, GenerateFixtureBuilderUtils utils) {
        this.buildMethodFactory = buildMethodFactory;
        this.selfMethodFactory = selfMethodFactory;
        this.withMethodFactory = withMethodFactory;
        this.ignoreMethodFactory = ignoreMethodFactory;
        this.nameFactory = nameFactory;
        this.utils = utils;
    }

    List<MethodSpec> generateFieldMethods(Element typeToBuild, String builderClassName) {
        Map<String, ParamType> fields = typeToBuild.getEnclosedElements().stream()
                .filter(e -> e.getKind().equals(ElementKind.FIELD))
                .collect(Collectors.toMap(
                        element -> element.getSimpleName().toString(),
                        utils::extractParamType
                ));

        return fields.entrySet()
                .stream()
                .flatMap(e -> generateBuilderMethodsForField(e.getKey(), e.getValue(), builderClassName))
                .toList();
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
