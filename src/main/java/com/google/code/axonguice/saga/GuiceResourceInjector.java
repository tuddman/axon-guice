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

import com.google.inject.Injector;
import org.axonframework.saga.ResourceInjector;
import org.axonframework.saga.Saga;

import javax.inject.Inject;

/**
 * Guice-based {@link ResourceInjector} implementation.
 *
 * @author Alexey Krylov
 * @see SagaModule#bindResourceInjector()
 * @since 14.02.13
 */
public class GuiceResourceInjector implements ResourceInjector {

	/*===========================================[ INSTANCE VARIABLES ]===========*/

    @Inject
    private Injector injector;

	/*===========================================[ INTERFACE METHODS ]============*/

    @Override
    public void injectResources(Saga saga) {
        injector.injectMembers(saga);
    }
}