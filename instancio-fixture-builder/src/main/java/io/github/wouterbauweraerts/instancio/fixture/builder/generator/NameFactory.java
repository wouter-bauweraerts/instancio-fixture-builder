package io.github.wouterbauweraerts.instancio.fixture.builder.generator;

import javax.annotation.Nonnull;

public class NameFactory {
    static final String FIXTURE_BUILDER_SUFFIX = "FixtureBuilder";

    public String fieldMethodWithPrefix(@Nonnull String prefix, @Nonnull String fieldName) {
        return prefix + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    public String fixtureBuilderName(@Nonnull String className) {
        return className + FIXTURE_BUILDER_SUFFIX;
    }
}
