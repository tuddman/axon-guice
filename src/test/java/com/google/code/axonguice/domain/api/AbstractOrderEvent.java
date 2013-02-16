/*
 * Copyright (c) 2013, i-Free. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.google.code.axonguice.domain.api;

import com.google.code.axonguice.domain.model.OrderId;

import java.io.Serializable;

/**
 * AbstractOrderEvent - TODO: description
 *
 * @author Alexey Krylov (AleX)
 * @since 17.02.13
 */
public class AbstractOrderEvent implements Serializable {

	/*===========================================[ STATIC VARIABLES ]=============*/

    private static final long serialVersionUID = 1169187425619138782L;

	/*===========================================[ INSTANCE VARIABLES ]===========*/

    protected OrderId orderId;

	/*===========================================[ CONSTRUCTORS ]=================*/

    protected AbstractOrderEvent(OrderId orderId) {
        this.orderId = orderId;
    }

	/*===========================================[ GETTER/SETTER ]================*/

    public OrderId getOrderId() {
        return orderId;
    }
}
