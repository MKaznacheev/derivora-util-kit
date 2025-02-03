# Derivora Util Kit

## About the Project

Derivora Util Kit is a module designed to implement fundamental programming utilities in Java. This module is part of the derivora project and provides a collection of reusable tools and helper classes for various applications.

The project has started active development. Currently, it includes core validation utilities, such as ValidationException, ValidationUtil, and Validator.

## Objectives of the Module

* Improve development efficiency: Offer ready-made solutions for common programming tasks.
* Versatility: Create tools suitable for a wide range of use cases.
* Lightweight and efficient: Minimize overhead when using the utilities.

## Project Structure

* Package `xyz.derivora.utilkit.arrays offers` utilities for performing common array operations, such as merging arrays with type safety and determining common component types.
* Package `xyz.derivora.utilkit.lazy` serves as the root package for utilities that facilitate lazy initialization and efficient resource management.
    * Subpackage `xyz.derivora.utilkit.lazy.value` provides utilities for lazily initialized values, designed for single-threaded environments.
    * Subpackage `xyz.derivora.utilkit.lazy.singleton` contains tools for managing lazily initialized singleton objects, including both single-threaded and thread-safe implementations.
* Package `xyz.derivora.utilkit.reflection` provides utilities for performing common reflective operations, such as accessing fields and retrieving their values safely.
  * Subpackage `xyz.derivora.utilkit.reflection.primitives` provides utilities for working with Java's primitive types and their corresponding wrapper classes.
* Package `xyz.derivora.utilkit.validation` provides essential tools for implementing validation logic across the application.

## License

This project is licensed under the GNU Lesser General Public License v3.0.
See the [LICENSE](./LICENSE) file for details.