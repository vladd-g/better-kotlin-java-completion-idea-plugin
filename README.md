# Better Kotlin – Java Completion

This plugin enhances the Java development experience when working with Kotlin data classes by filtering out generated `componentN()` functions from code completion (auto-complete) suggestions.

## Features:
- Filters out generated `componentN()` functions from auto-complete suggestions when working with Kotlin data classes in Java code.
- Improves code readability by focusing on meaningful methods and properties in completion lists.
- Reduces clutter in auto-complete dropdowns, making it easier to find relevant members.

## Why Use This Plugin?
When working on mixed Java-Kotlin projects, developers often encounter autogenerated `componentN()` functions in auto-complete suggestions for Kotlin data classes. These functions, while useful for destructuring in Kotlin, don't serve a purpose in Java code (moreover, they don't appear when calling from Kotlin) and can clutter the auto-complete dropdown.

This plugin streamlines your development process by filtering out these functions from completion results, allowing you to focus on the properties and methods that matter most in your Java code.

## Ideal For:
- Teams working on mixed Java-Kotlin codebases.
- Java developers interfacing with Kotlin libraries or modules.
- Projects transitioning from Java to Kotlin, where interoperability is crucial.

Boost your productivity and code clarity with Better Kotlin – Java Completion!