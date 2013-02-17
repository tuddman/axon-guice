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

package com.google.code.axonguice.domain.api.command;

import com.google.code.axonguice.commandhandling.annotation.CommandHandlerComponent;
import com.google.code.axonguice.domain.model.Order;
import com.google.code.axonguice.domain.model.OrderQueryService;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;

import javax.inject.Inject;

/**
 * OrderCommandHandler - TODO: description
 *
 * @author Alexey Krylov
 * @since 05.02.13
 */
@CommandHandlerComponent
public class OrderCommandHandler {

    /*===========================================[ INSTANCE VARIABLES ]===========*/

    private Repository<Order> orderRepository;

    /*===========================================[ CLASS METHODS ]================*/

    @CommandHandler
    public Boolean on(CreateOrderCommand command) {
        Order order = new Order(command.getOrderId(), command.getName());
        orderRepository.add(order);
        return true;
    }

    @CommandHandler
    public void on(ChangeOrderNameCommand command) {
        Order order = orderRepository.load(command.getOrderId());
        order.setName(command.getNewName());
    }

    @CommandHandler
    public void on(DeleteOrderCommand command) {
        Order order = orderRepository.load(command.getOrderId());
        order.delete();
    }

    @CommandHandler
    public void on(AddOrderItemCommand command) {
        Order order = orderRepository.load(command.getOrderId());
        order.addOrderItem(command.getItemId(), command.getOrderPrice());
    }

    @CommandHandler
    public boolean on(RemoveOrderItemCommand command, OrderQueryService orderQueryService) {
        Order order = orderRepository.load(command.getOrderId());
        order.removeOrderItem(command.getItemId());
        return orderQueryService!=null;
    }

    @Inject
    public void setRepository(Repository<Order> orderRepository) {
        this.orderRepository = orderRepository;
    }
}