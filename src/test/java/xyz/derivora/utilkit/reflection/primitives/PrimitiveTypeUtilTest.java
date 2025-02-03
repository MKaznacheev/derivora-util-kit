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

package xyz.derivora.utilkit.reflection.primitives;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ArgumentsSources;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Tag("xyz/derivora/utilkit/reflection/primitives")
@DisplayName("Base tests for primitive type utils")
@Disabled("This is a base test class and should not be executed directly")
abstract class PrimitiveTypeUtilTest {

    protected final PrimitiveTypeUtil primitiveTypeUtil;

    protected PrimitiveTypeUtilTest(PrimitiveTypeUtil primitiveTypeUtil) {
        this.primitiveTypeUtil = primitiveTypeUtil;
    }

    @Nested
    @Tag("xyz/derivora/utilkit/reflection/primitives")
    @Tag("isPrimitive")
    @DisplayName("Tests for isPrimitive")
    class IsPrimitiveTests {

        @Test
        @DisplayName("Should throw NullPointerException when class is Null")
        void isPrimitive_withNullClass_shouldThrowNullPointerException() {
            assertThrows(
                    NullPointerException.class,
                    () -> primitiveTypeUtil.isPrimitive(null)
            );
        }

        @ParameterizedTest
        @ValueSource(classes = {Integer.class, Object.class, int[].class})
        @DisplayName("Should return false when type is not primitive")
        void isPrimitive_withNonPrimitiveType_shouldReturnFalse(Class<?> clazz) {
            boolean result = primitiveTypeUtil.isPrimitive(clazz);
            assertFalse(result);
        }

        @ParameterizedTest
        @ArgumentsSource(PrimitiveTypeProvider.class)
        @DisplayName("Should return true when type is primitive")
        void isPrimitive_withPrimitiveType_shouldReturnTrue(Class<?> clazz) {
            boolean result = primitiveTypeUtil.isPrimitive(clazz);
            assertTrue(result);
        }
    }

    @Nested
    @Tag("xyz/derivora/utilkit/reflection/primitives")
    @Tag("isWrapperClass")
    @DisplayName("Tests for isWrapperClass")
    class IsWrapperClassTests {

        @Test
        @DisplayName("Should throw NullPointerException when class is null")
        void isWrapperClass_withNullClass_shouldThrowNullPointerException() {
            assertThrows(
                    NullPointerException.class,
                    () -> primitiveTypeUtil.isWrapperClass(null)
            );
        }

        @ParameterizedTest
        @ValueSource(classes = {int.class, Object.class, Void.class, int[].class, Integer[].class})
        @DisplayName("Should return false when class is not wrapper")
        void isWrapperClass_withNonWrapperClass_shouldReturnFalse(Class<?> clazz) {
            boolean result = primitiveTypeUtil.isWrapperClass(clazz);
            assertFalse(result);
        }

        @ParameterizedTest
        @ArgumentsSource(WrapperClassProvider.class)
        @DisplayName("Should return true when class is wrapper")
        void isWrapperClass_withWrapperClass_shouldReturnTrue(Class<?> clazz) {
            boolean result = primitiveTypeUtil.isWrapperClass(clazz);
            assertTrue(result);
        }
    }

    @Nested
    @Tag("xyz/derivora/utilkit/reflection/primitives")
    @Tag("isPrimitiveOrWrapper")
    @DisplayName("Tests for isPrimitiveOrWrapper")
    class IsPrimitiveOrWrapperTests {

        @Test
        @DisplayName("Should throw NullPointerException when class is null")
        void isPrimitiveOrWrapper_withNullClass_shouldThrowNullPointerException() {
            assertThrows(
                    NullPointerException.class,
                    () -> primitiveTypeUtil.isPrimitiveOrWrapper(null)
            );
        }

        @ParameterizedTest
        @ValueSource(classes = {Object.class, Void.class, int[].class, Integer[].class})
        @DisplayName("Should return false when class is not primitive or wrapper")
        void isPrimitiveOrWrapper_withNonPrimitiveOrWrapperClass_shouldReturnFalse(Class<?> clazz) {
            boolean result = primitiveTypeUtil.isPrimitiveOrWrapper(clazz);
            assertFalse(result);
        }

        @ParameterizedTest
        @ArgumentsSources({
                @ArgumentsSource(PrimitiveTypeProvider.class),
                @ArgumentsSource(WrapperClassProvider.class)
        })
        @DisplayName("Should return true when class is primitive or wrapper")
        void isPrimitiveOrWrapper_withPrimitiveOrWrapperClass_shouldReturnTrue(Class<?> clazz) {
            boolean result = primitiveTypeUtil.isPrimitiveOrWrapper(clazz);
            assertTrue(result);
        }
    }

    @Nested
    @Tag("xyz/derivora/utilkit/reflection/primitives")
    @Tag("getPrimitiveFor")
    @DisplayName("Tests for getPrimitiveFor")
    class GetPrimitiveForTests {

        @Test
        @DisplayName("Should throw NullPointerException when class is null")
        void getPrimitiveFor_withNullClass_shouldThrowNullPointerException() {
            assertThrows(
                    NullPointerException.class,
                    () -> primitiveTypeUtil.getPrimitiveFor(null)
            );
        }

        @ParameterizedTest
        @ValueSource(classes = {Object.class, int[].class, Integer[].class})
        @DisplayName("Should return empty value when class is not wrapper")
        void getPrimitiveFor_withNonWrapperClass_shouldReturnEmptyValue(Class<?> clazz) {
            Optional<Class<?>> optionalClass = primitiveTypeUtil.getPrimitiveFor(clazz);
            assertTrue(optionalClass.isEmpty());
        }

        @ParameterizedTest
        @ArgumentsSource(PrimitiveTypeProvider.class)
        @DisplayName("Should return primitive type class value when class is primitive")
        void getPrimitiveFor_withPrimitiveType_shouldReturnPrimitiveType(Class<?> clazz) {
            Optional<Class<?>> optionalClass = primitiveTypeUtil.getPrimitiveFor(clazz);
            assertEquals(Optional.of(clazz), optionalClass);
        }

        @ParameterizedTest
        @ArgumentsSource(PrimitiveMapProvider.class)
        @DisplayName("Should return correct value when class is wrapper or Void")
        void getPrimitiveFor_withWrapperClass_shouldReturnCorrectValue(Class<?> wrapper, Class<?> primitive) {
            Optional<Class<?>> optionalClass = primitiveTypeUtil.getPrimitiveFor(wrapper);
            assertEquals(Optional.of(primitive), optionalClass);
        }
    }

    @Nested
    @Tag("xyz/derivora/utilkit/reflection/primitives")
    @Tag("getMaxValueFor")
    @DisplayName("Tests for getMaxValueFor")
    class GetMaxValueForTests {

        @Test
        @DisplayName("Should throw NullPointerException when class is null")
        void getMaxValueFor_withNullClass_shouldThrowNullPointerException() {
            assertThrows(
                    NullPointerException.class,
                    () -> primitiveTypeUtil.getMaxValueFor(null)
            );
        }

        @ParameterizedTest
        @ValueSource(classes = {Object.class, Void.class, Boolean.class, int[].class, Integer[].class})
        @DisplayName("Should return empty value when class is not a numeric wrapper")
        void getMaxValueFor_withInvalidClass_shouldReturnEmptyValue(Class<?> clazz) {
            Optional<Number> optionalNumber = primitiveTypeUtil.getMaxValueFor(clazz);
            assertTrue(optionalNumber.isEmpty());
        }

        @ParameterizedTest
        @MethodSource("wrapperToMaxValue")
        @DisplayName("Should return correct value when class is wrapper")
        void getMaxValueFor_withWrapperClass_shouldReturnCorrectValue(Class<?> clazz, Number number) {
            Optional<Number> optionalNumber = primitiveTypeUtil.getMaxValueFor(clazz);
            assertEquals(Optional.of(number), optionalNumber);
        }

        static Stream<Object[]> wrapperToMaxValue() {
            return Stream.of(
                    new Object[]{Character.class, (int) Character.MAX_VALUE},
                    new Object[]{Byte.class, Byte.MAX_VALUE},
                    new Object[]{Short.class, Short.MAX_VALUE},
                    new Object[]{Integer.class, Integer.MAX_VALUE},
                    new Object[]{Long.class, Long.MAX_VALUE},
                    new Object[]{Float.class, Float.MAX_VALUE},
                    new Object[]{Double.class, Double.MAX_VALUE}
            );
        }
    }

    @Nested
    @Tag("xyz/derivora/utilkit/reflection/primitives")
    @Tag("getMinValueFor")
    @DisplayName("Tests for getMinValueFor")
    class GetMinValueForTests {

        @Test
        @DisplayName("Should throw NullPointerException when class is null")
        void getMinValueFor_withNullClass_shouldThrowNullPointerException() {
            assertThrows(
                    NullPointerException.class,
                    () -> primitiveTypeUtil.getMinValueFor(null)
            );
        }

        @ParameterizedTest
        @ValueSource(classes = {Object.class, Void.class, Boolean.class, int[].class, Integer[].class})
        @DisplayName("Should return empty value when class is not a numeric wrapper")
        void getMinValueFor_withInvalidClass_shouldReturnEmptyValue(Class<?> clazz) {
            Optional<Number> optionalNumber = primitiveTypeUtil.getMinValueFor(clazz);
            assertTrue(optionalNumber.isEmpty());
        }

        @ParameterizedTest
        @MethodSource("wrapperToMinValue")
        @DisplayName("Should return correct value when class is wrapper")
        void getMinValueFor_withWrapperClass_shouldReturnCorrectValue(Class<?> clazz, Number number) {
            Optional<Number> optionalNumber = primitiveTypeUtil.getMinValueFor(clazz);
            assertEquals(Optional.of(number), optionalNumber);
        }

        static Stream<Object[]> wrapperToMinValue() {
            return Stream.of(
                    new Object[]{Character.class, (int) Character.MIN_VALUE},
                    new Object[]{Byte.class, Byte.MIN_VALUE},
                    new Object[]{Short.class, Short.MIN_VALUE},
                    new Object[]{Integer.class, Integer.MIN_VALUE},
                    new Object[]{Long.class, Long.MIN_VALUE},
                    new Object[]{Float.class, Float.MIN_VALUE},
                    new Object[]{Double.class, Double.MIN_VALUE}
            );
        }
    }

    protected interface PrimitiveTypeUtil {

        boolean isPrimitive(Class<?> clazz);

        boolean isWrapperClass(Class<?> clazz);

        boolean isPrimitiveOrWrapper(Class<?> clazz);

        Optional<Class<?>> getPrimitiveFor(Class<?> clazz);

        Optional<Number> getMaxValueFor(Class<?> wrapper);

        Optional<Number> getMinValueFor(Class<?> wrapper);
    }
}
