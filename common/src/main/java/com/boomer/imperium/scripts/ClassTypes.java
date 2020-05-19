package com.boomer.imperium.scripts;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

public final class ClassTypes {

    public static Set<String> LIBGDX_PRIMITIVES = ImmutableSet.of(
            "com.badlogic.gdx.math.Vector2",
            "com.badlogic.gdx.math.Vector3",
            "com.badlogic.gdx.math.Rectangle",
            "com.badlogic.gdx.math.Circle");

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

    private ClassTypes(){}
}
