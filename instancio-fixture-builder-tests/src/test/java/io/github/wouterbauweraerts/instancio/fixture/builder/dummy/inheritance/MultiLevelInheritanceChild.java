package io.github.wouterbauweraerts.instancio.fixture.builder.dummy.inheritance;

import java.time.LocalDate;
import java.util.Objects;

public class MultiLevelInheritanceChild extends ParentClass{
    private LocalDate childField;

    public MultiLevelInheritanceChild(String baseClassField, Integer parentField, LocalDate childField) {
        super(baseClassField, parentField);
        this.childField = childField;
    }

    public LocalDate getChildField() {
        return childField;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MultiLevelInheritanceChild that)) return false;
        return Objects.equals(childField, that.childField)
                && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(childField);
    }
}
