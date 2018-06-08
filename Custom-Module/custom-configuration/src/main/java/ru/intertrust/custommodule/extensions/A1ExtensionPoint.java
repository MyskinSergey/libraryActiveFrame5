package ru.intertrust.custommodule.extensions;

import ru.intertrust.cm.core.business.api.dto.DomainObject;
import ru.intertrust.cm.core.business.api.dto.FieldModification;
import ru.intertrust.cm.core.dao.api.extension.BeforeSaveExtensionHandler;
import ru.intertrust.cm.core.dao.api.extension.ExtensionPoint;

import java.util.List;

/**
 * Created by Myskin Sergey on 18.04.2018.
 */

@ExtensionPoint(filter = "a1")
public class A1ExtensionPoint implements BeforeSaveExtensionHandler {

    @Override
    public void onBeforeSave(DomainObject domainObject, List<FieldModification> list) {
        final String typeName = domainObject.getTypeName();
        System.out.printf("before save '" + typeName + "' object");
        System.out.println();
    }
}
