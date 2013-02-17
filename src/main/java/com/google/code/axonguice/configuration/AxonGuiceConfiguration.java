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

package com.google.code.axonguice.configuration;

/**
 * AxonGuiceConfiguration - TODO: description
 * //todo
 * @author Alexey Krylov (lexx)
 * @since 17.02.13
 */
public interface AxonGuiceConfiguration {

/*===========================================[ STATIC VARIABLES ]=============*/
/*===========================================[ INSTANCE VARIABLES ]===========*/
/*===========================================[ CONSTRUCTORS ]=================*/
/*===========================================[ CLASS METHODS ]================*/
/*
@Override
protected DomainModule createDomainModule() {
    return new DomainModule(Order.class);
}
*/
/*

    String[] getRepositoriesScanPackages();

    Collection<ClassesSearchGroup> getRepositoriesClassesSearchGroup();


    Collection<Class<? extends EventSourcedAggregateRoot>> getRepositoriesClasses();



    String[] getRepositoriesScanPackages();

    Collection<ClassesSearchGroup> getRepositoriesClassesSearchGroup();


    Collection<Class<? extends EventSourcedAggregateRoot>> getRepositoriesClasses();

    @Override
    protected AggregateRootCommandHandlingModule createAggregateRootCommandHandlingModule() {
        return new AggregateRootCommandHandlingModule(Order.class);
    }

    @Override
    protected CommandHandlingModule createCommandHandlingModule() {
        return new CommandHandlingModule(SimpleCommandHandler.class, OrderCommandHandler.class);
    }

    @Override
    protected EventHandlingModule createEventHandlingModule() {
        return new EventHandlingModule(SimpleEventHandler.class);
    }

    @Override
    protected SagaModule createSagaModule() {
        return new SagaModule(TestOrderSaga.class);
    }
*/


}
