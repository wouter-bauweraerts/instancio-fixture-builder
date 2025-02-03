package io.github.wouterbauweraerts.instancio.fixture.builder.generate.inheritance;

import org.instancio.Instancio;
import org.instancio.Model;

import io.github.wouterbauweraerts.instancio.fixture.builder.GenerateFixtureBuilder;
import io.github.wouterbauweraerts.instancio.fixture.builder.InstancioModel;
import io.github.wouterbauweraerts.instancio.fixture.builder.dummy.inheritance.MultiLevelInheritanceChild;

@GenerateFixtureBuilder(builderForType = MultiLevelInheritanceChild.class, fixtureClass = MultiLevelInheritanceChildFixtures.class)
public class MultiLevelInheritanceChildFixtures {
    @InstancioModel
    public static final Model<MultiLevelInheritanceChild> MULTI_LEVEL_INHERITANCE_CHILD_MODEL = Instancio.of(MultiLevelInheritanceChild.class)
            .toModel();
}
