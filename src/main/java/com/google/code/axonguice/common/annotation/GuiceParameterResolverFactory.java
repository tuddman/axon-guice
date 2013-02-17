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

package com.google.code.axonguice.common.annotation;

import com.google.inject.Injector;
import org.axonframework.common.annotation.FixedValueParameterResolver;
import org.axonframework.common.annotation.ParameterResolver;
import org.axonframework.common.annotation.ParameterResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.lang.annotation.Annotation;

/**
 * GuiceParameterResolverFactory - TODO: description
 *
 * @author Alexey Krylov
 * @since 17.02.13
 */
public class GuiceParameterResolverFactory extends ParameterResolverFactory {

	/*===========================================[ STATIC VARIABLES ]=============*/

    private static final Logger logger = LoggerFactory.getLogger(GuiceParameterResolverFactory.class);

	/*===========================================[ INSTANCE VARIABLES ]===========*/

    @Inject
    protected Injector injector;

	/*===========================================[ INTERFACE METHODS ]============*/

    @Override
    protected ParameterResolver createInstance(Annotation[] memberAnnotations, Class<?> parameterType, Annotation[] parameterAnnotations) {
        try {
            return new FixedValueParameterResolver(injector.getInstance(parameterType));
        } catch (Exception e) {
            logger.error(String.format("Unable to instantiate [%s]", parameterType), e);
            return null;
        }
    }

    @Override
    public boolean supportsPayloadResolution() {
        return false;
    }
}
