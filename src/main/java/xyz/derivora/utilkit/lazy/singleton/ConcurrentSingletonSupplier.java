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

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * A thread-safe lazy singleton implementation using a {@link Supplier}.
 * <p>
 * This class ensures that the singleton instance is initialized only once,
 * even in a multithreaded environment. After the instance is initialized,
 * the supplier is cleared to save memory. If the supplier returns {@code null},
 * an {@link ExecutionException} is thrown, wrapping an {@link IllegalStateException}.
 * </p>
 *
 * @param <T> the type of the singleton instance
 */
public class ConcurrentSingletonSupplier<T> extends AbstractLazySingleton<T> {

    /**
     * The supplier used to lazily initialize the singleton instance.
     * Marked as {@code volatile} to ensure visibility across threads.
     */
    protected volatile Supplier<T> supplier;

    /**
     * Constructs a {@code ConcurrentSingletonSupplier} with the given supplier.
     *
     * @param supplier the supplier to provide the singleton instance; must not be {@code null}
     * @throws NullPointerException if the supplier is {@code null}
     */
    public ConcurrentSingletonSupplier(Supplier<T> supplier) {
        this.supplier = Objects.requireNonNull(supplier, "Supplier cannot be null");
    }

    /**
     * Lazily initializes the singleton instance in a thread-safe manner using the supplier.
     * <p>
     * If the supplier returns {@code null}, an {@link IllegalStateException} is thrown.
     * </p>
     *
     * @throws IllegalStateException if the supplier returns {@code null}
     */
    @Override
    protected void initializeInstanceIfNeeded() {
        if (supplier == null) {
            return; // Already initialized
        }

        synchronized (this) {
            if (supplier == null) {
                return; // Double-check
            }

            instance = supplier.get();
            if (instance == null){
                throw new IllegalStateException("Supplier returned null, but a non-null value is required.");
            }

            supplier = null; // Clear supplier to save memory
        }
    }
}
