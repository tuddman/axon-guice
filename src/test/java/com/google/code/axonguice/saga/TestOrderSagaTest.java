/*
 * Copyright (c) 2013, i-Free. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.google.code.axonguice.saga;

import com.google.code.axonguice.AxonGuiceTest;
import com.google.code.axonguice.domain.api.command.CreateOrderCommand;
import com.google.code.axonguice.domain.api.command.DeleteOrderCommand;
import com.google.code.axonguice.domain.model.Order;
import com.google.code.axonguice.domain.model.OrderId;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.repository.Repository;
import org.axonframework.saga.SagaRepository;
import org.axonframework.unitofwork.UnitOfWork;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * OrderSagaTest - TODO: description
 *
 * @author Alexey Krylov (AleX)
 * @since 14.02.13
 */
public class TestOrderSagaTest extends AxonGuiceTest {

	/*===========================================[ INSTANCE VARIABLES ]===========*/

    @Inject
    private CommandGateway commandGateway;

    @Inject
    private Repository<Order> orderRepository;

    @Inject
    private Provider<UnitOfWork> unitOfWorkProvider;

    @Inject
    private SagaRepository sagaRepository;

	/*===========================================[ CLASS METHODS ]================*/

    @Test
    public void testOrderSaga() {
        OrderId orderId = new OrderId();
        commandGateway.send(new CreateOrderCommand(orderId, "TestOrder"));

        UnitOfWork unitOfWork = unitOfWorkProvider.get();
        Assert.assertEquals(orderRepository.load(orderId).getIdentifier(), orderId);
        unitOfWork.commit();

        Assert.assertNotNull("TestOrderSaga is not started", TestOrderSaga.LAST_ACTIVE_IDENTIFIER);
        TestOrderSaga saga = (TestOrderSaga) sagaRepository.load(TestOrderSaga.LAST_ACTIVE_IDENTIFIER);
        Assert.assertNotNull("TestOrderSaga is not found in SagaRepository", saga);
        Assert.assertTrue("Injection into TestOrderSaga failed", saga.isCommandGatewayInjected());
        Assert.assertTrue("TestOrderSaga is not active", saga.isActive());

        commandGateway.send(new DeleteOrderCommand(orderId));
        Assert.assertFalse("TestOrderSaga is not ended", saga.isActive());
    }
}
