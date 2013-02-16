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

package com.google.code.axonguice.saga;

import com.google.inject.Provider;
import org.axonframework.saga.GenericSagaFactory;
import org.axonframework.saga.ResourceInjector;
import org.axonframework.saga.SagaFactory;

import javax.inject.Inject;

/**
 * GuiceSagaFactory - TODO: description
 *
 * @author Alexey Krylov
 * @since 14.02.13
 */
public class GenericSagaFactoryProvider implements Provider<SagaFactory> {

	/*===========================================[ INSTANCE VARIABLES ]===========*/

    @Inject
    private ResourceInjector injector;

	/*===========================================[ INTERFACE METHODS ]============*/

    @Override
    public SagaFactory get() {
        GenericSagaFactory sagaFactory = new GenericSagaFactory();
        sagaFactory.setResourceInjector(injector);
        return sagaFactory;
    }
}