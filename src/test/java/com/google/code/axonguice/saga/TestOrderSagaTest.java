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

package com.google.code.axonguice.saga;

import com.google.code.axonguice.AxonGuiceTestBase;
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
 * @author Alexey Krylov
 * @since 14.02.13
 */
public class TestOrderSagaTest extends AxonGuiceTestBase {

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
        Assert.assertEquals("Order is not created", orderRepository.load(orderId).getIdentifier(), orderId);
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
