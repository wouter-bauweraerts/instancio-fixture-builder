package io.github.wouterbauweraerts.instancio.fixture.builder.generator;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.JavaFile;
import com.palantir.javapoet.TypeSpec;

class JavaFileFactory {
    JavaFile createJavaFile(
            String packageName,
            String fixtureClassPackageName,
            String fixtureClassName,
            TypeSpec classDefinition,
            String instancioModelFieldName
    ) {
        return JavaFile.builder(packageName, classDefinition)
                .addStaticImport(ClassName.get(fixtureClassPackageName, fixtureClassName), instancioModelFieldName)
                .build();
    }
}
