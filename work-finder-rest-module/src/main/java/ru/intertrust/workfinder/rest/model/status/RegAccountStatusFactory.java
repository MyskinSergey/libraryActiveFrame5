package ru.intertrust.workfinder.rest.model.status;

/**
 * Created by Vitaliy Orlov on 30.12.2016.
 */
public class RegAccountStatusFactory extends BaseStatusCodeFactory{

    public StatusCode accountAlreadyExist(){
        return  getGeneralErrorCode("Account already exist");
    }

    @Override
    public int getCurrentSection() {
        return REG_ACCOUNT_SECTION;
    }
}
