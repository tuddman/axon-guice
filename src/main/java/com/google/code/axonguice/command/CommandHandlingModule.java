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

package com.google.code.axonguice.command;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.unitofwork.UnitOfWork;
import org.axonframework.unitofwork.UnitOfWorkFactory;

/**
 * Command Handling elements bind module.
 *
 * @author Alexey Krylov
 * @since 06.02.13
 */
public class CommandHandlingModule extends AbstractModule {

	/*===========================================[ INTERFACE METHODS ]============*/

    @Override
    protected void configure() {
        //TODO autoregistration of command handlers (? with aggregate roots?)
        bindCommandBus();
        bindCommandGateway();
        bindUnitOfWorkFactory();
        bindUnitOfWork();
    }

    protected void bindCommandBus() {
        bind(CommandBus.class).to(SimpleCommandBus.class).in(Scopes.SINGLETON);
    }

    protected void bindCommandGateway() {
        bind(CommandGateway.class).toProvider(CommandGatewayProvider.class).in(Scopes.SINGLETON);
    }

    protected void bindUnitOfWorkFactory() {
        bind(UnitOfWorkFactory.class).toProvider(UnitOfWorkFactoryProvider.class).in(Scopes.SINGLETON);
    }

    protected void bindUnitOfWork() {
        bind(UnitOfWork.class).toProvider(UnitOfWorkProvider.class);
    }
}
