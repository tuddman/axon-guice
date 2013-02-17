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

import org.axonframework.domain.AggregateRoot;
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.eventsourcing.AggregateSnapshotter;
import org.axonframework.eventsourcing.EventCountSnapshotterTrigger;
import org.axonframework.eventsourcing.SnapshotterTrigger;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides {@link EventCountSnapshotterTrigger} as {@link SnapshotterTrigger} implementation.
 *
 * @author Alexey Krylov
 * @see EventSourcingRepositoryModule#bindSnapshotterTrigger(Class)
 * @since 08.02.13
 */
public class EventCountSnapshotterTriggerProvider extends SnapshotterTriggerProvider {

    /*===========================================[ CONSTRUCTORS ]=================*/

    protected EventCountSnapshotterTriggerProvider(Class<? extends AggregateRoot> aggregateRootClass) {
        super(aggregateRootClass);
    }

    /*===========================================[ INTERFACE METHODS ]============*/

    @Override
    public SnapshotterTrigger get() {
        EventCountSnapshotterTrigger snapshotterTrigger = new EventCountSnapshotterTrigger();

        List<AggregateFactory<?>> factories = new ArrayList<>();
        factories.add(aggregateFactoryProvider.get());

        AggregateSnapshotter snapshotter = new AggregateSnapshotter();
        snapshotter.setEventStore(eventStore);
        snapshotter.setAggregateFactories(factories);

        snapshotterTrigger.setSnapshotter(snapshotter);
        return snapshotterTrigger;
    }
}