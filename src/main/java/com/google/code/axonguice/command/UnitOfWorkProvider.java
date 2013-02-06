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

import org.axonframework.unitofwork.UnitOfWork;
import org.axonframework.unitofwork.UnitOfWorkFactory;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * @author Alexey Krylov (lexx)
 * @since 06.02.13
 */
public class UnitOfWorkProvider implements Provider<UnitOfWork> {

	/*===========================================[ INSTANCE VARIABLES ]===========*/

    @Inject
    private UnitOfWorkFactory factory;

	/*===========================================[ CONSTRUCTORS ]=================*/

    @Override
    public UnitOfWork get() {
        return factory.createUnitOfWork();
    }
}
