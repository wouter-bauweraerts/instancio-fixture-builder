package io.github.wouterbauweraerts.instancio.fixture.builder.dummy.inheritance;

public class ChildClass extends BaseClass {
    private int childField;

    public ChildClass(String baseClassField, int childField) {
        super(baseClassField);
        this.childField = childField;
    }

    public int getChildField() {
        return childField;
    }

    public void setChildField(int childField) {
        this.childField = childField;
    }
}
