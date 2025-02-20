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

/**
 * The derivora-util-kit module provides core utilities to support various application-level features.
 *
 * <p>Currently, it includes:
 * <ul>
 *   <li>Array utilities for performing common operations, such as merging arrays with type safety.</li>
 *   <li>Lazy initialization utilities for efficient resource management, including support for
 *       single-threaded and multithreaded environments.</li>
 *   <li>Number utilities for comparing and handling various {@link java.lang.Number} types</li>
 *   <li>Reflection utilities for performing common reflective operations, such as accessing fields and
 *       retrieving their values safely.</li>
 *   <li>Validation utilities for ensuring data integrity and correctness.</li>
 * </ul>
 */
module derivora.util.kit {
    exports xyz.derivora.utilkit.arrays;
    exports xyz.derivora.utilkit.lazy.singleton;
    exports xyz.derivora.utilkit.lazy.value;
    exports xyz.derivora.utilkit.numbers;
    exports xyz.derivora.utilkit.reflection;
    exports xyz.derivora.utilkit.reflection.primitives;
    exports xyz.derivora.utilkit.validation;
}