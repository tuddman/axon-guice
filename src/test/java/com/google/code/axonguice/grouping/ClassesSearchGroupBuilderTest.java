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

package com.google.code.axonguice.grouping;

import com.google.code.axonguice.AxonGuiceTestBase;
import com.google.code.axonguice.commandhandling.SimpleCommandHandler;
import com.google.code.axonguice.commandhandling.annotation.CommandHandlerComponent;
import com.google.common.base.Predicate;
import org.junit.Assert;
import org.junit.Test;
import org.reflections.Reflections;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author Alexey Krylov
 * @since 18.02.13
 */
public class ClassesSearchGroupBuilderTest extends AxonGuiceTestBase {

    /*===========================================[ CLASS METHODS ]================*/

    @Test
    public void testClassesSearchGroupBuilder() {
        ClassesSearchGroup classesSearchGroup = ClassesSearchGroupBuilder.forPackage("com.google.code.axonguice").
                withInclusionFilterPredicate(new Predicate<Class>() {
                    @Override
                    public boolean apply(@Nullable Class input) {
                        return SimpleCommandHandler.class.isAssignableFrom(input);
                    }
                }
                ).build();

        Assert.assertEquals("com.google.code.axonguice", classesSearchGroup.getPackages().iterator().next());

        AbstractClassesGroupingModule groupingModule = new AbstractClassesGroupingModule() {
            @Override
            protected void configure() {

            }
        };

        Reflections reflections = groupingModule.createReflections(Arrays.asList("com.google.code.axonguice"));
        Collection found = groupingModule.filterSearchResult(reflections.getTypesAnnotatedWith(CommandHandlerComponent.class), classesSearchGroup);
        Assert.assertEquals("Invalid cound of found CommandHandlerComponents", 1, found.size());
        Assert.assertEquals("Found CommandHandler is not SimpleCommandHandler", SimpleCommandHandler.class, found.iterator().next());
    }
}
