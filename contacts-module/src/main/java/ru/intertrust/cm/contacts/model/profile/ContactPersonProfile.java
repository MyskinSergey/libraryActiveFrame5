package ru.intertrust.cm.contacts.model.profile;

import ru.intertrust.cm.contacts.model.CPersonOtherAddress;
import ru.intertrust.cm.contacts.model.ContContactPerson;

import java.util.List;

/**
 * Created by Vitaliy Orlov on 13.01.2017.
 */
public class ContactPersonProfile {

    private ContContactPerson contactProfile;
    private List<CPersonOtherAddress> addresses;

    public ContContactPerson getContactProfile() {
        return contactProfile;
    }

    public void setContactProfile(ContContactPerson contactProfile) {
        this.contactProfile = contactProfile;
    }

    public List<CPersonOtherAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<CPersonOtherAddress> addresses) {
        this.addresses = addresses;
    }
}
