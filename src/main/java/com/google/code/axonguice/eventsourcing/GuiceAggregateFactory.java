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
import org.axonframework.eventsourcing.EventSourcedAggregateRoot;
import org.axonframework.eventsourcing.GenericAggregateFactory;

import javax.annotation.Resource;

/**
 * GuiceAggregateFactory - TODO: description
 * //TODO injection into Aggregates support
 *
 * @author Alexey Krylov (lexx)
 * @since 08.02.13
 */
public class GuiceAggregateFactory extends GenericAggregateFactory {

    /*===========================================[ INSTANCE VARIABLES ]===========*/

    private Injector injector;

    /*===========================================[ CONSTRUCTORS ]=================*/

    public GuiceAggregateFactory(Class<? extends EventSourcedAggregateRoot> aggregateRootClass) {
        super(aggregateRootClass);
    }

    /*===========================================[ CLASS METHODS ]================*/

    @Override
    protected EventSourcedAggregateRoot postProcessInstance(EventSourcedAggregateRoot aggregate) {
        injector.injectMembers(aggregate);
        return aggregate;
    }

    /*===========================================[ GETTER/SETTER ]================*/

    @Resource
    public void setInjector(Injector injector) {
        this.injector = injector;
    }
}