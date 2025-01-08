package io.github.wouterbauweraerts.instancio.fixture.builder.generate;

import static org.instancio.Select.field;

import org.instancio.Instancio;
import org.instancio.Model;

import io.github.wouterbauweraerts.instancio.fixture.builder.GenerateFixtureBuilder;
import io.github.wouterbauweraerts.instancio.fixture.builder.InstancioModel;
import io.github.wouterbauweraerts.instancio.fixture.builder.dummy.ContactDetailsFixtures;
import io.github.wouterbauweraerts.instancio.fixture.builder.dummy.Person;

@GenerateFixtureBuilder(builderForType = Dummy.class, fixtureClass = DummyFixtures.class)
@SuppressWarnings("unused")
public class DummyFixtures {
    @InstancioModel
    static final Model<Dummy> DUMMY_MODEL = Instancio.of(Dummy.class)
            .generate(field(Person::getBirthDate), gen -> gen.temporal().localDate().past())
            .supply(field(Person::getContactDetails), ContactDetailsFixtures::contactDetails)
            .toModel();

    public static DummyFixtureBuilder dummyFixtureBuilder() {
        return new DummyFixtureBuilder();
    }
}
