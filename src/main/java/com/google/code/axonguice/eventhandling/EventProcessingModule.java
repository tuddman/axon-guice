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

package com.google.code.axonguice.eventhandling;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;

/**
 * Event Processing elements bind module.
 *
 * @author Alexey Krylov
 * @since 06.02.13
 */
public class EventProcessingModule extends AbstractModule {

    /*===========================================[ INTERFACE METHODS ]============*/

    @Override
    protected void configure() {
        //todo autoregistration type listeners
        bindEventBus();
    }

    protected void bindEventBus() {
        bind(EventBus.class).to(SimpleEventBus.class).in(Scopes.SINGLETON);
    }
}