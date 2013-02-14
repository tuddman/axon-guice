/*
 * Copyright (c) 2013, i-Free. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.google.code.axonguice.saga;

import com.google.code.axonguice.domain.api.OrderCreatedEvent;
import com.google.code.axonguice.domain.api.OrderDeletedEvent;
import com.google.code.axonguice.domain.model.Order;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.repository.Repository;
import org.axonframework.saga.annotation.AbstractAnnotatedSaga;
import org.axonframework.saga.annotation.EndSaga;
import org.axonframework.saga.annotation.SagaEventHandler;
import org.axonframework.saga.annotation.StartSaga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * OrderSaga - TODO: description
 *
 * @author Alexey Krylov (AleX)
 * @since 14.02.13
 */
public class TestOrderSaga extends AbstractAnnotatedSaga {

	/*===========================================[ STATIC VARIABLES ]=============*/

    private static final Logger logger = LoggerFactory.getLogger(TestOrderSaga.class);

    static String LAST_ACTIVE_IDENTIFIER;

	/*===========================================[ INSTANCE VARIABLES ]===========*/

    @Inject
    private transient CommandGateway commandGateway;

    @Inject
    private Repository<Order> orderRepository;

	/*===========================================[ CLASS METHODS ]================*/

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent event) {
        LAST_ACTIVE_IDENTIFIER = getSagaIdentifier();
        logger.info("Started Saga for: " + event.getOrderId());
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderDeletedEvent event) {
        end();
        logger.info("Saga Ended for: " + event.getOrderId());
    }

    public boolean isCommandGatewayInjected() {
        return commandGateway != null;
    }
}
