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

import org.junit.jupiter.api.*;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

@Tag("xyz/derivora/utilkit/lazy/value")
@Tag("base-test")
@DisplayName("Base tests for constant suppliers")
@Disabled("This is a base test class and should not be executed directly")
abstract class AbstractConstantSupplierTest {

    protected abstract <T> LazyValue<T> createConstantSupplier(Supplier<T> supplier);

    @Test
    @DisplayName("Constructor should throw NullPointerException when supplier is null")
    void constructor_withNullSupplier_shouldThrowNullPointerException() {
        assertThrows(
                NullPointerException.class,
                () -> createConstantSupplier(null)
        );
    }

    @Test
    @DisplayName("Should throw ExecutionException when supplier throws RuntimeException")
    void resolve_whenSupplierFails_shouldThrowExecutionException() {
        Supplier<Object> supplier = () -> {
            throw new RuntimeException();
        };
        LazyValue<Object> constantSupplier = createConstantSupplier(supplier);

        assertThrows(
                ExecutionException.class,
                constantSupplier::resolve
        );
    }

    @Test
    @DisplayName("Should not throw Exception when supplier does not throw Exception")
    void resolve_whenSupplierSucceeds_shouldNotThrowException() {
        Supplier<Object> supplier = () -> "value";
        LazyValue<Object> constantSupplier = createConstantSupplier(supplier);
        assertDoesNotThrow(constantSupplier::resolve);
    }

    @Test
    @DisplayName("Should return empty optional value when supplier returns null value")
    void resolve_whenSupplierReturnsNull_shouldReturnEmptyValue() throws ExecutionException {
        Supplier<Object> supplier = () -> null;
        LazyValue<Object> constantSupplier = createConstantSupplier(supplier);
        Optional<Object> optionalValue = constantSupplier.resolve();
        assertEquals(Optional.empty(), optionalValue);
    }

    @Test
    @DisplayName("Should return optional value when supplier returns non-null value")
    void resolve_whenSupplierReturnsNonNull_shouldReturnValue() throws ExecutionException {
        String value = "value";
        Supplier<String> supplier = () -> value;
        LazyValue<String> constantSupplier = createConstantSupplier(supplier);
        Optional<String> optionalValue = constantSupplier.resolve();
        assertEquals(Optional.of(value), optionalValue);
    }
}
