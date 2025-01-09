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
                new MethodNameFactory()
        );
    }
}
