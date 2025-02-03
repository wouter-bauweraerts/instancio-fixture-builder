package io.github.wouterbauweraerts.instancio.fixture.builder.dummy.inheritance;

import java.time.LocalDate;

public class MultiLevelInheritanceChild extends ParentClass{
    private LocalDate childField;

    public MultiLevelInheritanceChild(String baseClassField, int parentField, LocalDate childField) {
        super(baseClassField, parentField);
        this.childField = childField;
    }

    public LocalDate getChildField() {
        return childField;
    }
}
