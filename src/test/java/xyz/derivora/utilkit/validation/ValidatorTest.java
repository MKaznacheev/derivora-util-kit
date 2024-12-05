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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import xyz.derivora.utilkit.validation.ValidationException;
import xyz.derivora.utilkit.validation.Validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("xyz/derivora/utilkit/validation")
@DisplayName("Tests for Validator")
class ValidatorTest {

    @Test
    @DisplayName("Unnecessary Validator accepts any object without exceptions")
    void unnecessaryValidator_whenObjectIsAny_shouldNotThrowException() {
        Validator<Object> validator = Validator.unnecessary();
        assertDoesNotThrow(() -> validator.validate(null));
        assertDoesNotThrow(() -> validator.validate(new Object()));
    }

    @Test
    @DisplayName("NonNull Validator should not throw exception when object is not null")
    void nonNullValidator_whenObjectIsNotNull_shouldNotThrowException() {
        Validator<Object> validator = Validator.nonNull();
        assertDoesNotThrow(() -> validator.validate(new Object()));
    }

    @Test
    @DisplayName("NonNull Validator should throw exception when object is null")
    void nonNullValidator_whenObjectIsNull_shouldThrowException() {
        Validator<Object> validator = Validator.nonNull();
        assertThrows(ValidationException.class, () -> validator.validate(null));
    }
}
