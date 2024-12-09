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

package xyz.derivora.utilkit.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@Tag("xyz/derivora/utilkit/validation")
@DisplayName("Tests for ValidationUtil")
class ValidationUtilsTest {

    @Nested
    @Tag("xyz/derivora/utilkit/validation")
    @Tag("array")
    @DisplayName("Tests for arrays")
    class ArrayTests {

        @Test
        @DisplayName("Should throw NullPointerException when array is null")
        void requireAllNonNull_whenArrayIsNull_shouldThrowNullPointerException() {
            Object[] objects = null;
            assertThrows(
                    NullPointerException.class,
                    () -> ValidationUtils.requireAllNonNull(objects)
            );
        }

        @Test
        @DisplayName("Should throw ValidationException when an element in the array is null")
        void requireAllNonNull_whenArrayHasNullElement_shouldThrowValidationException() {
            String customMessage = "Custom message";
            ValidationException exception = assertThrows(
                    ValidationException.class,
                    () -> ValidationUtils.requireAllNonNull(customMessage, new Object(), null, new Object())
            );
            assertEquals(customMessage, exception.getMessage());
        }

        @Test
        @DisplayName("Should not throw exception when all elements in the array are non-null")
        void requireAllNonNull_whenAllArrayElementsAreNonNull_shouldNotThrowException() {
            assertDoesNotThrow(
                    () -> ValidationUtils.requireAllNonNull(new Object(), new Object(), new Object())
            );
        }
    }

    @Nested
    @Tag("xyz/derivora/utilkit/validation")
    @Tag("collection")
    @DisplayName("Tests for collections")
    class CollectionTests {

        @Test
        @DisplayName("Should throw NullPointerException when collection is null")
        void requireAllNonNull_whenCollectionIsNull_shouldThrowNullPointerException() {
            Collection<Object> objects = null;
            assertThrows(
                    NullPointerException.class,
                    () -> ValidationUtils.requireAllNonNull(objects)
            );
        }

        @Test
        @DisplayName("Should throw ValidationException when an element in the collection is null")
        void requireAllNonNull_whenCollectionHasNullElement_shouldThrowValidationException() {
            String customMessage = "Custom message";
            Collection<Object> objects = Arrays.asList(new Object(), null, new Object());
            ValidationException exception = assertThrows(
                    ValidationException.class,
                    () -> ValidationUtils.requireAllNonNull(customMessage, objects)
            );
            assertEquals(customMessage, exception.getMessage());
        }

        @Test
        @DisplayName("Should not throw exception when all elements in the collection are non-null")
        void requireAllNonNull_whenAllCollectionElementsAreNonNull_shouldNotThrowException() {
            Collection<Object> objects = Arrays.asList(new Object(), new Object(), new Object());
            assertDoesNotThrow(
                    () -> ValidationUtils.requireAllNonNull(objects)
            );
        }
    }
}
