package ru.intertrust.workfinder.rest.model.status;

/**
 * Created by Vitaliy Orlov on 30.12.2016.
 */
public abstract class BaseStatusCodeFactory {
    public static final int GENERAL_SUCCESS = 0;
    public static final int GENERAL_ERROR = 1;


    public static final int REG_ACCOUNT_SECTION = 1;
    public static final int EMPLOYEE_PROFILE_SECTION = 2;
    public static final int DICTIONARY_SECTION = 3;


    public StatusCode success(){
        return new StatusCode(getCurrentSection(), GENERAL_SUCCESS, "OK");
    }

    public StatusCode getGeneralErrorCode(String message){
        return new StatusCode(getCurrentSection(), GENERAL_ERROR, message);
    }

    public abstract int getCurrentSection();
}
