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

/**
 * Functional interface for validating objects based on predefined rules.
 * This interface provides two pre-defined validators:
 * <ul>
 *   <li><code>unnecessary()</code>: A validator that accepts all objects without any conditions.</li>
 *   <li><code>nonNull()</code>: A validator that accepts only non-null objects.</li>
 * </ul>
 * <p>
 * If an object does not satisfy the validation rules, a <code>ValidationException</code> is thrown.
 * </p>
 *
 * @param <T> the type of the object to be validated
 * @see ValidationException
 */
@FunctionalInterface
public interface Validator<T> {

    /**
     * Validates the given object.
     * Throws a <code>ValidationException</code> if the object does not meet the validation criteria.
     *
     * @param arg the object to validate
     * @throws ValidationException if the object fails validation
     */
    void validate(T arg);

    /**
     * Returns a <code>Validator&lt;Object&gt;</code> that always validates objects unconditionally.
     *
     * @return a validator that accepts all objects without any conditions
     */
    static Validator<Object> unnecessary() {
        return arg -> {};
    }

    /**
     * Returns a <code>Validator&lt;Object&gt;</code> that accepts only non-null objects.
     *
     * @return a validator that accepts non-null objects
     */
    static Validator<Object> nonNull() {
        return arg -> {
            if (arg == null) {
                throw new ValidationException("Argument cannot be null");
            }
        };
    }
}
