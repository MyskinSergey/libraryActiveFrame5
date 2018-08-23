package ru.intertrust.workfinder.annotation;

/**
 * Created by Vitaliy Orlov on 20.12.2016.
 */
public interface DoFieldValueConverter<DO_TYPE, MODEL_TYPE>{
    public DO_TYPE convertToDomain(MODEL_TYPE value);
    public MODEL_TYPE convertToModel(DO_TYPE value);
}
