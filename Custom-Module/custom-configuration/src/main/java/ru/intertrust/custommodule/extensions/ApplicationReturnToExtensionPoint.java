package ru.intertrust.custommodule.extensions;

import ru.intertrust.cm.core.business.api.dto.DomainObject;
import ru.intertrust.cm.core.business.api.dto.FieldModification;
import ru.intertrust.cm.core.dao.api.extension.BeforeSaveExtensionHandler;
import ru.intertrust.cm.core.dao.api.extension.ExtensionPoint;

import java.util.List;

@ExtensionPoint(filter = "lib_application")
public class ApplicationReturnToExtensionPoint implements BeforeSaveExtensionHandler {
    @Override
    public void onBeforeSave(DomainObject domainObject, List<FieldModification> list) {
        final String typeName = domainObject.getTypeName();
        System.out.println("##### Before save" + typeName);
    }
}
