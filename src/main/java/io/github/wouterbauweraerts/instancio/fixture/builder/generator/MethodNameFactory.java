package io.github.wouterbauweraerts.instancio.fixture.builder.generator;

import javax.annotation.Nonnull;

public class MethodNameFactory {
    public String fieldMethodWithPrefix(@Nonnull String prefix, @Nonnull String fieldName) {
        return prefix + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
}
