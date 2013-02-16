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

package com.google.code.axonguice.repository;

import com.google.code.axonguice.grouping.AbstractClassesGroupingModule;
import com.google.code.axonguice.grouping.ClassesSearchGroup;
import com.google.code.axonguice.util.ReflectionsHelper;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.google.inject.util.Types;
import org.axonframework.domain.AggregateRoot;
import org.axonframework.eventsourcing.EventSourcedAggregateRoot;
import org.axonframework.eventsourcing.SnapshotterTrigger;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.SnapshotEventStore;
import org.axonframework.repository.Repository;
import org.reflections.Reflections;

import java.util.Collection;

/**
 * RepositoryModule - TODO: description
 *
 * @author Alexey Krylov
 * @since 06.02.13
 */
public class RepositoryModule extends AbstractClassesGroupingModule<EventSourcedAggregateRoot> {

    /*===========================================[ CONSTRUCTORS ]=================*/

    @SafeVarargs
    public RepositoryModule(Class<? extends EventSourcedAggregateRoot>... classes) {
        super(classes);
    }

    public RepositoryModule(String... aggregatesRepositoriesScanPackages) {
        super(aggregatesRepositoriesScanPackages);
    }

    public RepositoryModule(Collection<ClassesSearchGroup> aggregatesRepositoriesSearchGroups) {
        super(aggregatesRepositoriesSearchGroups);
    }

    /*===========================================[ INTERFACE METHODS ]============*/

    @Override
    protected void configure() {
        bindEventStore();
        bindSnaphotEventStore();
        bindRepositories();
    }

    protected void bindEventStore() {
        // upcasting is here
        bind(EventStore.class).toProvider(SimpleEventStoreProvider.class).in(Scopes.SINGLETON);
    }

    protected void bindSnaphotEventStore() {
        bind(SnapshotEventStore.class).toProvider(SimpleEventStoreProvider.class).in(Scopes.SINGLETON);
    }

    protected void bindRepositories() {
        if (classesGroup.isEmpty()) {
            for (ClassesSearchGroup classesSearchGroup : classesSearchGroups) {
                Collection<String> packagesToScan = classesSearchGroup.getPackages();
                logger.info(String.format("Searching %s for EventSourced Aggregate Roots to bind Repositories", packagesToScan));

                Reflections reflections = createReflections(packagesToScan);
                bindRepositories(filterSearchResult(ReflectionsHelper.findAggregateRoots(reflections, EventSourcedAggregateRoot.class), classesSearchGroup));
            }
        } else {
            bindRepositories(classesGroup);
        }
    }

    protected void bindRepositories(Iterable<Class<? extends EventSourcedAggregateRoot>> aggregateRoots) {
        for (Class<? extends EventSourcedAggregateRoot> aggregateRootClass : aggregateRoots) {
            logger.info(String.format("\tFound: [%s]", aggregateRootClass.getName()));
            bindSnapshotterTrigger(aggregateRootClass);
            bindRepository(aggregateRootClass);
        }
    }

    protected void bindSnapshotterTrigger(Class<? extends AggregateRoot> aggregateRootClass) {
        Provider snapshotterTriggerProvider = new SimpleEventCountSnapshotterTriggerProvider(aggregateRootClass);
        requestInjection(snapshotterTriggerProvider);
        bind(Key.get(SnapshotterTrigger.class, Names.named(aggregateRootClass.getName()))).toProvider(snapshotterTriggerProvider).in(Scopes.SINGLETON);
        logger.info(String.format("\t\tSnapshotterTrigger set to: [%s]", snapshotterTriggerProvider.getClass().getName()));
    }

    protected void bindRepository(Class<? extends AggregateRoot> aggregateRootClass) {
        Provider repositoryProvider = new EventSourcingRepositoryProvider(aggregateRootClass);
        requestInjection(repositoryProvider);
        bind(Key.get(TypeLiteral.get(Types.newParameterizedType(Repository.class, aggregateRootClass)))).toProvider(repositoryProvider).in(Scopes.SINGLETON);
        logger.info(String.format("\t\tRepository set to: [%s]", repositoryProvider.getClass().getName()));
    }
}