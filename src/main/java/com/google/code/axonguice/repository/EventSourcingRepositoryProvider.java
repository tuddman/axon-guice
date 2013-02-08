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

import org.axonframework.domain.AggregateRoot;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.SnapshotterTrigger;
import org.axonframework.repository.Repository;

/**
 * EventSourcingRepositoryProvider - TODO: description
 *
 * @author Alexey Krylov (lexx)
 * @since 08.02.13
 */
public class EventSourcingRepositoryProvider extends RepositoryProvider {

    /*===========================================[ CONSTRUCTORS ]=================*/

    public EventSourcingRepositoryProvider(Class<? extends AggregateRoot> aggregateRootClass) {
        super(aggregateRootClass);
    }

    /*===========================================[ INTERFACE METHODS ]============*/

    @Override
    public Repository get() {
        EventSourcingRepository repository = new EventSourcingRepository(aggregateFactoryProvider.get());
        repository.setEventStore(eventStore);
        repository.setEventBus(eventBus);

        if (snapshotterTriggerProvider != null) {
            SnapshotterTrigger snapshotterTrigger = snapshotterTriggerProvider.get();
            if (snapshotterTrigger != null) {
                repository.setSnapshotterTrigger(snapshotterTrigger);
            }
        }
        return repository;
    }
}
