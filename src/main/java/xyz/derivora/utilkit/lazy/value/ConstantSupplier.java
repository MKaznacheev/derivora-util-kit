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

import java.util.Objects;
import java.util.function.Supplier;

/**
 * A lazily initialized value that uses a {@link Supplier} to compute the value only once.
 * <p>
 * This implementation is designed for single-threaded use cases. The value is computed
 * on the first access and the supplier is released afterward to save memory.
 * </p>
 *
 * @param <T> the type of the value
 */
public class ConstantSupplier<T> extends AbstractLazyValue<T> {

    /**
     * A supplier to compute the value.
     */
    protected Supplier<T> supplier;

    /**
     * Constructs a {@code ConstantSupplier} with the given supplier.
     *
     * @param supplier the supplier to provide the value; must not be {@code null}
     * @throws NullPointerException if the supplier is {@code null}
     */
    public ConstantSupplier(Supplier<T> supplier) {
        this.supplier = Objects.requireNonNull(supplier, "Supplier cannot be null");
    }

    /**
     * Initializes the value lazily using the supplier if it has not been initialized yet.
     * <p>
     * This method is intended for single-threaded environments and does not provide
     * thread-safety guarantees.
     * </p>
     */
    @Override
    protected void initializeValueIfNeeded() {
        if (supplier != null) {
            value = supplier.get();
            supplier = null; // Clear the supplier to save memory
        }
    }
}
