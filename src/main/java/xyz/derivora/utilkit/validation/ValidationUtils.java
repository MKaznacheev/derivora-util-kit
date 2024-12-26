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

import java.util.Collection;
import java.util.Objects;

/**
 * Utility class for validating objects to ensure they are non-null.
 * <p>
 * This class provides methods to validate arrays and collections of objects,
 * with the ability to specify custom exception messages.
 * <p>
 * If any object in the provided array or collection is null, a {@link ValidationException}
 * is thrown. If the array or collection itself is null, a {@link NullPointerException}
 * is thrown.
 * <p>
 * This class is not instantiable.
 */
public final class ValidationUtils {

    /**
     * The default exception message used when a null object is encountered.
     */
    public static final String DEFAULT_MESSAGE = "Object cannot be null";

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private ValidationUtils() {
    }

    /**
     * Ensures all objects in the provided array are non-null.
     *
     * @param objects the array of objects to validate
     * @throws NullPointerException if the array is null
     * @throws ValidationException  if any object in the array is null
     */
    public static void requireAllNonNull(Object... objects) {
        requireAllNonNull(DEFAULT_MESSAGE, objects);
    }

    /**
     * Ensures all objects in the provided array are non-null, using a custom exception message.
     *
     * @param message the custom message for the exception if an object is null
     * @param objects the array of objects to validate
     * @throws NullPointerException if the array is null
     * @throws ValidationException  if any object in the array is null
     */
    public static void requireAllNonNull(String message, Object... objects) {
        Objects.requireNonNull(objects, "The provided array is null");

        if (message == null) {
            message = DEFAULT_MESSAGE;
        }

        for (Object object : objects) {
            if (object == null) {
                throw new ValidationException(message);
            }
        }
    }

    /**
     * Ensures all objects in the provided collection are non-null.
     *
     * @param objects the collection of objects to validate
     * @throws NullPointerException if the collection is null
     * @throws ValidationException  if any object in the collection is null
     */
    public static void requireAllNonNull(Collection<Object> objects) {
        requireAllNonNull(DEFAULT_MESSAGE, objects);
    }

    /**
     * Ensures all objects in the provided collection are non-null, using a custom exception message.
     *
     * @param message the custom message for the exception if an object is null
     * @param objects the collection of objects to validate
     * @throws NullPointerException if the collection is null
     * @throws ValidationException  if any object in the collection is null
     */
    public static void requireAllNonNull(String message, Collection<Object> objects) {
        Objects.requireNonNull(objects, "The provided collection is null");

        if (message == null) {
            message = DEFAULT_MESSAGE;
        }

        for (Object object : objects) {
            if (object == null) {
                throw new ValidationException(message);
            }
        }
    }
}
