/*
 * Copyright (c) 2013, i-Free. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.google.code.axonguice.domain.model;

import com.google.code.axonguice.domain.api.OrderCreatedEvent;
import com.google.code.axonguice.domain.api.OrderDeletedEvent;
import com.google.code.axonguice.domain.api.OrderNameChangedEvent;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

import javax.inject.Inject;

/**
 * Order - TODO: description
 *
 * @author Alexey Krylov (lexx)
 * @since 05.02.13
 */
public class Order extends AbstractAnnotatedAggregateRoot {

    private static final long serialVersionUID = 873954089951691102L;

    @AggregateIdentifier
    private OrderId orderId;

    @Inject
    private OrderQueryService orderQueryService;

    private String name;

    @SuppressWarnings("UnusedDeclaration")
    protected Order() {
    }

    public Order(OrderId orderId, String name) {
        apply(new OrderCreatedEvent(orderId, name));
    }

    public void delete() {
        apply(new OrderDeletedEvent(orderId));
    }

    @Override
    public OrderId getIdentifier() {
        return orderId;
    }

    public void setName(String newName) {
        apply(new OrderNameChangedEvent(newName));
    }

    @EventHandler
    public void on(OrderCreatedEvent event) {
        orderId = event.getOrderId();
        name = event.getName();
    }

    @EventHandler
    public void on(OrderNameChangedEvent event) {
        name = event.getName();
    }

    @EventHandler
    public void on(OrderDeletedEvent event) {
        markDeleted();
    }

    public OrderQueryService getOrderQueryService() {
        return orderQueryService;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order");
        sb.append("{orderId=").append(orderId);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
