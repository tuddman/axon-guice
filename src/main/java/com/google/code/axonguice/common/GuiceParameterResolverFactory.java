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

package com.google.code.axonguice.common;

import com.google.inject.Injector;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.common.annotation.FixedValueParameterResolver;
import org.axonframework.common.annotation.ParameterResolver;
import org.axonframework.common.annotation.ParameterResolverFactory;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.lang.annotation.Annotation;

/**
 * Guice-based parameter resolver factory. It allows to inject resources into {@link EventHandler}/{@link
 * CommandHandler} annotated methods.
 *
 * @author Alexey Krylov
 * @see ParameterResolverFactoryModule
 * @since 17.02.13
 */
public class GuiceParameterResolverFactory implements ParameterResolverFactory {

	/*===========================================[ STATIC VARIABLES ]=============*/

    private static final Logger logger = LoggerFactory.getLogger(GuiceParameterResolverFactory.class);

	/*===========================================[ INSTANCE VARIABLES ]===========*/

    @Inject
    protected Injector injector;

	/*===========================================[ INTERFACE METHODS ]============*/

    @Override
    public ParameterResolver createInstance(Annotation[] memberAnnotations, Class<?> parameterType, Annotation[] parameterAnnotations) {
        try {
            return new FixedValueParameterResolver(injector.getInstance(parameterType));
        } catch (Exception e) {
            logger.error(String.format("Unable to instantiate [%s]", parameterType), e);
            return null;
        }
    }

}
