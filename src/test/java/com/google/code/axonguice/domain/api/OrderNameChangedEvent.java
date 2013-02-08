/*
 * Copyright (c) 2013, i-Free. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.google.code.axonguice.domain.api;

import java.io.Serializable;

/**
 * OrderCreatedEvent - TODO: description
 *
 * @author Alexey Krylov (lexx)
 * @since 05.02.13
 */
public class OrderNameChangedEvent implements Serializable {

	/*===========================================[ STATIC VARIABLES ]=============*/

    private static final long serialVersionUID = -8704115771781113833L;

    /*===========================================[ INSTANCE VARIABLES ]===========*/

    private String name;

    /*===========================================[ CONSTRUCTORS ]=================*/

    public OrderNameChangedEvent(String name) {
        this.name = name;
    }

	/*===========================================[ GETTER/SETTER ]================*/

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("OrderCreatedEvent");
        sb.append("{name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
