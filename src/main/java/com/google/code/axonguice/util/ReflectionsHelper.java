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

import org.axonframework.domain.AggregateRoot;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Collection;

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
        Collection<Class<? extends T>> result = new ArrayList<>();
        Reflections axonReflections = new Reflections("org.axonframework");
        Iterable<Class<? extends T>> genericAggregateRootSubclasses = axonReflections.getSubTypesOf(rootClass);
        for (Class<? extends T> aggregateRootSubclass : genericAggregateRootSubclasses) {
            result.addAll(reflections.getSubTypesOf(aggregateRootSubclass));
        }
        return result;
    }
}