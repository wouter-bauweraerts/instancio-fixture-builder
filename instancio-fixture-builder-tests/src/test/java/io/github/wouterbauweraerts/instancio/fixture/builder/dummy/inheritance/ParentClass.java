package io.github.wouterbauweraerts.instancio.fixture.builder.dummy.inheritance;

public class ParentClass extends BaseClass {
    private Integer parentField;

    public ParentClass(String baseClassField, Integer parentField) {
        super(baseClassField);
        this.parentField = parentField;
    }

    public Integer getParentField() {
        return parentField;
    }
}
