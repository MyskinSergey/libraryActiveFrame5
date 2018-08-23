package ru.intertrust.workfinder.rest.model;

import ru.intertrust.workfinder.rest.model.status.StatusCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Vitaliy Orlov on 01.12.2016.
 */
@XmlRootElement(name =  "StatusResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatusResponse {
    @XmlElement
    private boolean success;
    @XmlElement
    private String result;

    @XmlElement
    private DataResponse dataResponse;

    @XmlElement
    private StatusCode statusCode;

    public DataResponse getDataResponse() {
        return dataResponse;
    }

    public void setDataResponse(DataResponse dataResponse) {
        this.dataResponse = dataResponse;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }
}
