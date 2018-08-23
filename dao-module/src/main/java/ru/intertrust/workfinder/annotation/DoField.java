package ru.intertrust.workfinder.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Vitaliy Orlov on 20.12.2016.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DoField {
    public enum FieldType{
        STRING,
        LONG,
        DATE,
        BOOLEAN,
        REFERENCE_ID
    }

    String name();
    FieldType type();
    Class<? extends DoFieldValueConverter> classConverter() default  DoFieldValueConverter.class;
}
