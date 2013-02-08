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
import java.util.Collection;
import java.util.HashSet;

import static com.google.common.collect.Collections2.filter;

/**
 * AbstractGroupingModule - TODO: description
 *
 * @author Alexey Krylov (lexx)
 * @since 08.02.13
 */
public abstract class AbstractClassesGroupingModule extends AbstractModule {

    /*===========================================[ INSTANCE VARIABLES ]===========*/

    protected Logger logger;

    /*===========================================[ CONSTRUCTORS ]=================*/

    protected Collection<ClassesGroup> classesGroups;

    /*===========================================[ INTERFACE METHODS ]============*/

    protected AbstractClassesGroupingModule(Collection<ClassesGroup> classesGroups) {
        logger = LoggerFactory.getLogger(getClass());
        this.classesGroups = new ArrayList<ClassesGroup>(classesGroups);
    }

    protected AbstractClassesGroupingModule(String... scanPackages) {
        logger = LoggerFactory.getLogger(getClass());
        classesGroups = new ArrayList<ClassesGroup>();

        for (String scanPackage : scanPackages) {
            classesGroups.add(new ClassesGroup(scanPackage));
        }
    }

    /*===========================================[ CLASS METHODS ]================*/

    protected <T> Collection<Class<? extends T>> filterClasses(final ClassesGroup classesGroup, Collection<Class<? extends T>> classesCollection) {
        return filter(classesCollection, new Predicate<Class<?>>() {
            @Override
            public boolean apply(Class<?> input) {
                return !input.isInterface() && !Modifier.isAbstract(input.getModifiers()) && classesGroup.matches(input);
            }
        });
    }

    protected Reflections createReflections(Iterable<String> packagesToScan) {
        Collection<URL> scanUrls = new HashSet<URL>();
        for (String packageName : packagesToScan) {
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
