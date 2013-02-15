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

package com.google.code.axonguice.jsr250;

import com.google.code.axonguice.AxonGuiceTest;
import com.google.inject.Inject;
import com.google.inject.Scopes;
import com.mycila.inject.jsr250.Jsr250Destroyer;
import org.junit.Assert;
import org.junit.Test;

/**
 * TestJsr250Support - TODO: description
 *
 * @author Alexey Krylov (lexx)
 * @since 08.02.13
 */

public class Jsr250SupportTest extends AxonGuiceTest {

    /*===========================================[ INSTANCE VARIABLES ]===========*/

    @Inject
    private Jsr250EnabledService jsr250EnabledService;

    /*===========================================[ CLASS METHODS ]================*/

    @Test
    public void testJsr250EnabledService() {
        Assert.assertTrue(Scopes.isSingleton(injector.getBinding(jsr250EnabledService.getClass())));
        Assert.assertTrue(jsr250EnabledService.isPostConstuctInvoked());
        Assert.assertTrue(jsr250EnabledService.isResourceSet());
        Jsr250EnabledService service = injector.getInstance(Jsr250EnabledService.class);
        Assert.assertEquals(jsr250EnabledService.getResource(), service.getResource());
        injector.getInstance(Jsr250Destroyer.class).preDestroy();
        Assert.assertTrue(jsr250EnabledService.isPreDestroyInvoked());
    }
}
