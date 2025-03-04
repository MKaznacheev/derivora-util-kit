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

package xyz.derivora.utilkit.arrays;

/**
 * A functional interface for creating arrays of a given length.
 *
 * <p>Serves as a concise and readable alternative to {@code IntFunction<T[]>}.</p>
 *
 * @param <T> the type of array elements
 */
@FunctionalInterface
public interface ArrayGenerator<T> {

    /**
     * Creates an array of the specified length.
     *
     * @param length the length of the array
     * @return an array of type {@code T[]} with the given length
     * @throws NegativeArraySizeException if {@code length} is negative
     */
    T[] generate(int length);
}
