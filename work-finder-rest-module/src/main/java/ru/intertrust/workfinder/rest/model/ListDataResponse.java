package ru.intertrust.workfinder.rest.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vitaliy Orlov on 10.01.2017.
 */
public class ListDataResponse implements DataResponse {

    private List<DataResponse> dataResponses;

    public ListDataResponse() {
    }

    public ListDataResponse(List<? extends DataResponse> dataResponses) {
        this.dataResponses = new ArrayList<>(dataResponses);
    }

    public List<DataResponse> getDataResponses() {
        return dataResponses;
    }

    public void setDataResponses(List<DataResponse> dataResponses) {
        this.dataResponses = dataResponses;
    }
}
