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

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Types;
import org.axonframework.domain.AggregateRoot;
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.eventsourcing.SnapshotterTrigger;
import org.axonframework.eventstore.SnapshotEventStore;

import javax.inject.Inject;

/**
 * SnapshotterProvider - TODO: description
 *
 * @author Alexey Krylov
 * @since 08.02.13
 */
public abstract class SnapshotterTriggerProvider implements Provider<SnapshotterTrigger> {

    /*===========================================[ INSTANCE VARIABLES ]===========*/

    protected SnapshotEventStore eventStore;
    protected Class<? extends AggregateRoot> aggregateRootClass;
    protected Provider<AggregateFactory> aggregateFactoryProvider;

    /*===========================================[ CONSTRUCTORS ]=================*/

    protected SnapshotterTriggerProvider(Class<? extends AggregateRoot> aggregateRootClass) {
        this.aggregateRootClass = aggregateRootClass;
    }

    @Inject
    void init(Injector injector, SnapshotEventStore eventStore) {
        this.eventStore = eventStore;
        aggregateFactoryProvider = (Provider<AggregateFactory>) injector.getProvider(Key.get(TypeLiteral.get(Types.newParameterizedType(AggregateFactory.class, aggregateRootClass))));
    }
}