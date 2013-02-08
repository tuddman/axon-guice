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

import com.google.inject.Injector;
import com.google.inject.Stage;
import com.mycila.testing.junit.MycilaJunitRunner;
import com.mycila.testing.plugin.guice.GuiceContext;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * AxonGuiceTest - TODO: description
 *
 * @author Alexey Krylov (lexx)
 * @since 06.02.13
 */
@RunWith(MycilaJunitRunner.class)
@GuiceContext(value = {AxonGuiceTestModule.class}, stage = Stage.PRODUCTION)
public abstract class AxonGuiceTest {

    /*===========================================[ INSTANCE VARIABLES ]===========*/

    protected Logger logger;

    @Inject
    protected Injector injector;

    @PostConstruct
    protected void init() {
        logger = LoggerFactory.getLogger(getClass());
    }
}
