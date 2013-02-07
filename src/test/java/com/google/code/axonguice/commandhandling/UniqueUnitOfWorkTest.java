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

import com.google.code.axonguice.AxonGuiceTest;
import org.axonframework.unitofwork.UnitOfWork;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * UniqueUnitOfWorkTest - TODO: description
 *
 * @author Alexey Krylov (lexx)
 * @since 06.02.13
 */
public class UniqueUnitOfWorkTest extends AxonGuiceTest {

    /*===========================================[ INSTANCE VARIABLES ]===========*/

    @Inject
    private Provider<UnitOfWork> unitOfWorkProvider;

    /*===========================================[ CLASS METHODS ]================*/

    @Test
    public void testUnitOfWorkEachInjectionUnique() {
        Assert.assertNotEquals(injector.getInstance(UnitOfWork.class), injector.getInstance(UnitOfWork.class));
    }

    @Test
    public void testUnitOfWorkEachProviderGetIsUnique() {
        Assert.assertNotEquals(unitOfWorkProvider.get(), unitOfWorkProvider.get());
    }

    @Test
    public void testInjectedUnitOfWorkIsStarted() {
        UnitOfWork unitOfWork = injector.getInstance(UnitOfWork.class);
        Assert.assertTrue(unitOfWork.isStarted());
    }
}
