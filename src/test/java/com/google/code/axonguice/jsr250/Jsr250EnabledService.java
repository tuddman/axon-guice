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

package com.google.code.axonguice.jsr250;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.inject.Singleton;

/**
 * ServiceWithPostConstuct - TODO: description
 *
 * @author Alexey Krylov
 * @since 08.02.13
 */
@Singleton
public class Jsr250EnabledService {

    /*===========================================[ INSTANCE VARIABLES ]===========*/

    private boolean postConstuctInvoked;
    private Jsr250Resource resource;
    private boolean preDestroyInvoked;

    /*===========================================[ CLASS METHODS ]================*/

    @PostConstruct
    protected void postConstuct() {
        postConstuctInvoked = true;
    }

    /*===========================================[ GETTER/SETTER ]================*/

    @Resource
    public void setResource(Jsr250Resource resource) {
        this.resource = resource;
    }

    public boolean isPostConstuctInvoked() {
        return postConstuctInvoked;
    }

    public boolean isResourceSet() {
        return resource != null;
    }

    @PreDestroy
    protected void preDestroy() {
        preDestroyInvoked = true;
    }

    public boolean isPreDestroyInvoked() {
        return preDestroyInvoked;
    }

    public Jsr250Resource getResource() {
        return resource;
    }
}
