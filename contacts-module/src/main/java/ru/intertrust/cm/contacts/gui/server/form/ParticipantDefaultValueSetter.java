package ru.intertrust.cm.contacts.gui.server.form;

import ru.intertrust.cm.core.business.api.dto.ReferenceValue;
import ru.intertrust.cm.core.business.api.dto.Value;
import ru.intertrust.cm.core.gui.api.server.widget.FormDefaultValueSetter;
import ru.intertrust.cm.core.gui.model.ComponentName;
import ru.intertrust.cm.core.gui.model.form.FieldPath;
import ru.intertrust.cm.core.gui.model.form.FormObjects;
import ru.intertrust.cm.core.gui.model.form.FormState;

/**
 * Created by IntelliJ IDEA.
 * Developer: Ravil Abdulkhairov
 * Date: 12.01.2017
 * Time: 16:02
 * To change this template use File | Settings | File and Code Templates.
 */
@ComponentName("participant.value.setter")
public class ParticipantDefaultValueSetter implements FormDefaultValueSetter {

    @Override
    public Value[] getDefaultValues(FormObjects formObjects, FieldPath fieldPath) {
        return new Value[0];
    }

    @Override
    public Value getDefaultValue(FormObjects formObjects, FieldPath fieldPath) {
        return null;
    }

    @Override
    public Value[] getDefaultValues(FormState formState, FieldPath fieldPath) {
        return new Value[0];
    }

    @Override
    public Value getDefaultValue(FormState formState, FieldPath fieldPath) {
        if("project".equalsIgnoreCase(fieldPath.getPath())) {
            return new ReferenceValue(formState.getParentId());
        }
        return null;
    }
}
