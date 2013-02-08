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
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.eventsourcing.AggregateSnapshotter;
import org.axonframework.eventsourcing.EventCountSnapshotterTrigger;
import org.axonframework.eventsourcing.SnapshotterTrigger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * EventCountSnapshotterTriggerProviderImpl - TODO: description
 *
 * @author Alexey Krylov (lexx)
 * @since 08.02.13
 */
public class SimpleEventCountSnapshotterTriggerProvider extends SnapshotterTriggerProvider {

	/*===========================================[ CONSTRUCTORS ]=================*/

    protected SimpleEventCountSnapshotterTriggerProvider(Class<? extends AggregateRoot> aggregateRootClass) {
        super(aggregateRootClass);
    }

	/*===========================================[ INTERFACE METHODS ]============*/

    @Override
    public SnapshotterTrigger get() {
        EventCountSnapshotterTrigger snapshotterTrigger = new EventCountSnapshotterTrigger();

        List<AggregateFactory<?>> genericAggregateFactories = new ArrayList<AggregateFactory<?>>();

        Map<String, AggregateFactory> aggregateFactoryMap = aggregateFactoriesProvider.get();
        AggregateFactory aggregateFactory1 = aggregateFactoryMap.get(aggregateRootClass.getName());

        // Searching for appropriate AggregateFactory
        //AggregateFactory aggregateFactory = (AggregateFactory) injector.getInstance(Key.get(TypeLiteral.get(Types.newParameterizedType(AggregateFactory.class, aggregateRootClass))));
        genericAggregateFactories.add(aggregateFactory1);

        AggregateSnapshotter snapshotter = new AggregateSnapshotter();
        snapshotter.setEventStore(eventStore);
        snapshotter.setAggregateFactories(genericAggregateFactories);


        snapshotterTrigger.setSnapshotter(snapshotter);
        return snapshotterTrigger;
    }
}
