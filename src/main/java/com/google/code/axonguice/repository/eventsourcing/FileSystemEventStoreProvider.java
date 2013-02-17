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

package com.google.code.axonguice.repository.eventsourcing;

import com.google.inject.Provider;
import org.axonframework.eventstore.SnapshotEventStore;
import org.axonframework.eventstore.fs.FileSystemEventStore;
import org.axonframework.eventstore.fs.SimpleEventFileResolver;

import java.io.File;

/**
 * Provides {@link FileSystemEventStore} as an {@link SnapshotEventStore} implementation.
 *
 * @author Alexey Krylov
 * @see EventSourcedRepositoryModule#bindEventStore()
 * @since 07.02.13
 */
public class FileSystemEventStoreProvider implements Provider<SnapshotEventStore> {

    /*===========================================[ INTERFACE METHODS ]============*/

    @Override
    public SnapshotEventStore get() {
        File tempFile = new File(System.getProperty("user.dir"), "axonguice-eventstore");
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }
        return new FileSystemEventStore(new SimpleEventFileResolver(tempFile));
    }
}