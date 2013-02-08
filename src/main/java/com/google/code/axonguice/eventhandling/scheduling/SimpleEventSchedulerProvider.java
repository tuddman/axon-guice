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

package com.google.code.axonguice.eventhandling.scheduling;

import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.scheduling.EventScheduler;
import org.axonframework.eventhandling.scheduling.java.SimpleEventScheduler;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

/**
 * SimpleEventSchedulerProvider - TODO: description
 *
 * @author Alexey Krylov (lexx)
 * @since 07.02.13
 */
public class SimpleEventSchedulerProvider implements Provider<EventScheduler> {

    /*===========================================[ INSTANCE VARIABLES ]===========*/

    @Inject
    protected EventBus eventBus;

    /*===========================================[ INTERFACE METHODS ]============*/

    @Override
    public EventScheduler get() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() * 5, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "EventSchedulerExecutor");
            }
        });
        return new SimpleEventScheduler(executorService, eventBus);
    }
}