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

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.stream.Stream;

/**
 * A utility class for performing operations on arrays.
 * <p>
 * The {@code ArrayUtils} class provides static methods for common operations on arrays,
 * such as merging arrays while determining a common component type. These methods are designed
 * to handle arrays with generic types and provide robust error handling for cases where the
 * input arrays are null or incompatible.
 * </p>
 *
 *
 * <h2>Usage Examples:</h2>
 * <p>
 * Merging two compatible arrays:
 * <pre>
 * {@code
 * Integer[] integers = {1, 2, 3};
 * Double[] doubles = {4.5, 5.5};
 * Number[] merged = ArrayUtils.merge(integers, doubles);
 * // merged contains {1, 2, 3, 4.5, 5.5} with a component type of Number
 * }
 * </pre>
 * </p>
 * <p>
 * Attempting to merge arrays with incompatible component types:
 * <pre>
 * {@code
 * String[] strings = {"a", "b"};
 * Object[] merged = ArrayUtils.merge(integers, strings);
 * // Throws ClassCastException because Integer and String only share Object as a supertype,
 * // and neither is assignable from the other
 * }
 * </pre>
 * </p>
 * <p>
 * Merging arrays with a manually specified component type:
 * <pre>
 * {@code
 * String[] strings = {"a", "b"};
 * Object[] merged = ArrayUtils.merge(integers, strings, Object[]::new);
 * // merged contains {1, 2, 3, "a", "b"} with a component type of Object
 * }
 * </pre>
 * </p>
 *
 */
public final class ArrayUtils {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private ArrayUtils() {
    }

    /**
     * Merges two arrays into a single array.
     * <p>
     * This method combines the elements of two input arrays and returns a new array containing all elements.
     * The resulting array is created using the provided generator function to define the array type.
     * </p>
     *
     * Example usage:
     * <pre>
     * {@code
     * Number[] first = {1.5, 2.5};
     * Integer[] second = {3, 4};
     * Number[] merged = merge(first, second, Number[]::new);
     * // merged contains {1.5, 2.5, 3, 4}
     * }
     * </pre>
     *
     * @param <T> the type of the elements in the arrays
     * @param firstArray the first array to merge; must not be {@code null}
     * @param secondArray the second array to merge; must not be {@code null}
     * @param generator a function to create an array of type {@code T}; must not be {@code null}
     * @return a new array containing all elements of {@code firstArray} followed by all elements of {@code secondArray}
     * @throws NullPointerException if {@code firstArray}, {@code secondArray}, or {@code generator} is {@code null}
     * @throws ArrayStoreException if the generator produces an array type incompatible with the element type {@code T}
     * <p>
     */
    public static <T> T[] merge(T[] firstArray, T[] secondArray, IntFunction<T[]> generator) {
        Objects.requireNonNull(firstArray, "First array cannot be null");
        Objects.requireNonNull(secondArray, "Second array cannot be null");
        Objects.requireNonNull(generator, "Generator cannot be null");

        Stream<T> firstStream = Arrays.stream(firstArray);
        Stream<T> secondStream = Arrays.stream(secondArray);

        try {
            return Stream.concat(firstStream, secondStream).toArray(generator);
        } catch (ArrayStoreException e) {
            throw new ArrayStoreException("Type mismatch: generator produced an incompatible array");
        }
    }

    /**
     * Merges two arrays into a single array.
     * <p>
     * This method combines the elements of two input arrays into a new array. The resulting array's
     * component type is determined as the most specific common type of the component types of the input arrays:
     * <ul>
     * <li>If the component type of one array is assignable from the component type of the other array,
     * the resulting array will use the more specific of the two types as its component type.</li>
     * <li>If the component types of the input arrays do not have a direct relationship but share a common
     * supertype, and that supertype is not one of the input array's component types, the method throws
     * a {@code ClassCastException}.</li>
     * </ul>
     * </p>
     *
     * Example usage:
     * <pre>
     * {@code
     * Integer[] first = {1, 2, 3};
     * Double[] second = {4.5, 5.5};
     * Number[] merged = merge(first, second);
     * // merged contains {1, 2, 3, 4.5, 5.5} with a component type of Number
     *
     * String[] strings = {"a", "b"};
     * Object[] objects = merge(first, strings);
     * // throws ClassCastException because Integer and String only share Object as a supertype, and neither is assignable from the other
     * }
     * </pre>
     *
     * @param <T> the type of the elements in the arrays
     * @param firstArray the first array to merge; must not be {@code null}
     * @param secondArray the second array to merge; must not be {@code null}
     * @return a new array containing all elements of {@code firstArray} followed by all elements of {@code secondArray}.
     *         The array's component type will be determined based on the described rules.
     * @throws NullPointerException if {@code firstArray} or {@code secondArray} is {@code null}
     * @throws ClassCastException if the component types of the input arrays are incompatible
     * <p>
     */
    public static <T> T[] merge(T[] firstArray, T[] secondArray) {
        Objects.requireNonNull(firstArray, "First array cannot be null");
        Objects.requireNonNull(secondArray, "Second array cannot be null");

        Class<? extends T> commonComponentType = getCommonComponentType(firstArray, secondArray);

        @SuppressWarnings("unchecked")
        T[] resultArray = (T[]) Array.newInstance(
                commonComponentType,
                firstArray.length + secondArray.length
        );

        IntFunction<T[]> generator = size -> resultArray;
        return merge(firstArray, secondArray, generator);
    }

    /**
     * Determines the common component type of two arrays.
     * <p>
     * This method returns the component type that is compatible with both input arrays.
     * The common component type will be either the component type of the first array or
     * the component type of the second array, depending on which one can be assigned from
     * the other.
     * </p>
     * <p>
     * If the component types of the two arrays do not have a direct relationship
     * (e.g., they share a common supertype that is neither the type of the first array
     * nor the type of the second array), the method throws a {@code ClassCastException}.
     * </p>
     *
     * Example usage:
     * <pre>
     * {@code
     * Integer[] first = {1, 2, 3};
     * Number[] second = {4.5, 5.5};
     * Class<? extends Number> commonType = getCommonComponentType(first, second);
     * // commonType will be Number.class
     * }
     * </pre>
     *
     * @param <T> the type of the elements in the arrays
     * @param firstArray the first array whose component type is to be compared
     * @param secondArray the second array whose component type is to be compared
     * @return the common component type of the two arrays, which will be either the component type
     *         of the first array or the component type of the second array
     * @throws ClassCastException if the component types of the two arrays are incompatible
     *         and cannot be assigned from one to the other
     * <p>
     */
    private static <T> Class<? extends T> getCommonComponentType(T[] firstArray, T[] secondArray) {
        @SuppressWarnings("unchecked")
        Class<? extends T> firstComponentType = (Class<? extends T>) firstArray.getClass().getComponentType();

        @SuppressWarnings("unchecked")
        Class<? extends T> secondComponentType = (Class<? extends T>) secondArray.getClass().getComponentType();

        Class<? extends T> commonComponentType;
        if (firstComponentType.isAssignableFrom(secondComponentType)) {
            commonComponentType = firstComponentType;
        } else if (secondComponentType.isAssignableFrom(firstComponentType)) {
            commonComponentType = secondComponentType;
        } else {
            String message = String.format(
                    "Incompatible array component types: %s and %s",
                    firstComponentType.getTypeName(),
                    secondComponentType.getTypeName()
            );
            throw new ClassCastException(message);
        }

        return commonComponentType;
    }
}
