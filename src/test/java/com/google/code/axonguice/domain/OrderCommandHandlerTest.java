/*
 * Copyright (c) 2013, i-Free. All Rights Reserved.
 * Use is subject to license terms.
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
 * OrderCommandHandlerTest - TODO: description
 *
 * @author Alexey Krylov (AleX)
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
