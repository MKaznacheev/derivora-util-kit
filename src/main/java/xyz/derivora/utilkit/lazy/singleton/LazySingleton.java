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
 * Represents a lazily initialized singleton.
 * <p>
 * This interface defines a contract for creating and accessing a single instance
 * of a class in a lazy manner. The instance is created only when it is accessed
 * for the first time.
 * </p>
 *
 * @param <T> the type of the singleton instance
 */
public interface LazySingleton<T> {

    /**
     * Resolves the lazily initialized singleton instance.
     * <p>
     * If an error occurs during initialization, an {@link ExecutionException}
     * is thrown, allowing the caller to handle the failure appropriately.
     * </p>
     *
     * @return the singleton instance
     * @throws ExecutionException if an error occurs during initialization
     */
    T resolve() throws ExecutionException;
}
