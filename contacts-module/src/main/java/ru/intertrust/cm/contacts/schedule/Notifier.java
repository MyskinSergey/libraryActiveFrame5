package ru.intertrust.cm.contacts.schedule;

import org.springframework.beans.factory.annotation.Autowired;

import ru.intertrust.cm.commons.DobjectsManager;
import ru.intertrust.cm.core.business.api.CollectionsService;
import ru.intertrust.cm.core.business.api.CrudService;
import ru.intertrust.cm.core.business.api.dto.DomainObject;
import ru.intertrust.cm.core.business.api.dto.IdentifiableObject;
import ru.intertrust.cm.core.business.api.dto.IdentifiableObjectCollection;
import ru.intertrust.cm.core.business.api.schedule.ScheduleTask;
import ru.intertrust.cm.core.business.api.schedule.ScheduleTaskHandle;
import ru.intertrust.cm.core.business.api.schedule.ScheduleTaskParameters;

import javax.ejb.EJBContext;
import javax.ejb.SessionContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ravil Abdulkhairov
 * on 22.12.2014.
 */
@ScheduleTask(name = "Notifier", hour = "*/8", minute = "0", active = true)
public class Notifier implements ScheduleTaskHandle {
    private static final String QUERY_FOR_EXECUTORS = "select P.* " +
            "from person P " +
            "join person_role PR on PR.person=P.id " +
            "join role R on R.id=pR.role and R.name='Исполнитель' ";

    private static final String QUERY_NOTIFY_FOR = "select " +
            "OOC.id as id, " +
            "OO.name as occasion_name, " +
            "COALESCE(OO.date_occasion, " +
            "(SELECT Date_Occasion " +
            "FROM Occa_Calendar_Occasion " +
            "WHERE date_part('year',Date_Occasion)= date_part('year',now()) " +
            "AND Occasion=OO.id LIMIT 1" +
            ")) " +
            "as occasion_date, " +
            "CCP.title as person_title " +
            "from Occa_Occasion_CPerson OOC " +
            "join occa_occasion OO ON OO.id=OOC.occasion " +
            "join cont_contact_person CCP on CCP.id=OOC.contact_person " +
            "where (OOC.notified is null OR OOC.notified=0) " +
            "and " +
            "( " +
            "(DATE_PART('day', COALESCE(OO.date_occasion, " +
            "(SELECT Date_Occasion " +
            "FROM Occa_Calendar_Occasion " +
            "WHERE date_part('year',Date_Occasion)= date_part('year',now()) " +
            "AND Occasion=OO.id LIMIT 1" +
            "))-date_trunc('day',now()))>0 " +
            "and " +
            "DATE_PART('day', COALESCE(OO.date_occasion, " +
            "(SELECT Date_Occasion " +
            "FROM Occa_Calendar_Occasion " +
            "WHERE date_part('year',Date_Occasion)= date_part('year',now()) " +
            "AND Occasion=OO.id LIMIT 1" +
            "))-date_trunc('day',now()))<=OOC.notifyfor " +
            "and " +
            "OOC.notifyfortype=1 " +
            ") " +
            "OR " +
            "( " +
            "TRUNC(DATE_PART('day', COALESCE(OO.date_occasion, " +
            "(SELECT Date_Occasion " +
            "FROM Occa_Calendar_Occasion " +
            "WHERE date_part('year',Date_Occasion)= date_part('year',now()) " +
            "AND Occasion=OO.id LIMIT 1" +
            "))-date_trunc('day',now()))/7)>=0  " +
            "and " +
            "TRUNC(DATE_PART('day', COALESCE(OO.date_occasion, " +
            "(SELECT Date_Occasion " +
            "FROM Occa_Calendar_Occasion " +
            "WHERE date_part('year',Date_Occasion)= date_part('year',now()) " +
            "AND Occasion=OO.id LIMIT 1" +
            "))-date_trunc('day',now()))/7)<=OOC.notifyfor " +
            "and " +
            "OOC.notifyfortype=2 " +
            "))";

    private static final String QUERY_RESET_NOTIFIED = "select " +
            "OOC.id as id " +
            "from Occa_Occasion_CPerson OOC " +
            "join occa_occasion OO ON OO.id=OOC.occasion " +
            "join cont_contact_person CCP on CCP.id=OOC.contact_person " +
            "where (OOC.notified=1 AND date_trunc('day',now()) > COALESCE(OO.date_occasion, " +
            "(SELECT Date_Occasion " +
            "FROM Occa_Calendar_Occasion " +
            "WHERE date_part('year',Date_Occasion)= date_part('year',now()) " +
            "AND Occasion=OO.id LIMIT 1" +
            ")))";

    private static final String PRIORITY_HIGH = "HIGH";
    private static final String FIELD_NEW = "new";
    private static final String FIELD_BODY = "body";
    private static final String FIELD_SUBJECT = "subject";
    private static final String FIELD_PRIORITY = "priority";
    private static final String FIELD_TO = "to";
    private static final String FIELD_FROM = "from";
    private static final String FIELD_LOGIN = "login";
    private static final String FIELD_TITLE = "person_title";
    private static final String FIELD_OCCASION_DATE = "occasion_date";
    private static final String FIELD_OCCASION_NAME= "occasion_name";
    private static final String FIELD_NOTIFIED = "notified";
    private static final String DO_PERSON = "person";
    private static final String DO_NOTIFICATION = "notification";
    private static final String VALUE_ADMIN = "admin";

    private static final String MSG_SUBJECT = "Напоминание";
    private static final String MSG_BODY = "Поздравить человека: %s с праздником %s который состоится %s";
    private DobjectsManager dobjectsManager;

    @Autowired
    private CollectionsService collectionsService;

    @Autowired
    private CrudService crudService;



    private static final String NOTIFICATION_DATE_FORMAT = "dd.MM.yyyy HH:mm:ss";


    @Override
    public String execute(EJBContext ejbContext, SessionContext sessionContext, ScheduleTaskParameters parameters)
            throws InterruptedException {
        List<DomainObject> objectsForUpdate;
        dobjectsManager = new DobjectsManager();
        IdentifiableObjectCollection addresseeCollection = collectionsService.findCollectionByQuery(QUERY_FOR_EXECUTORS);
        IdentifiableObjectCollection occasionsCollection = collectionsService.findCollectionByQuery(QUERY_NOTIFY_FOR);

        if((addresseeCollection!=null && addresseeCollection.size()>0)
                &&
                (occasionsCollection!=null && occasionsCollection.size()>0)
                )
        {
            objectsForUpdate = new ArrayList<DomainObject>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

            for(IdentifiableObject OC : occasionsCollection){ // по каждому событию
                for(IdentifiableObject AD : addresseeCollection){ // для каждого с ролью Исполнитель
                    send(MSG_SUBJECT,
                            String.format(MSG_BODY,
                                    OC.getString(FIELD_TITLE),
                                    OC.getString(FIELD_OCCASION_NAME),
                                    dateFormat.format(OC.getTimestamp(FIELD_OCCASION_DATE))
                            ),
                            AD.getString(FIELD_LOGIN));
                }

                objectsForUpdate.add(crudService.find(OC.getId()));

            }
            for(DomainObject L : objectsForUpdate){
                L.setBoolean(FIELD_NOTIFIED,true);
            }
            crudService.save(objectsForUpdate);
        }

        IdentifiableObjectCollection resetCollection = collectionsService.findCollectionByQuery(QUERY_RESET_NOTIFIED);
        if(resetCollection!=null && resetCollection.size()>0){
            List<DomainObject> objectsForReset = new ArrayList<DomainObject>();
            for(IdentifiableObject R : resetCollection)
                objectsForReset.add(crudService.find(R.getId()));
            for(DomainObject OR : objectsForReset){
                OR.setBoolean(FIELD_NOTIFIED, false);
            }
            crudService.save(objectsForReset);

        }

        return "NOTIFIER COMPLETED";
    }

    private void send(String subj, String body, String adresseeLogin){
        DomainObject notification = dobjectsManager.getDaoUtil().createDomainObject(DO_NOTIFICATION);
        String now = new SimpleDateFormat(NOTIFICATION_DATE_FORMAT).format(new Date());
        String subject = new StringBuilder().append(now).append(" - ").append(subj).toString();
        notification.setString(FIELD_SUBJECT, subject);
        DomainObject fromPerson = dobjectsManager.getDaoUtil().findDomainObjectByField(DO_PERSON, FIELD_LOGIN, VALUE_ADMIN);
        DomainObject addressee = dobjectsManager.getDaoUtil().findDomainObjectByField(DO_PERSON, FIELD_LOGIN, adresseeLogin);
        notification.setReference(FIELD_FROM, fromPerson);
        notification.setReference(FIELD_TO, addressee);
        notification.setString(FIELD_BODY, body);
        notification.setString(FIELD_PRIORITY,PRIORITY_HIGH);
        notification.setBoolean(FIELD_NEW, true);
        dobjectsManager.getDaoUtil().save(notification);
    }
}
