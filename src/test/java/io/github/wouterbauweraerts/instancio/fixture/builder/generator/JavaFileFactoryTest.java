package io.github.wouterbauweraerts.instancio.fixture.builder.generator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import com.palantir.javapoet.JavaFile;
import com.palantir.javapoet.TypeSpec;

class JavaFileFactoryTest {
    JavaFileFactory factory = new JavaFileFactory();

    @Test
    void createJavaFile_returnsExpected() {
        String packageName = Instancio.create(String.class);
        String fixturePackageName = Instancio.create(String.class);
        String fixture = Instancio.create(String.class);
        String modelFieldName = Instancio.create(String.class);

        TypeSpec classDef = mock(TypeSpec.class);

        JavaFile createdFile = factory.createJavaFile(packageName, fixturePackageName, fixture, classDef, modelFieldName);

        assertThat(createdFile).isNotNull()
                .isInstanceOf(JavaFile.class)
                .returns(packageName, JavaFile::packageName)
                .returns(classDef, JavaFile::typeSpec);

        assertThat(createdFile.toString()).contains("import static %s.%s.%s;".formatted(fixturePackageName, fixture, modelFieldName));
    }
}