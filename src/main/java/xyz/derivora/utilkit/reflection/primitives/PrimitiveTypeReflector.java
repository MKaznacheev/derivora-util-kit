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

import xyz.derivora.utilkit.reflection.FieldUtils;

import java.util.Objects;
import java.util.Optional;

/**
 * Utility class for working with primitive types and their wrapper classes in Java.
 * <p>
 * This class provides methods to check whether a given class is a primitive or a wrapper type,
 * retrieve the corresponding primitive type for a wrapper, and obtain the minimum and maximum values
 * for numeric wrapper types.
 * <p>
 * The class is designed as a static utility and cannot be instantiated.
 *
 * <h2>Features:</h2>
 * <ul>
 *     <li>Check if a class is a primitive or a wrapper ({@link #isPrimitive(Class)}, {@link #isWrapperClass(Class)},
 *         {@link #isPrimitiveOrWrapper(Class)})</li>
 *     <li>Retrieve the primitive type for a wrapper ({@link #getPrimitiveFor(Class)})</li>
 *     <li>Retrieve the min and max values for numeric wrapper types ({@link #getMinValueFor(Class)},
 *         {@link #getMaxValueFor(Class)})</li>
 * </ul>
 *
 * <p><b>Note:</b> {@code Void.class} is explicitly excluded from being considered a valid wrapper type.</p>
 *
 */
public final class PrimitiveTypeReflector {

    /** Field name for retrieving the primitive type from wrapper classes (e.g., {@code Integer.TYPE}). */
    private static final String TYPE = "TYPE";

    /** Field name for retrieving the maximum value constant of wrapper classes (e.g., {@code Integer.MAX_VALUE}). */
    private static final String MAX_VALUE = "MAX_VALUE";

    /** Field name for retrieving the minimum value constant of wrapper classes (e.g., {@code Integer.MIN_VALUE}). */
    private static final String MIN_VALUE = "MIN_VALUE";

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private PrimitiveTypeReflector() {
    }

    /**
     * Checks whether the given class represents a primitive type.
     * <p>
     * This method is a convenience wrapper for {@link Class#isPrimitive()}
     * to ensure consistency when working with primitive types in reflection.
     *
     * @param clazz the class to check (must not be {@code null})
     * @return {@code true} if the class is a primitive type; {@code false} otherwise
     * @throws NullPointerException if {@code clazz} is {@code null}
     */
    public static boolean isPrimitive(Class<?> clazz) {
        Objects.requireNonNull(clazz, "Class cannot be null");
        return clazz.isPrimitive();
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
        return isPrimitiveOrWrapper(clazz, false);
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
        return isPrimitiveOrWrapper(clazz, true);
    }

    /**
     * Checks whether the given class is a primitive type or a wrapper of a primitive type.
     * <p>
     * This method does not consider {@code Void.class} as a valid wrapper type.
     *
     * @param clazz          the class to check
     * @param allowPrimitive if {@code true}, the method returns {@code true} for primitive types;
     *                       if {@code false}, only wrapper types are considered valid
     * @return {@code true} if the class is a primitive type (and {@code allowPrimitive} is {@code true})
     * or if it is a wrapper of a primitive type (excluding {@code Void.class});
     * {@code false} otherwise
     */
    private static boolean isPrimitiveOrWrapper(Class<?> clazz, boolean allowPrimitive) {
        if (clazz == Void.class) {
            return false;
        }

        if (clazz.isPrimitive()) {
            return allowPrimitive;
        }

        Optional<Class<?>> primitiveType = getPrimitiveFor(clazz);
        return primitiveType.isPresent();
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

        if (clazz.isPrimitive()) {
            return Optional.of(clazz);
        }

        Optional<Class<?>> optionalType = FieldUtils.getClassFieldValue(TYPE, clazz);
        if (optionalType.isEmpty()) {
            return optionalType;
        }

        return optionalType.get().isPrimitive()
                ? optionalType
                : Optional.empty();
    }

    /**
     * Retrieves the maximum value for a given wrapper class of a primitive type.
     * <p>
     * This method returns the value of the {@code MAX_VALUE} constant for numeric wrapper classes
     * (e.g., {@code Integer.MAX_VALUE}, {@code Double.MAX_VALUE}). If the provided class is
     * {@code Character.class}, the method returns {@code (int) Character.MAX_VALUE}.
     * If the class is not a numeric wrapper, an empty {@code Optional} is returned.
     *
     * @param wrapper the wrapper class to check (must not be {@code null})
     * @return an {@code Optional} containing the maximum value for the given wrapper class,
     * or an empty {@code Optional} if the class is not a numeric wrapper
     * @throws NullPointerException if {@code wrapper} is {@code null}
     */
    public static Optional<Number> getMaxValueFor(Class<?> wrapper) {
        Objects.requireNonNull(wrapper, "Wrapper cannot be null");

        if (!isWrapperClass(wrapper)) {
            return Optional.empty();
        }

        return FieldUtils.getStaticFieldValue(MAX_VALUE, wrapper).map(PrimitiveTypeReflector::mapToNumber);
    }

    /**
     * Retrieves the minimum value for a given wrapper class of a primitive type.
     * <p>
     * This method returns the value of the {@code MIN_VALUE} constant for numeric wrapper classes
     * (e.g., {@code Integer.MIN_VALUE}, {@code Double.MIN_VALUE}).
     * If the provided class is {@code Character.class}, the method returns {@code (int) Character.MIN_VALUE}.
     * If the class is not a numeric wrapper, an empty {@code Optional} is returned.
     *
     * @param wrapper the wrapper class to check (must not be {@code null})
     * @return an {@code Optional} containing the minimum value for the given wrapper class,
     *         or an empty {@code Optional} if the class is not a numeric wrapper
     * @throws NullPointerException if {@code wrapper} is {@code null}
     */
    public static Optional<Number> getMinValueFor(Class<?> wrapper) {
        Objects.requireNonNull(wrapper, "Wrapper cannot be null");

        if (!isWrapperClass(wrapper)) {
            return Optional.empty();
        }

        return FieldUtils.getStaticFieldValue(MIN_VALUE, wrapper).map(PrimitiveTypeReflector::mapToNumber);
    }

    /**
     * Converts the given object to a {@code Number} instance.
     * <p>
     * If the object is an instance of {@code Number}, it is returned as is.
     * If the object is a {@code Character}, it is cast to {@code char} and then converted to an {@code int}.
     *
     * @param obj the object to convert (must be an instance of {@code Number} or {@code Character})
     * @return a {@code Number} representation of the given object
     * @throws ClassCastException if the object is not a {@code Number} or {@code Character}
     */
    private static Number mapToNumber(Object obj) {
        return obj instanceof Number
                ? (Number) obj
                : (int) (char) obj;
    }
}
