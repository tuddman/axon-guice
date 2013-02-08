/*
 * Copyright (c) 2013, i-Free. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.google.code.axonguice.domain.model;

import org.axonframework.domain.IdentifierFactory;

import java.io.Serializable;

/**
 * OrderId - TODO: description
 *
 * @author Alexey Krylov (lexx)
 * @since 05.02.13
 */
public class OrderId implements Serializable {

	/*===========================================[ STATIC VARIABLES ]=============*/

    private static final long serialVersionUID = -4836858910109015405L;

	/*===========================================[ INSTANCE VARIABLES ]===========*/

    private String identifier;

	/*===========================================[ CONSTRUCTORS ]=================*/

    public OrderId() {
        identifier = IdentifierFactory.getInstance().generateIdentifier();
    }

	/*===========================================[ CLASS METHODS ]================*/

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OrderId)) {
            return false;
        }

        OrderId orderId = (OrderId) obj;

        if (!identifier.equals(orderId.identifier)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return identifier.hashCode();
    }

    @Override
    public String toString() {
        return identifier;
    }
}
