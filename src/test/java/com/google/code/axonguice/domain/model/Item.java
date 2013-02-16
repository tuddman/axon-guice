/*
 * Copyright (c) 2013, i-Free. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.google.code.axonguice.domain.model;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity;

/**
 * OrderItem - TODO: description
 *
 * @author Alexey Krylov (AleX)
 * @since 17.02.13
 */
public class Item extends AbstractAnnotatedEntity {

	/*===========================================[ INSTANCE VARIABLES ]===========*/

    private ItemId itemId;
    private final long itemPrice;

	/*===========================================[ CONSTRUCTORS ]=================*/

    public Item(ItemId itemId, long itemPrice) {
        this.itemId = itemId;
        this.itemPrice = itemPrice;
    }

	/*===========================================[ GETTER/SETTER ]================*/

    public long getItemPrice() {
        return itemPrice;
    }

    public ItemId getItemId() {
        return itemId;
    }
}
