package io.github.wouterbauweraerts.instancio.fixture.builder.generate.inheritance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.util.Random;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.instancio.fixture.builder.dummy.inheritance.ChildClass;
import io.github.wouterbauweraerts.instancio.fixture.builder.dummy.inheritance.ChildClassFixtureBuilder;
import io.github.wouterbauweraerts.instancio.fixture.builder.dummy.inheritance.MultiLevelInheritanceChild;
import io.github.wouterbauweraerts.instancio.fixture.builder.dummy.inheritance.MultiLevelInheritanceChildFixtureBuilder;

class FixtureBuilderWithInheritanceTest {
    @Test
    void fixtureBuilderAlsoPopulatesFieldsFromBaseClass() {
        assertThat(new ChildClassFixtureBuilder().build())
                .satisfies(cc -> assertThat(cc.getBaseClassField()).isNotNull());
    }

    @Test
    void fixtureBuilderCanManipulateFielsFromBaseClass() {
        String random = Instancio.create(String.class);
        assertThat(
                new ChildClassFixtureBuilder()
                        .withBaseClassField(random)
                        .build()
        ).isNotNull()
                .returns(random, ChildClass::getBaseClassField);
    }

    @Test
    void fixtureBuilderAlsoPopulatesFieldsInMultiLevelBaseClasses() {
        assertThat(new MultiLevelInheritanceChildFixtureBuilder().build())
                .satisfies(ml -> assertAll(() -> {
                    assertThat(ml.getChildField()).isNotNull();
                    assertThat(ml.getParentField()).isNotNull();
                    assertThat(ml.getBaseClassField()).isNotNull();
                }));
    }

    @Test
    void fixtureBuilderCanManipulateFieldsFromAllBaseClasses() {
        LocalDate childField = LocalDate.now();
        int parentField = new Random().nextInt();
        String baseClassField = Instancio.create(String.class);

        assertThat(
                new MultiLevelInheritanceChildFixtureBuilder()
                        .withChildField(childField)
                        .withParentField(parentField)
                        .withBaseClassField(baseClassField)
                        .build()
        ).returns(childField, MultiLevelInheritanceChild::getChildField)
                .returns(parentField, MultiLevelInheritanceChild::getParentField)
                .returns(baseClassField, MultiLevelInheritanceChild::getBaseClassField);
    }

    @Test
    void fixtureBuilderCanIgnoreFieldsFromAllBaseClasses() {

        assertThat(
                new MultiLevelInheritanceChildFixtureBuilder()
                        .ignoreChildField()
                        .ignoreParentField()
                        .ignoreBaseClassField()
                        .build()
        ).returns(null, MultiLevelInheritanceChild::getChildField)
                .returns(null, MultiLevelInheritanceChild::getParentField)
                .returns(null, MultiLevelInheritanceChild::getBaseClassField);
    }
}
