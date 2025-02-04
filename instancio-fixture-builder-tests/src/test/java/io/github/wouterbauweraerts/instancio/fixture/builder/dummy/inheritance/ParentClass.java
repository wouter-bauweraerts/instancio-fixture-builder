package io.github.wouterbauweraerts.instancio.fixture.builder.dummy.inheritance;

import java.util.Objects;

public class ParentClass extends BaseClass {
    private Integer parentField;

    public ParentClass(String baseClassField, Integer parentField) {
        super(baseClassField);
        this.parentField = parentField;
    }

    public Integer getParentField() {
        return parentField;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ParentClass that)) return false;
        return Objects.equals(parentField, that.parentField)
                && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(parentField);
    }
}
