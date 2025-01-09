package io.github.wouterbauweraerts.instancio.fixture.builder.generator;

class FactoryProvider {
    private FactoryProvider() {
    }

    static BuilderMethodFactory builderMethodFactory() {
        return new BuilderMethodFactory(
                new BuildMethodFactory(),
                new SelfMethodFactory(),
                new WithMethodFactory(),
                new IgnoreMethodFactory(),
                nameFactory()
        );
    }

    static NameFactory nameFactory() {
        return new NameFactory();
    }

    static FixtureBuilderFactory fixtureBuilderFactory() {
        return new FixtureBuilderFactory();
    }

    static JavaFileFactory fileFactory() {
        return new JavaFileFactory();
    }

    static GeneratedAnnotationFactory generatedAnnotationFactory() {
        return new GeneratedAnnotationFactory();
    }
}
