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

package xyz.derivora.utilkit.reflection;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;

/**
 * Utility class for retrieving field values via reflection.
 *
 * <p>This class provides methods to access public and static fields of a given class or object,
 * ensuring both type-safe and unchecked retrieval approaches. It allows retrieving field values
 * as instances of a specified type while handling potential access restrictions and type mismatches gracefully.
 *
 * <p>Methods in this class return {@code Optional} to indicate whether the field value could be successfully retrieved.
 * If the requested field is not found, is inaccessible, or cannot be cast to the expected type,
 * an empty {@code Optional} is returned.
 *
 * <h2>Type Safety</h2>
 * <p>There are two approaches to retrieving field values:
 * <ul>
 *     <li><b>Safe retrieval</b> – Methods that require an explicit type parameter ({@code Class<T> distinctType})
 *     perform type-safe casting and prevent unexpected {@code ClassCastException} occurrences by returning
 *     an empty {@code Optional}.</li>
 *     <li><b>Unchecked retrieval</b> – Methods without an explicit type parameter perform unchecked casts,
 *     meaning a {@code ClassCastException} may not occur immediately but instead manifest later when the
 *     retrieved value is used.</li>
 * </ul>
 *
 * <h2>Access to Static and Instance Fields</h2>
 * <p>Methods in this class support retrieving:
 * <ul>
 *     <li><b>Instance fields</b> – Values are retrieved from a specific object instance.</li>
 *     <li><b>Static fields</b> – Values are retrieved from a class definition using
 *     a {@code null} object reference.</li>
 * </ul>
 *
 * <p><b>Note:</b> This class only provides access to <b>public</b> fields. Private and protected fields
 * are not accessible.
 *
 * <h2>Usage Examples</h2>
 *
 * <p>Retrieving a public field value safely:
 * <pre>{@code
 * Optional<Integer> value = FieldUtils.getFieldValue("someField", someObject, Integer.class);
 * value.ifPresent(System.out::println);
 * }</pre>
 *
 * <p>Retrieving a public static field value:
 * <pre>{@code
 * Optional<String> staticValue = FieldUtils.getStaticFieldValue("CONSTANT", SomeClass.class, String.class);
 * }</pre>
 *
 * <p>Retrieving a {@code Class<?>} type field value:
 * <pre>{@code
 * Optional<Class<?>> clazz = FieldUtils.getClassFieldValue("TYPE", SomeClass.class);
 * }</pre>
 *
 * <p>This class cannot be instantiated.
 */
public final class FieldUtils {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private FieldUtils() {
    }

    /**
     * Retrieves a public field with the specified name from the given class.
     *
     * @param fieldName the name of the field to retrieve (must not be {@code null})
     * @param clazz     the class from which to retrieve the field (must not be {@code null})
     * @return an {@code Optional} containing the field if found, or an empty {@code Optional} if the field does not exist or cannot be accessed
     * @throws NullPointerException if {@code clazz} or {@code fieldName} is {@code null}
     */
    public static Optional<Field> getField(String fieldName, Class<?> clazz) {
        Objects.requireNonNull(clazz, "Class cannot be null");

        Field field;
        try {
            field = clazz.getField(fieldName);
        } catch (SecurityException | NoSuchFieldException e) {
            return Optional.empty();
        }

        return Optional.of(field);
    }

    /**
     * Retrieves the value of a public field with the specified name from the given object, ensuring type safety.
     *
     * <p>This method first attempts to locate a public field with the given name in the object's class.
     * If the field is found, its value is retrieved and cast to the specified type in a safe manner.
     * If the field does not exist, is inaccessible, or its value cannot be cast to the expected type,
     * an empty {@code Optional} is returned.
     *
     * <p><b>Type Safety:</b> This method enforces type safety by requiring the caller to provide an expected type.
     * If the field value is not of the expected type, a {@code ClassCastException} will be caught internally,
     * and an empty {@code Optional} will be returned instead of throwing an exception.
     *
     * @param <T>          the expected type of the field value
     * @param fieldName    the name of the field to retrieve (must not be {@code null})
     * @param object       the object from which the field value is extracted (must not be {@code null})
     * @param distinctType the expected type of the field value for safe casting (must not be {@code null})
     * @return an {@code Optional} containing the field value if the field is found, accessible, and of the expected type,
     * otherwise an empty {@code Optional}
     * @throws NullPointerException if {@code fieldName}, {@code object} or {@code distinctType} is {@code null}
     */
    public static <T> Optional<T> getFieldValue(String fieldName, Object object, Class<T> distinctType) {
        Objects.requireNonNull(object, "Object cannot be null");
        Objects.requireNonNull(distinctType, "Distinct type cannot be null");

        Optional<Field> optionalField = getField(fieldName, object.getClass());

        if (optionalField.isEmpty()) {
            return Optional.empty();
        }
        Field field = optionalField.get();

        return getFieldValue(field, object, distinctType);
    }

    /**
     * Retrieves the value of a public field with the specified name from the given object using an unchecked cast.
     *
     * <p>This method first attempts to locate a public field with the given name in the object's class.
     * If the field is found, its value is retrieved and cast to the expected type without compile-time type safety.
     * If the field does not exist, is inaccessible, or the cast is invalid, an empty {@code Optional} is returned.
     *
     * <p><b>Warning:</b> Since this method does not enforce type safety, a {@code ClassCastException} may not occur immediately
     * but can manifest later in an unexpected location when the retrieved value is used.
     *
     * @param <T>       the expected type of the field value (determined by the caller, but not enforced)
     * @param fieldName the name of the field to retrieve (must not be {@code null})
     * @param object    the object from which the field value is extracted (must not be {@code null})
     * @return an {@code Optional} containing the field value if the field is found, accessible, and cast successfully,
     * otherwise an empty {@code Optional}
     * @throws NullPointerException if {@code fieldName} or {@code object} is {@code null}
     * @implNote This method performs an unchecked cast, meaning that if the actual type of the field value does not match
     * the expected type, a {@code ClassCastException} might not occur immediately but later when the value is used.
     * If type safety is required, consider using an alternative method {@link #getFieldValue(String, Object, Class)} with explicit type enforcement.
     */
    public static <T> Optional<T> getFieldValue(String fieldName, Object object) {
        Objects.requireNonNull(object, "Object cannot be null");

        Optional<Field> optionalField = getField(fieldName, object.getClass());

        if (optionalField.isEmpty()) {
            return Optional.empty();
        }
        Field field = optionalField.get();

        return getFieldValue(field, object);
    }

    /**
     * Retrieves the value of a public field with the specified name from the given object,
     * ensuring that the field's value is a {@code Class<?>} instance.
     *
     * <p>This method first attempts to retrieve the field value and then casts it to {@code Class<?>}
     * in a type-safe manner. If the field does not exist, is inaccessible, or does not contain
     * a value of type {@code Class<?>}, an empty {@code Optional} is returned.
     *
     * <p><b>Type Safety:</b> This method uses an explicit type cast to enforce that the retrieved value
     * is of type {@code Class<?>}. While the cast is technically unchecked, it is constrained to the
     * expected type, minimizing the risk of unexpected {@code ClassCastException} occurrences.
     *
     * @param fieldName the name of the field to retrieve (must not be {@code null})
     * @param object    the object from which the field value is extracted (must not be {@code null})
     * @return an {@code Optional} containing the {@code Class<?>} instance if found and of the correct type,
     * otherwise an empty {@code Optional}
     * @throws NullPointerException if {@code fieldName} or {@code object} is {@code null}
     */
    public static Optional<Class<?>> getClassFieldValue(String fieldName, Object object) {
        @SuppressWarnings("unchecked")
        Optional<Class<?>> optionalClass = getFieldValue(fieldName, object, (Class<Class<?>>) (Class<?>) Class.class);

        return optionalClass;
    }

    /**
     * Retrieves the value of a static field with the specified name from the given class, ensuring type safety.
     *
     * <p>This method first attempts to locate a public static field with the given name in the specified class.
     * If the field is found, its value is retrieved and cast to the specified type in a safe manner.
     * If the field does not exist, is inaccessible, is not static, or its value cannot be cast to the expected type,
     * an empty {@code Optional} is returned.
     *
     * <p><b>Type Safety:</b> This method enforces type safety by requiring the caller to provide an expected type.
     * If the field value is not of the expected type, a {@code ClassCastException} will be caught internally,
     * and an empty {@code Optional} will be returned instead of throwing an exception.
     *
     * @param <T>          the expected type of the field value
     * @param fieldName    the name of the static field to retrieve (must not be {@code null})
     * @param clazz        the class from which to retrieve the static field value (must not be {@code null})
     * @param distinctType the expected type of the field value for safe casting (must not be {@code null})
     * @return an {@code Optional} containing the field value if the field is found, is static, and of the expected type,
     * otherwise an empty {@code Optional}
     * @throws NullPointerException if {@code fieldName}, {@code clazz} or {@code distinctType} is {@code null}
     * @implNote This method internally calls {@code getField} to retrieve the field and then attempts to access
     * its value using a {@code null} object reference, as required for static fields.
     */
    public static <T> Optional<T> getStaticFieldValue(String fieldName, Class<?> clazz, Class<T> distinctType) {
        Objects.requireNonNull(distinctType, "Distinct type cannot be null");

        Optional<Field> optionalField = getField(fieldName, clazz);

        if (optionalField.isEmpty()) {
            return Optional.empty();
        }
        Field field = optionalField.get();

        return getFieldValue(field, null, distinctType);
    }

    /**
     * Retrieves the value of a static field with the specified name from the given class using an unchecked cast.
     *
     * <p>This method first attempts to locate a public static field with the given name in the specified class.
     * If the field is found, its value is retrieved and cast to the expected type without compile-time type safety.
     * If the field does not exist, is inaccessible, is not static, or its value cannot be cast to the expected type,
     * an empty {@code Optional} is returned.
     *
     * <p><b>Warning:</b> Since this method does not enforce type safety, a {@code ClassCastException} may not occur
     * immediately but instead manifest later in an unexpected location when the retrieved value is used.
     *
     * @param <T>       the expected type of the field value (determined by the caller, but not enforced)
     * @param fieldName the name of the static field to retrieve (must not be {@code null})
     * @param clazz     the class from which to retrieve the static field value (must not be {@code null})
     * @return an {@code Optional} containing the field value if the field is found, is static, and cast successfully,
     * otherwise an empty {@code Optional}
     * @throws NullPointerException if {@code fieldName} or {@code clazz} is {@code null}
     * @implNote This method performs an unchecked cast, meaning that if the actual type of the field value does not match
     * the expected type, a {@code ClassCastException} might not occur immediately but later when the value is used.
     * If type safety is required, consider using an alternative method with explicit type enforcement.
     */
    public static <T> Optional<T> getStaticFieldValue(String fieldName, Class<?> clazz) {
        Optional<Field> optionalField = getField(fieldName, clazz);

        if (optionalField.isEmpty()) {
            return Optional.empty();
        }
        Field field = optionalField.get();

        return getFieldValue(field, null);
    }

    /**
     * Retrieves the value of a static field with the specified name from the given class,
     * ensuring that the field's value is a {@code Class<?>} instance.
     *
     * <p>This method first attempts to retrieve the value of a public static field and ensures that
     * it is of type {@code Class<?>}. If the field does not exist, is inaccessible, is not static,
     * or its value is not of the expected type, an empty {@code Optional} is returned.
     *
     * <p><b>Type Safety:</b> This method explicitly casts the retrieved value to {@code Class<?>}.
     * While this cast is unchecked, it is constrained to the expected type, minimizing the risk of
     * unexpected {@code ClassCastException} occurrences.
     *
     * @param fieldName the name of the static field to retrieve (must not be {@code null})
     * @param clazz     the class from which to retrieve the static field value (must not be {@code null})
     * @return an {@code Optional} containing the {@code Class<?>} instance if found and of the correct type,
     * otherwise an empty {@code Optional}
     * @throws NullPointerException if {@code fieldName} or {@code clazz} is {@code null}
     * @implNote This method calls {@code getStaticFieldValue} with an explicit cast to ensure that the
     * retrieved field value is a {@code Class<?>} instance. If type safety is a concern,
     * consider validating the result before usage.
     */
    public static Optional<Class<?>> getClassFieldValue(String fieldName, Class<?> clazz) {
        @SuppressWarnings("unchecked")
        Optional<Class<?>> optionalClass = getStaticFieldValue(
                fieldName,
                clazz,
                (Class<Class<?>>) (Class<?>) Class.class
        );

        return optionalClass;
    }

    /**
     * Retrieves the value of the specified field from the given object with type safety.
     *
     * <p>This method attempts to access the field's value and cast it to the specified type.
     * If the field is inaccessible, the value cannot be cast to the provided type, or the object is {@code null}
     * while the field is not static, an empty {@code Optional} is returned.
     *
     * @param <T>          the expected type of the field value
     * @param field        the field to retrieve the value from (must not be {@code null}, otherwise an empty {@code Optional} is returned)
     * @param object       the object from which the field value is extracted (can be {@code null} only if the field is static)
     * @param distinctType the expected type of the field value for safe casting (must not be {@code null}, otherwise an empty {@code Optional} is returned)
     * @return an {@code Optional} containing the field value if accessible and of the expected type, otherwise an empty {@code Optional}
     * @throws IllegalArgumentException if {@code object} is not an instance of the class or interface declaring the field,
     *                                  or a subclass/implementing class thereof
     */
    private static <T> Optional<T> getFieldValue(Field field, Object object, Class<T> distinctType) {
        T value;
        try {
            Object resultObj = field.get(object);
            value = distinctType.cast(resultObj);
        } catch (NullPointerException | IllegalAccessException | ClassCastException e) {
            return Optional.empty();
        }

        return Optional.ofNullable(value);
    }

    /**
     * Retrieves the value of the specified field from the given object using an unchecked cast.
     *
     * <p>This method attempts to access the field's value and casts it to the expected type.
     * The cast is performed without compile-time type safety, meaning that if the field value is not of the expected type,
     * a {@code ClassCastException} might not occur immediately but instead manifest later in an unexpected location
     * when the value is used. If an exception occurs within this method, an empty {@code Optional} is returned.
     *
     * @param <T>    the expected type of the field value (determined by the caller, but not enforced)
     * @param field  the field to retrieve the value from (must not be {@code null}, otherwise an empty {@code Optional} is returned)
     * @param object the object from which the field value is extracted (can be {@code null} only if the field is static)
     * @return an {@code Optional} containing the field value if accessible and cast successfully, otherwise an empty {@code Optional}
     * @throws IllegalArgumentException if {@code object} is not an instance of the class or interface declaring the field,
     *                                  or a subclass/implementing class thereof
     * @implNote This method performs an unchecked cast using {@code @SuppressWarnings("unchecked")}.
     * If type safety is required, consider using {@link #getFieldValue(Field, Object, Class)} instead.
     */
    private static <T> Optional<T> getFieldValue(Field field, Object object) {
        try {
            @SuppressWarnings("unchecked")
            T value = (T) field.get(object);
            return Optional.ofNullable(value);
        } catch (NullPointerException | IllegalAccessException | ClassCastException e) {
            return Optional.empty();
        }
    }
}
