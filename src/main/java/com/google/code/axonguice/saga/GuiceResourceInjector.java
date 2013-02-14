/*
 * Copyright (c) 2013, i-Free. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.google.code.axonguice.saga;

import com.google.inject.Injector;
import org.axonframework.saga.ResourceInjector;
import org.axonframework.saga.Saga;

import javax.inject.Inject;

/**
 * GuiceResourceInjector - TODO: description
 *
 * @author Alexey Krylov (AleX)
 * @since 14.02.13
 */
public class GuiceResourceInjector implements ResourceInjector {

	/*===========================================[ INSTANCE VARIABLES ]===========*/

    @Inject
    private Injector injector;

	/*===========================================[ INTERFACE METHODS ]============*/

    @Override
    public void injectResources(Saga saga) {
        injector.injectMembers(saga);
    }
}
