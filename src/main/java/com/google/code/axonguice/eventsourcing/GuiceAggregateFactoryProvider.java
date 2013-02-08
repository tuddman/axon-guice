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

package com.google.code.axonguice.eventsourcing;

import com.google.inject.Injector;
import com.google.inject.Provider;
import org.axonframework.eventsourcing.EventSourcedAggregateRoot;

import javax.inject.Inject;

/**
 * GuiceAggregateFactory - TODO: description
 * //TODO injection into Aggregates support
 *
 * @author Alexey Krylov (lexx)
 * @since 08.02.13
 */
public class GuiceAggregateFactoryProvider implements Provider<GuiceAggregateFactory> {

    /*===========================================[ INSTANCE VARIABLES ]===========*/

    @Inject
    protected Injector injector;
    protected Class<? extends EventSourcedAggregateRoot> aggregateRootClass;

    /*===========================================[ CONSTRUCTORS ]=================*/

    public GuiceAggregateFactoryProvider(Class<? extends EventSourcedAggregateRoot> aggregateRootClass) {
        this.aggregateRootClass = aggregateRootClass;
    }

    /*===========================================[ CLASS METHODS ]================*/

    @Override
    public GuiceAggregateFactory get() {
        GuiceAggregateFactory aggregateFactory = new GuiceAggregateFactory(aggregateRootClass);
        injector.injectMembers(aggregateFactory);
        return aggregateFactory;
    }
}