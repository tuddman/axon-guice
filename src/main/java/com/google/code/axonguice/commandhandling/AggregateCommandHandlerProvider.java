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


import com.google.inject.Injector;
import com.google.inject.Provider;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.domain.AggregateRoot;

import javax.inject.Inject;

/**
 * CommandHandlerProvider - TODO: description
 *
 * @author Alexey Krylov (lexx)
 * @since 07.02.13
 */
public class AggregateCommandHandlerProvider implements Provider {

    /*===========================================[ INSTANCE VARIABLES ]===========*/

    @Inject
    private Injector injector;

    @Inject
    private CommandBus commandBus;

    private Class<? extends AggregateRoot> aggregateRootClass;

    public AggregateCommandHandlerProvider(Class<? extends AggregateRoot> aggregateRootClass) {
        this.aggregateRootClass = aggregateRootClass;
    }

    /*===========================================[ CONSTRUCTORS ]=================*/


    /*===========================================[ INTERFACE METHODS ]============*/

    @Override
    public Object get() {
        return aggregateRootClass;
        /*try {
            //TODO get repository for this aggregate
            ///injector.getInstance()
            AggregateAnnotationCommandHandler.subscribe(aggregateRootClass, commandBus, comm);
            return aggregateRootClass;
        } catch (Exception e) {
            throw new ProvisionException(String.format("Unable to instantiate CommandHandler class: [%s]", handlerClass), e);
        }*/
    }
}
