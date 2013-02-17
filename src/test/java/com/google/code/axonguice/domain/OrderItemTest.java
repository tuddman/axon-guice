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
import com.google.code.axonguice.domain.api.command.AddOrderItemCommand;
import com.google.code.axonguice.domain.api.command.CreateOrderCommand;
import com.google.code.axonguice.domain.api.command.RemoveOrderItemCommand;
import com.google.code.axonguice.domain.model.ItemId;
import com.google.code.axonguice.domain.model.Order;
import com.google.code.axonguice.domain.model.OrderId;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.EventSourcedEntity;
import org.axonframework.repository.Repository;
import org.axonframework.unitofwork.UnitOfWork;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.Collection;

/**
 * @author Alexey Krylov
 * @since 08.02.13
 */
public class OrderItemTest extends AxonGuiceTestBase {

    /*===========================================[ INSTANCE VARIABLES ]===========*/

    @Inject
    private CommandGateway commandGateway;

    @Inject
    private Repository<Order> orderRepository;

    @Inject
    private Provider<UnitOfWork> unitOfWorkProvider;

    /*===========================================[ CLASS METHODS ]================*/

    @Test
    public void testCreateOrderWithTwoItems() throws InterruptedException {
        OrderId orderId = new OrderId();
        commandGateway.send(new CreateOrderCommand(orderId, "TestOrder"));

        UnitOfWork unitOfWork = unitOfWorkProvider.get();
        Assert.assertEquals("Order is not created", orderRepository.load(orderId).getIdentifier(), orderId);
        unitOfWork.commit();

        ItemId itemId1 = new ItemId();
        ItemId itemId2 = new ItemId();
        commandGateway.send(new AddOrderItemCommand(orderId, itemId1, 10));
        commandGateway.send(new AddOrderItemCommand(orderId, itemId2, 20));

        Order orderWithItems = getOrderById(orderId);
        Assert.assertNotNull("Order is not found", orderWithItems);

        Assert.assertEquals("Order has invalid items count", 2, orderWithItems.getOrderItemsCount());
        Assert.assertEquals("Order has invalid price", 30, orderWithItems.getOrderPrice());
    }

    @Test
    public void testCreateOrderWithTwoItemsAndDropThem() throws InterruptedException {
        OrderId orderId = new OrderId();
        commandGateway.send(new CreateOrderCommand(orderId, "TestOrder2"));

        ItemId itemId1 = new ItemId();
        ItemId itemId2 = new ItemId();
        commandGateway.send(new AddOrderItemCommand(orderId, itemId1, 10));
        commandGateway.send(new AddOrderItemCommand(orderId, itemId2, 20));

        Order order = getOrderById(orderId);
        Collection<EventSourcedEntity> childEntities = order.getChildEntities();
        Assert.assertEquals("Order has invalid child entities count", 2, childEntities.size());

        Assert.assertEquals("Order has invalid items count", 2, getOrderById(orderId).getOrderItemsCount());
        commandGateway.send(new RemoveOrderItemCommand(orderId, itemId1));
        commandGateway.send(new RemoveOrderItemCommand(orderId, itemId2));
        Assert.assertEquals("Order has invalid items count", 0, getOrderById(orderId).getOrderItemsCount());
    }

    protected Order getOrderById(OrderId orderId) {
        UnitOfWork unitOfWork = unitOfWorkProvider.get();
        Order orderWithItems = orderRepository.load(orderId);
        Assert.assertNotNull("Order is not found", orderWithItems);
        unitOfWork.commit();
        return orderWithItems;
    }
}