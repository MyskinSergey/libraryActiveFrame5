package ru.intertrust.cm.contacts.extension;

import org.springframework.beans.factory.annotation.Autowired;

import ru.intertrust.cm.commons.DobjectsManager;
import ru.intertrust.cm.core.business.api.dto.DomainObject;
import ru.intertrust.cm.core.business.api.dto.FieldModification;
import ru.intertrust.cm.core.business.api.dto.TimelessDate;
import ru.intertrust.cm.core.dao.api.extension.BeforeSaveExtensionHandler;
import ru.intertrust.cm.core.dao.api.extension.ExtensionPoint;



import java.util.Calendar;
import java.util.List;

/**
 * Created by Ravil Abdulkhairov on 20.10.2014.
 */
@ExtensionPoint(filter = "Occa_Occasion_Calculated")
public class OccasionCalculatedBeforeSaveExtensionPoint implements BeforeSaveExtensionHandler  {
    private static String FIELD_TYPE = "Occasion_Type";
    private static String FIELD_MONTH = "Month";
    private static String FIELD_DAY_OF_WEEK = "Day_Week";
    private static String FIELD_WEEK = "Number_Week";
    private static String FIELD_DATE_OCCASION = "Date_Occasion";
    private static Long FIRST_TYPE = 2l;

    @Autowired
    private DobjectsManager dobjectsManager;

    @Override
    public void onBeforeSave(DomainObject domainObject, List<FieldModification> fieldModifications) {
        TimelessDate tDate;
        Calendar currentDate = Calendar.getInstance();
        domainObject.setLong(FIELD_TYPE, FIRST_TYPE);
        if(domainObject.getLong(FIELD_MONTH)!=null
                && domainObject.getLong(FIELD_DAY_OF_WEEK)!=null
                && domainObject.getLong(FIELD_WEEK)!=null){
            tDate = dobjectsManager.getDateByDayWeekMonth(domainObject.getLong(FIELD_DAY_OF_WEEK),
                    domainObject.getLong(FIELD_WEEK),
                    domainObject.getLong(FIELD_MONTH),
                    Long.valueOf(currentDate.get(Calendar.YEAR)));
            domainObject.setTimelessDate(FIELD_DATE_OCCASION,tDate);
        }

    }
}
