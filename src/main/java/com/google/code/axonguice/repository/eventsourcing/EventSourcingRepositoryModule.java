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

package com.google.code.axonguice.repository.eventsourcing;

import com.google.code.axonguice.grouping.ClassesSearchGroup;
import com.google.code.axonguice.repository.RepositoryModule;
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

import java.util.Collection;

/**
 * EventSourcingRepositoryModule - TODO: description
 *
 * @author Alexey Krylov (lexx)
 * @since 17.02.13
 */
public class EventSourcingRepositoryModule extends RepositoryModule {

	/*===========================================[ CONSTRUCTORS ]=================*/

    @SafeVarargs
    public EventSourcingRepositoryModule(Class<? extends EventSourcedAggregateRoot>... classes) {
        super(classes);
    }

    public EventSourcingRepositoryModule(String... aggregatesRepositoriesScanPackages) {
        super(aggregatesRepositoriesScanPackages);
    }

    public EventSourcingRepositoryModule(Collection<ClassesSearchGroup> aggregatesRepositoriesSearchGroups) {
        super(aggregatesRepositoriesSearchGroups);
    }

    /*===========================================[ CLASS METHODS ]================*/

    @Override
    protected void configure() {
        bindEventStore();
        bindSnaphotEventStore();
        super.configure();
    }

    protected void bindEventStore() {
        // upcasting is here
        bind(EventStore.class).toProvider(SimpleEventStoreProvider.class).in(Scopes.SINGLETON);
    }

    protected void bindSnaphotEventStore() {
        bind(SnapshotEventStore.class).toProvider(SimpleEventStoreProvider.class).in(Scopes.SINGLETON);
    }

	/*===========================================[ INTERFACE METHODS ]============*/

    @Override
    protected void bindRepository(Class<? extends AggregateRoot> aggregateRootClass) {
        bindSnapshotterTrigger(aggregateRootClass);
        Provider repositoryProvider = new EventSourcingRepositoryProvider(aggregateRootClass);
        requestInjection(repositoryProvider);
        bind(Key.get(TypeLiteral.get(Types.newParameterizedType(Repository.class, aggregateRootClass)))).toProvider(repositoryProvider).in(Scopes.SINGLETON);
        logger.info(String.format("\t\tRepository set to: [%s]", repositoryProvider.getClass().getName()));
    }

    protected void bindSnapshotterTrigger(Class<? extends AggregateRoot> aggregateRootClass) {
        Provider snapshotterTriggerProvider = new SimpleEventCountSnapshotterTriggerProvider(aggregateRootClass);
        requestInjection(snapshotterTriggerProvider);
        bind(Key.get(SnapshotterTrigger.class, Names.named(aggregateRootClass.getName()))).toProvider(snapshotterTriggerProvider).in(Scopes.SINGLETON);
        logger.info(String.format("\t\tSnapshotterTrigger set to: [%s]", snapshotterTriggerProvider.getClass().getName()));
    }
}