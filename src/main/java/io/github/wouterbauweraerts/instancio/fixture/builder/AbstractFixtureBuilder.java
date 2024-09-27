package io.github.wouterbauweraerts.instancio.fixture.builder;

import static org.instancio.Select.field;

import java.util.HashMap;
import java.util.Map;

import org.instancio.GetMethodSelector;
import org.instancio.Instancio;
import org.instancio.InstancioApi;
import org.instancio.Model;
import org.instancio.Selector;

public abstract class AbstractFixtureBuilder<TYPEBUILDER, SELF extends AbstractFixtureBuilder<TYPEBUILDER, SELF>> {
    private final Map<Selector, Object> fieldValues = new HashMap<>();

    protected <PROPTYPE> SELF setField(GetMethodSelector<TYPEBUILDER, PROPTYPE> methodReference, PROPTYPE value) {
        return set(field(methodReference), value);
    }

    protected SELF ignoreField(GetMethodSelector<TYPEBUILDER, ?> methodReference) {
        return ignore(field(methodReference));
    }

    protected SELF set(Selector selector, Object value) {
        fieldValues.put(selector, value);
        return self();
    }

    protected SELF ignore(Selector selector) {
        fieldValues.put(selector, null);
        return self();
    }

    protected final TYPEBUILDER buildInternal(Model<TYPEBUILDER> model) {
        InstancioApi<TYPEBUILDER> instancioApi = Instancio.of(model);

        fieldValues.forEach(instancioApi::set);

        return instancioApi.create();
    }

    public abstract TYPEBUILDER build();

    public abstract SELF self();
}
