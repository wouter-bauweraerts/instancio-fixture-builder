package io.github.wouterbauweraerts.instancio.fixture.builder.generator;

import static javax.lang.model.element.ElementKind.CLASS;
import static javax.lang.model.element.ElementKind.FIELD;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeKind;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.TypeName;

import io.github.wouterbauweraerts.instancio.fixture.builder.generator.util.GenerateFixtureBuilderUtils;

class ToFixtureBuilderFactory {

    private final NameFactory nameFactory;
    private final GenerateFixtureBuilderUtils utils;


    public ToFixtureBuilderFactory(NameFactory nameFactory, GenerateFixtureBuilderUtils utils) {
        this.nameFactory = nameFactory;
        this.utils = utils;
    }

    public MethodSpec generateToFixtureBuilder(String builderClassName, Element expectedParameterType) {

        return MethodSpec.methodBuilder("toFixtureBuilder")
                .addModifiers(PUBLIC)
                .addModifiers(STATIC)
                .addParameter(TypeName.get(expectedParameterType.asType()), "obj", FINAL)
                .returns(ClassName.bestGuess(builderClassName))
                .addCode(this.methodBody(builderClassName, expectedParameterType))
                .build();
    }

    private CodeBlock methodBody(String builderClassName, Element elementToClone) {
        CodeBlock.Builder builder = CodeBlock.builder()
                .addStatement("$L builder = new $L()", builderClassName, builderClassName);

        builder.add(shallowCopyClassFields(elementToClone));

        return builder
                .addStatement("return builder")
                .build();
    }

    private CodeBlock shallowCopyClassFields(Element elementToClone) {
        CodeBlock.Builder builder = CodeBlock.builder();

        Stream.concat(
                        elementToClone.getEnclosedElements().stream(),
                        inheritedFields(elementToClone)
                ).filter(e -> e.getKind().equals(FIELD))
                .filter(e -> !e.getModifiers().contains(Modifier.STATIC))
                .map(e -> new ElementTypeTriple(
                        e.getSimpleName().toString(),
                        e.asType().getKind().equals(TypeKind.BOOLEAN),
                        e.asType().getKind().isPrimitive()
                        )
                )
                .collect(Collectors.toMap(
                        triple -> nameFactory.fieldMethodWithPrefix("with", triple.name),
                        triple -> getObjFieldValue(triple, elementToClone)
                ))
                .entrySet().stream()
                .map(e -> "builder.%s(%s)".formatted(e.getKey(), e.getValue()))
                .forEach(builder::addStatement);

        return builder.build();
    }

    private Stream<? extends Element> inheritedFields(Element element) {
        Element superclass = utils.getSuperclass(element);
        String superClassname = superclass.getSimpleName().toString();

        if (superClassname.equals("Object")) {
            return Stream.empty();
        }

        return Stream.concat(
                superclass.getEnclosedElements().stream(),
                inheritedFields(superclass)
        );
    }

    private String getObjFieldValue(ElementTypeTriple triple, Element elementToClone) {
        String getMethod = nameFactory.fieldMethodWithPrefix(triple.isBoolean() && triple.isPrimitive() ? "is" :"get", triple.name());
        return (elementToClone.getKind().equals(CLASS)
                ? CodeBlock.builder().addStatement("obj.$L()", getMethod).build().toString()
                : CodeBlock.builder().addStatement("obj.$L()", triple.name()).build().toString()
        ).trim().replace(";", "");
    }

    private static record ElementTypeTriple(
            String name,
            boolean isBoolean,
            boolean isPrimitive
    ) {
    }
}
