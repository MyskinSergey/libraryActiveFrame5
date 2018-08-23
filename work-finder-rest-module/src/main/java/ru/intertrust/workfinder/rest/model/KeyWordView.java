package ru.intertrust.workfinder.rest.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Vitaliy Orlov on 11.01.2017.
 */
@XmlRootElement(name = "KeyWord")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class KeyWordView implements DataResponse {
    @XmlElement
    private String word ;
    @XmlElement
    private String id;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
