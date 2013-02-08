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
public class CreateOrderCommand implements Serializable {

    /*===========================================[ STATIC VARIABLES ]=============*/

    private static final long serialVersionUID = -5341525095597432079L;

    /*===========================================[ INSTANCE VARIABLES ]===========*/

    private String name;

    @TargetAggregateIdentifier
    private OrderId orderId;

    /*===========================================[ CONSTRUCTORS ]=================*/

    public CreateOrderCommand(OrderId orderId, String name) {
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
}
