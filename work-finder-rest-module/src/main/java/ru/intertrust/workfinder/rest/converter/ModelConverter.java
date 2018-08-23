package ru.intertrust.workfinder.rest.converter;

import java.util.List;

/**
 * Created by Vitaliy Orlov on 10.01.2017.
 */
public interface ModelConverter <SOURCE, TARGET> {
    TARGET convertFromSource(SOURCE source);
    SOURCE convertFromTarget(TARGET source);

    List<TARGET> convertFromListSource(List<SOURCE> source);
    List<SOURCE> convertFromListTarget(List<TARGET> source);

}
