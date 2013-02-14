/*
 * Copyright (c) 2013, i-Free. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.google.code.axonguice.domain.api;

import com.google.code.axonguice.domain.model.OrderId;

import java.io.Serializable;

/**
 * OrderDeletedEvent - TODO: description
 *
 * @author Alexey Krylov (lexx)
 * @since 06.02.13
 */
public class OrderDeletedEvent implements Serializable {

    /*===========================================[ STATIC VARIABLES ]=============*/

    private static final long serialVersionUID = -8704115771781113833L;

    /*===========================================[ INSTANCE VARIABLES ]===========*/
    private OrderId orderId;

    /*===========================================[ CONSTRUCTORS ]=================*/

    public OrderDeletedEvent(OrderId orderId) {
        this.orderId = orderId;
    }

    /*===========================================[ GETTER/SETTER ]================*/

    public OrderId getOrderId() {
        return orderId;
    }
}
