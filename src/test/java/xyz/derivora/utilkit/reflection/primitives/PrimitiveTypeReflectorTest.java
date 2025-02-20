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
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("xyz/derivora/utilkit/reflection/primitives")
@DisplayName("Tests for PrimitiveTypeReflector")
class PrimitiveTypeReflectorTest extends PrimitiveTypeUtilTest {

    PrimitiveTypeReflectorTest() {
        super(new PrimitiveTypeReflectorAdapter());
    }

    @ParameterizedTest
    @ArgumentsSource(PrimitiveTypeProvider.class)
    @DisplayName("Should return empty value when class is primitive type")
    void getMaxValueFor_withPrimitiveType_shouldReturnEmptyValue(Class<?> clazz) {
        Optional<Number> optionalNumber = PrimitiveTypeReflector.getMaxValueFor(clazz);
        assertTrue(optionalNumber.isEmpty());
    }

    @ParameterizedTest
    @ArgumentsSource(PrimitiveTypeProvider.class)
    @DisplayName("Should return empty value when class is primitive type")
    void getMinValueFor_withPrimitiveType_shouldReturnEmptyValue(Class<?> clazz) {
        Optional<Number> optionalNumber = PrimitiveTypeReflector.getMinValueFor(clazz);
        assertTrue(optionalNumber.isEmpty());
    }

    static class PrimitiveTypeReflectorAdapter implements PrimitiveTypeUtilTest.PrimitiveTypeUtil {

        @Override
        public boolean isPrimitive(Class<?> clazz) {
            return PrimitiveTypeReflector.isPrimitive(clazz);
        }

        @Override
        public boolean isWrapperClass(Class<?> clazz) {
            return PrimitiveTypeReflector.isWrapperClass(clazz);
        }

        @Override
        public boolean isPrimitiveOrWrapper(Class<?> clazz) {
            return PrimitiveTypeReflector.isPrimitiveOrWrapper(clazz);
        }

        @Override
        public Optional<Class<?>> getPrimitiveFor(Class<?> clazz) {
            return PrimitiveTypeReflector.getPrimitiveFor(clazz);
        }

        @Override
        public Optional<Number> getMaxValueFor(Class<?> wrapper) {
            return PrimitiveTypeReflector.getMaxValueFor(wrapper);
        }

        @Override
        public Optional<Number> getMinValueFor(Class<?> wrapper) {
            return PrimitiveTypeReflector.getMinValueFor(wrapper);
        }
    }
}
