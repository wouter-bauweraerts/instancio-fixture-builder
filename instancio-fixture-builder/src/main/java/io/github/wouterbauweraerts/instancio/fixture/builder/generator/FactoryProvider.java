package io.github.wouterbauweraerts.instancio.fixture.builder.generator;

import javax.annotation.processing.ProcessingEnvironment;

import io.github.wouterbauweraerts.instancio.fixture.builder.generator.util.GenerateFixtureBuilderUtils;

class FactoryProvider {
    private FactoryProvider() {
    }

    static BuilderMethodFactory builderMethodFactory(ProcessingEnvironment processingEnv) {
        return new BuilderMethodFactory(
                new BuildMethodFactory(),
                new SelfMethodFactory(),
                new WithMethodFactory(),
                new IgnoreMethodFactory(),
                nameFactory(),
                new FixtureBuilderMethodFactory(),
                toFixtureBuilderFactory(processingEnv),
                new GenerateFixtureBuilderUtils(processingEnv)
        );
    }

    static ToFixtureBuilderFactory toFixtureBuilderFactory(ProcessingEnvironment processingEnv) {
        return new ToFixtureBuilderFactory(nameFactory(), new GenerateFixtureBuilderUtils(processingEnv));
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
