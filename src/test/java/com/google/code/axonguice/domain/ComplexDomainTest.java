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

import com.google.code.axonguice.AxonGuiceTest;
import com.google.code.axonguice.AxonGuiceTestModule;
import com.google.code.axonguice.domain.command.ChangeOrderNameCommand;
import com.google.code.axonguice.domain.command.CreateOrderCommand;
import com.google.code.axonguice.domain.command.DeleteOrderCommand;
import com.google.code.axonguice.domain.model.Order;
import com.google.code.axonguice.domain.model.OrderId;
import com.google.inject.Stage;
import com.mycila.testing.plugin.guice.GuiceContext;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.AggregateDeletedException;
import org.axonframework.repository.Repository;
import org.axonframework.unitofwork.UnitOfWork;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * ComplexDomainTests - TODO: description
 *
 * @author Alexey Krylov (lexx)
 * @since 08.02.13
 */
@GuiceContext(value = {AxonGuiceTestModule.class, TestDomainModule.class}, stage = Stage.PRODUCTION)
public class ComplexDomainTest extends AxonGuiceTest {

    /*===========================================[ INSTANCE VARIABLES ]===========*/

    @Inject
    private CommandGateway commandGateway;

    @Inject
    private Repository<Order> orderRepository;

    @Inject
    private Provider<UnitOfWork> unitOfWorkProvider;

    /*===========================================[ CLASS METHODS ]================*/

    @Test
    public void testCreateOrder() throws InterruptedException {
        OrderId orderId = new OrderId();
        commandGateway.send(new CreateOrderCommand(orderId, "TestOrder"));

        UnitOfWork unitOfWork = unitOfWorkProvider.get();
        Assert.assertEquals(orderRepository.load(orderId).getIdentifier(), orderId);
        unitOfWork.commit();
    }

    @Test
    public void testMultipleOrderNameChange() {
        OrderId orderId = new OrderId();
        commandGateway.send(new CreateOrderCommand(orderId, "TestOrder"));

        for (int i = 0; i < 11; i++) {
            commandGateway.send(new ChangeOrderNameCommand(orderId, "TestOrderName" + i));
        }

        UnitOfWork unitOfWork = unitOfWorkProvider.get();
        Assert.assertEquals("TestOrderName10", orderRepository.load(orderId).getName());
        unitOfWork.commit();
    }

    @Test
    public void testDeleteAndLoadOrder() {
        OrderId orderId = new OrderId();
        commandGateway.send(new CreateOrderCommand(orderId, "OrderToDelete"));
        UnitOfWork unitOfWork = unitOfWorkProvider.get();
        Assert.assertEquals(orderRepository.load(orderId).getIdentifier(), orderId);
        unitOfWork.commit();

        commandGateway.send(new DeleteOrderCommand(orderId));


        UnitOfWork loadUoW = unitOfWorkProvider.get();
        Exception exception = null;
        try {
            orderRepository.load(orderId);
        } catch (AggregateDeletedException e) {
            exception = e;
        } finally {
            loadUoW.commit();
        }

        Assert.assertTrue(exception instanceof AggregateDeletedException);
    }

    @Test
    public void testMultithreadedDeleteAndLoadOrder() throws Exception {
        final OrderId orderId = new OrderId();
        commandGateway.send(new CreateOrderCommand(orderId, "OrderToDelete"));

        Executor executorService = Executors.newCachedThreadPool();

        int maxThreads = 5;
        final CountDownLatch countDownLatch = new CountDownLatch(maxThreads);
        final CountDownLatch awaitLatch = new CountDownLatch(maxThreads);

        for (int i = 0; i < maxThreads; i++) {
            final int finalI = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        countDownLatch.await();
                        commandGateway.send(new DeleteOrderCommand(orderId));
                        logger.info("sent: " + finalI);
                        awaitLatch.countDown();
                    } catch (InterruptedException e) {
                        logger.error("Error", e);
                    }
                }
            });

            countDownLatch.countDown();
        }

        awaitLatch.await();
        // Check
        UnitOfWork unitOfWork = unitOfWorkProvider.get();
        Exception exception = null;
        try {
            orderRepository.load(orderId);
        } catch (AggregateDeletedException e) {
            exception = e;
        } finally {
            unitOfWork.commit();
        }

        Assert.assertTrue(exception instanceof AggregateDeletedException);
    }

    @Test
    public void testAggregateRootInjection() {
        OrderId firstOrderId = new OrderId();
        commandGateway.send(new CreateOrderCommand(firstOrderId, "FirstOrder"));

        UnitOfWork unitOfWork = unitOfWorkProvider.get();
        Order firstOrder = orderRepository.load(firstOrderId);
        Assert.assertEquals(firstOrder.getIdentifier(), firstOrderId);
        Assert.assertNotNull(firstOrder.getOrderQueryService());
        unitOfWork.commit();

        OrderId secondOrderId = new OrderId();
        commandGateway.send(new CreateOrderCommand(secondOrderId, "FirstOrder"));

        unitOfWork = unitOfWorkProvider.get();
        Order secondOrder = orderRepository.load(secondOrderId);
        Assert.assertEquals(secondOrder.getIdentifier(), secondOrderId);
        Assert.assertNotNull(secondOrder.getOrderQueryService());
        unitOfWork.commit();

        Assert.assertEquals(firstOrder.getOrderQueryService(), secondOrder.getOrderQueryService());
    }
}