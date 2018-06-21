package ru.intertrust.custommodule.extensions;

import org.springframework.beans.factory.annotation.Autowired;
import ru.intertrust.cm.core.business.api.dto.DomainObject;
import ru.intertrust.cm.core.business.api.dto.FieldModification;
import ru.intertrust.cm.core.business.api.dto.Id;
import ru.intertrust.cm.core.dao.api.CurrentUserAccessor;
import ru.intertrust.cm.core.dao.api.extension.BeforeSaveExtensionHandler;
import ru.intertrust.cm.core.dao.api.extension.ExtensionPoint;
import ru.intertrust.custommodule.constant.LibApplicationDO;

import java.util.List;

@ExtensionPoint(filter = "lib_application")
public class ApplicationReturnToExtensionPoint implements BeforeSaveExtensionHandler {

    @Autowired
    private CurrentUserAccessor currentUserAccessor;

    @Override
    public void onBeforeSave(DomainObject domainObject, List<FieldModification> list) {
        final String typeName = domainObject.getTypeName();
        System.out.println("##### Before save" + typeName);

        // Ссылка на системную сущность “Person”. Заполняется автоматически: Текущий пользователь. Менять нельзя.
        final Id appAuthorId = domainObject.getReference(LibApplicationDO.AUTHOR_FIELD);
        if (appAuthorId == null) {
            Id currentUserId = currentUserAccessor.getCurrentUserId();
            domainObject.setReference(LibApplicationDO.AUTHOR_FIELD, currentUserId);
        }
    }
}
