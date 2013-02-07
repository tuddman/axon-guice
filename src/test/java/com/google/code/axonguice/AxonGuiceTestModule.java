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

import com.google.code.axonguice.commandhandling.CommandHandlingModule;
import com.google.code.axonguice.eventhandling.EventHandlingModule;

/**
 * AxonGuiceTestModule - TODO: description
 *
 * @author Alexey Krylov (lexx)
 * @since 07.02.13
 */
public class AxonGuiceTestModule extends AxonGuiceModule {

	/*===========================================[ CLASS METHODS ]================*/

    @Override
    protected CommandHandlingModule createCommandHandlingModule() {
        return new CommandHandlingModule("com.google.code.axonguice");
    }

    @Override
    protected EventHandlingModule createEventHandlingModule() {
        return new EventHandlingModule("com.google.code.axonguice");
    }
}
