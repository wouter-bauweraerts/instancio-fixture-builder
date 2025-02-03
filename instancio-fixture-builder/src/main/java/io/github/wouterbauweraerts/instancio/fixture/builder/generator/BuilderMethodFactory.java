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
    private final ToFixtureBuilderFactory toFixtureBuilderFactory;

    BuilderMethodFactory(BuildMethodFactory buildMethodFactory, SelfMethodFactory selfMethodFactory, WithMethodFactory withMethodFactory, IgnoreMethodFactory ignoreMethodFactory, NameFactory nameFactory, GenerateFixtureBuilderUtils utils, ToFixtureBuilderFactory toFixtureBuilderFactory) {
        this.buildMethodFactory = buildMethodFactory;
        this.selfMethodFactory = selfMethodFactory;
        this.withMethodFactory = withMethodFactory;
        this.ignoreMethodFactory = ignoreMethodFactory;
        this.nameFactory = nameFactory;
        this.utils = utils;
        this.toFixtureBuilderFactory = toFixtureBuilderFactory;
    }

    List<MethodSpec> generateFieldMethods(Element typeToBuild, String builderClassName) {
        Map<String, ParamType> fields = typeToBuild.getEnclosedElements().stream()
                .filter(e -> e.getKind().equals(ElementKind.FIELD))
                .collect(Collectors.toMap(
                        element -> element.getSimpleName().toString(),
                        utils::extractParamType
                ));

        Stream<MethodSpec> currentClassBuilderMethods = fields.entrySet()
                .stream()
                .flatMap(e -> generateBuilderMethodsForField(e.getKey(), e.getValue(), builderClassName));

        return Stream.concat(
                currentClassBuilderMethods,
                generateBuilderMethodsForInheritedFields(typeToBuild, builderClassName)
        ).toList();
    }

    private Stream<MethodSpec> generateBuilderMethodsForInheritedFields(Element type, String builderClassName) {
        Element superclass = utils.getSuperclass(type);
        String superClassname = superclass.getSimpleName().toString();

        if (superClassname.equals("Object")) {
            return Stream.empty();
        }

        Map<String, ParamType> fields = superclass.getEnclosedElements().stream()
                .filter(e -> e.getKind().equals(ElementKind.FIELD))
                .collect(Collectors.toMap(
                        element -> element.getSimpleName().toString(),
                        utils::extractParamType
                ));

        return Stream.concat(
                fields.entrySet()
                        .stream()
                        .flatMap(e -> generateBuilderMethodsForInheritedField(e.getKey(), e.getValue(), builderClassName, superClassname)),
                generateBuilderMethodsForInheritedFields(superclass, builderClassName)
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

    private Stream<MethodSpec> generateBuilderMethodsForInheritedField(String fieldName, ParamType paramType, String builderClassName, String superClassname) {
        String withMethodName = nameFactory.fieldMethodWithPrefix("with", fieldName);
        String ignoreMethodName = nameFactory.fieldMethodWithPrefix("ignore", fieldName);

        return Stream.of(
                withMethodFactory.generateInheritedWithMethod(withMethodName, fieldName, paramType, builderClassName, superClassname),
                ignoreMethodFactory.generateIgnoreMethod(ignoreMethodName, withMethodName, builderClassName, paramType.isPrimitive())
        ).filter(Objects::nonNull);
    }

    public MethodSpec generateSelf(String builderClassName) {
        return selfMethodFactory.generateSelf(builderClassName);
    }

    public MethodSpec generateBuild(Element model, String typeToBuild) {
        return buildMethodFactory.generateBuild(model, typeToBuild);
    }

    public MethodSpec generateToFixtureBuilder(String builderClassName, Element expectedParameterType) {
        return toFixtureBuilderFactory.generateToFixtureBuilder(builderClassName, expectedParameterType);
    }
}
