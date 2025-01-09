package io.github.wouterbauweraerts.instancio.fixture.builder.generator;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

class FactoryProviderTest {
    @Test
    void builderMethodFactory_returnsExpectedInstance() throws Exception {
        BuilderMethodFactory factory = FactoryProvider.builderMethodFactory();

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
        assertThat(getPrivateField(factory, "methodNameFactory"))
                .isNotNull()
                .isInstanceOf(MethodNameFactory.class);
    }

    private Object getPrivateField(Object instance, String fieldName) throws Exception {
        Field declaredField = instance.getClass().getDeclaredField(fieldName);
        declaredField.setAccessible(true);

        return declaredField.get(instance);
    }
}