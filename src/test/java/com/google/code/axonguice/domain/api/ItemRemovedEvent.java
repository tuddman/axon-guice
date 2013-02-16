/*
 * Copyright (c) 2013, i-Free. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.google.code.axonguice.domain.api;

import com.google.code.axonguice.domain.model.ItemId;

/**
 * ItemRemovedEvent - TODO: description
 *
 * @author Alexey Krylov (AleX)
 * @since 17.02.13
 */
public class ItemRemovedEvent {

	/*===========================================[ INSTANCE VARIABLES ]===========*/

    private ItemId itemId;

	/*===========================================[ CONSTRUCTORS ]=================*/

    public ItemRemovedEvent(ItemId itemId) {
        this.itemId = itemId;
    }

	/*===========================================[ GETTER/SETTER ]================*/

    public ItemId getItemId() {
        return itemId;
    }
}
