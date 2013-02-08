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


import com.google.inject.*;
import com.google.inject.util.Types;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.annotation.AggregateAnnotationCommandHandler;
import org.axonframework.domain.AggregateRoot;
import org.axonframework.repository.Repository;

import javax.inject.Inject;

/**
 * CommandHandlerProvider - TODO: description
 *
 * @author Alexey Krylov (lexx)
 * @since 07.02.13
 */
public class AggregateCommandHandlerProvider implements Provider {

    /*===========================================[ INSTANCE VARIABLES ]===========*/

    protected Injector injector;
    protected CommandBus commandBus;

    private Class<? extends AggregateRoot> aggregateRootClass;

    /*===========================================[ CONSTRUCTORS ]=================*/

    public AggregateCommandHandlerProvider(Class<? extends AggregateRoot> aggregateRootClass) {
        this.aggregateRootClass = aggregateRootClass;
    }

    @Inject
    void init(Injector injector, CommandBus commandBus) {
        this.injector = injector;
        this.commandBus = commandBus;
    }

    /*===========================================[ INTERFACE METHODS ]============*/

    @Override
    public Object get() {
        try {
            Repository repository = (Repository) injector.getInstance(Key.get(TypeLiteral.get(Types.newParameterizedType(Repository.class, aggregateRootClass))));
            AggregateAnnotationCommandHandler.subscribe(aggregateRootClass, repository, commandBus);
            return aggregateRootClass;
        } catch (Exception e) {
            throw new ProvisionException(String.format("Unable to instantiate AggregateCommandHandler class for: [%s]", aggregateRootClass), e);
        }
    }
}
