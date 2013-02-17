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

package com.google.code.axonguice.domain.model;

import com.google.code.axonguice.domain.api.*;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.EventSourcedEntity;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcedMember;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Order - TODO: description
 * todo protected?
 *
 * @author Alexey Krylov
 * @since 05.02.13
 */
public class Order extends AbstractAnnotatedAggregateRoot {

	/*===========================================[ STATIC VARIABLES ]=============*/

    private static final long serialVersionUID = 873954089951691102L;

	/*===========================================[ INSTANCE VARIABLES ]===========*/

    @AggregateIdentifier
    private OrderId orderId;

    @Inject
    private OrderQueryService orderQueryService;

    @EventSourcedMember
    private List<Item> items = new ArrayList<>();

    private String name;

	/*===========================================[ CONSTRUCTORS ]=================*/

    @SuppressWarnings("UnusedDeclaration")
    protected Order() {
    }

    public Order(OrderId orderId, String name) {
        apply(new OrderCreatedEvent(orderId, name));
    }

	/*===========================================[ CLASS METHODS ]================*/

    public void addOrderItem(ItemId itemId, long itemPrice) {
        apply(new ItemAddedEvent(itemId, itemPrice));
    }

    public void removeOrderItem(ItemId itemId) {
        apply(new ItemRemovedEvent(itemId));
    }

    public int getOrderItemsCount() {
        return items.size();
    }

    public long getOrderPrice() {
        long total = 0;
        for (Item item : items) {
            total += item.getItemPrice();
        }
        return total;
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
    protected void on(OrderCreatedEvent event) {
        orderId = event.getOrderId();
        name = event.getName();
    }

    @EventHandler
    protected void on(OrderNameChangedEvent event) {
        name = event.getName();
    }

    @EventHandler
    protected void on(OrderDeletedEvent event) {
        markDeleted();
    }

    @EventHandler
    protected void on(ItemAddedEvent event) {
        items.add(new Item(event.getItemId(), event.getItemPrice()));
    }

    @EventHandler
    protected void on(ItemRemovedEvent event) {
        ItemId itemId = event.getItemId();
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getItemId().equals(itemId)) {
                iterator.remove();
                break;
            }
        }
    }

    @Override
    public Collection<EventSourcedEntity> getChildEntities() {
        return super.getChildEntities();
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

	/*===========================================[ GETTER/SETTER ]================*/

    public OrderQueryService getOrderQueryService() {
        return orderQueryService;
    }

    public String getName() {
        return name;
    }
}
