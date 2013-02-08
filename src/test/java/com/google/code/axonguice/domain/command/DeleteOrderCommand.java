/*
 * Copyright (c) 2013, i-Free. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.google.code.axonguice.domain.command;

import com.google.code.axonguice.domain.model.OrderId;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import java.io.Serializable;

/**
 * CreateToDoItemCommand - TODO: description
 *
 * @author Alexey Krylov (lexx)
 * @since 05.02.13
 */
public class DeleteOrderCommand implements Serializable {

    /*===========================================[ STATIC VARIABLES ]=============*/

    private static final long serialVersionUID = -5341525095597432079L;

    /*===========================================[ INSTANCE VARIABLES ]===========*/

    @TargetAggregateIdentifier
    private OrderId orderId;

    /*===========================================[ CONSTRUCTORS ]=================*/

    public DeleteOrderCommand(OrderId orderId) {
        this.orderId = orderId;
    }

    /*===========================================[ GETTER/SETTER ]================*/

    public OrderId getOrderId() {
        return orderId;
    }
}
