package ru.intertrust.workfinder.rest.converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vitaliy Orlov on 10.01.2017.
 */
public abstract class CommonModelConverter <SOURCE, TARGET> implements ModelConverter<SOURCE, TARGET>{

    @Override
    public TARGET convertFromSource(SOURCE source) {
        try{
            if(source == null){
                return null;
            }
            TARGET result = getTargetClass().newInstance();
            fillTargetBySource(result, source);
            return result;
        }catch (Exception e){
            throw new RuntimeException("Error in converting models.", e);
        }
    }

    @Override
    public SOURCE convertFromTarget(TARGET target) {
        try{
            if(target == null){
                return null;
            }
            SOURCE result = getSourceClass().newInstance();
            fillSourceFromTarget(result, target);
            return result;
        }catch (Exception e){
            throw new RuntimeException("Error in converting models.", e);
        }
    }

    @Override
    public List<TARGET> convertFromListSource(List<SOURCE> source) {
        List<TARGET> result = new ArrayList<>();
        if(source != null){
            for(SOURCE srcElement : source){
                result.add(convertFromSource(srcElement));
            }
        }

        return result;
    }

    @Override
    public List<SOURCE> convertFromListTarget(List<TARGET> source) {
        List<SOURCE> result = new ArrayList<>();

        if(source != null){
            for(TARGET srcElement : source){
                result.add(convertFromTarget(srcElement));
            }
        }

        return result;
    }

    protected abstract void fillTargetBySource(TARGET target, SOURCE source);
    protected abstract void fillSourceFromTarget(SOURCE source, TARGET target);

    private Class<SOURCE> getSourceClass(){
        Class<? extends Object> currentClass = getClass();
        while (true) {
            Type genericSuperClass = currentClass.getGenericSuperclass();
            if (genericSuperClass instanceof ParameterizedType) {
                try {
                    Type resultType = ((ParameterizedType) genericSuperClass).getActualTypeArguments()[0];
                    if(resultType instanceof ParameterizedType){
                        return  (Class<SOURCE>)((ParameterizedType)resultType).getRawType();
                    }
                    return (Class<SOURCE>)resultType;

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else if (genericSuperClass instanceof Class<?>) {
                currentClass = currentClass.getSuperclass();
            } else {
                throw new RuntimeException("Could not instantiate response type");
            }
        }
    }


    private Class<TARGET> getTargetClass(){
        Class<? extends Object> currentClass = getClass();
        while (true) {
            Type genericSuperClass = currentClass.getGenericSuperclass();
            if (genericSuperClass instanceof ParameterizedType) {
                try {

                    Type resultType = ((ParameterizedType) genericSuperClass).getActualTypeArguments()[1];
                    if(resultType instanceof ParameterizedType){
                        return  (Class<TARGET>)((ParameterizedType)resultType).getRawType();
                    }
                    return (Class<TARGET>)resultType;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else if (genericSuperClass instanceof Class<?>) {
                currentClass = currentClass.getSuperclass();
            } else {
                throw new RuntimeException("Could not instantiate response type");
            }
        }
    }

}
