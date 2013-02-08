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

package com.google.code.axonguice;

import com.google.code.axonguice.grouping.AbstractClassesGroupingModule;
import com.google.code.axonguice.commandhandling.CommandHandlingModule;
import com.google.code.axonguice.domain.DomainModule;
import com.google.code.axonguice.eventhandling.EventHandlingModule;
import com.google.code.axonguice.jsr250.Jsr250Module;
import com.google.code.axonguice.repository.RepositoryModule;
import com.google.code.axonguice.saga.SagaModule;
import com.google.inject.AbstractModule;

import java.util.Arrays;

public class AxonGuiceModule extends AbstractModule {

	/*===========================================[ INSTANCE VARIABLES ]===========*/

    protected String[] autoScanPackages;

	/*===========================================[ CONSTRUCTORS ]=================*/

    public AxonGuiceModule(String... autoScanPackages) {
        this.autoScanPackages = Arrays.copyOf(autoScanPackages, autoScanPackages.length);
    }

	/*===========================================[ INTERFACE METHODS ]============*/

    @Override
    protected void configure() {
        // support of @PostConstuct and @Resource
        install(createJsr250Module());
        install(createRepositoryModule());
        install(createCommandHandlingModule());
        install(createDomainModule());
        install(createEventHandlingModule());
        install(createSagaModule());
    }

    protected Jsr250Module createJsr250Module() {
        return new Jsr250Module();
    }

    protected RepositoryModule createRepositoryModule() {
        return new RepositoryModule(autoScanPackages);
    }

    protected AbstractClassesGroupingModule createCommandHandlingModule() {
        return new CommandHandlingModule(autoScanPackages);
    }

    protected DomainModule createDomainModule() {
        return new DomainModule();
    }

    protected EventHandlingModule createEventHandlingModule() {
        return new EventHandlingModule(autoScanPackages);
    }

    protected SagaModule createSagaModule() {
        return new SagaModule();
    }
}