package io.github.wouterbauweraerts.instancio.fixture.builder;

import static org.instancio.Select.field;

import java.util.HashMap;
import java.util.Map;

import org.instancio.GetMethodSelector;
import org.instancio.Instancio;
import org.instancio.InstancioApi;
import org.instancio.Model;
import org.instancio.Selector;

/**
 *
 * @param <TYPEBUILDER> : The class that you will be instantiating using instancio in combination with the AbstractFixtureBuilder
 * @param <SELF> : The name of the subclass of the AbstractFixtureBuilder
 */
public abstract class AbstractFixtureBuilder<TYPEBUILDER, SELF extends AbstractFixtureBuilder<TYPEBUILDER, SELF>> {
    private final Map<Selector, Object> fieldValues = new HashMap<>();

    /**
     * setField method will define the value for a given field, which will be used once the instance is constructed
     * @param methodReference method-reference to the getter of the field you want to set
     * @param value the value you want to assign
     * @return returns an instance of the FixtureBuilder
     * @param <PROPTYPE> type of the attribute you want to set to a specific value
     */
    protected <PROPTYPE> SELF setField(GetMethodSelector<TYPEBUILDER, PROPTYPE> methodReference, PROPTYPE value) {
        return set(field(methodReference), value);
    }

    /**
     * ignoreField will set a value to NULL
     * @param methodReference method-reference referencing the getter of the field you want to ignore
     * @return current instance of the FixtureBuilder
     */
    protected SELF ignoreField(GetMethodSelector<TYPEBUILDER, ?> methodReference) {
        return ignore(field(methodReference));
    }

    /**
     * set will set a field or multiple fields to a given value. See instancio documentation for available Selectors
     * @param selector instancio Selector
     * @param value value to assign
     * @return current instance of FixtureBuilder
     */
    protected SELF set(Selector selector, Object value) {
        fieldValues.put(selector, value);
        return self();
    }

    /**
     * ignore a field / multiple fields using an instancio Selector
     * @param selector instancio Selector to be used. See instancio documentation for available Selectors
     * @return
     */
    protected SELF ignore(Selector selector) {
        fieldValues.put(selector, null);
        return self();
    }

    /**
     * This method will construct the actual instance. It will take into account the model parameter, but also the values that were specified/ignored
     * @param model Instancio model to be used for object creation
     * @return Instance of the type being constructed
     */
    protected final TYPEBUILDER buildInternal(Model<TYPEBUILDER> model) {
        InstancioApi<TYPEBUILDER> instancioApi = Instancio.of(model);

        fieldValues.forEach(instancioApi::set);

        return instancioApi.create();
    }

    /**
     * should call the buildInternal method with the correct model to instantiate the object. Should be implemented in each class
     * @return newly created instance
     */
    public abstract TYPEBUILDER build();

    /**
     * should always return 'this' in every implementation. This has to be implemented in each class
     * @return current fixture builder instance
     */
    public abstract SELF self();
}
