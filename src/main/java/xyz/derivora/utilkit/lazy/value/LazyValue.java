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
 * Represents a lazily initialized value that is computed only when accessed.
 * <p>
 * This interface is particularly useful for saving computational resources
 * or memory when the value may not be needed immediately or at all.
 * </p>
 *
 * @param <T> the type of the value
 */
public interface LazyValue<T> {

    /**
     * Resolves the lazy value and returns it as an {@link Optional}.
     * <p>
     * If the value could not be computed due to an error, an {@link ExecutionException} is thrown.
     * This ensures that the caller is aware of any issues during the computation process.
     * </p>
     *
     * @return an {@link Optional} containing the value if it was successfully computed;
     *         an empty {@link Optional} if the value is {@code null}.
     * @throws ExecutionException if an error occurs during value computation
     */
    Optional<T> resolve() throws ExecutionException;
}
