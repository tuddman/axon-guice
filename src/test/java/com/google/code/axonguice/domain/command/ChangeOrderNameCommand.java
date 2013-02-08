/*
 * Copyright (c) 2013, i-Free. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.google.code.axonguice.domain.command;

import com.google.code.axonguice.domain.model.OrderId;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * ChangeOrderNameCommand - TODO: description
 *
 * @author Alexey Krylov (lexx)
 * @since 06.02.13
 */
public class ChangeOrderNameCommand {

    /*===========================================[ STATIC VARIABLES ]=============*/

    private static final long serialVersionUID = -5341525095597432079L;

    /*===========================================[ INSTANCE VARIABLES ]===========*/

    private String newName;

    @TargetAggregateIdentifier
    private OrderId orderId;

    /*===========================================[ CONSTRUCTORS ]=================*/

    public ChangeOrderNameCommand(OrderId orderId, String newName) {
        this.newName = newName;
        this.orderId = orderId;
    }

    /*===========================================[ GETTER/SETTER ]================*/

    public String getNewName() {
        return newName;
    }

    public OrderId getOrderId() {
        return orderId;
    }
}
