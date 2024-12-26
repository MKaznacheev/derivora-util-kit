/*
 * This file is part of Derivora Util Kit.
 *
 * Derivora Util Kit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Derivora Util Kit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Derivora Util Kit. If not, see https://www.gnu.org/licenses/lgpl-3.0.html.
 */

package xyz.derivora.utilkit.lazy.singleton;

import java.util.concurrent.ExecutionException;

/**
 * An abstract base class for a lazily initialized singleton.
 * <p>
 * This class provides a framework for implementing the {@link LazySingleton}
 * interface, delegating the actual initialization logic to subclasses.
 * </p>
 *
 * @param <T> the type of the singleton instance
 */
public abstract class AbstractLazySingleton<T> implements LazySingleton<T> {

    /**
     * The lazily initialized singleton instance.
     */
    protected T instance;

    /**
     * Resolves the lazily initialized singleton instance.
     * <p>
     * Ensures the instance is initialized before returning it. If an error
     * occurs during initialization, it throws an {@link ExecutionException}.
     * </p>
     *
     * @return the singleton instance
     * @throws ExecutionException if an error occurs during initialization
     */
    @Override
    public final T resolve() throws ExecutionException {
        try {
            initializeInstanceIfNeeded();
        } catch (Exception e) {
            throw new ExecutionException("Error while resolving lazy instance", e);
        }

        return instance;
    }

    /**
     * Initializes the singleton instance if it has not been initialized yet.
     * Subclasses must override this method to provide the specific initialization logic.
     */
    protected abstract void initializeInstanceIfNeeded();
}
