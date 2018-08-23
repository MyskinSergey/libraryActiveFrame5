package ru.intertrust.gui.workfinder.client;

import ru.intertrust.cm.core.gui.api.client.Component;
import ru.intertrust.cm.core.gui.impl.client.PluginPanel;
import ru.intertrust.cm.core.gui.impl.client.action.SimpleServerAction;
import ru.intertrust.cm.core.gui.model.ComponentName;
import ru.intertrust.cm.core.gui.model.action.ActionContext;
import ru.intertrust.cm.core.gui.model.action.ActionData;
import ru.intertrust.cm.core.gui.model.action.SaveActionContext;
import ru.intertrust.cm.core.gui.model.form.FormState;
import ru.intertrust.cm.core.gui.model.plugin.IsDomainObjectEditor;

@ComponentName("reg.approve.reg.request.action")
public class RegistrationApproveRegRequestAction extends SimpleServerAction {


    @Override
    protected SaveActionContext appendCurrentContext(ActionContext initialContext) {
        final IsDomainObjectEditor editor = (IsDomainObjectEditor) getPlugin();
        final FormState formState = editor.getFormState();
        final SaveActionContext context = (SaveActionContext) initialContext;
        context.setRootObjectId(formState.getObjects().getRootNode().getDomainObject().getId());
        context.setFormState(formState);
        context.setPluginState(editor.getFormPluginState());
        return context;
    }


    protected void onSuccess(ActionData result) {
        PluginPanel owner = plugin.getOwner();
        owner.getCurrentPlugin().refresh();
    }


    @Override
    public Component createNew() {
        return new RegistrationApproveRegRequestAction();
    }
}
