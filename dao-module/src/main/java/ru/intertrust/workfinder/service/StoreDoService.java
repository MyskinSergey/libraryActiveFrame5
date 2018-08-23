package ru.intertrust.workfinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.intertrust.cm.core.business.api.CollectionsService;
import ru.intertrust.cm.core.business.api.CrudService;
import ru.intertrust.cm.core.business.api.dto.*;
import ru.intertrust.workfinder.annotation.DoField;
import ru.intertrust.workfinder.annotation.DoFieldValueConverter;
import ru.intertrust.workfinder.annotation.DoType;
import ru.intertrust.workfinder.exception.DomainObjectProcessingException;
import ru.intertrust.workfinder.model.BaseDomainObject;
import ru.intertrust.workfinder.model.BatchDomainObject;


import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Vitaliy Orlov on 20.12.2016.
 */
@Component
public class StoreDoService {




    public enum CompareType{
        EQUALS,
        LIKE
    }

    @Autowired
    private CrudService crudService;

    @Autowired
    private CollectionsService collectionsService;

    public <T extends BaseDomainObject> T saveDo(T domainInfo){
        DomainObject domainObject = null;
        if(domainInfo.getId() != null){
            domainObject = crudService.find(domainInfo.getId());
        }else{
            domainObject = crudService.createDomainObject(getDoTypeName(domainInfo.getClass()));
        }

        for(Field filed : getFields(domainInfo.getClass())){
            DoField doFieldInfo = filed.getAnnotation(DoField.class);
            if(doFieldInfo != null){
                String targetFiledName = doFieldInfo.name();
                DoField.FieldType targetType = doFieldInfo.type();
                Object fieldValue = null;
                try{
                    filed.setAccessible(true);
                    Class<? extends DoFieldValueConverter> converterClass = doFieldInfo.classConverter();
                    if(converterClass != DoFieldValueConverter.class){
                        DoFieldValueConverter converter = converterClass.newInstance();
                        fieldValue = converter.convertToDomain(filed.get(domainInfo));
                    } else{
                        fieldValue = filed.get(domainInfo);
                    }


                    if(DoField.FieldType.STRING.equals(targetType)){
                        domainObject.setString(targetFiledName, (String)fieldValue);
                    }else if(DoField.FieldType.LONG.equals(targetType)){
                        domainObject.setLong(targetFiledName, (Long) fieldValue);
                    }else if(DoField.FieldType.DATE.equals(targetType)){
                        domainObject.setTimestamp(targetFiledName, (Date) fieldValue);
                    }else if(DoField.FieldType.BOOLEAN.equals(targetType)){
                        domainObject.setBoolean(targetFiledName, (Boolean) fieldValue);
                    }
                    else if(DoField.FieldType.REFERENCE_ID.equals(targetType)){
                        domainObject.setReference(targetFiledName, (Id) fieldValue);
                    }


                }catch (Exception e){
                    throw new DomainObjectProcessingException("Ошибка при серилизации поля {"+filed.getName()+"}", e);
                }

            }

        }

        domainObject = crudService.save(domainObject);
        return (T)fillObject(domainInfo.getClass(), domainObject);
    }

    public void deleteAll(List<? extends BaseDomainObject> doList) {
        if(doList != null){
            for(BaseDomainObject doItem : doList){
                if(doItem != null && doItem.getId() != null){
                    crudService.delete(doItem.getId());
                }
            }
        }
    }

    public <T extends BaseDomainObject> BatchDomainObject<T> saveDoBatch(BatchDomainObject<T> batchDomainObject) {
        if(batchDomainObject == null || batchDomainObject.getData() == null){
            return null;
        }

        BatchDomainObject<T> result = new BatchDomainObject<>();
        result.setData(new ArrayList<T>());
        for(T doItem : batchDomainObject.getData()){
            result.getData().add(saveDo(doItem));
        }
        return result;
    }


    private List<Field> getFields(Class aClass) {
        List<Field> result = new ArrayList<Field>(Arrays.asList(aClass.getDeclaredFields()));
        if(aClass.getSuperclass() != null){
            result.addAll( getFields(aClass.getSuperclass()));
        }
        return result;
    }

    public <T extends BaseDomainObject> T loadDo(Id id, Class<T> tagretClass){
        try{
            DomainObject domainObject = crudService.find(id);
            return fillObject(tagretClass, domainObject);
        }catch (Exception e){
            throw new DomainObjectProcessingException("Ошибка при загрузде ДО {"+ (id != null ? id.toStringRepresentation() : "id == null")+"} " + tagretClass.getCanonicalName());
        }
    }

    public <T extends BaseDomainObject> T loadFirstDo(Class<T> targetObjectClass, String fieldName, Object value){
       List<T> result = loadDoList(targetObjectClass, fieldName, value, 0,0);
        return result == null || result.isEmpty() ? null : result.get(0);
    }


    private <T extends BaseDomainObject> T fillObject(Class<T> targetObjectClass, DomainObject domainObject) {
        String targetDomainType = getDoTypeName(targetObjectClass);
        if(domainObject.getTypeName().equalsIgnoreCase(targetDomainType)){
            try{
                T result = targetObjectClass.newInstance();
                result.setId(domainObject.getId());
                for(Field field : getFields(targetObjectClass)){
                    DoField doFieldInfo = field.getAnnotation(DoField.class);
                    field.setAccessible(true);
                    if(doFieldInfo != null) {
                        String targetFiledName = doFieldInfo.name();
                        DoField.FieldType targetType = doFieldInfo.type();
                        Class<? extends DoFieldValueConverter> converterClass = doFieldInfo.classConverter();
                        Object fieldValue = null;

                        if (DoField.FieldType.STRING.equals(targetType)) {
                            fieldValue = domainObject.getString(targetFiledName);
                        } else if (DoField.FieldType.LONG.equals(targetType)) {
                            fieldValue = domainObject.getLong(targetFiledName);
                        } else if (DoField.FieldType.DATE.equals(targetType)) {
                            fieldValue = domainObject.getTimestamp(targetFiledName);
                        } else if (DoField.FieldType.BOOLEAN.equals(targetType)) {
                            fieldValue = domainObject.getBoolean(targetFiledName);
                        }else if (DoField.FieldType.REFERENCE_ID.equals(targetType)) {
                            fieldValue = domainObject.getReference(targetFiledName);
                        }
                        if (converterClass != DoFieldValueConverter.class) {
                            DoFieldValueConverter converter = converterClass.newInstance();
                            field.set(result, converter.convertToModel(fieldValue));
                        }else{
                            field.set(result, fieldValue);
                        }
                    }
                }
                return result;
            }catch (Exception e){
                throw new DomainObjectProcessingException(e.getMessage(), e);
            }
        }else{
            throw new DomainObjectProcessingException("Несоответствие доменного объекта {"+domainObject.getTypeName()+"} и java class {"+targetObjectClass.getCanonicalName()+" : "+targetDomainType+"}");
        }
    }


    private static String getDoTypeName(Class doClass){
        DoType annotation = (DoType) doClass.getAnnotation(DoType.class);
        if(annotation == null){
            throw new DomainObjectProcessingException("class {"+doClass.getCanonicalName()+"} не содержит аннотации DoType");
        }
        String doType = ((DoType)annotation).doTypeName();

        if(doType == null || doType.trim().isEmpty()){
            throw new DomainObjectProcessingException("некорректное занчение DoType");
        }
        return doType;
    }

    public <T extends BaseDomainObject> List<T> loadDoList(Class<T> targetObjectClass, String fieldName, Object value, int offset, int limit) {
      return loadDoList(targetObjectClass, fieldName, value, CompareType.EQUALS, offset, limit);
    }

    public <T extends BaseDomainObject> List<T> loadDoList(Class<T> targetObjectClass, String fieldName, Object value, CompareType compareType , int offset, int limit) {
        try{
            List<T> result = new ArrayList<>();
            String targetDomainType = getDoTypeName(targetObjectClass);

            List<Value> params  = new ArrayList<>();

            StringBuilder sql = new StringBuilder("select t_0.id from "+ targetDomainType + " as t_0 ");
            boolean isFieldFound = false;
            Class sourceClass = targetObjectClass;
            boolean isSQLCompleted = false;
            while(!isFieldFound || sourceClass == null){
                try{
                    Field field = sourceClass.getDeclaredField(fieldName);
                    DoField annotation = field.getAnnotation(DoField.class);
                    isFieldFound = true;
                    if(annotation != null){
                        DoField.FieldType targetType = annotation.type();
                        params.add(getValue(targetType, value));

                        if(sourceClass == targetObjectClass){
                            if(CompareType.EQUALS.equals(compareType)){
                                sql.append(" where t_0." +  annotation.name());

                            }else  if(CompareType.LIKE.equals(compareType)){
                                sql.append(" where upper(t_0." +  annotation.name() +") ");
                            }

                        }else{
                            sql.append(" left join " + getDoTypeName(sourceClass) + " as t_1 where upper(t_1." + annotation.name() +") ");
                        }

                        if(CompareType.EQUALS.equals(compareType)){
                            sql.append(" = {0}");
                        }else if(CompareType.LIKE.equals(compareType)){
                            sql.append(" like upper({0}) ");
                        }

                        isSQLCompleted = true;
                    }
                }catch (Exception e){
                    sourceClass = sourceClass.getSuperclass();
                }

            }

            if(isSQLCompleted){


                IdentifiableObjectCollection res = collectionsService.findCollectionByQuery(sql.toString(), params, offset, limit);
                if(res != null ){
                    for(int index = 0; index < res.size(); index++){
                        result.add(loadDo(res.get(index).getId(), targetObjectClass));
                    }
                }

                return result;
            }else{
                throw new DomainObjectProcessingException("Ошибка при поиска объекта по полю, sql не построен");
            }


        }catch (Exception e){
            throw new DomainObjectProcessingException("Ошибка при поиска объекта по полю", e);
        }
    }

    private Value getValue( DoField.FieldType targetType, Object fieldValue){
        if(DoField.FieldType.STRING.equals(targetType)){
            return new StringValue((String)fieldValue);
        }else if(DoField.FieldType.LONG.equals(targetType)){
            return new LongValue((Long) fieldValue);
        }else if(DoField.FieldType.DATE.equals(targetType)){
            return new DateTimeValue((Date) fieldValue);
        }else if(DoField.FieldType.BOOLEAN.equals(targetType)){
           return new BooleanValue((Boolean) fieldValue);
        }else if(DoField.FieldType.REFERENCE_ID.equals(targetType)){
          return new ReferenceValue((Id) fieldValue);
        }else{
            throw new DomainObjectProcessingException("Тип "+targetType.name()+" не поддерживается");
        }

    }
}
