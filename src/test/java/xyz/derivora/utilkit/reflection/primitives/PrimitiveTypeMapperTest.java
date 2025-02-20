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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Tag("xyz/derivora/utilkit/reflection/primitives")
@DisplayName("Tests for PrimitiveTypeMapper")
class PrimitiveTypeMapperTest extends PrimitiveTypeUtilTest {

    protected PrimitiveTypeMapperTest() {
        super(new PrimitiveTypeMapperAdapter());
    }

    @ParameterizedTest
    @MethodSource("primitiveToMaxValue")
    @DisplayName("Should return correct value when class is primitive type")
    void getMaxValueFor_withPrimitiveType_shouldReturnCorrectValue(Class<?> clazz, Number number) {
        Optional<Number> optionalNumber = PrimitiveTypeMapper.getMaxValueFor(clazz);
        assertEquals(Optional.of(number), optionalNumber);
    }

    static Stream<Object[]> primitiveToMaxValue() {
        return Stream.of(
                new Object[]{char.class, (int) Character.MAX_VALUE},
                new Object[]{byte.class, Byte.MAX_VALUE},
                new Object[]{short.class, Short.MAX_VALUE},
                new Object[]{int.class, Integer.MAX_VALUE},
                new Object[]{long.class, Long.MAX_VALUE},
                new Object[]{float.class, Float.MAX_VALUE},
                new Object[]{double.class, Double.MAX_VALUE}
        );
    }

    @ParameterizedTest
    @MethodSource("primitiveToMinValue")
    @DisplayName("Should return correct value when class is primitive type")
    void getMinValueFor_withPrimitiveType_shouldReturnCorrectValue(Class<?> clazz, Number number) {
        Optional<Number> optionalNumber = PrimitiveTypeMapper.getMinValueFor(clazz);
        assertEquals(Optional.of(number), optionalNumber);
    }

    static Stream<Object[]> primitiveToMinValue() {
        return Stream.of(
                new Object[]{char.class, (int) Character.MIN_VALUE},
                new Object[]{byte.class, Byte.MIN_VALUE},
                new Object[]{short.class, Short.MIN_VALUE},
                new Object[]{int.class, Integer.MIN_VALUE},
                new Object[]{long.class, Long.MIN_VALUE},
                new Object[]{float.class, Float.MIN_VALUE},
                new Object[]{double.class, Double.MIN_VALUE}
        );
    }

    @Nested
    @Tag("xyz/derivora/utilkit/reflection/primitives")
    @Tag("getWrapperFor")
    @DisplayName("Tests for getWrapperFor")
    class GetWrapperForTests {

        @Test
        @DisplayName("Should throw NullPointerException when class is null")
        void getWrapperFor_withNullClass_shouldThrowNullPointerException() {
            assertThrows(
                    NullPointerException.class,
                    () -> PrimitiveTypeMapper.getWrapperFor(null)
            );
        }

        @ParameterizedTest
        @ValueSource(classes = {Object.class, Void.class, int[].class, Integer[].class})
        @ArgumentsSource(WrapperClassProvider.class)
        @DisplayName("Should return empty value when class is not primitive type")
        void getWrapperFor_withNonePrimitiveType_shouldReturnEmptyValue(Class<?> clazz) {
            Optional<Class<?>> optionalClass = PrimitiveTypeMapper.getWrapperFor(clazz);
            assertTrue(optionalClass.isEmpty());
        }

        @ParameterizedTest
        @ArgumentsSource(PrimitiveMapProvider.class)
        @DisplayName("Should return correct value when class is primitive type")
        void getWrapperFor_withPrimitiveType_shouldReturnCorrectValue(Class<?> wrapper, Class<?> primitive) {
            Optional<Class<?>> optionalClass = PrimitiveTypeMapper.getWrapperFor(primitive);
            assertEquals(Optional.of(wrapper), optionalClass);
        }
    }

    static class PrimitiveTypeMapperAdapter implements PrimitiveTypeUtil {

        @Override
        public boolean isPrimitive(Class<?> clazz) {
            return PrimitiveTypeMapper.isPrimitive(clazz);
        }

        @Override
        public boolean isWrapperClass(Class<?> clazz) {
            return PrimitiveTypeMapper.isWrapperClass(clazz);
        }

        @Override
        public boolean isPrimitiveOrWrapper(Class<?> clazz) {
            return PrimitiveTypeMapper.isPrimitiveOrWrapper(clazz);
        }

        @Override
        public Optional<Class<?>> getPrimitiveFor(Class<?> clazz) {
            return PrimitiveTypeMapper.getPrimitiveFor(clazz);
        }

        @Override
        public Optional<Number> getMaxValueFor(Class<?> wrapper) {
            return PrimitiveTypeMapper.getMaxValueFor(wrapper);
        }

        @Override
        public Optional<Number> getMinValueFor(Class<?> wrapper) {
            return PrimitiveTypeMapper.getMinValueFor(wrapper);
        }
    }
}
