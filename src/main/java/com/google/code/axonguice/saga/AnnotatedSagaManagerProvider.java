/*
 * Copyright (c) 2013, i-Free. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.google.code.axonguice.saga;

import com.google.inject.Injector;
import com.google.inject.Provider;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.saga.SagaFactory;
import org.axonframework.saga.SagaManager;
import org.axonframework.saga.SagaRepository;
import org.axonframework.saga.annotation.AbstractAnnotatedSaga;
import org.axonframework.saga.annotation.AnnotatedSagaManager;

import javax.inject.Inject;
import java.util.Collection;

/**
 * SagaManagerProvider - TODO: description
 *
 * @author Alexey Krylov (AleX)
 * @since 14.02.13
 */
public class AnnotatedSagaManagerProvider implements Provider<SagaManager> {

	/*===========================================[ INSTANCE VARIABLES ]===========*/

    protected Injector injector;
    protected SagaRepository sagaRepository;
    protected EventBus eventBus;
    protected SagaFactory sagaFactory;
    protected Class<? extends AbstractAnnotatedSaga>[] sagaTypes;

	/*===========================================[ CONSTRUCTORS ]=================*/

    public AnnotatedSagaManagerProvider(Collection<Class<? extends AbstractAnnotatedSaga>> sagaTypes) {
        this.sagaTypes = sagaTypes.toArray(new Class[sagaTypes.size()]);
    }

    @Inject
    protected void init(Injector injector, SagaRepository sagaRepository, EventBus eventBus, SagaFactory sagaFactory) {
        this.injector = injector;
        this.sagaRepository = sagaRepository;
        this.eventBus = eventBus;
        this.sagaFactory = sagaFactory;
    }

	/*===========================================[ INTERFACE METHODS ]============*/

    @Override
    public SagaManager get() {
        AnnotatedSagaManager annotatedSagaManager = new AnnotatedSagaManager(sagaRepository, sagaFactory, eventBus, sagaTypes);
        // support for SagaManager @PostConstruct
        injector.injectMembers(annotatedSagaManager);
        return annotatedSagaManager;
    }
}
