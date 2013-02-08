/*
 * Copyright (c) 2013, i-Free. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.google.code.axonguice.domain.api;


import com.google.code.axonguice.domain.model.OrderId;

import java.io.Serializable;

/**
 * OrderCreatedEvent - TODO: description
 *
 * @author Alexey Krylov (lexx)
 * @since 05.02.13
 */
public class OrderCreatedEvent implements Serializable {

	/*===========================================[ STATIC VARIABLES ]=============*/

    private static final long serialVersionUID = -8704115771781113833L;

    /*===========================================[ INSTANCE VARIABLES ]===========*/

    private String name;
    private OrderId orderId;

    /*===========================================[ CONSTRUCTORS ]=================*/

    public OrderCreatedEvent(OrderId orderId, String name) {
        this.name = name;
        this.orderId = orderId;
    }

	/*===========================================[ GETTER/SETTER ]================*/

    public String getName() {
        return name;
    }

    public OrderId getOrderId() {
        return orderId;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("OrderCreatedEvent");
        sb.append("{name='").append(name).append('\'');
        sb.append(", orderId=").append(orderId);
        sb.append('}');
        return sb.toString();
    }
}
