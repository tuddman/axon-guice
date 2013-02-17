/*
 * Copyright (C) 2013 the original author or authors.
 * See the notice.md file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.code.axonguice.util;

import com.google.common.base.Preconditions;
import org.axonframework.domain.AggregateRoot;
import org.reflections.Reflections;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Integration module reflections helper utility.
 *
 * @author Alexey Krylov
 * @since 08.02.13
 */
public class ReflectionsHelper {

    /*===========================================[ CONSTRUCTORS ]=================*/

    private ReflectionsHelper() {
    }

    /*===========================================[ CLASS METHODS ]================*/

    /**
     * Finds all subclasses of specified Aggregate Root class with specified {@link Reflections} instance.
     *
     * @param reflections preconfigured Reflections instance
     * @param rootClass   root class of required Aggregate Roots
     * @param <T>         returning collection parametrization parameter
     *
     * @return subclasses of specified Aggregate Root class or empty collection
     */
    public static <T extends AggregateRoot> Collection<Class<? extends T>> findAggregateRoots(Reflections reflections, Class<T> rootClass) {
        Preconditions.checkArgument(reflections != null);
        Preconditions.checkArgument(rootClass != null);

        Collection<Class<? extends T>> result = new ArrayList<>();
        Reflections axonReflections = new Reflections("org.axonframework");
        Iterable<Class<? extends T>> genericAggregateRootSubclasses = axonReflections.getSubTypesOf(rootClass);
        for (Class<? extends T> aggregateRootSubclass : genericAggregateRootSubclasses) {
            result.addAll(reflections.getSubTypesOf(aggregateRootSubclass));
        }
        return result;
    }

    /**
     * Finds and returns class of first parametrization parameter.
     *
     * @param aClass generic class
     *
     * @return parameter class or {@code null} if parameter can't be found
     *
     * @throws IllegalArgumentException if specified {@code aClass} is null
     */
    public static Class getFirstTypeParameterClass(Class aClass) {
        Preconditions.checkArgument(aClass != null);
        return getTypeParameterClass(aClass, 0);
    }

    /**
     * Finds and returns class of N parametrization parameter.
     *
     * @param aClass         generic class
     * @param parameterIndex parameter index
     *
     * @return parameter class or {@code null} if parameter can't be found
     *
     * @throws IllegalArgumentException if specified {@code aClass} is null or {@code parameterIndex} &lt; 0
     */
    public static Class getTypeParameterClass(Class aClass, int parameterIndex) {
        Preconditions.checkArgument(aClass != null);
        Preconditions.checkArgument(parameterIndex >= 0);

        List<Type> types = new ArrayList<>();

        // check interfaces
        getGenericInterfacesActualTypes(types, aClass);

        Class result = findAppropriateType(types, parameterIndex);
        if (result == null) {
            types.clear();
            // check superclasses
            getGenericSuperclassActualTypes(types, aClass);
        }
        return findAppropriateType(types, parameterIndex);
    }

    public static void getGenericInterfacesActualTypes(Collection<Type> types, Class aClass) {
        if (aClass != null && types != null) {
            Type[] interfaces = aClass.getGenericInterfaces();
            for (Type anInterface : interfaces) {
                if (anInterface instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) anInterface;
                    Type[] actualTypes = parameterizedType.getActualTypeArguments();
                    types.addAll(Arrays.asList(actualTypes));
                } else if (anInterface instanceof Class) {
                    Class typeClass = (Class) anInterface;
                    getGenericInterfacesActualTypes(types, typeClass);
                }
            }
        }
    }

    public static void getGenericSuperclassActualTypes(Collection<Type> types, Class aClass) {
        if (aClass != null && types != null) {
            Type superclass = aClass.getGenericSuperclass();
            if (superclass instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) superclass;
                Type[] interfaces = parameterizedType.getActualTypeArguments();
                types.addAll(Arrays.asList(interfaces));
            } else if (superclass instanceof Class) {
                Class sClass = (Class) superclass;
                getGenericInterfacesActualTypes(types, sClass);
                getGenericSuperclassActualTypes(types, aClass.getSuperclass());
            }
        }
    }

    private static Class findAppropriateType(List<Type> types, int parameterIndex) {
        for (int i = 0; i < types.size(); i++) {
            if (i == parameterIndex) {
                Type type = types.get(i);
                if (type instanceof Class) {
                    return (Class) type;
                }
            }
        }
        return null;
    }

    /**
     * Finds and returns class of specified generic parametrization class.
     *
     * @param aClass                generic class
     * @param genericParameterClass generic parametrization class
     *
     * @return parameter class or {@code null} if parameter can't be found
     *
     * @throws IllegalArgumentException if specified {@code aClass}  or {@code genericParameterClass} is null
     */
    public static <T> Class<T> getTypeParameterClass(Class aClass, Class<T> genericParameterClass) {
        Preconditions.checkArgument(aClass != null);
        Preconditions.checkArgument(genericParameterClass != null);

        Collection<Type> types = new ArrayList<>();

        // check interfaces
        getGenericInterfacesActualTypes(types, aClass);

        Class result = findAppropriateType(types, genericParameterClass);
        if (result == null) {
            types.clear();
            // check superclasses
            getGenericSuperclassActualTypes(types, aClass);
        }
        return findAppropriateType(types, genericParameterClass);
    }

    private static <T> Class<T> findAppropriateType(Iterable<Type> types, Class<T> genericParameterClass) {
        for (Type type : types) {
            if (type instanceof Class && genericParameterClass.isAssignableFrom((Class<?>) type)) {
                return (Class) type;
            }
        }
        return null;
    }
}