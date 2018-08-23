package ru.intertrust.cm.contacts.extension;

import org.springframework.beans.factory.annotation.Autowired;

import ru.intertrust.cm.contacts.util.Constants;
import ru.intertrust.cm.core.business.api.CrudService;
import ru.intertrust.cm.core.business.api.dto.DomainObject;
import ru.intertrust.cm.core.business.api.dto.FieldModification;
import ru.intertrust.cm.core.business.api.dto.Id;
import ru.intertrust.cm.core.dao.api.extension.BeforeSaveExtensionHandler;
import ru.intertrust.cm.core.dao.api.extension.ExtensionPoint;

import java.util.List;

/**
 * Created by Konstantin Gordeev on 13.10.2014.
 */
@ExtensionPoint(filter = "Addr_Region")
public class AddressBeforeSaveExtensionPoint implements BeforeSaveExtensionHandler {

    @Autowired
    private CrudService crudService;

    @Override
    public void onBeforeSave(DomainObject domainObject, List<FieldModification> fieldModifications) {
        String title = domainObject.getString(Constants.FIELD_NAME_NAME);

        if (title == null || title.trim().isEmpty()) {
            return;
        }

        Id parentId = domainObject.getReference(Constants.FIELD_NAME_SUBORDINATION);

        if (parentId != null) {
            DomainObject countryParent = crudService.find(parentId);
            if (countryParent == null) {
                return;
            }
            title = title + ", " + countryParent.getString(Constants.FIELD_NAME_TITLE);
        }

        domainObject.setString(Constants.FIELD_NAME_TITLE, title);
    }
}