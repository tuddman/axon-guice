/*
 * Copyright (c) 2013, i-Free. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.google.code.axonguice.domain.api.command;

import com.google.code.axonguice.domain.model.OrderId;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * AbstractOrderCommand - TODO: description
 *
 * @author Alexey Krylov (AleX)
 * @since 17.02.13
 */
public class AbstractOrderCommand {

	/*===========================================[ INSTANCE VARIABLES ]===========*/

    @TargetAggregateIdentifier
    protected OrderId orderId;

	/*===========================================[ CONSTRUCTORS ]=================*/

    protected AbstractOrderCommand(OrderId orderId) {
        this.orderId = orderId;
    }

	/*===========================================[ GETTER/SETTER ]================*/

    public OrderId getOrderId() {
        return orderId;
    }
}
