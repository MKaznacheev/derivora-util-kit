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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Tag("xyz/derivora/utilkit/lazy/singleton")
@DisplayName("Tests for ConcurrentSingletonSupplier")
class ConcurrentSingletonSupplierTest extends AbstractSingletonSupplierTest {

    @Override
    protected <T> LazySingleton<T> createSingletonSupplier(Supplier<T> supplier) {
        return new ConcurrentSingletonSupplier<>(supplier);
    }

    @Test
    @DisplayName("Supplier should be cleared after the first resolve call")
    void resolve_always_shouldClearSupplier() throws ExecutionException {
        Supplier<String> supplier = () -> "value";
        ConcurrentSingletonSupplier<String> concurrentSingleSupplier = new ConcurrentSingletonSupplier<>(supplier);

        concurrentSingleSupplier.resolve(); // First resolve call
        assertNull(concurrentSingleSupplier.supplier); // Supplier should be cleared
    }

    @Test
    @DisplayName("Should initialize instance only once in a multithreaded environment")
    void resolve_whenConcurrentAccess_shouldInitializeOnce() throws InterruptedException, ExecutionException {
        String value = "value";
        Supplier<String> supplier = () -> value;
        ConcurrentSingletonSupplier<String> concurrentSingletonSupplier = new ConcurrentSingletonSupplier<>(supplier);

        int threadCount = 10;
        try (ExecutorService executorService = Executors.newFixedThreadPool(threadCount)) {
            List<Future<String>> results = new ArrayList<>();

            for (int i = 0; i < threadCount; i++) {
                Future<String> future = executorService.submit(concurrentSingletonSupplier::resolve);
                results.add(future);
            }
            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.SECONDS);

            // Ensure all threads get the same instance
            for (Future<String> result : results) {
                assertEquals(value, result.get());
            }
        }
    }
}
