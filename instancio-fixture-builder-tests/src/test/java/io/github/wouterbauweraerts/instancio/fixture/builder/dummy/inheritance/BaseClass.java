package io.github.wouterbauweraerts.instancio.fixture.builder.dummy.inheritance;

import java.util.Objects;

public class BaseClass {
    private String baseClassField;

    public BaseClass(String baseClassField) {
        this.baseClassField = baseClassField;
    }

    public String getBaseClassField() {
        return baseClassField;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BaseClass baseClass)) return false;
        return Objects.equals(baseClassField, baseClass.baseClassField);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(baseClassField);
    }
}
