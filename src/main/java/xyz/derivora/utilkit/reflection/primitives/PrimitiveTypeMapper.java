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

import java.util.Objects;
import java.util.Optional;

/**
 * A utility class for working with Java's primitive types and their corresponding wrapper classes.
 * <p>
 * This class provides methods to check whether a given class is a primitive type or a wrapper,
 * retrieve the corresponding primitive or wrapper type, and obtain the minimum and maximum
 * values for numeric primitive types.
 * <p>
 * The class is designed as a static utility and cannot be instantiated.
 *
 * <h2>Features:</h2>
 * <ul>
 *     <li>Check if a class is a primitive or a wrapper ({@link #isPrimitive(Class)}, {@link #isWrapperClass(Class)},
 *         {@link #isPrimitiveOrWrapper(Class)})</li>
 *     <li>Retrieve the primitive type for a wrapper ({@link #getPrimitiveFor(Class)})</li>
 *     <li>Retrieve the wrapper class for a primitive ({@link #getWrapperFor(Class)})</li>
 *     <li>Retrieve the min and max values for numeric primitive types ({@link #getMinValueFor(Class)},
 *         {@link #getMaxValueFor(Class)})</li>
 * </ul>
 *
 * <p><b>Note:</b> {@code Void.class} is explicitly excluded from being considered a valid wrapper type.</p>
 */
public final class PrimitiveTypeMapper {

    private static final PrimitiveTypeMapping[] PRIMITIVE_TYPE_MAP = PrimitiveTypeMapping.values();

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private PrimitiveTypeMapper() {
    }

    /**
     * Checks whether the given class represents a primitive type.
     *
     * @param clazz the class to check (must not be {@code null})
     * @return {@code true} if the class is a primitive type; {@code false} otherwise
     * @throws NullPointerException if {@code clazz} is {@code null}
     */
    public static boolean isPrimitive(Class<?> clazz) {
        Objects.requireNonNull(clazz, "Class cannot be null");
        return isPrimitiveOrWrapper(clazz, true, false);
    }

    /**
     * Checks whether the given class is a wrapper of a primitive type.
     * <p>
     * This method considers only wrapper classes (e.g., {@code Integer}, {@code Double}) as valid.
     * Primitive types (e.g., {@code int}, {@code double}) and {@code Void.class} are explicitly excluded.
     *
     * @param clazz the class to check (must not be {@code null})
     * @return {@code true} if the class is a wrapper of a primitive type (excluding {@code Void.class});
     * {@code false} otherwise
     * @throws NullPointerException if {@code clazz} is {@code null}
     */
    public static boolean isWrapperClass(Class<?> clazz) {
        Objects.requireNonNull(clazz, "Class cannot be null");
        return isPrimitiveOrWrapper(clazz, false, true);
    }

    /**
     * Checks whether the given class is a primitive type or a wrapper of a primitive type.
     * <p>
     * This method considers both primitive types (e.g., {@code int}, {@code double}) and
     * their corresponding wrapper classes (e.g., {@code Integer}, {@code Double}) as valid.
     * However, {@code Void.class} is explicitly excluded and does not count as a wrapper type.
     *
     * @param clazz the class to check (must not be {@code null})
     * @return {@code true} if the class is a primitive type or a wrapper of a primitive type
     * (excluding {@code Void.class}); {@code false} otherwise
     * @throws NullPointerException if {@code clazz} is {@code null}
     */
    public static boolean isPrimitiveOrWrapper(Class<?> clazz) {
        Objects.requireNonNull(clazz, "Class cannot be null");
        return isPrimitiveOrWrapper(clazz, true, true);
    }

    /**
     * Checks whether the given class represents a primitive type or its corresponding wrapper type.
     * <p>
     * This method allows fine-grained control over which types should be considered valid.
     * If {@code allowPrimitives} is {@code true}, primitive types (e.g., {@code int}, {@code double})
     * are considered valid. If {@code allowWrappers} is {@code true}, wrapper classes
     * (e.g., {@code Integer}, {@code Double}) are considered valid.
     * <p>
     * This method does not consider {@code Void.class} as a valid wrapper type.
     *
     * @param clazz          the class to check
     * @param allowPrimitives if {@code true}, the method returns {@code true} for primitive types;
     *                        if {@code false}, only wrapper types are considered valid
     * @param allowWrappers  if {@code true}, the method returns {@code true} for wrapper types;
     *                       if {@code false}, only primitive types are considered valid
     * @return {@code true} if the class is a primitive type (and {@code allowPrimitives} is {@code true})
     * or if it is a wrapper type (and {@code allowWrappers} is {@code true});
     * {@code false} otherwise
     */
    private static boolean isPrimitiveOrWrapper(Class<?> clazz, boolean allowPrimitives, boolean allowWrappers) {
        if (clazz == Void.class) {
            return false;
        }

        for (PrimitiveTypeMapping pair : PRIMITIVE_TYPE_MAP) {
            if (allowPrimitives && pair.primitiveIs(clazz) || allowWrappers && pair.wrapperIs(clazz)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Retrieves the corresponding primitive type for the given wrapper class.
     * <p>
     * If the provided class is already a primitive type, it is returned as is.
     * If the class is a wrapper type (e.g., {@code Integer.class}), its corresponding
     * primitive type (e.g., {@code int.class}) is returned.
     * If there is no corresponding primitive type, an empty {@code Optional} is returned.
     *
     * @param clazz the class to check (must not be {@code null})
     * @return an {@code Optional} containing the corresponding primitive type
     * if one exists, or an empty {@code Optional} if the class is not a wrapper
     * or has no corresponding primitive type
     * @throws NullPointerException if {@code clazz} is {@code null}
     */
    public static Optional<Class<?>> getPrimitiveFor(Class<?> clazz) {
        Objects.requireNonNull(clazz, "Class cannot be null");

        for (PrimitiveTypeMapping pair : PRIMITIVE_TYPE_MAP) {
            if (pair.primitiveIs(clazz)) {
                return Optional.of(clazz);
            }

            if (pair.wrapperIs(clazz)) {
                return Optional.of(pair.primitive);
            }
        }

        return Optional.empty();
    }

    /**
     * Retrieves the corresponding wrapper class for the given primitive type.
     * <p>
     * If the provided class is a primitive type (e.g., {@code int.class}), its corresponding
     * wrapper class (e.g., {@code Integer.class}) is returned.
     * If the class is already a wrapper type or does not have a corresponding wrapper,
     * an empty {@code Optional} is returned.
     *
     * @param clazz the class to check (must not be {@code null})
     * @return an {@code Optional} containing the corresponding wrapper class
     * if one exists, or an empty {@code Optional} if the class is not a primitive
     * or has no corresponding wrapper class
     * @throws NullPointerException if {@code clazz} is {@code null}
     */
    public static Optional<Class<?>> getWrapperFor(Class<?> clazz) {
        Objects.requireNonNull(clazz, "Class cannot be null");

        for (PrimitiveTypeMapping pair : PRIMITIVE_TYPE_MAP) {
            if (pair.primitiveIs(clazz)) {
                return Optional.of(pair.wrapper);
            }
        }

        return Optional.empty();
    }

    /**
     * Retrieves the maximum value for a given primitive type or its corresponding wrapper class.
     * <p>
     * This method returns the value of the {@code MAX_VALUE} constant for numeric primitive types
     * (e.g., {@code Integer.MAX_VALUE}, {@code Double.MAX_VALUE}) and their corresponding wrapper classes
     * (e.g., {@code Integer.class}, {@code Double.class}). If the provided class is {@code Character.class},
     * the method returns {@code (int) Character.MAX_VALUE}.
     * <p>
     * If the class is neither a numeric primitive nor a numeric wrapper, an empty {@code Optional} is returned.
     *
     * @param clazz the class to check (must not be {@code null})
     * @return an {@code Optional} containing the maximum value for the given class
     * if it is a numeric primitive or a numeric wrapper, or an empty {@code Optional} otherwise
     * @throws NullPointerException if {@code clazz} is {@code null}
     */
    public static Optional<Number> getMaxValueFor(Class<?> clazz) {
        Objects.requireNonNull(clazz, "Class cannot be null");

        for (PrimitiveTypeMapping pair : PRIMITIVE_TYPE_MAP) {
            if (pair.primitiveIs(clazz) || pair.wrapperIs(clazz)) {
                return pair.getMaxValue();
            }
        }

        return Optional.empty();
    }

    /**
     * Retrieves the minimum value for a given primitive type or its corresponding wrapper class.
     * <p>
     * This method returns the value of the {@code MIN_VALUE} constant for numeric primitive types
     * (e.g., {@code Integer.MIN_VALUE}, {@code Double.MIN_VALUE}) and their corresponding wrapper classes
     * (e.g., {@code Integer.class}, {@code Double.class}). If the provided class is {@code Character.class},
     * the method returns {@code (int) Character.MIN_VALUE}.
     * <p>
     * If the class is neither a numeric primitive nor a numeric wrapper, an empty {@code Optional} is returned.
     *
     * @param clazz the class to check (must not be {@code null})
     * @return an {@code Optional} containing the minimum value for the given class
     * if it is a numeric primitive or a numeric wrapper, or an empty {@code Optional} otherwise
     * @throws NullPointerException if {@code clazz} is {@code null}
     */
    public static Optional<Number> getMinValueFor(Class<?> clazz) {
        Objects.requireNonNull(clazz, "Class cannot be null");

        for (PrimitiveTypeMapping pair : PRIMITIVE_TYPE_MAP) {
            if (pair.primitiveIs(clazz) || pair.wrapperIs(clazz)) {
                return pair.getMinValue();
            }
        }

        return Optional.empty();
    }

    /**
     * Represents a mapping between primitive types and their corresponding wrapper classes.
     * <p>
     * This enum defines the relationships between Java's primitive types (e.g., {@code int}, {@code double})
     * and their respective wrapper classes (e.g., {@code Integer}, {@code Double}).
     * It also stores the minimum and maximum values for numeric types, if applicable.
     * <p>
     * {@code VOID} and {@code BOOLEAN} do not have numeric min/max values,
     * so their {@code minValue} and {@code maxValue} are {@code null}.
     */
    private enum PrimitiveTypeMapping {

        VOID(void.class, Void.class, null, null),
        BOOLEAN(boolean.class, Boolean.class, null, null),
        CHAR(char.class, Character.class, (int) Character.MIN_VALUE, (int) Character.MAX_VALUE),
        BYTE(byte.class, Byte.class, Byte.MIN_VALUE, Byte.MAX_VALUE),
        SHORT(short.class, Short.class, Short.MIN_VALUE, Short.MAX_VALUE),
        INT(int.class, Integer.class, Integer.MIN_VALUE, Integer.MAX_VALUE),
        LONG(long.class, Long.class, Long.MIN_VALUE, Long.MAX_VALUE),
        FLOAT(float.class, Float.class, Float.MIN_VALUE, Float.MAX_VALUE),
        DOUBLE(double.class, Double.class, Double.MIN_VALUE, Double.MAX_VALUE);

        /** The primitive type associated with this mapping (e.g., {@code int.class}). */
        private final Class<?> primitive;

        /** The wrapper class associated with this mapping (e.g., {@code Integer.class}). */
        private final Class<?> wrapper;

        /** The minimum value for numeric types, or {@code null} if not applicable. */
        private final Number minValue;

        /** The maximum value for numeric types, or {@code null} if not applicable. */
        private final Number maxValue;

        /**
         * Constructs a mapping between a primitive type and its corresponding wrapper class.
         *
         * @param primitive the primitive type (e.g., {@code int.class})
         * @param wrapper the corresponding wrapper class (e.g., {@code Integer.class})
         * @param minValue the minimum value for numeric types, or {@code null} if not applicable
         * @param maxValue the maximum value for numeric types, or {@code null} if not applicable
         */
        PrimitiveTypeMapping(Class<?> primitive, Class<?> wrapper, Number minValue, Number maxValue) {
            this.primitive = primitive;
            this.wrapper = wrapper;
            this.minValue = minValue;
            this.maxValue = maxValue;
        }

        /**
         * Checks if the given class is the primitive type of this mapping.
         *
         * @param primitive the class to check
         * @return {@code true} if the class matches the primitive type of this mapping, {@code false} otherwise
         */
        private boolean primitiveIs(Class<?> primitive) {
            return this.primitive == primitive;
        }

        /**
         * Checks if the given class is the wrapper type of this mapping.
         *
         * @param wrapper the class to check
         * @return {@code true} if the class matches the wrapper type of this mapping, {@code false} otherwise
         */
        private boolean wrapperIs(Class<?> wrapper) {
            return this.wrapper == wrapper;
        }

        /**
         * Retrieves the minimum value for this primitive type, if applicable.
         *
         * @return an {@code Optional} containing the minimum value, or an empty {@code Optional} if not applicable
         */
        private Optional<Number> getMinValue() {
            return Optional.ofNullable(minValue);
        }

        /**
         * Retrieves the maximum value for this primitive type, if applicable.
         *
         * @return an {@code Optional} containing the maximum value, or an empty {@code Optional} if not applicable
         */
        private Optional<Number> getMaxValue() {
            return Optional.ofNullable(maxValue);
        }
    }
}
