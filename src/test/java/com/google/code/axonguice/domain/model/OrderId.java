/*
 * Copyright (C) 2013 the original author or authors.
 * See the notice.md file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.code.axonguice.domain.model;

import org.axonframework.domain.IdentifierFactory;

import java.io.Serializable;

/**
 * @author Alexey Krylov
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

    private OrderId(String identifier) {
        this.identifier = identifier;
    }

    /*===========================================[ CLASS METHODS ]================*/

    public static OrderId valueOf(String orderId) {
        return new OrderId(orderId);
    }

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
