package io.github.wouterbauweraerts.instancio.fixture.builder.generator.parameter;

import com.palantir.javapoet.TypeName;

public record ParamType(
        String fullyQualifiedName,
        TypeName typeName
) {
    public static ParamType of(String fullyQualifiedName) {
        return new ParamType(fullyQualifiedName, null);
    }

    public static ParamType of(TypeName typeName) {
        return new ParamType(null, typeName);
    }

    public boolean isPrimitive() {
        return typeName != null;
    }
}
