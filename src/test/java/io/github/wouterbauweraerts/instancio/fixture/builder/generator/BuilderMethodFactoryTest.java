package io.github.wouterbauweraerts.instancio.fixture.builder.generator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.lang.model.element.Element;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BuilderMethodFactoryTest {

    @InjectMocks
    private BuilderMethodFactory builderMethodFactory;
    @Mock
    BuildMethodFactory buildMethodFactory;
    @Mock
    SelfMethodFactory selfMethodFactory;
    @Mock
    WithMethodFactory withMethodFactory;
    @Mock
    IgnoreMethodFactory ignoreMethodFactory;
    @Mock
    NameFactory nameFactory;

    @Test
    void generateSelf_delegatesToExpectedFactory() {
        String builderClassName = Instancio.create(String.class);

        builderMethodFactory.generateSelf(builderClassName);

        verify(selfMethodFactory).generateSelf(builderClassName);
    }

    @Test
    void generateBuild() {
        String typeName = Instancio.create(String.class);
        Element model = mock(Element.class);

        builderMethodFactory.generateBuild(model, typeName);

        verify(buildMethodFactory).generateBuild(model, typeName);
    }
}