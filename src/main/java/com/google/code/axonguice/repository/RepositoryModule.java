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
import com.google.code.axonguice.grouping.ClassesGroup;
import com.google.inject.Key;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Types;
import org.axonframework.domain.AggregateRoot;
import org.axonframework.eventstore.EventStore;
import org.axonframework.repository.Repository;
import org.reflections.Reflections;

import java.util.Collection;

/**
 * RepositoryModule - TODO: description
 *
 * @author Alexey Krylov (lexx)
 * @since 06.02.13
 */
public class RepositoryModule extends AbstractClassesGroupingModule {

    /*===========================================[ CONSTRUCTORS ]=================*/

    public RepositoryModule(Collection<ClassesGroup> aggregatesRepositoriesClassesGroups) {
        super(aggregatesRepositoriesClassesGroups);
    }

    public RepositoryModule(String... aggregatesRepositoriesScanPackages) {
        super(aggregatesRepositoriesScanPackages);
    }

    /*===========================================[ INTERFACE METHODS ]============*/

    @Override
    protected void configure() {
        /*
        MongoEventStore eventStore = new MongoEventStore(mongoTemplate);
        // we need to configure the repository
        EventCountSnapshotterTrigger snapshotterTrigger = new EventCountSnapshotterTrigger();
        snapshotterTrigger.setTrigger(10);

        AggregateSnapshotter snapshotter = new AggregateSnapshotter();
        snapshotter.setEventStore(MongoEventStore);

        List<AggregateFactory<?>> genericAggregateFactories = new ArrayList<AggregateFactory<?>>();
        genericAggregateFactories.add(new GenericAggregateFactory(Order.class));

        snapshotter.setAggregateFactories(genericAggregateFactories);

        snapshotterTrigger.setSnapshotter(snapshotter);

        EventSourcingRepository<Order> repository = new EventSourcingRepository(Order.class);
        repository.setEventStore(eventStore);
        repository.setEventBus(eventBus);
        repository.setSnapshotterTrigger(snapshotterTrigger);
*/
        //  TODO       * - Repository -> SnapshotterTrigger
        // TODO find all aggregates and bind + option to override binding process with manual binder
        bindEventStore();
        bindRepositories();
        //EventSourcingRepository
    }

    protected void bindEventStore() {
        // upcasting is here
        bind(EventStore.class).toProvider(SimpleEventStoreProvider.class).in(Scopes.SINGLETON);
    }

    protected void bindRepositories() {
        for (ClassesGroup classesGroup : classesGroups) {
            Collection<String> packagesToScan = classesGroup.getPackages();
            logger.info(String.format("Scanning %s for Aggregate Roots", packagesToScan));

            Reflections reflections = createReflections(packagesToScan);

            Iterable<Class<? extends AggregateRoot>> validAggregateRoots = filterClasses(classesGroup, reflections.getSubTypesOf(AggregateRoot.class));

            for (Class<? extends AggregateRoot> aggregateRootClass : validAggregateRoots) {
                logger.info(String.format("Found AggregateRoot: [%s]", aggregateRootClass.getName()));
                bindSnapshotter(aggregateRootClass);
                bindRepository(aggregateRootClass);
            }
        }
    }

    protected void bindSnapshotter(Class<? extends AggregateRoot> aggregateRootClass) {
        RepositoryProvider repositoryProvider = new EventSourcingRepositoryProvider(aggregateRootClass);
        requestInjection(repositoryProvider);
        bind(Key.get(TypeLiteral.get(Types.newParameterizedType(Repository.class, aggregateRootClass)))).in(Scopes.SINGLETON);
    }

    protected void bindRepository(Class<? extends AggregateRoot> aggregateRootClass) {
        RepositoryProvider repositoryProvider = new EventSourcingRepositoryProvider(aggregateRootClass);
        requestInjection(repositoryProvider);
        bind(Key.get(TypeLiteral.get(Types.newParameterizedType(Repository.class, aggregateRootClass)))).in(Scopes.SINGLETON);
    }
}