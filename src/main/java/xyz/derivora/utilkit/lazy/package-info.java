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
 * Provides utilities for lazy initialization and resource management.
 * <p>
 * The classes and interfaces in this package and its subpackages are designed to
 * facilitate efficient resource usage through lazy initialization techniques. These utilities
 * help to defer expensive computations or memory allocation until the resources are
 * actually needed.
 * </p>
 *
 * <h2>Key Features</h2>
 * <ul>
 *     <li>Support for lazy value initialization ({@link xyz.derivora.utilkit.lazy.value}).</li>
 *     <li>Lazy initialization for singleton objects ({@link xyz.derivora.utilkit.lazy.singleton}).</li>
 *     <li>Thread-safe and single-threaded implementations to suit various use cases.</li>
 * </ul>
 *
 * <h2>Subpackages</h2>
 * <ul>
 *     <li>{@link xyz.derivora.utilkit.lazy.value} - Contains utilities for lazily initialized values.</li>
 *     <li>{@link xyz.derivora.utilkit.lazy.singleton} - Provides utilities for managing lazily initialized singleton objects.</li>
 * </ul>
 *
 * <h2>Usage Examples</h2>
 * <p>Example: Lazily initialized singleton object (thread-safe):</p>
 * <pre>{@code
 * LazySingleton<String> threadSafeSingleton = new ConcurrentSingletonSupplier<>(() -> "Hello, Lazy World!");
 * String instance = threadSafeSingleton.resolve();
 * }</pre>
 *
 * <p>Example: Lazily initialized value:</p>
 * <pre>{@code
 * LazyValue<Integer> lazyValue = new ConstantSupplier<>(() -> computeExpensiveValue());
 * Optional<Integer> result = lazyValue.resolve();
 * }</pre>
 *
 * <h2>Design Philosophy</h2>
 * <p>
 * This package is designed to simplify the implementation of lazy initialization,
 * promoting better resource management and performance optimization. The tools
 * provided here are flexible and easy to integrate into both single-threaded and
 * multithreaded environments.
 * </p>
 *
 * @see xyz.derivora.utilkit.lazy.value
 * @see xyz.derivora.utilkit.lazy.singleton
 */
package xyz.derivora.utilkit.lazy;