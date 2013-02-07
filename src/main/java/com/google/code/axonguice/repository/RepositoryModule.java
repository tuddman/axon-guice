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

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import org.axonframework.eventstore.EventStore;

/**
 * RepositoryModule - TODO: description
 *
 * @author Alexey Krylov (lexx)
 * @since 06.02.13
 */
public class RepositoryModule extends AbstractModule {
/*===========================================[ STATIC VARIABLES ]=============*/
/*===========================================[ INSTANCE VARIABLES ]===========*/
/*===========================================[ CONSTRUCTORS ]=================*/
/*===========================================[ CLASS METHODS ]================*/

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
        //  TODO       * - Repository -> EventStore, EventBus, Snapshotter, SnapshotterTrigger
        // TODO find all aggregates and bind + option to override binding process with manual binder
        bindEventStore();
    }

    protected void bindEventStore() {
        bind(EventStore.class).toProvider(SimpleEventStoreProvider.class).in(Scopes.SINGLETON);
    }
}
