/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.webinv.service.container;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Krzysztof Kardasz
 */
public class ServiceContainer {
    private Map<String, ServiceProvider> container = new HashMap<>();

    /**
     * Register service provider
     *
     * @param factory
     * @return
     */
    public ServiceContainer register(ServiceProvider<?> factory) {
        if (!container.containsKey(factory.provides().getName())) {
            container.put(factory.provides().getName(), factory);
        }

        return this;
    }

    /**
     * Check that service exists
     *
     * @param service
     * @return
     */
    public boolean has(Class service) {
        return container.containsKey(service.getName());
    }

    /**
     * Return service instance
     *
     * @param service
     * @param <T>
     * @return
     * @throws ServiceNotFoundException
     */
    public <T> T get(Class<T> service) throws ServiceNotFoundException {
        if (!container.containsKey(service.getName())) {
            throw new ServiceNotFoundException(
                String.format("Service: %s not found", service.getName())
            );
        }

        return (T) container.get(service.getName()).provide(this);
    }
}
