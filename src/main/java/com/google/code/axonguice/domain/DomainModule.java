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

package com.google.code.axonguice.domain;

import com.google.code.axonguice.eventsourcing.GuiceAggregateFactoryProvider;
import com.google.code.axonguice.grouping.AbstractClassesGroupingModule;
import com.google.code.axonguice.grouping.ClassesGroup;
import com.google.code.axonguice.util.ReflectionsHelper;
import com.google.inject.Provider;
import com.google.inject.Scopes;
import com.google.inject.multibindings.MapBinder;
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.eventsourcing.EventSourcedAggregateRoot;
import org.reflections.Reflections;

import java.util.Collection;

/**
 * EventBusModule - TODO: description
 *
 * @author Alexey Krylov (lexx)
 * @since 06.02.13
 */
public class DomainModule extends AbstractClassesGroupingModule {

    /*===========================================[ CONSTRUCTORS ]=================*/

    public DomainModule(String... aggregatesScanPackages) {
        super(aggregatesScanPackages);
    }

    public DomainModule(Collection<ClassesGroup> aggregatesClassesGroups) {
        super(aggregatesClassesGroups);
    }

    /*===========================================[ INTERFACE METHODS ]============*/

    @Override
    protected void configure() {
        bindAggregatesFactories();
    }

    protected void bindAggregatesFactories() {
        MapBinder<String, AggregateFactory> mapBinder = MapBinder.newMapBinder(binder(), String.class, AggregateFactory.class);

        for (ClassesGroup classesGroup : classesGroups) {
            Collection<String> packagesToScan = classesGroup.getPackages();
            logger.info(String.format("Searching %s for EventSourced Aggregate Roots", packagesToScan));

            Reflections reflections = createReflections(packagesToScan);

            Iterable<Class<? extends EventSourcedAggregateRoot>> validAggregateRoots = filterClasses(classesGroup, ReflectionsHelper.findAggregateClasses(reflections, EventSourcedAggregateRoot.class));

            for (Class<? extends EventSourcedAggregateRoot> aggregateRootClass : validAggregateRoots) {
                logger.info(String.format("\tFound: [%s]", aggregateRootClass.getName()));
                bindAggregateFactory(mapBinder, aggregateRootClass);
            }
        }
    }

    protected void bindAggregateFactory(MapBinder<String, AggregateFactory> mapBinder, Class<? extends EventSourcedAggregateRoot> aggregateRootClass) {
        Provider aggregateFactoryProvider = new GuiceAggregateFactoryProvider(aggregateRootClass);
        requestInjection(aggregateFactoryProvider);
        mapBinder.addBinding(aggregateRootClass.getName()).toProvider(aggregateFactoryProvider).in(Scopes.SINGLETON);
        logger.info(String.format("\t\tAggregateFactory set to: [%s]", aggregateFactoryProvider.getClass().getName()));
    }
}
