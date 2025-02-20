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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.Objects;

/**
 * A comparator for comparing {@link Number} instances of different types.
 * <p>
 * This class provides a robust way to compare numbers while handling
 * various numeric types, including primitive numeric wrappers,
 * {@link BigInteger}, and {@link BigDecimal}. It ensures accurate
 * comparisons by converting numbers to a common format when necessary.
 * </p>
 * <p>
 * The comparator follows these comparison rules:
 * </p>
 * <ul>
 *   <li>If both numbers are of the same class and implement {@link Comparable},
 *       they are compared directly.</li>
 *   <li>Integral primitive types ({@link Byte}, {@link Short}, {@link Integer}, {@link Long})
 *       are compared as {@code long} values.</li>
 *   <li>Floating-point numbers ({@link Float}, {@link Double}) are compared as {@code double} values.</li>
 *   <li>{@link BigInteger} values are compared directly, or converted to {@link BigDecimal} when necessary.</li>
 *   <li>For all other cases, both numbers are converted to {@link BigDecimal} for a precise comparison.</li>
 * </ul>
 * <p>
 * This class follows the singleton pattern, providing a single instance via
 * {@link #getInstance()}.
 * </p>
 */
public final class NumberComparator implements Comparator<Number> {

    /**
     * A singleton instance of {@link NumberComparator}.
     */
    private static final NumberComparator SINGLETON = new NumberComparator();

    /**
     * Private constructor to enforce the singleton pattern.
     * <p>
     * This constructor prevents external instantiation of {@link NumberComparator},
     * ensuring that the only accessible instance is the singleton provided by
     * {@link #getInstance()}.
     * </p>
     */
    private NumberComparator() {
    }

    /**
     * Returns the singleton instance of {@link NumberComparator}.
     * <p>
     * This method provides access to a single, reusable instance of
     * {@link NumberComparator}, ensuring efficient and consistent number comparisons.
     * </p>
     *
     * @return the singleton instance of {@link NumberComparator}
     */
    public static NumberComparator getInstance() {
        return SINGLETON;
    }

    /**
     * Compares two {@link Number} instances, handling various numeric types.
     * <p>
     * This method supports comparisons between different {@code Number} types,
     * ensuring correct ordering regardless of their representation.
     * </p>
     * <p>
     * If the comparison cannot be performed due to an invalid number format, an
     * {@link IllegalArgumentException} is thrown.
     * </p>
     *
     * @param firstNumber  the first number to compare; must not be {@code null}
     * @param secondNumber the second number to compare; must not be {@code null}
     * @return a negative integer, zero, or a positive integer as {@code firstNumber} is less than,
     *         equal to, or greater than {@code secondNumber}
     * @throws NullPointerException  if either {@code firstNumber} or {@code secondNumber} is {@code null}
     * @throws ClassCastException    if an unexpected type prevents a valid comparison
     * @throws IllegalArgumentException if a numeric conversion issue occurs during comparison
     */
    @Override
    public int compare(Number firstNumber, Number secondNumber) {
        Objects.requireNonNull(firstNumber, "First number cannot be null");
        Objects.requireNonNull(secondNumber, "Second number cannot be null");

        if (firstNumber instanceof Comparable && firstNumber.getClass() == secondNumber.getClass()) {
            @SuppressWarnings("unchecked")
            Comparable<Number> firstComparable = (Comparable<Number>) firstNumber;
            try {
                return firstComparable.compareTo(secondNumber);
            } catch (Exception e) {
                throw new ClassCastException(generateExceptionMessage(firstNumber, secondNumber));
            }
        }

        if (isPrimitiveIntegral(firstNumber)) {
            return compare(firstNumber.longValue(), secondNumber);
        }

        try {
            if (isPrimitiveFractional(firstNumber)) {
                return compare(firstNumber.doubleValue(), secondNumber);
            }

            if (firstNumber instanceof BigInteger firstBigInteger) {
                return compare(firstBigInteger, secondNumber);
            }

            return compareAsBigDecimal(firstNumber, secondNumber);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(generateExceptionMessage(firstNumber, secondNumber), e);
        }
    }

    /**
     * Compares a {@code long} value with a {@link Number}.
     * <p>
     * If the {@code secondNumber} is a primitive integral type,
     * the comparison is performed using {@link Long#compare(long, long)}.
     * Otherwise, the comparison is delegated to {@link #compare(Number, Number)}
     * with reversed arguments.
     * </p>
     *
     * @param firstNumber  the first number, represented as a {@code long}
     * @param secondNumber the second number to compare
     * @return a negative integer, zero, or a positive integer as {@code firstNumber}
     * is less than, equal to, or greater than {@code secondNumber}
     */
    private int compare(long firstNumber, Number secondNumber) {
        if (isPrimitiveIntegral(secondNumber)) {
            return Long.compare(firstNumber, secondNumber.longValue());
        }

        return -compare(secondNumber, firstNumber);
    }

    /**
     * Compares a {@code double} value with a {@link Number}.
     * <p>
     * If {@code secondNumber} is an instance of {@link Long}, the comparison is performed
     * using {@link BigDecimal} to ensure precision. Otherwise, if {@code secondNumber} is a
     * primitive integral type or a primitive fractional type, the comparison
     * is performed using {@link Double#compare(double, double)}.
     * If {@code secondNumber} does not match any of these cases, the comparison is delegated
     * to {@link #compare(Number, Number)} with reversed arguments.
     * </p>
     *
     * @param firstNumber  the first number, represented as a {@code double}
     * @param secondNumber the second number to compare
     * @return a negative integer, zero, or a positive integer as {@code firstNumber}
     * is less than, equal to, or greater than {@code secondNumber}
     * @throws NumberFormatException if conversion to {@link BigDecimal} fails due to invalid input
     */
    private int compare(double firstNumber, Number secondNumber) {
        if (secondNumber instanceof Long) {
            return compareAsBigDecimal(firstNumber, secondNumber);
        }

        if (isPrimitiveIntegral(secondNumber) || isPrimitiveFractional(secondNumber)) {
            return Double.compare(firstNumber, secondNumber.doubleValue());
        }

        return -compare(secondNumber, firstNumber);
    }

    /**
     * Compares a {@link BigInteger} with a {@link Number}.
     * <p>
     * If {@code secondNumber} is a primitive integral type,
     * it is converted to a {@link BigInteger}, and the comparison is
     * performed using {@link BigInteger#compareTo(BigInteger)}.
     * </p>
     * <p>
     * If {@code secondNumber} is a primitive fractional type, both numbers are converted
     * to {@link BigDecimal} for precise comparison.
     * </p>
     * <p>
     * If {@code secondNumber} does not match any of these cases, the comparison
     * is delegated to {@link #compare(Number, Number)} with reversed arguments.
     * </p>
     *
     * @param firstNumber  the first number, represented as a {@link BigInteger}
     * @param secondNumber the second number to compare
     * @return a negative integer, zero, or a positive integer as {@code firstNumber}
     * is less than, equal to, or greater than {@code secondNumber}
     * @throws NumberFormatException if conversion to {@link BigDecimal} fails due to invalid input
     */
    private int compare(BigInteger firstNumber, Number secondNumber) {
        if (isPrimitiveIntegral(secondNumber)) {
            BigInteger secondBigInteger = BigInteger.valueOf(secondNumber.longValue());
            return firstNumber.compareTo(secondBigInteger);
        }

        if (isPrimitiveFractional(secondNumber)) {
            return compareAsBigDecimal(firstNumber, secondNumber);
        }

        return -compare(secondNumber, firstNumber);
    }

    /**
     * Compares two {@link Number} instances by converting them to {@link BigDecimal}.
     * <p>
     * This method ensures a precise numerical comparison by converting both {@code firstNumber}
     * and {@code secondNumber} to {@link BigDecimal} using {@link #convertToBigDecimal(Number)}.
     * The comparison is then performed using {@link BigDecimal#compareTo(BigDecimal)},
     * which follows numerical ordering rules.
     * </p>
     *
     * @param firstNumber  the first number to compare
     * @param secondNumber the second number to compare
     * @return a negative integer, zero, or a positive integer as {@code firstNumber}
     * is less than, equal to, or greater than {@code secondNumber}
     * @throws NumberFormatException if conversion to {@link BigDecimal} fails due to invalid input
     */
    private static int compareAsBigDecimal(Number firstNumber, Number secondNumber) {
        BigDecimal firstBigDecimal = convertToBigDecimal(firstNumber);
        BigDecimal secondBigDecimal = convertToBigDecimal(secondNumber);
        return firstBigDecimal.compareTo(secondBigDecimal);
    }

    /**
     * Converts a {@link Number} to a {@link BigDecimal}.
     * <p>
     * This method ensures that any given {@code Number} is converted to a {@link BigDecimal}
     * using an appropriate strategy based on its type:
     * </p>
     * <ul>
     *   <li>If {@code number} is already an instance of {@link BigDecimal}, it is returned as is.</li>
     *   <li>If {@code number} is a primitive integral type ({@link Byte}, {@link Short},
     *       {@link Integer}, or {@link Long}), it is converted using {@link BigDecimal#BigDecimal(long)}.</li>
     *   <li>If {@code number} is a primitive fractional type ({@link Float} or {@link Double}),
     *       it is converted using {@link BigDecimal#valueOf(double)} to avoid precision loss.</li>
     *   <li>If {@code number} is an instance of {@link BigInteger}, it is converted using
     *       {@link BigDecimal#BigDecimal(BigInteger)}.</li>
     *   <li>For any other type, the method uses {@link Number#toString()} and
     *       {@link BigDecimal#BigDecimal(String)} for conversion.</li>
     * </ul>
     *
     * @param number the number to convert
     * @return a {@link BigDecimal} representation of the given {@code number}
     * @throws NumberFormatException if {@code number.toString()} does not represent a valid numeric value
     */
    private static BigDecimal convertToBigDecimal(Number number) {
        if (number instanceof BigDecimal bigDecimal) {
            return bigDecimal;
        }

        if (isPrimitiveIntegral(number)) {
            return new BigDecimal(number.longValue());
        }

        if (isPrimitiveFractional(number)) {
            return BigDecimal.valueOf(number.doubleValue());
        }

        if (number instanceof BigInteger bigInteger) {
            return new BigDecimal(bigInteger);
        }

        return new BigDecimal(number.toString());
    }

    /**
     * Determines whether the given number is a primitive integral type.
     * <p>
     * This method returns {@code true} if the provided {@code number} is an instance
     * of {@link Byte}, {@link Short}, {@link Integer}, or {@link Long}, indicating that
     * it represents an integral value.
     * </p>
     *
     * @param number the number to check
     * @return {@code true} if {@code number} is an instance of {@link Byte}, {@link Short},
     * {@link Integer}, or {@link Long}, otherwise {@code false}
     */
    private static boolean isPrimitiveIntegral(Number number) {
        return number instanceof Byte ||
                number instanceof Short ||
                number instanceof Integer ||
                number instanceof Long;
    }

    /**
     * Determines whether the given number is a primitive fractional type.
     * <p>
     * This method returns {@code true} if the provided {@code number} is an instance
     * of {@link Float} or {@link Double}, indicating that it represents a floating-point value.
     * </p>
     *
     * @param number the number to check
     * @return {@code true} if {@code number} is an instance of {@link Float} or {@link Double},
     * otherwise {@code false}
     */
    private static boolean isPrimitiveFractional(Number number) {
        return number instanceof Float ||
                number instanceof Double;
    }

    /**
     * Generates an exception message for failed number comparisons.
     * <p>
     * This method formats a descriptive error message containing the types and values
     * of the two numbers involved in the failed comparison. It helps in debugging by
     * providing clear information about the incompatible types or unexpected values.
     * </p>
     *
     * @param firstNumber  the first number in the comparison
     * @param secondNumber the second number in the comparison
     * @return a formatted string describing the failure reason, including the number types and values
     */
    private static String generateExceptionMessage(Number firstNumber, Number secondNumber) {
        return String.format(
                "Failed to compare numbers of type %s and %s with values %s and %s.",
                firstNumber.getClass().getName(),
                secondNumber.getClass().getName(),
                firstNumber,
                secondNumber
        );
    }
}
