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

package com.google.code.axonguice.repository;

import com.google.inject.Provider;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.fs.FileSystemEventStore;
import org.axonframework.eventstore.fs.SimpleEventFileResolver;

import java.io.File;

/**
 * EventStoreProvider - TODO: description
 *
 * @author Alexey Krylov (lexx)
 * @since 07.02.13
 */
public class SimpleEventStoreProvider implements Provider<EventStore> {

    /*===========================================[ INTERFACE METHODS ]============*/

    @Override
    public EventStore get() {
        File tempFile = new File(System.getProperty("java.io.tmpdir"), "axonguice-eventstore");
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }
        return new FileSystemEventStore(new SimpleEventFileResolver(tempFile));
    }
}
