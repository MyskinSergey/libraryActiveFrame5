package ru.intertrust.cm.contacts.gui.server.provider;

import org.springframework.stereotype.Component;
import ru.intertrust.cm.core.business.api.dto.LongValue;
import ru.intertrust.cm.core.business.api.dto.Value;
import ru.intertrust.cm.core.gui.api.server.widget.WidgetContext;
import ru.intertrust.cm.core.gui.impl.server.widget.EnumerationMapProvider;
import ru.intertrust.cm.core.gui.model.ComponentName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Developer: Ravil Abdulkhairov
 * Date: 09.01.2017
 * Time: 17:39
 * To change this template use File | Settings | File and Code Templates.
 */
@Component
@ComponentName("employment.type.provider")
public class EmploymentTypesProvider implements EnumerationMapProvider {
    @Override
    @SuppressWarnings("rawtypes")
    public Map<String, Value> getMap(WidgetContext widgetContext) {
        Map<String,Value> values = new HashMap<>();
        values.put("Удаленно",new LongValue(0));
        values.put("Частично удаленно",new LongValue(1));
        values.put("В офисе",new LongValue(2));
        return values;
    }
}
