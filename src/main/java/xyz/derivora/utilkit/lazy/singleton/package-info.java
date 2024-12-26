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

/**
 * Provides utilities for lazy initialization of singleton objects.
 * <p>
 * The classes and interfaces in this package are designed to simplify
 * the creation and management of singleton instances that are initialized
 * lazily. These implementations help optimize resource usage by delaying
 * the instantiation of objects until they are needed.
 * </p>
 *
 * <h2>Core Components</h2>
 * <ul>
 *     <li>{@link xyz.derivora.utilkit.lazy.singleton.LazySingleton} - The primary interface defining a contract for lazy singleton initialization.</li>
 *     <li>{@link xyz.derivora.utilkit.lazy.singleton.AbstractLazySingleton} - A base class providing a framework for lazy singleton initialization.</li>
 *     <li>{@link xyz.derivora.utilkit.lazy.singleton.SingletonSupplier} - A single-threaded implementation of {@link xyz.derivora.utilkit.lazy.singleton.LazySingleton} using a {@link java.util.function.Supplier}.</li>
 *     <li>{@link xyz.derivora.utilkit.lazy.singleton.ConcurrentSingletonSupplier} - A thread-safe implementation of {@link xyz.derivora.utilkit.lazy.singleton.LazySingleton} using a {@link java.util.function.Supplier}.</li>
 * </ul>
 *
 * <h2>Usage Examples</h2>
 * <pre>{@code
 * // Example: Single-threaded lazy singleton
 * LazySingleton<String> lazySingleton = new SingletonSupplier<>(() -> "Hello, Singleton!");
 * String instance = lazySingleton.resolve();
 *
 * // Example: Thread-safe lazy singleton
 * LazySingleton<Integer> threadSafeSingleton = new ConcurrentSingletonSupplier<>(() -> computeExpensiveValue());
 * Integer result = threadSafeSingleton.resolve();
 * }</pre>
 *
 * <h2>Design Considerations</h2>
 * <p>
 * The utilities in this package are particularly useful when:
 * </p>
 * <ul>
 *     <li>You need to ensure a single instance of an object is created and used.</li>
 *     <li>Object initialization is resource-intensive and should be delayed until first access.</li>
 *     <li>Thread-safety is required for singleton initialization.</li>
 * </ul>
 *
 * <h2>Thread Safety</h2>
 * <p>
 * For multithreaded environments, prefer using {@link xyz.derivora.utilkit.lazy.singleton.ConcurrentSingletonSupplier}, which ensures thread-safe lazy initialization.
 * The {@link xyz.derivora.utilkit.lazy.singleton.SingletonSupplier} is intended for single-threaded use only.
 * </p>
 *
 * @see xyz.derivora.utilkit.lazy.singleton.LazySingleton
 * @see xyz.derivora.utilkit.lazy.singleton.AbstractLazySingleton
 * @see xyz.derivora.utilkit.lazy.singleton.SingletonSupplier
 * @see xyz.derivora.utilkit.lazy.singleton.ConcurrentSingletonSupplier
 */
package xyz.derivora.utilkit.lazy.singleton;