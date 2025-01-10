package io.github.wouterbauweraerts.instancio.fixture.builder.generate;

import static org.instancio.Select.field;

import org.instancio.Instancio;
import org.instancio.Model;

import io.github.wouterbauweraerts.instancio.fixture.builder.GenerateFixtureBuilder;
import io.github.wouterbauweraerts.instancio.fixture.builder.InstancioModel;
import io.github.wouterbauweraerts.instancio.fixture.builder.dummy.ContactDetailsFixtures;
import io.github.wouterbauweraerts.instancio.fixture.builder.dummy.Person;

@SuppressWarnings("unused")
@GenerateFixtureBuilder(builderForType = DummyWithPrimitives.class, fixtureClass = DummyWithPrimitivesFixtures.class)
public class DummyWithPrimitivesFixtures {
    @InstancioModel
    static final Model<DummyWithPrimitives> DUMMY_MODEL = Instancio.of(DummyWithPrimitives.class)
            .generate(field(Person::getBirthDate), gen -> gen.temporal().localDate().past())
            .supply(field(Person::getContactDetails), ContactDetailsFixtures::contactDetails)
            .toModel();
}
