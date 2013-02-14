/*
 * Copyright (c) 2013, i-Free. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.google.code.axonguice.domain.api.command;

import com.google.code.axonguice.commandhandling.annotation.CommandHandlerComponent;
import com.google.code.axonguice.domain.model.Order;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;

import javax.inject.Inject;

/**
 * OrderCommandHandler - TODO: description
 *
 * @author Alexey Krylov (lexx)
 * @since 05.02.13
 */
@CommandHandlerComponent
public class OrderCommandHandler {

    /*===========================================[ INSTANCE VARIABLES ]===========*/

    @Inject
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
}