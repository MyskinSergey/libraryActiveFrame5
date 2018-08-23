package ru.intertrust.cm.contacts.extension;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import ru.intertrust.cm.contacts.util.Constants;
import ru.intertrust.cm.core.business.api.dto.DomainObject;
import ru.intertrust.cm.core.business.api.dto.FieldModification;
import ru.intertrust.cm.core.dao.api.extension.BeforeSaveExtensionHandler;
import ru.intertrust.cm.core.dao.api.extension.ExtensionPoint;

import java.util.List;
import java.util.UUID;

/**
 * Created by Konstantin Gordeev on 6.11.2014.
 */
@ExtensionPoint(filter = "cont_organization")
public class OrganizationBeforeSaveExtensionPoint implements BeforeSaveExtensionHandler {

    @Override
    public void onBeforeSave(DomainObject domainObject, List<FieldModification> fieldModifications) {
        String fullName = domainObject.getString(Constants.FIELD_NAME_FULLNAME);

        if (fullName == null || fullName.trim().isEmpty()) {
            domainObject.setString(Constants.FIELD_NAME_FULLNAME, domainObject.getString(Constants.FIELD_NAME_NAME));
        }
        String uid = domainObject.getString(Constants.FIELD_NAME_UID);
        if (uid == null || uid.trim().isEmpty()){
            domainObject.setString(Constants.FIELD_NAME_UID, UUID.randomUUID().toString());
        }


    }
}