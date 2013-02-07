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

import com.google.code.axonguice.commandhandling.annotation.CommandHandlerComponent;
import com.google.common.base.Predicate;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Scopes;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.unitofwork.UnitOfWork;
import org.axonframework.unitofwork.UnitOfWorkFactory;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static com.google.common.collect.Collections2.filter;

/**
 * Command Handling elements bind module.
 *
 * @author Alexey Krylov
 * @since 06.02.13
 */
public class CommandHandlingModule extends AbstractModule {

    /*===========================================[ STATIC VARIABLES ]=============*/

    private static final Logger logger = LoggerFactory.getLogger(CommandHandlingModule.class);

    private Collection<CommandHandlersGroup> commandHandlersGroups;

    /*===========================================[ INTERFACE METHODS ]============*/

    public CommandHandlingModule(Collection<CommandHandlersGroup> commandHandlersGroups) {
        this.commandHandlersGroups = new ArrayList<CommandHandlersGroup>(commandHandlersGroups);
    }

    public CommandHandlingModule(String... commandHandlersScanPackage) {
        commandHandlersGroups = new ArrayList<CommandHandlersGroup>();

        for (String scanPackage : commandHandlersScanPackage) {
            commandHandlersGroups.add(new CommandHandlersGroup(scanPackage));
        }
    }

    @Override
    protected void configure() {
        bindCommandBus();
        bindCommandGateway();
        bindUnitOfWorkFactory();
        bindUnitOfWork();
        bindCommandHandlers();
        //TODO command handlers in aggregate roots
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

    protected void bindCommandHandlers() {
        for (final CommandHandlersGroup group : commandHandlersGroups) {

            Collection<String> packagesToScan = group.getCommandHandlersPackages();
            logger.info(String.format("Scanning %s for Command Handlers", packagesToScan));


            Collection<URL> scanUrls = new HashSet<URL>();
            for (String packageName : packagesToScan) {
                scanUrls.addAll(ClasspathHelper.forPackage(packageName));
            }

            Collection<Class<?>> allHandlers = findCommandHandlers(scanUrls);

            // Extraction of instantiable @CommandHandler implementations
            Iterable<Class<?>> validHandlerClasses = filter(allHandlers, new Predicate<Class<?>>() {
                @Override
                public boolean apply(Class<?> input) {
                    return !input.isInterface() && !Modifier.isAbstract(input.getModifiers()) && group.matches(input);
                }
            });

            for (Class<?> handlerClass : validHandlerClasses) {
                logger.info(String.format("Found CommandHandler: [%s]", handlerClass.getName()));
                Provider commandHandlerProvider = new CommandHandlerProvider(handlerClass);
                requestInjection(commandHandlerProvider);
                bind(handlerClass).toProvider(commandHandlerProvider).in(Scopes.SINGLETON);
            }
        }
    }

    protected Collection<Class<?>> findCommandHandlers(Collection<URL> scanUrls) {
        Reflections reflections = createReflections(scanUrls);
        return reflections.getTypesAnnotatedWith(CommandHandlerComponent.class);
    }

    protected Reflections createReflections(Collection<URL> urls) {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setUrls(urls);
        configurationBuilder.setScanners(new TypeAnnotationsScanner(), new SubTypesScanner());
        return new Reflections(configurationBuilder);
    }
}