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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.*;

@Tag("xyz/derivora/utilkit/arrays")
@DisplayName("Tests for ArrayUtils")
class ArrayUtilsTest {

    @Nested
    @Tag("xyz/derivora/utilkit/arrays")
    @Tag("custom-generator")
    @DisplayName("Tests with custom generator")
    class CustomGeneratorTests {

        @Test
        @DisplayName("Should throw NullPointerException when first array is null")
        void merge_whenFirstArrayIsNull_shouldThrowNullPointerException() {
            Number[] first = null;
            Integer[] second = {3, 4};
            assertThrows(
                    NullPointerException.class,
                    () -> ArrayUtils.merge(first, second, Number[]::new)
            );
        }

        @Test
        @DisplayName("Should throw NullPointerException when second array is null")
        void merge_whenSecondArrayIsNull_shouldThrowNullPointerException() {
            Number[] first = {1.5, 2.5};
            Integer[] second = null;
            assertThrows(
                    NullPointerException.class,
                    () -> ArrayUtils.merge(first, second, Number[]::new)
            );
        }

        @Test
        @DisplayName("Should throw NullPointerException when generator is null")
        void merge_whenGeneratorIsNull_shouldThrowNullPointerException() {
            Number[] first = {1.5, 2.5};
            Integer[] second = {3, 4};
            assertThrows(
                    NullPointerException.class,
                    () -> ArrayUtils.merge(first, second, null)
            );
        }

        @Test
        @DisplayName("Should throw ArrayStoreException when generator produces incompatible array")
        void merge_whenGeneratorProducesIncompatibleArray_shouldThrowArrayStoreException() {
            Number[] first = {1.5, 2.5};
            Object[] second = {new Object(), new Object()};
            assertThrows(
                    ArrayStoreException.class,
                    () -> ArrayUtils.merge(first, second, Serializable[]::new)
            );
        }

        @Test
        @DisplayName("Should merge arrays with same component types into a single array")
        void merge_withSameComponentTypes_shouldMergeSuccessfully() {
            Integer[] first = {1, 2};
            Integer[] second = {3, 4};
            Object[] merged = ArrayUtils.merge(first, second, Integer[]::new);
            Object[] expected = {1, 2, 3, 4};
            assertArrayEquals(expected, merged);
        }

        @Test
        @DisplayName("Should merge arrays with different component types into a single array")
        void merge_withDifferentComponentTypes_shouldMergeSuccessfully() {
            Number[] first = {1.5, 2.5};
            String[] second = {"a", "b"};
            Object[] merged = ArrayUtils.merge(first, second, Object[]::new);
            Object[] expected = {1.5, 2.5, "a", "b"};
            assertArrayEquals(expected, merged);
        }
    }

    @Nested
    @Tag("xyz/derivora/utilkit/arrays")
    @Tag("produced-generator")
    @DisplayName("Tests with produced generator")
    class ProducedGeneratorTests {

        @Test
        @DisplayName("Should throw NullPointerException when first array is null")
        void merge_whenFirstArrayIsNull_shouldThrowNullPointerException() {
            Number[] first = null;
            Integer[] second = {3, 4};
            assertThrows(
                    NullPointerException.class,
                    () -> ArrayUtils.merge(first, second)
            );
        }

        @Test
        @DisplayName("Should throw NullPointerException when second array is null")
        void merge_whenSecondArrayIsNull_shouldThrowNullPointerException() {
            Number[] first = {1.5, 2.5};
            Integer[] second = null;
            assertThrows(
                    NullPointerException.class,
                    () -> ArrayUtils.merge(first, second)
            );
        }

        @Test
        @DisplayName("Should merge arrays with same component types into a single array")
        void merge_whenArraysHaveSameComponentTypes_shouldMergeSuccessfully() {
            Integer[] first = {1, 2};
            Integer[] second = {3, 4};
            Object[] merged = ArrayUtils.merge(first, second);
            Object[] expected = {1, 2, 3, 4};
            assertArrayEquals(expected, merged);
        }

        @Test
        @DisplayName("Should merge arrays with different component types into a single array")
        void merge_whenArraysHaveDifferentComponentTypes_shouldMergeSuccessfully() {
            Number[] first = {1.5, 2.5};
            Integer[] second = {3, 4};
            Object[] merged = ArrayUtils.merge(first, second);
            Object[] expected = {1.5, 2.5, 3, 4};
            assertArrayEquals(expected, merged);
        }

        @Test
        @DisplayName("Should throw ClassCastException for incompatible component types")
        void merge_whenArrayComponentTypesAreIncompatible_shouldThrowClassCastException() {
            Number[] first = {1.5, 2.5};
            String[] second = {"a", "b"};
            assertThrows(
                    ClassCastException.class,
                    () -> ArrayUtils.merge(first, second)
            );
        }
    }
}
