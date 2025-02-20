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

package xyz.derivora.utilkit.numbers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Tag("xyz/derivora/utilkit/numbers")
@DisplayName("Tests for NumberComparator")
class NumberComparatorTest {

    private static final NumberComparator NUMBER_COMPARATOR = NumberComparator.getInstance();

    private static final Number[] ZERO_NUMBERS = {
            (byte) 0, (short) 0,
            0, 0L, 0.0f, 0.0,
            BigInteger.ZERO,
            BigDecimal.ZERO,
            CustomNumber.ZERO,
            CustomComparableNumber.ZERO
    };

    private static final Number[] ONE_NUMBERS = {
            (byte) 1, (short) 1,
            1, 1L, 1.0f, 1.0,
            BigInteger.ONE,
            BigDecimal.ONE,
            CustomNumber.ONE,
            CustomComparableNumber.ONE
    };

    private static final Number[] NEGATIVE_ONE_NUMBERS = {
            (byte) -1, (short) -1,
            -1, -1L, -1.0f, -1.0,
            BigInteger.ONE.negate(),
            BigDecimal.ONE.negate(),
            CustomNumber.NEGATIVE_ONE,
            CustomComparableNumber.NEGATIVE_ONE
    };

    @Test
    @DisplayName("Should throw NullPointerException when first number is null")
    void compare_withNullFirstNumber_shouldThrowNullPointerException() {
        assertThrows(
                NullPointerException.class,
                () -> NUMBER_COMPARATOR.compare(null, 0)
        );
    }

    @Test
    @DisplayName("Should throw NullPointerException when second number is null")
    void compare_withNullSecondNumber_shouldThrowNullPointerException() {
        assertThrows(
                NullPointerException.class,
                () -> NUMBER_COMPARATOR.compare(0, null)
        );
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when first number cannot be cast to BigDecimal")
    void compare_withIllegalFirstNumber_shouldThrowIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> NUMBER_COMPARATOR.compare(CustomNumber.ILLEGAL_NUMBER, 0)
        );
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when second number cannot be cast to BigDecimal")
    void compare_withIllegalSecondNumber_shouldThrowIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> NUMBER_COMPARATOR.compare(0, CustomNumber.ILLEGAL_NUMBER)
        );
    }

    @Test
    @DisplayName("Should throw ClassCastException when numbers are incomparable")
    void compare_withIncomparableNumbers_shouldThrowClassCastException() {
        Number firstNumber = CustomComparableNumber.getIncomparableInstance("-1");
        Number secondNumber = CustomComparableNumber.getIncomparableInstance("1");

        assertThrows(
                ClassCastException.class,
                () -> NUMBER_COMPARATOR.compare(firstNumber, secondNumber)
        );
    }

    @ParameterizedTest
    @FieldSource({"ZERO_NUMBERS", "ONE_NUMBERS", "NEGATIVE_ONE_NUMBERS"})
    @DisplayName("Should return zero for identical numbers")
    void compare_withIdenticalNumbers_shouldReturnZero(Number number) {
        int result = NUMBER_COMPARATOR.compare(number, number);
        assertEquals(0, result);
    }

    @ParameterizedTest
    @MethodSource("provideEqualNumbers")
    @DisplayName("Should return zero for equal numbers")
    void compare_withEqualNumbers_shouldReturnZero(Number firstNumber, Number secondNumber) {
        int result = NUMBER_COMPARATOR.compare(firstNumber, secondNumber);
        assertEquals(0, result);
    }

    @ParameterizedTest
    @MethodSource("provideLessThanPairs")
    @DisplayName("Should return negative value when first number is less than second")
    void compare_whenFirstIsLessThanSecond_shouldReturnNegativeValue(Number lesser, Number greater) {
        int result = NUMBER_COMPARATOR.compare(lesser, greater);
        assertTrue(result < 0);
    }

    @ParameterizedTest
    @MethodSource("provideLessThanPairs")
    @DisplayName("Should return positive value when first number is greater than second")
    void compare_whenFirstIsGreaterThanSecond_shouldReturnPositiveValue(Number lesser, Number greater) {
        int result = NUMBER_COMPARATOR.compare(greater, lesser);
        assertTrue(result > 0);
    }

    static Stream<Object[]> provideEqualNumbers() {
        Stream<Object[]> zeroesStream = generateNumberPairs(ZERO_NUMBERS);
        Stream<Object[]> positiveStream = generateNumberPairs(ONE_NUMBERS);
        Stream<Object[]> negativeStream = generateNumberPairs(NEGATIVE_ONE_NUMBERS);

        return Stream.of(zeroesStream, positiveStream, negativeStream)
                     .flatMap(s -> s);
    }

    static Stream<Object[]> generateNumberPairs(Number[] numbers) {
        int numbersCount = numbers.length;
        // Number of combinations of elementsCount taken 2 at a time
        int pairsCount = (numbersCount - 1) * numbersCount / 2;
        Number[][] pairs = new Number[pairsCount][2];

        int processedPairsCount = 0;
        for (int i = 0; i < numbersCount; i++) {
            for (int j = i + 1; j < numbersCount; j++) {
                int pos = processedPairsCount + j - i - 1;
                pairs[pos] = new Number[]{numbers[i], numbers[j]};
            }
            processedPairsCount += numbersCount - i - 1;
        }

        return Arrays.stream(pairs);
    }

    static Stream<Object[]> provideLessThanPairs() {
        Stream<Object[]> negativeOneToZero = generateNumberPairs(NEGATIVE_ONE_NUMBERS, ZERO_NUMBERS);
        Stream<Object[]> negativeOneToOne = generateNumberPairs(NEGATIVE_ONE_NUMBERS, ONE_NUMBERS);
        Stream<Object[]> zeroToOne = generateNumberPairs(ZERO_NUMBERS, ONE_NUMBERS);

        return Stream.of(negativeOneToZero, negativeOneToOne, zeroToOne)
                     .flatMap(s -> s);
    }

    static Stream<Object[]> generateNumberPairs(Number[] firstArr, Number[] secondArr) {
        int pairsCount = firstArr.length * secondArr.length;
        Number[][] pairs = new Number[pairsCount][2];

        for (int i = 0; i < firstArr.length; i++) {
            for (int j = 0; j < secondArr.length; j++) {
                int pos = i * secondArr.length + j;
                pairs[pos] = new Number[]{firstArr[i], secondArr[j]};
            }
        }

        return Arrays.stream(pairs);
    }
}
