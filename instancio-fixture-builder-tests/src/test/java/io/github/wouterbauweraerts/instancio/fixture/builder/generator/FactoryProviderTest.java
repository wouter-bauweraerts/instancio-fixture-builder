package io.github.wouterbauweraerts.instancio.fixture.builder.generator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Field;

import javax.annotation.processing.ProcessingEnvironment;

import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.instancio.fixture.builder.generator.util.GenerateFixtureBuilderUtils;

class FactoryProviderTest {
    @Test
    void builderMethodFactory_returnsExpectedInstance() throws Exception {
        ProcessingEnvironment processingEnv = mock(ProcessingEnvironment.class);

        BuilderMethodFactory factory = FactoryProvider.builderMethodFactory(processingEnv);

        assertThat(factory).isInstanceOf(BuilderMethodFactory.class);
        assertThat(getPrivateField(factory, "buildMethodFactory"))
                .isNotNull()
                .isInstanceOf(BuildMethodFactory.class);
        assertThat(getPrivateField(factory, "selfMethodFactory"))
                .isNotNull()
                .isInstanceOf(SelfMethodFactory.class);
        assertThat(getPrivateField(factory, "withMethodFactory"))
                .isNotNull()
                .isInstanceOf(WithMethodFactory.class);
        assertThat(getPrivateField(factory, "ignoreMethodFactory"))
                .isNotNull()
                .isInstanceOf(IgnoreMethodFactory.class);
        assertThat(getPrivateField(factory, "nameFactory"))
                .isNotNull()
                .isInstanceOf(NameFactory.class);
        assertThat(getPrivateField(factory, "utils"))
                .isNotNull()
                .isInstanceOf(GenerateFixtureBuilderUtils.class);
    }

    private Object getPrivateField(Object instance, String fieldName) throws Exception {
        Field declaredField = instance.getClass().getDeclaredField(fieldName);
        declaredField.setAccessible(true);

        return declaredField.get(instance);
    }
}