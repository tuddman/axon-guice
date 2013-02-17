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

import com.google.code.axonguice.domain.eventsourcing.GuiceAggregateFactoryProvider;
import com.google.code.axonguice.grouping.AbstractClassesGroupingModule;
import com.google.code.axonguice.grouping.ClassesSearchGroup;
import com.google.code.axonguice.util.ReflectionsHelper;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Types;
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.eventsourcing.EventSourcedAggregateRoot;
import org.reflections.Reflections;

import java.util.Collection;

/**
 * Binds aggregate factories for all provided/found Aggregate Roots. Aggregate factory required to allows injection
 * into aggregates.
 *
 * @author Alexey Krylov
 * @see AggregateFactory
 * @see GuiceAggregateFactoryProvider
 * @since 06.02.13
 */
public class DomainModule extends AbstractClassesGroupingModule<EventSourcedAggregateRoot> {

	/*===========================================[ CONSTRUCTORS ]=================*/

    @SafeVarargs
    public DomainModule(Class<? extends EventSourcedAggregateRoot>... aggregateRootsClasses) {
        super(aggregateRootsClasses);
    }

    public DomainModule(String... aggregatesScanPackages) {
        super(aggregatesScanPackages);
    }

    public DomainModule(Collection<ClassesSearchGroup> aggregatesClassesSearchGroups) {
        super(aggregatesClassesSearchGroups);
    }

    /*===========================================[ INTERFACE METHODS ]============*/

    @Override
    protected void configure() {
        bindAggregatesFactories();
    }

    protected void bindAggregatesFactories() {
        logger.info("Binding Aggregates Factories");
        if (classesGroup.isEmpty()) {
            for (ClassesSearchGroup classesSearchGroup : classesSearchGroups) {
                Collection<String> packagesToScan = classesSearchGroup.getPackages();
                logger.info(String.format("Searching %s for EventSourced Aggregate Roots", packagesToScan));

                Reflections reflections = createReflections(packagesToScan);

                Iterable<Class<? extends EventSourcedAggregateRoot>> validAggregateRoots =
                        filterSearchResult(ReflectionsHelper.findAggregateRoots(reflections, EventSourcedAggregateRoot.class), classesSearchGroup);

                bindAggregateFactories(validAggregateRoots);
            }
        } else {
            bindAggregateFactories(classesGroup);
        }
    }

    protected void bindAggregateFactories(Iterable<Class<? extends EventSourcedAggregateRoot>> aggregateRoots) {
        for (Class<? extends EventSourcedAggregateRoot> aggregateRootClass : aggregateRoots) {
            logger.info(String.format("\tFound: [%s]", aggregateRootClass.getName()));
            bindAggregateFactory(aggregateRootClass);
        }
    }

    protected void bindAggregateFactory(Class<? extends EventSourcedAggregateRoot> aggregateRootClass) {
        Provider aggregateFactoryProvider = new GuiceAggregateFactoryProvider(aggregateRootClass);
        requestInjection(aggregateFactoryProvider);

        bind(Key.get(TypeLiteral.get(Types.newParameterizedType(AggregateFactory.class, aggregateRootClass)))).toProvider(aggregateFactoryProvider).in(Scopes.SINGLETON);
        logger.info(String.format("\t\tAggregateFactory set to: [%s]", aggregateFactoryProvider.getClass().getName()));
    }
}