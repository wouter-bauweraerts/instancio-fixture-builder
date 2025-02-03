package io.github.wouterbauweraerts.instancio.fixture.builder.dummy.inheritance;

public class BaseClass {
    private String baseClassField;

    public BaseClass(String baseClassField) {
        this.baseClassField = baseClassField;
    }

    public String getBaseClassField() {
        return baseClassField;
    }
}
