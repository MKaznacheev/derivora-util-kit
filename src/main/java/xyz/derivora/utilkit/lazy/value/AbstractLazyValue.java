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

package xyz.derivora.utilkit.lazy.value;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * Abstract implementation of the {@link LazyValue} interface.
 * <p>
 * Provides a mechanism for lazy initialization of a value,
 * delegating the actual initialization logic to subclasses.
 * </p>
 *
 * @param <T> the type of the value
 */
public abstract class AbstractLazyValue<T> implements LazyValue<T> {

    /**
     * The lazily initialized value.
     */
    protected T value;

    /**
     * Resolves the value by ensuring it is initialized, if necessary.
     * Subclasses must implement the logic for initializing the value.
     *
     * @return an {@link Optional} containing the value if it is not {@code null};
     *         an empty {@link Optional} otherwise
     * @throws ExecutionException if an error occurs during initialization
     */
    @Override
    public final Optional<T> resolve() throws ExecutionException {
        try {
            initializeValueIfNeeded();
        } catch (Exception e) {
            throw new ExecutionException("Error while resolving lazy value", e);
        }

        return Optional.ofNullable(value);
    }

    /**
     * Initializes the value if it has not been initialized yet.
     * Subclasses must override this method to provide the specific initialization logic.
     */
    protected abstract void initializeValueIfNeeded();
}
