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
 * Provides utilities for lazy initialization of values.
 * <p>
 * The classes and interfaces in this package are designed to support scenarios
 * where values are computed only when accessed for the first time, which helps
 * to save resources like memory and computational effort.
 * </p>
 *
 * <h2>Core Components</h2>
 * <ul>
 *     <li>{@link xyz.derivora.utilkit.lazy.value.LazyValue} - The primary interface representing a lazily initialized value.</li>
 *     <li>{@link xyz.derivora.utilkit.lazy.value.AbstractLazyValue} - An abstract base class providing a framework for lazy initialization logic.</li>
 *     <li>{@link xyz.derivora.utilkit.lazy.value.ConstantSupplier} - A single-threaded implementation of {@link xyz.derivora.utilkit.lazy.value.LazyValue} that uses a {@link java.util.function.Supplier} to compute the value.</li>
 *     <li>{@link xyz.derivora.utilkit.lazy.value.ConcurrentConstantSupplier} - A thread-safe implementation of {@link xyz.derivora.utilkit.lazy.value.LazyValue} for concurrent environments.</li>
 * </ul>
 *
 * <h2>Usage Examples</h2>
 * <pre>{@code
 * // Single-threaded example with ConstantSupplier
 * LazyValue<String> lazyValue = new ConstantSupplier<>(() -> "Hello, World!");
 * System.out.println(lazyValue.resolve().orElse("Default Value"));
 *
 * // Multi-threaded example with ConcurrentConstantSupplier
 * LazyValue<Integer> threadSafeValue = new ConcurrentConstantSupplier<>(() -> computeExpensiveValue());
 * Integer result = threadSafeValue.resolve().orElse(0);
 * }</pre>
 *
 * <h2>Design Considerations</h2>
 * <p>
 * These utilities are designed to provide flexibility and clarity when working
 * with deferred initialization. They are particularly useful in scenarios
 * where:
 * </p>
 * <ul>
 *     <li>Expensive computations are needed but may not always be required.</li>
 *     <li>Memory optimization is critical by avoiding unnecessary allocations.</li>
 *     <li>Thread-safe lazy initialization is essential in concurrent environments.</li>
 * </ul>
 *
 * @see xyz.derivora.utilkit.lazy.value.LazyValue
 * @see xyz.derivora.utilkit.lazy.value.AbstractLazyValue
 * @see xyz.derivora.utilkit.lazy.value.ConstantSupplier
 * @see xyz.derivora.utilkit.lazy.value.ConcurrentConstantSupplier
 */
package xyz.derivora.utilkit.lazy.value;