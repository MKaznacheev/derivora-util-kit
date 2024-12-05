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
 * Exception thrown to indicate that a validation error has occurred.
 * <p>
 * This exception is typically used in scenarios where an object fails to meet
 * predefined validation criteria.
 * </p>
 * <p>
 * It extends <code>RuntimeException</code>, allowing it to be used as an unchecked exception.
 * </p>
 *
 * @see RuntimeException
 */
public class ValidationException extends RuntimeException {

    /**
     * Constructs a new <code>ValidationException</code> with no detail message.
     */
    public ValidationException() {
    }

    /**
     * Constructs a new <code>ValidationException</code> with the specified detail message.
     *
     * @param message the detail message, which can be retrieved using {@link Throwable#getMessage()}
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * Constructs a new <code>ValidationException</code> with the specified detail message
     * and cause.
     *
     * @param message the detail message, which can be retrieved using {@link Throwable#getMessage()}
     * @param cause   the cause of the exception, which can be retrieved using {@link Throwable#getCause()}
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new <code>ValidationException</code> with the specified cause.
     *
     * @param cause the cause of the exception, which can be retrieved using {@link Throwable#getCause()}
     */
    public ValidationException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new <code>ValidationException</code> with the specified detail message,
     * cause, suppression enabled or disabled, and writable stack trace enabled or disabled.
     *
     * @param message            the detail message, which can be retrieved using {@link Throwable#getMessage()}
     * @param cause              the cause of the exception, which can be retrieved using {@link Throwable#getCause()}
     * @param enableSuppression  whether suppression is enabled or disabled
     * @param writableStackTrace whether the stack trace should be writable
     */
    protected ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
