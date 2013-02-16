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
 * @author Alexey Krylov
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
