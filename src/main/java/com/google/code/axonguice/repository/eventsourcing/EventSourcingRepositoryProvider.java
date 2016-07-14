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

import com.google.code.axonguice.repository.RepositoryProvider;
import com.google.inject.*;
import com.google.inject.name.Names;
import com.google.inject.util.Types;
import org.axonframework.domain.AggregateRoot;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.SnapshotterTrigger;
import org.axonframework.eventstore.EventStore;
import org.axonframework.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Provides {@link EventSourcingRepository} instance for specified Aggregate Root class.
 *
 * @author Alexey Krylov
 * @see EventSourcedRepositoryModule#bindRepository(Class)
 * @since 08.02.13
 */
public class EventSourcingRepositoryProvider extends RepositoryProvider {

	/*===========================================[ STATIC VARIABLES ]=============*/

    private static final Logger logger = LoggerFactory.getLogger(EventSourcingRepositoryProvider.class);

    /*===========================================[ INSTANCE VARIABLES ]===========*/

    protected EventBus eventBus;
    protected EventStore eventStore;
    protected Provider<SnapshotterTrigger> snapshotterTriggerProvider;
    protected Provider<AggregateFactory> aggregateFactoryProvider;

    /*===========================================[ CONSTRUCTORS ]=================*/

    public EventSourcingRepositoryProvider(Class<? extends AggregateRoot> aggregateRootClass) {
        super(aggregateRootClass);
    }

    @Inject
    void init(EventBus eventBus,
              EventStore eventStore,
              Injector injector) {
        this.eventBus = eventBus;
        this.eventStore = eventStore;

        // SnapshotterTrigger is not mandatory
        try {
            snapshotterTriggerProvider = injector.getProvider(Key.get(SnapshotterTrigger.class, Names.named(aggregateRootClassName)));
        } catch (ConfigurationException e) {
            String message = String.format("No SnapshotterTrigger found for: [%s]", aggregateRootClassName);
            logger.info(message);
            logger.debug(e.getMessage(), e);
        }

        aggregateFactoryProvider = (Provider<AggregateFactory>) injector.getProvider(Key.get(TypeLiteral.get(Types.newParameterizedType(AggregateFactory.class, aggregateRootClass))));
    }

    /*===========================================[ INTERFACE METHODS ]============*/

    @Override
    public Repository get() {
        EventSourcingRepository repository = new EventSourcingRepository(aggregateFactoryProvider.get(), eventStore);
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