/*
 * Copyright (c) 2013, i-Free. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.google.code.axonguice.domain.api;

import com.google.code.axonguice.domain.model.ItemId;

/**
 * ItemAddedEvent - TODO: description
 *
 * @author Alexey Krylov (AleX)
 * @since 17.02.13
 */
public class ItemAddedEvent {

	/*===========================================[ INSTANCE VARIABLES ]===========*/

    private ItemId itemId;
    private long itemPrice;

	/*===========================================[ CONSTRUCTORS ]=================*/

    public ItemAddedEvent(ItemId itemId, long itemPrice) {
        this.itemId = itemId;
        this.itemPrice = itemPrice;
    }

	/*===========================================[ GETTER/SETTER ]================*/

    public ItemId getItemId() {
        return itemId;
    }

    public long getItemPrice() {
        return itemPrice;
    }
}
