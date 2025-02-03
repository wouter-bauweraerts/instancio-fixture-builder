package io.github.wouterbauweraerts.instancio.fixture.builder.generate.inheritance;

import org.instancio.Instancio;
import org.instancio.Model;

import io.github.wouterbauweraerts.instancio.fixture.builder.GenerateFixtureBuilder;
import io.github.wouterbauweraerts.instancio.fixture.builder.InstancioModel;
import io.github.wouterbauweraerts.instancio.fixture.builder.dummy.inheritance.ChildClass;

@GenerateFixtureBuilder(builderForType = ChildClass.class, fixtureClass = ChildClassFixtures.class)
public class ChildClassFixtures {
    @InstancioModel
    public static final Model<ChildClass> CHILD_CLASS_MODEL = Instancio.of(ChildClass.class)
            .toModel();
}
