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

package com.google.code.axonguice.commandhandling;

import com.google.code.axonguice.AxonGuiceModule;
import com.google.code.axonguice.grouping.AbstractClassesGroupingModule;
import com.google.code.axonguice.grouping.ClassesSearchGroup;
import com.google.code.axonguice.util.ReflectionsHelper;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Types;
import org.axonframework.domain.AggregateRoot;
import org.reflections.Reflections;

import java.util.Collection;

/**
 * Registers specified Aggregate Roots as CommandBus subscribers.
 *
 * @author Alexey Krylov
 * @see AxonGuiceModule#createAggregateRootCommandHandlingModule()
 * @since 15.02.13
 */
public class AggregateRootCommandHandlingModule extends AbstractClassesGroupingModule<AggregateRoot> {

	/*===========================================[ CONSTRUCTORS ]=================*/

    @SafeVarargs
    public AggregateRootCommandHandlingModule(Class<? extends AggregateRoot>... classes) {
        super(classes);
    }

    public AggregateRootCommandHandlingModule(Collection<ClassesSearchGroup> classesSearchGroups) {
        super(classesSearchGroups);
    }

    public AggregateRootCommandHandlingModule(String... scanPackages) {
        super(scanPackages);
    }

	/*===========================================[ INTERFACE METHODS ]============*/

    @Override
    protected void configure() {
        bindAggregateCommandHandlers();
    }

    protected void bindAggregateCommandHandlers() {
        logger.info("Binding Aggregate Roots Command Handlers");
        if (classesGroup.isEmpty()) {
            for (ClassesSearchGroup classesSearchGroup : classesSearchGroups) {
                Collection<String> packagesToScan = classesSearchGroup.getPackages();
                logger.info(String.format("Searching %s for Aggregate Roots Command Handlers", packagesToScan));

                Reflections reflections = createReflections(packagesToScan);
                bindAggregateCommandHandlers(filterSearchResult(ReflectionsHelper.findAggregateRoots(reflections, AggregateRoot.class), classesSearchGroup));
            }
        } else {
            bindAggregateCommandHandlers(classesGroup);
        }
    }

    protected void bindAggregateCommandHandlers(Iterable<Class<? extends AggregateRoot>> aggregateRoots) {
        // Aggregate Roots self-subscription as CommandHandlers
        for (Class<? extends AggregateRoot> aggregateRootClass : aggregateRoots) {
            logger.info(String.format("\tFound AggregateRoot: [%s]", aggregateRootClass.getName()));
            bindAggregateCommandHandler(aggregateRootClass);
        }
    }

    protected void bindAggregateCommandHandler(Class<? extends AggregateRoot> aggregateRootClass) {
        Provider commandHandlerProvider = new AggregateAnnotationCommandHandlerProvider(aggregateRootClass);
        requestInjection(commandHandlerProvider);
        bind(Key.get(TypeLiteral.get(Types.newParameterizedType(AggregateAnnotationCommandHandlerProvider.class, aggregateRootClass)))).toProvider(commandHandlerProvider).asEagerSingleton();
    }
}