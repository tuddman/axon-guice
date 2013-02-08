/*
 * Copyright (c) 2013, i-Free. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.google.code.axonguice.domain.api;

import org.axonframework.eventhandling.annotation.EventHandler;

/**
 * OrderCreatedEventHandler - TODO: description
 *
 * @author Alexey Krylov (lexx)
 * @since 05.02.13
 */
public class OrderCreatedEventHandler {
    @EventHandler
    public void handle(OrderCreatedEvent event) {
        System.out.println("event = " + event);
    }
}
