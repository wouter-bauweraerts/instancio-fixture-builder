package io.github.wouterbauweraerts.instancio.fixture.builder.generator.exception;

public class FixtureBuilderGenerationException extends RuntimeException {
    public FixtureBuilderGenerationException(String message) {
        super(message);
    }

    public static FixtureBuilderGenerationException unableToReadAnnotation() {
        return new FixtureBuilderGenerationException("Unable to read @GenerateFixtureBuilder annotation details");
    }

    public static FixtureBuilderGenerationException missingValue(String keyValue) {
        return new FixtureBuilderGenerationException("Unable to read value from %s in @GenerateFixtureBuilder annotation".formatted(keyValue));
    }

    public static FixtureBuilderGenerationException writeException(String message) {
        return new FixtureBuilderGenerationException(message);
    }

    public static FixtureBuilderGenerationException modelNotFound() {
        return new FixtureBuilderGenerationException("No Instancio model found");
    }
}
