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

package com.google.code.axonguice.grouping;

import com.google.common.base.Predicate;
import com.google.inject.AbstractModule;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import static com.google.common.collect.Collections2.filter;

/**
 * Convenient base class for all Modules which can be configured with a set of classes.
 *
 * @author Alexey Krylov
 * @since 08.02.13
 */
public abstract class AbstractClassesGroupingModule<T> extends AbstractModule {

    /*===========================================[ INSTANCE VARIABLES ]===========*/

    protected Logger logger;

    /*===========================================[ CONSTRUCTORS ]=================*/

    protected Collection<ClassesSearchGroup> classesSearchGroups;
    protected Collection<Class<? extends T>> classesGroup;

    /*===========================================[ INTERFACE METHODS ]============*/

    protected AbstractClassesGroupingModule() {
        logger = LoggerFactory.getLogger(getClass());
        classesSearchGroups = new ArrayList<>();
        classesGroup = new ArrayList<>();
    }

    @SafeVarargs
    protected AbstractClassesGroupingModule(Class<? extends T>... classes) {
        this();
        if (classes != null && classes.length > 0) {
            classesGroup = Arrays.asList(classes);
        }
    }

    protected AbstractClassesGroupingModule(Collection<ClassesSearchGroup> classesSearchGroups) {
        this();
        if (classesSearchGroups != null && !classesSearchGroups.isEmpty()) {
            this.classesSearchGroups.addAll(classesSearchGroups);
        }
    }

    protected AbstractClassesGroupingModule(String... scanPackages) {
        this();
        if (scanPackages != null && scanPackages.length > 0) {
            for (String scanPackage : scanPackages) {
                classesSearchGroups.add(new ClassesSearchGroup(scanPackage));
            }
        }
    }

    /*===========================================[ CLASS METHODS ]================*/

    protected <T> Collection<Class<? extends T>> filterSearchResult(Collection<Class<? extends T>> searchResult, final ClassesSearchGroup searchGroup) {
        return filter(searchResult, new Predicate<Class<?>>() {
            @Override
            public boolean apply(Class<?> input) {
                return !input.isInterface() && !Modifier.isAbstract(input.getModifiers()) && searchGroup.matches(input);
            }
        });
    }

    protected Reflections createReflections(Iterable<String> searchablePackages) {
        Collection<URL> scanUrls = new HashSet<>();
        for (String packageName : searchablePackages) {
            scanUrls.addAll(ClasspathHelper.forPackage(packageName));
        }

        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setUrls(scanUrls);
        configurationBuilder.setScanners(
                new TypeAnnotationsScanner(),
                new SubTypesScanner(),
                new MethodAnnotationsScanner(),
                new FieldAnnotationsScanner());
        return new Reflections(configurationBuilder);
    }
}