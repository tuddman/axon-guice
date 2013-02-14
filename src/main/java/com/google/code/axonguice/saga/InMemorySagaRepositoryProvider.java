/*
 * Copyright (c) 2013, i-Free. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.google.code.axonguice.saga;

import com.google.inject.Provider;
import org.axonframework.saga.SagaRepository;
import org.axonframework.saga.repository.inmemory.InMemorySagaRepository;

/**
 * SagaRepositoryProvider - TODO: description
 *
 * @author Alexey Krylov (AleX)
 * @since 14.02.13
 */
public class InMemorySagaRepositoryProvider implements Provider<SagaRepository> {

	/*===========================================[ INTERFACE METHODS ]============*/

    @Override
    public SagaRepository get() {
        return new InMemorySagaRepository();
    }
}