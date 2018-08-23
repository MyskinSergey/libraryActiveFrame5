package ru.intertrust.workfinder.model;

import java.util.List;

/**
 * Created by Vitaliy Orlov on 12.01.2017.
 */
public class BatchDomainObject <T extends BaseDomainObject>{
    private List<T> data;

    public BatchDomainObject(List<T> data) {
        this.data = data;
    }

    public BatchDomainObject() {
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
