package ru.intertrust.cm.contacts.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ru.intertrust.cm.commons.DobjectsManager;
import ru.intertrust.cm.core.business.api.CollectionsService;
import ru.intertrust.cm.core.business.api.CrudService;
import ru.intertrust.cm.core.business.api.dto.DomainObject;
import ru.intertrust.cm.core.business.api.dto.IdentifiableObject;
import ru.intertrust.cm.core.business.api.dto.IdentifiableObjectCollection;
import ru.intertrust.cm.core.business.api.dto.TimelessDate;
import ru.intertrust.cm.core.business.api.schedule.ScheduleTask;
import ru.intertrust.cm.core.business.api.schedule.ScheduleTaskHandle;
import ru.intertrust.cm.core.business.api.schedule.ScheduleTaskParameters;




import javax.ejb.EJBContext;
import javax.ejb.SessionContext;

import java.util.Calendar;


/**
 * Created by Ravil Abdulkhairov
 * <p/>
 * Периодическое задание, пересчитывает даты калькулируемых событий для текущего года,
 * тип событий - 2
 */

@ScheduleTask(name = "DateRefresher", hour = "*/8", minute = "0", active = true)
public class DateRefresher implements ScheduleTaskHandle {

    private static String MSG_COL_SERVICE_ERROR = "Collection Service or Crud Service accessors are not instantiated.Can`t proceed.";
    private static String MSG_FIND_OBJECT_ERROR = "Cant`t find object by Id %s";
    private static String FIELD_DATE_OCCASION = "Date_Occasion";
    private static String FIELD_MONTH = "Month";
    private static String FIELD_DAY_OF_WEEK = "Day_Week";
    private static String FIELD_WEEK = "Number_Week";
    private static String QUERY_TYPE_TWO = "select " +
            "OOC.id as id, " +
            "OOC.Day_Week as dayOfWeek," +
            "OOC.Number_Week as numberOfWeek," +
            "OOC.Month as month " +
            "from Occa_Occasion_Calculated OOC";
    private static String QUERY_FIXED = "select * from occa_occasion_fixed";

    private Logger logger = LoggerFactory.getLogger(DateRefresher.class);

    @Autowired
    protected CollectionsService collectionsService;

    @Autowired
    protected CrudService crudService;

    @Override
    public String execute(EJBContext ejbContext, SessionContext sessionContext, ScheduleTaskParameters parameters)
            throws InterruptedException {
        IdentifiableObjectCollection collection;
        IdentifiableObjectCollection fixedCollection;
        TimelessDate tDate;
        Calendar currentDate = Calendar.getInstance();
        DomainObject currentObject;
        DobjectsManager dobjectsManager = new DobjectsManager();

        if(collectionsService!=null && crudService!=null){
            collection = collectionsService.findCollectionByQuery(QUERY_TYPE_TWO);
            if(collection!=null && collection.size()>0){

                for(IdentifiableObject I : collection){
                    currentObject = crudService.find(I.getId());
                    if(currentObject!=null){
                        tDate = dobjectsManager.getDateByDayWeekMonth(currentObject.getLong(FIELD_DAY_OF_WEEK),
                                currentObject.getLong(FIELD_WEEK),
                                currentObject.getLong(FIELD_MONTH),
                                Long.valueOf(currentDate.get(Calendar.YEAR)));
                        currentObject.setTimelessDate(FIELD_DATE_OCCASION,tDate);
                        crudService.save(currentObject);
                    }
                    else {
                        logger.warn(String.format(MSG_FIND_OBJECT_ERROR,I.getId().toStringRepresentation()));
                    }
                }
            }

            fixedCollection = collectionsService.findCollectionByQuery(QUERY_FIXED);
            if(fixedCollection!=null && fixedCollection.size()>0){
                for(IdentifiableObject O : fixedCollection){
                    currentObject = crudService.find(O.getId());
                    tDate = currentObject.getTimelessDate(FIELD_DATE_OCCASION);
                    int year = currentDate.get(Calendar.YEAR);
                    int month = tDate.getMonth();
                    int dayOfMonth = tDate.getDayOfMonth();
                    tDate = new TimelessDate(year,month,dayOfMonth);
                    currentObject.setTimelessDate(FIELD_DATE_OCCASION,tDate);
                    crudService.save(currentObject);
                }
            }
        }
        else {
            logger.warn(MSG_COL_SERVICE_ERROR);
        }


        return "COMPLETE";

    }


}
