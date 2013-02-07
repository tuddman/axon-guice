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

package com.google.code.axonguice.eventhandling;

import com.google.code.axonguice.eventhandling.annotation.EventHandlerComponent;
import com.google.code.axonguice.eventhandling.scheduling.SimpleEventSchedulerProvider;
import com.google.common.base.Predicate;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Scopes;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.scheduling.EventScheduler;
import org.reflections.Reflections;
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
 * Event Processing elements bind module.
 *
 * @author Alexey Krylov
 * @since 06.02.13
 */
public class EventHandlingModule extends AbstractModule {

    /*===========================================[ STATIC VARIABLES ]=============*/

    private static final Logger logger = LoggerFactory.getLogger(EventHandlingModule.class);

    /*===========================================[ INSTANCE VARIABLES ]===========*/

    private Collection<EventHandlersGroup> eventHandlersGroups;

    /*===========================================[ CONSTRUCTORS ]=================*/

    public EventHandlingModule(Collection<EventHandlersGroup> eventHandlersGroups) {
        this.eventHandlersGroups = new ArrayList<EventHandlersGroup>(eventHandlersGroups);
    }

    public EventHandlingModule(String... eventHandlersScanPackage) {
        eventHandlersGroups = new ArrayList<EventHandlersGroup>();

        for (String scanPackage : eventHandlersScanPackage) {
            eventHandlersGroups.add(new EventHandlersGroup(scanPackage));
        }
    }

    /*===========================================[ INTERFACE METHODS ]============*/

    @Override
    protected void configure() {
        bindEventBus();
        bindEventHandlers();
        bindEventScheduler();
    }

    protected void bindEventBus() {
        bind(EventBus.class).to(SimpleEventBus.class).in(Scopes.SINGLETON);
    }

    protected void bindEventHandlers() {
        for (EventHandlersGroup group : eventHandlersGroups) {
            Collection<String> packagesToScan = group.getCommandHandlersPackages();
            logger.info(String.format("Scanning %s for Event Handlers", packagesToScan));

            Reflections reflections = createReflections(packagesToScan);

            // Extraction of instantiable @EventHandler implementations
            Iterable<Class<?>> validHandlerClasses = filterHandlers(group, reflections.getTypesAnnotatedWith(EventHandlerComponent.class));

            for (Class<?> handlerClass : validHandlerClasses) {
                logger.info(String.format("Found CommandHandler: [%s]", handlerClass.getName()));
                Provider commandHandlerProvider = new EventHandlerProvider(handlerClass);
                requestInjection(commandHandlerProvider);
                bind(handlerClass).toProvider(commandHandlerProvider).in(Scopes.SINGLETON);
            }
        }
    }

    protected <T> Collection<Class<? extends T>> filterHandlers(final EventHandlersGroup group, Collection<Class<? extends T>> allHandlers) {
        return filter(allHandlers, new Predicate<Class<?>>() {
            @Override
            public boolean apply(Class<?> input) {
                return !input.isInterface() && !Modifier.isAbstract(input.getModifiers()) && group.matches(input);
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
        configurationBuilder.setScanners(new TypeAnnotationsScanner(), new SubTypesScanner());
        return new Reflections(configurationBuilder);
    }

    protected void bindEventScheduler() {
        bind(EventScheduler.class).toProvider(SimpleEventSchedulerProvider.class).in(Scopes.SINGLETON);
    }
}