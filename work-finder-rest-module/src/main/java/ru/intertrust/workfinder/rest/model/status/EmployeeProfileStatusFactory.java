package ru.intertrust.workfinder.rest.model.status;

/**
 * Created by Vitaliy Orlov on 30.12.2016.
 */
public class EmployeeProfileStatusFactory extends BaseStatusCodeFactory{

    @Override
    public int getCurrentSection() {
        return EMPLOYEE_PROFILE_SECTION;
    }
}
