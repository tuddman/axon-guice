/*
 * Copyright (c) 2013, i-Free. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.google.code.axonguice.domain.api.command;

import com.google.code.axonguice.domain.model.ItemId;
import com.google.code.axonguice.domain.model.OrderId;

/**
 * RemoveOrderItemCommand - TODO: description
 *
 * @author Alexey Krylov (AleX)
 * @since 17.02.13
 */
public class RemoveOrderItemCommand extends AbstractOrderCommand {

	/*===========================================[ INSTANCE VARIABLES ]===========*/

    private ItemId itemId;

	/*===========================================[ CONSTRUCTORS ]=================*/

    public RemoveOrderItemCommand(OrderId orderId, ItemId itemId) {
        super(orderId);
        this.itemId = itemId;
    }

	/*===========================================[ GETTER/SETTER ]================*/

    public ItemId getItemId() {
        return itemId;
    }
}
