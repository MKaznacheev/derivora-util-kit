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

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

@Tag("xyz/derivora/utilkit/lazy/singleton")
@Tag("base-test")
@DisplayName("Base tests for singleton suppliers")
@Disabled("This is a base test class and should not be executed directly")
abstract class AbstractSingletonSupplierTest {

    protected abstract <T> LazySingleton<T> createSingletonSupplier(Supplier<T> supplier);

    @Test
    @DisplayName("Constructor should throw NullPointerException when supplier is null")
    void constructor_withNullSupplier_shouldThrowNullPointerException() {
        assertThrows(
                NullPointerException.class,
                () -> createSingletonSupplier(null)
        );
    }

    @Test
    @DisplayName("Should throw ExecutionException when supplier throws RuntimeException")
    void resolve_whenSupplierFails_shouldThrowExecutionException() {
        Supplier<Object> supplier = () -> {
            throw new RuntimeException();
        };
        LazySingleton<Object> singletonSupplier = createSingletonSupplier(supplier);

        assertThrows(
                ExecutionException.class,
                singletonSupplier::resolve
        );
    }

    @Test
    @DisplayName("Should not throw Exception when supplier does not throw Exception")
    void resolve_whenSupplierSucceeds_shouldNotThrowException() {
        Supplier<Object> supplier = () -> "value";
        LazySingleton<Object> singletonSupplier = createSingletonSupplier(supplier);
        assertDoesNotThrow(singletonSupplier::resolve);
    }

    @Test
    @DisplayName("Should throw ExecutionException when supplier returns null value")
    void resolve_whenSupplierReturnsNull_shouldThrowExecutionException() {
        Supplier<Object> supplier = () -> null;
        LazySingleton<Object> singletonSupplier = createSingletonSupplier(supplier);
        assertThrows(
                ExecutionException.class,
                singletonSupplier::resolve
        );
    }

    @Test
    @DisplayName("Should return optional value when supplier returns non-null value")
    void resolve_whenSupplierReturnsNonNull_shouldReturnValue() throws ExecutionException {
        String value = "value";
        Supplier<String> supplier = () -> value;
        LazySingleton<String> singletonSupplier = createSingletonSupplier(supplier);
        String singleton = singletonSupplier.resolve();
        assertEquals(value, singleton);
    }
}
