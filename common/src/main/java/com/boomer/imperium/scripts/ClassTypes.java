package com.boomer.imperium.scripts;

import com.boomer.imperium.scripts.mirrors.*;
import com.google.common.collect.ImmutableSet;

import java.lang.reflect.Field;
import java.util.Set;

public final class ClassTypes {

    private static final String COMPONENT_CLASS_NAME = "com.mygdx.game.game.scripts.core.Component";

    private static final String VECTOR2 = "com.badlogic.gdx.math.Vector2";
    private static final String VECTOR3 = "com.badlogic.gdx.math.Vector3";
    private static final String RECTANGLE = "com.badlogic.gdx.math.Rectangle";
    private static final String CIRCLE = "com.badlogic.gdx.math.Circle";

    public static Set<String> LIBGDX_PRIMITIVES = ImmutableSet.of(VECTOR2,VECTOR3,RECTANGLE,CIRCLE);

    public static Set<Class> SUPPORTED_PRIMITIVES = ImmutableSet.of(
            int.class,
            Integer.class,
            float.class,
            Float.class,
            double.class,
            Double.class,
            long.class,
            Long.class,
            char.class,
            Character.class,
            boolean.class,
            Boolean.class,
            String.class);

    public static Attribute initializeAttributeForField(Field field){
        Attribute attribute = new Attribute(field.getName());
        Class<?> type = field.getType();
        if(type.equals(int.class) || type.equals(Integer.class)){
            attribute.setIntegerValue(0);
            return attribute;
        }
        if(type.equals(float.class) || type.equals(Float.class)){
            attribute.setFloatValue(0f);
            return attribute;
        }
        if(type.equals(double.class) || type.equals(Double.class)){
            attribute.setDoubleValue(0D);
            return attribute;
        }
        if(type.equals(long.class) || type.equals(Long.class)){
            attribute.setLongValue(0L);
            return attribute;
        }
        if(type.equals(boolean.class) || type.equals(Boolean.class)){
            attribute.setBooleanValue(false);
            return attribute;
        }
        if(type.equals(String.class)){
            attribute.setStringValue("");
            return attribute;
        }
        if(type.getCanonicalName().equals(VECTOR2)){
            attribute.setVector2Value(new Vector2Mirror(0f,0f));
            return attribute;
        }
        if(type.getCanonicalName().equals(VECTOR3)){
            attribute.setVector3Value(new Vector3Mirror(0f,0f,0f));
            return attribute;
        }
        if(type.getCanonicalName().equals(RECTANGLE)){
            attribute.setRectValue(new RectMirror(0f,0f,0f,0f));
            return attribute;
        }
        if(type.getCanonicalName().equals(CIRCLE)){
            attribute.setCircleValue(new CircleMirror(0f,0f,0f));
            return attribute;
        }
        return attribute;
    }

    public static boolean shouldExpand(Attribute attribute){
        if(attribute.getAttributeType().equals(AttributeType.VECTOR2)
                || attribute.getAttributeType().equals(AttributeType.VECTOR3)
                || attribute.getAttributeType().equals(AttributeType.RECT)
                || attribute.getAttributeType().equals(AttributeType.CIRCLE)) {
            return true;
        }
        return false;
    }

    public static boolean isComponent(Class clazz){
        if(clazz.getSuperclass()==null){
            return false;
        }else if(clazz.getSuperclass().getCanonicalName().contains(COMPONENT_CLASS_NAME)){
            return true;
        }
        return isComponent(clazz.getSuperclass());
    }

    private ClassTypes(){}
}
