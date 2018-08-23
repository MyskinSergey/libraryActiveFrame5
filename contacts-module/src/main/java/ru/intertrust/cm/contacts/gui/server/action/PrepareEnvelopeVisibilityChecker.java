package ru.intertrust.cm.contacts.gui.server.action;

import org.springframework.beans.factory.annotation.Autowired;

import ru.intertrust.cm.commons.DobjectsManager;
import ru.intertrust.cm.core.business.api.dto.DomainObject;
import ru.intertrust.cm.core.gui.api.server.action.ActionVisibilityChecker;
import ru.intertrust.cm.core.gui.api.server.action.ActionVisibilityContext;
import ru.intertrust.cm.core.gui.model.ComponentName;

@ComponentName("prepare.envelope.visibility.checker")
public class PrepareEnvelopeVisibilityChecker implements ActionVisibilityChecker {

    private static final String STATUS_READY = "Prepared";

    @Autowired
    private DobjectsManager dobjectsManager;

    @Override
    public boolean isVisible(ActionVisibilityContext context) {
        DomainObject currentDomainObject = context.getDomainObject();

        if (currentDomainObject != null && currentDomainObject.getId() != null) {
            if(currentDomainObject.getStatus() != null) {
                if (!dobjectsManager.getStatusById(currentDomainObject.getStatus()).equals(STATUS_READY)) {
                    return true;
                }
            }
        }

        return false;
    }
}
