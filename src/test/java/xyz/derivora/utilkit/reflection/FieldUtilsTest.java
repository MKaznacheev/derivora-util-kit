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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Tag("xyz/derivora/utilkit/reflection")
@DisplayName("Tests for FieldUtils")
class FieldUtilsTest {

    static final Example EXAMPLE = new Example();

    @Nested
    @Tag("xyz/derivora/utilkit/reflection")
    @Tag("getField")
    @DisplayName("Tests for getField")
    class GetFieldTests {

        @Test
        @DisplayName("Should throw NullPointerException when field name is null")
        void getField_withNullFieldName_shouldThrowNullPointerException() {
            assertThrows(
                    NullPointerException.class,
                    () -> FieldUtils.getField(null, Example.class)
            );
        }

        @Test
        @DisplayName("Should throw NullPointerException when class is null")
        void getField_withNullClass_shouldThrowNullPointerException() {
            assertThrows(
                    NullPointerException.class,
                    () -> FieldUtils.getField("fieldName", null)
            );
        }

        @Test
        @DisplayName("Should return empty value when field doesn't exist")
        void getField_whenNoSuchField_shouldReturnEmptyValue() {
            Optional<Field> optionalField = FieldUtils.getField("nonExistentField", Example.class);
            assertTrue(optionalField.isEmpty());
        }

        @Test
        @DisplayName("Should return optional value when field exists")
        void getField_whenFieldExists_shouldReturnValue() {
            String fieldName = "str";
            Optional<Field> optionalField = FieldUtils.getField(fieldName, Example.class);

            assertTrue(optionalField.isPresent());
            assertEquals(fieldName, optionalField.get().getName());
        }
    }

    @Nested
    @Tag("xyz/derivora/utilkit/reflection")
    @Tag("getFieldValue")
    @DisplayName("Tests for getFieldValue in safe context")
    class GetFieldValueSafeContextTests {

        @Test
        @DisplayName("Should throw NullPointerException when field name is null")
        void getFieldValue_withNullFieldName_shouldThrowNullPointerException() {
            assertThrows(
                    NullPointerException.class,
                    () -> FieldUtils.getFieldValue(null, EXAMPLE, String.class)
            );
        }

        @Test
        @DisplayName("Should throw NullPointerException when object is null")
        void getFieldValue_withNullObject_shouldThrowNullPointerException() {
            assertThrows(
                    NullPointerException.class,
                    () -> FieldUtils.getFieldValue("str", null, String.class)
            );
        }

        @Test
        @DisplayName("Should throw NullPointerException when distinct type is null")
        void getFieldValue_withNullDistinctType_shouldThrowNullPointerException() {
            assertThrows(
                    NullPointerException.class,
                    () -> FieldUtils.getFieldValue("str", EXAMPLE, null)
            );
        }

        @Test
        @DisplayName("Should return empty value when field doesn't exist")
        void getFieldValue_whenNoSuchField_shouldReturnEmptyValue() {
            Optional<String> optionalField = FieldUtils.getFieldValue(
                    "nonExistentField",
                    EXAMPLE,
                    String.class
            );
            assertTrue(optionalField.isEmpty());
        }

        @Test
        @DisplayName("Should return empty value when distinct type is wrong")
        void getFieldValue_withWrongDistinctType_shouldReturnEmptyValue() {
            Optional<Integer> optionalField = FieldUtils.getFieldValue("str", EXAMPLE, Integer.class);
            assertTrue(optionalField.isEmpty());
        }

        @Test
        @DisplayName("Should return optional value when field exists")
        void getFieldValue_whenFieldExists_shouldReturnValue() {
            Optional<String> optionalField = FieldUtils.getFieldValue("str", EXAMPLE, String.class);
            assertEquals(Optional.of(EXAMPLE.str), optionalField);
        }
    }

    @Nested
    @Tag("xyz/derivora/utilkit/reflection")
    @Tag("getFieldValue")
    @DisplayName("Tests for getFieldValue")
    class GetFieldValueTests {

        @Test
        @DisplayName("Should throw NullPointerException when field name is null")
        void getFieldValue_withNullFieldName_shouldThrowNullPointerException() {
            assertThrows(
                    NullPointerException.class,
                    () -> FieldUtils.getFieldValue(null, EXAMPLE)
            );
        }

        @Test
        @DisplayName("Should throw NullPointerException when object is null")
        void getFieldValue_withNullObject_shouldThrowNullPointerException() {
            assertThrows(
                    NullPointerException.class,
                    () -> FieldUtils.getFieldValue("str", null)
            );
        }

        @Test
        @DisplayName("Should return empty value when field doesn't exist")
        void getFieldValue_whenNoSuchField_shouldReturnEmptyValue() {
            Optional<String> optionalField = FieldUtils.getFieldValue("nonExistentField", EXAMPLE);
            assertTrue(optionalField.isEmpty());
        }

        @Test
        @DisplayName("Should return optional value when field exists")
        void getFieldValue_whenFieldExists_shouldReturnValue() {
            Optional<String> optionalField = FieldUtils.getFieldValue("str", EXAMPLE);
            assertEquals(Optional.of(EXAMPLE.str), optionalField);
        }
    }

    @Nested
    @Tag("xyz/derivora/utilkit/reflection")
    @Tag("getClassFieldValue")
    @DisplayName("Tests for getClassFieldValue")
    class GetClassFieldValueTests {

        @Test
        @DisplayName("Should throw NullPointerException when field name is null")
        void getClassFieldValue_withNullFieldName_shouldThrowNullPointerException() {
            assertThrows(
                    NullPointerException.class,
                    () -> FieldUtils.getClassFieldValue(null, EXAMPLE)
            );
        }

        @Test
        @DisplayName("Should throw NullPointerException when object is null")
        void getClassFieldValue_withNullObject_shouldThrowNullPointerException() {
            assertThrows(
                    NullPointerException.class,
                    () -> FieldUtils.getClassFieldValue("clazz", null)
            );
        }

        @Test
        @DisplayName("Should return empty value when field doesn't exist")
        void getClassFieldValue_whenNoSuchField_shouldReturnEmptyValue() {
            Optional<Class<?>> optionalField = FieldUtils.getClassFieldValue("nonExistentField", EXAMPLE);
            assertTrue(optionalField.isEmpty());
        }

        @Test
        @DisplayName("Should return empty value when field value type is wrong")
        void getClassFieldValue_withWrongFieldValueType_shouldReturnEmptyValue() {
            Optional<Class<?>> optionalField = FieldUtils.getClassFieldValue("str", EXAMPLE);
            assertTrue(optionalField.isEmpty());
        }

        @Test
        @DisplayName("Should return optional value when field exists")
        void getClassFieldValue_whenFieldExists_shouldReturnValue() {
            Optional<Class<?>> optionalField = FieldUtils.getClassFieldValue("clazz", EXAMPLE);
            assertEquals(Optional.of(EXAMPLE.clazz), optionalField);
        }
    }

    @Nested
    @Tag("xyz/derivora/utilkit/reflection")
    @Tag("getStaticFieldValue")
    @DisplayName("Tests for getStaticFieldValue in safe context")
    class GetStaticFieldValueSafeContextTests {

        @Test
        @DisplayName("Should throw NullPointerException when field name is null")
        void getStaticFieldValue_withNullFieldName_shouldThrowNullPointerException() {
            assertThrows(
                    NullPointerException.class,
                    () -> FieldUtils.getStaticFieldValue(null, Example.class, Integer.class)
            );
        }

        @Test
        @DisplayName("Should throw NullPointerException when class is null")
        void getStaticFieldValue_withNullClass_shouldThrowNullPointerException() {
            assertThrows(
                    NullPointerException.class,
                    () -> FieldUtils.getStaticFieldValue("INT", null, Integer.class)
            );
        }

        @Test
        @DisplayName("Should throw NullPointerException when distinct type is null")
        void getStaticFieldValue_withNullDistinctType_shouldThrowNullPointerException() {
            assertThrows(
                    NullPointerException.class,
                    () -> FieldUtils.getStaticFieldValue("INT", Example.class, null)
            );
        }

        @Test
        @DisplayName("Should return empty value when field doesn't exist")
        void getStaticFieldValue_whenNoSuchField_shouldReturnEmptyValue() {
            Optional<Integer> optionalField = FieldUtils.getStaticFieldValue(
                    "nonExistentField",
                    Example.class,
                    Integer.class
            );
            assertTrue(optionalField.isEmpty());
        }

        @Test
        @DisplayName("Should return empty value when distinct type is wrong")
        void getStaticFieldValue_withWrongDistinctType_shouldReturnEmptyValue() {
            Optional<String> optionalField = FieldUtils.getStaticFieldValue(
                    "INT",
                    Example.class,
                    String.class
            );
            assertTrue(optionalField.isEmpty());
        }

        @Test
        @DisplayName("Should return optional value when field exists")
        void getStaticFieldValue_whenFieldExists_shouldReturnValue() {
            Optional<Integer> optionalField = FieldUtils.getStaticFieldValue(
                    "INT",
                    Example.class,
                    Integer.class
            );
            assertEquals(Optional.of(Example.INT), optionalField);
        }
    }

    @Nested
    @Tag("xyz/derivora/utilkit/reflection")
    @Tag("getStaticFieldValue")
    @DisplayName("Tests for getStaticFieldValue")
    class GetStaticFieldValueTests {

        @Test
        @DisplayName("Should throw NullPointerException when field name is null")
        void getStaticFieldValue_withNullFieldName_shouldThrowNullPointerException() {
            assertThrows(
                    NullPointerException.class,
                    () -> FieldUtils.getStaticFieldValue(null, Example.class)
            );
        }

        @Test
        @DisplayName("Should throw NullPointerException when class is null")
        void getStaticFieldValue_withNullClass_shouldThrowNullPointerException() {
            assertThrows(
                    NullPointerException.class,
                    () -> FieldUtils.getStaticFieldValue("INT", null)
            );
        }

        @Test
        @DisplayName("Should return empty value when field doesn't exist")
        void getStaticFieldValue_whenNoSuchField_shouldReturnEmptyValue() {
            Optional<Integer> optionalField = FieldUtils.getStaticFieldValue("nonExistentField", Example.class);
            assertEquals(Optional.empty(), optionalField);
        }

        @Test
        @DisplayName("Should return optional value when field exists")
        void getStaticFieldValue_whenFieldExists_shouldReturnValue() {
            Optional<Integer> optionalField = FieldUtils.getStaticFieldValue("INT", Example.class);
            assertEquals(Optional.of(Example.INT), optionalField);
        }
    }

    @Nested
    @Tag("xyz/derivora/utilkit/reflection")
    @Tag("getClassFieldValue")
    @DisplayName("Tests for getClassFieldValue in static context")
    class GetClassFieldValueStaticContextTests {

        @Test
        @DisplayName("Should throw NullPointerException when field name is null")
        void getClassFieldValue_withNullFieldName_shouldThrowNullPointerException() {
            assertThrows(
                    NullPointerException.class,
                    () -> FieldUtils.getClassFieldValue(null, Example.class)
            );
        }

        @Test
        @DisplayName("Should throw NullPointerException when class is null")
        void getClassFieldValue_withNullClass_shouldThrowNullPointerException() {
            assertThrows(
                    NullPointerException.class,
                    () -> FieldUtils.getClassFieldValue("CLASS", null)
            );
        }

        @Test
        @DisplayName("Should return empty value when field doesn't exist")
        void getClassFieldValue_whenNoSuchField_shouldReturnEmptyValue() {
            Optional<Class<?>> optionalField = FieldUtils.getClassFieldValue("nonExistentField", Example.class);
            assertTrue(optionalField.isEmpty());
        }

        @Test
        @DisplayName("Should return empty value when field type type is wrong")
        void getStaticFieldValue_withWrongFieldType_shouldReturnEmptyValue() {
            Optional<Class<?>> optionalField = FieldUtils.getClassFieldValue("INT", Example.class);
            assertTrue(optionalField.isEmpty());
        }

        @Test
        @DisplayName("Should return optional value when field exists")
        void getStaticFieldValue_whenFieldExists_shouldReturnValue() {
            Optional<Class<?>> optionalField = FieldUtils.getClassFieldValue("CLASS", Example.class);
            assertEquals(Optional.of(Example.CLASS), optionalField);
        }
    }

    static class Example {

        public static final Integer INT = 0;
        public static final Class<Object> CLASS = Object.class;

        public final String str = "str";
        public final Class<Object> clazz = Object.class;
    }
}
