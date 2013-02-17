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

package com.google.code.axonguice.domain;

import com.google.code.axonguice.AxonGuiceTestBase;
import com.google.code.axonguice.domain.api.OrderCreatedEvent;
import com.google.code.axonguice.domain.api.command.CreateOrderCommand;
import com.google.code.axonguice.domain.api.command.OrderCommandHandler;
import com.google.code.axonguice.domain.model.Order;
import com.google.code.axonguice.domain.model.OrderId;
import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Alexey Krylov
 * @since 17.02.13
 */
@SuppressWarnings("JUnitTestMethodWithNoAssertions")
public class OrderCommandHandlerTest extends AxonGuiceTestBase {

	/*===========================================[ INSTANCE VARIABLES ]===========*/

    private FixtureConfiguration fixture;

	/*===========================================[ CLASS METHODS ]================*/

    @Before
    public void setUp() {
        fixture = Fixtures.newGivenWhenThenFixture(Order.class);
        OrderCommandHandler commandHandler = new OrderCommandHandler();
        commandHandler.setRepository(fixture.getRepository());
        fixture.registerAnnotatedCommandHandler(commandHandler);
    }

    @Test
    public void testCreateOrder() {
        OrderId orderId = new OrderId();
        String orderName = "TestOrder";
        CreateOrderCommand createOrderCommand = new CreateOrderCommand(orderId, orderName);
        fixture.given()
                .when(createOrderCommand)
                .expectEvents(new OrderCreatedEvent(orderId, orderName));
    }
}
