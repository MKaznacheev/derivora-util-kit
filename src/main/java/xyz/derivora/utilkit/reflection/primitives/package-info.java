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
 * Provides utilities for working with Java's primitive types and their corresponding wrapper classes.
 * <p>
 * This package contains utility classes that allow checking whether a class is a primitive or a wrapper,
 * retrieving the corresponding primitive or wrapper type, and obtaining minimum and maximum values
 * for numeric primitive types.
 * <p>
 * The classes in this package are designed as static utilities and cannot be instantiated.
 *
 * <h2>Included Classes:</h2>
 * <ul>
 *     <li>{@link xyz.derivora.utilkit.reflection.primitives.PrimitiveTypeMapper} -
 *         General utility for working with primitive and wrapper types, providing type checks, conversions,
 *         and retrieval of min/max values.</li>
 *     <li>{@link xyz.derivora.utilkit.reflection.primitives.PrimitiveTypeReflector} -
 *         Alternative implementation with a focus on reflection-based operations.</li>
 * </ul>
 *
 * <h2>Features:</h2>
 * <ul>
 *     <li>Check if a class is a primitive or a wrapper
 *         ({@link xyz.derivora.utilkit.reflection.primitives.PrimitiveTypeMapper#isPrimitive(Class)},
 *         {@link xyz.derivora.utilkit.reflection.primitives.PrimitiveTypeMapper#isWrapperClass(Class)},
 *         {@link xyz.derivora.utilkit.reflection.primitives.PrimitiveTypeMapper#isPrimitiveOrWrapper(Class)})</li>
 *     <li>Retrieve the primitive type for a wrapper
 *         ({@link xyz.derivora.utilkit.reflection.primitives.PrimitiveTypeMapper#getPrimitiveFor(Class)})</li>
 *     <li>Retrieve the wrapper class for a primitive
 *         ({@link xyz.derivora.utilkit.reflection.primitives.PrimitiveTypeMapper#getWrapperFor(Class)})</li>
 *     <li>Retrieve the min and max values for numeric primitive types
 *         ({@link xyz.derivora.utilkit.reflection.primitives.PrimitiveTypeMapper#getMinValueFor(Class)},
 *         {@link xyz.derivora.utilkit.reflection.primitives.PrimitiveTypeMapper#getMaxValueFor(Class)})</li>
 * </ul>
 *
 * <p><b>Note:</b> {@code Void.class} is explicitly excluded from being considered a valid wrapper type.</p>
 */
package xyz.derivora.utilkit.reflection.primitives;