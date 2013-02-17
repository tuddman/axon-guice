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

package com.google.code.axonguice.domain;

import com.google.code.axonguice.domain.eventsourcing.GuiceAggregateFactoryProvider;
import com.google.code.axonguice.grouping.AbstractClassesGroupingModule;
import com.google.code.axonguice.grouping.ClassesSearchGroup;
import org.axonframework.domain.AggregateRoot;
import org.axonframework.eventsourcing.AggregateFactory;

import java.util.Collection;

/**
 * Binds aggregate factories for all provided/found Aggregate Roots. Aggregate factory required to allows injection
 * into aggregates.
 *
 * @author Alexey Krylov
 * @see AggregateFactory
 * @see GuiceAggregateFactoryProvider
 * @since 06.02.13
 */
public abstract class DomainModule<T extends AggregateRoot> extends AbstractClassesGroupingModule<T> {

	/*===========================================[ CONSTRUCTORS ]=================*/

    @SafeVarargs
    protected DomainModule(Class<? extends T>... aggregateRootsClasses) {
        super(aggregateRootsClasses);
    }

    protected DomainModule(String... aggregatesScanPackages) {
        super(aggregatesScanPackages);
    }

    protected DomainModule(Collection<ClassesSearchGroup> aggregatesClassesSearchGroups) {
        super(aggregatesClassesSearchGroups);
    }
}