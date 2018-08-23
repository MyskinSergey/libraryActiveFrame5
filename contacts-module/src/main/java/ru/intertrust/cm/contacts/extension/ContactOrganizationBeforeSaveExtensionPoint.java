package ru.intertrust.cm.contacts.extension;

import ru.intertrust.cm.contacts.util.Constants;
import ru.intertrust.cm.core.business.api.dto.DomainObject;
import ru.intertrust.cm.core.business.api.dto.FieldModification;
import ru.intertrust.cm.core.dao.api.extension.BeforeSaveExtensionHandler;
import ru.intertrust.cm.core.dao.api.extension.ExtensionPoint;

import java.util.List;

/**
 * Created by Konstantin Gordeev on 13.10.2014.
 */
@ExtensionPoint(filter = "cont_cperson_org")
public class ContactOrganizationBeforeSaveExtensionPoint implements BeforeSaveExtensionHandler {

    @Override
    public void onBeforeSave(DomainObject domainObject, List<FieldModification> fieldModifications) {
        String position = domainObject.getString(Constants.FIELD_NAME_POSITION);
        String position1 = domainObject.getString(Constants.FIELD_NAME_POSITION1);
        if ((position1 == null || position1.trim().isEmpty()) && (position != null && !position.trim().isEmpty())) {
            domainObject.setString(Constants.FIELD_NAME_POSITION1, position);
        }
    }
}