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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

@Tag("xyz/derivora/utilkit/lazy/value")
@DisplayName("Tests for ConstantSupplier")
class ConstantSupplierTest extends AbstractConstantSupplierTest {

    @Override
    protected <T> LazyValue<T> createConstantSupplier(Supplier<T> supplier) {
        return new ConstantSupplier<>(supplier);
    }

    @Test
    @DisplayName("Supplier should be cleared after the first resolve call")
    void resolve_always_shouldClearSupplier() throws ExecutionException {
        Supplier<String> supplier = () -> "value";
        ConstantSupplier<String> constantSupplier = new ConstantSupplier<>(supplier);

        constantSupplier.resolve(); // First resolve call
        assertNull(constantSupplier.supplier); // Supplier should be cleared
    }
}
