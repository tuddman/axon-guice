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

package com.google.code.axonguice.domain.eventsourcing;

import com.google.inject.Injector;
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.eventsourcing.EventSourcedAggregateRoot;
import org.axonframework.eventsourcing.GenericAggregateFactory;

import javax.inject.Inject;

/**
 * Provides {@link AggregateFactory} with injection support.
 *
 * @author Alexey Krylov
 * @see GuiceAggregateFactoryProvider
 * @since 08.02.13
 */
public class GuiceGenericAggregateFactory extends GenericAggregateFactory {

    /*===========================================[ INSTANCE VARIABLES ]===========*/

    private Injector injector;

    /*===========================================[ CONSTRUCTORS ]=================*/

    public GuiceGenericAggregateFactory(Class<? extends EventSourcedAggregateRoot> aggregateRootClass) {
        super(aggregateRootClass);
    }

    /*===========================================[ CLASS METHODS ]================*/

    @Override
    protected EventSourcedAggregateRoot postProcessInstance(EventSourcedAggregateRoot aggregate) {
        injector.injectMembers(aggregate);
        return aggregate;
    }

    /*===========================================[ GETTER/SETTER ]================*/

    @Inject
    public void setInjector(Injector injector) {
        this.injector = injector;
    }
}