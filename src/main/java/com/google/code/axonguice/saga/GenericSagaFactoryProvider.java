/*
 * Copyright (c) 2013, i-Free. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.google.code.axonguice.saga;

import com.google.inject.Provider;
import org.axonframework.saga.GenericSagaFactory;
import org.axonframework.saga.ResourceInjector;
import org.axonframework.saga.SagaFactory;

import javax.inject.Inject;

/**
 * GuiceSagaFactory - TODO: description
 *
 * @author Alexey Krylov (AleX)
 * @since 14.02.13
 */
public class GenericSagaFactoryProvider implements Provider<SagaFactory> {

	/*===========================================[ INSTANCE VARIABLES ]===========*/

    @Inject
    private ResourceInjector injector;

	/*===========================================[ INTERFACE METHODS ]============*/

    @Override
    public SagaFactory get() {
        GenericSagaFactory sagaFactory = new GenericSagaFactory();
        sagaFactory.setResourceInjector(injector);
        return sagaFactory;
    }
}
