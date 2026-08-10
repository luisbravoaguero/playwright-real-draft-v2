# Copilot Instructions

## Project Context

This is a Java test automation framework built with:

- Java 17
- Playwright for Java
- Cucumber
- TestNG
- Maven
- Page Object Model
- ExtentReports
- PicoContainer where dependency injection is required

Generate code that is consistent with the existing architecture and conventions of this repository.

## General Coding Guidelines

- Write Java code unless another language is explicitly requested.
- Follow Java 17 conventions and existing project coding standards.
- Prefer clean, readable, maintainable, and reusable code.
- Follow SOLID principles where they provide practical value.
- Avoid unnecessary complexity and over-engineering.
- Do not duplicate functionality that already exists in the framework.
- Before creating a new utility, component, method, or abstraction, inspect the existing project for equivalent functionality.
- Reuse existing framework abstractions whenever possible.
- Keep methods focused on a single responsibility.
- Use descriptive method, class, and variable names.
- Do not add comments that merely restate what the code already communicates.
- Add comments only when they explain non-obvious behavior or an important design decision.

## Playwright Java Guidelines

- Use the Playwright Java API, not Playwright TypeScript syntax.
- Never use TypeScript-specific syntax such as `async`, `await`, or JavaScript arrow-function conventions when generating Java code.
- Translate Playwright TypeScript examples into their appropriate Playwright Java equivalents.
- Prefer Playwright's built-in auto-waiting capabilities over manual waits.
- Do not use `Thread.sleep()` unless explicitly requested and technically justified.
- Prefer resilient user-facing locators.
- Prioritize locator strategies in approximately this order:
    1. `getByRole()`
    2. `getByLabel()`
    3. `getByPlaceholder()`
    4. `getByText()`
    5. `getByTestId()` when the application provides reliable test IDs
    6. CSS selectors only when semantic locators are not practical
    7. XPath only as a last resort
- Avoid brittle selectors based on dynamic classes, generated IDs, or deep DOM structures.
- Use Playwright `Locator` objects instead of manually querying DOM elements whenever possible.
- Do not introduce Selenium APIs into Playwright classes.

## Page Object Model Guidelines

- Keep page locators inside Page Object or reusable Component classes.
- Do not place Playwright locators directly inside Cucumber step definitions unless there is a strong reason.
- Declare reusable locators as `private final Locator` fields when appropriate.
- Initialize page-specific locators in the Page Object constructor.
- Page Objects should encapsulate interactions with the UI.
- Keep business flows readable and avoid exposing unnecessary implementation details to step definitions.
- Reuse existing `BasePage` functionality such as synchronization, clicking, filling, waiting, and other common interactions instead of duplicating those mechanisms.
- Before implementing a new Playwright helper, inspect `BasePage` and existing component classes for equivalent functionality.
- Extract reusable Angular Material behavior into the existing material component abstractions when appropriate.

## Cucumber Guidelines

- Keep Cucumber step definitions thin.
- Step definitions should delegate UI behavior to Page Objects, Components, services, or utilities.
- Do not put complex Playwright interaction logic directly inside step-definition classes.
- Write Gherkin steps from the user's or business perspective rather than describing low-level UI implementation.
- Reuse existing step definitions when their meaning is genuinely equivalent.
- Do not create duplicate step definitions with slightly different wording unless necessary.
- Keep scenarios independent from one another.
- Do not make one scenario depend on the execution or state of another scenario.

## TestNG and Parallel Execution

- Preserve compatibility with TestNG and the existing Cucumber TestNG runner.
- Assume scenarios may be executed in parallel.
- Avoid introducing shared mutable state that could cause concurrency issues.
- Respect the existing framework lifecycle for Browser, BrowserContext, Page, hooks, and test execution.
- Do not create new Playwright, Browser, BrowserContext, or Page instances directly unless the existing architecture requires it.
- Use the framework's existing driver/browser management mechanisms.

## Synchronization and Stability

- Prefer Playwright auto-waiting and actionability checks.
- Avoid fixed waiting times.
- Handle expected application states explicitly.
- Use `addLocatorHandler()` only for genuinely unexpected or unpredictable overlays, dialogs, or UI interruptions.
- For predictable dialogs that are part of the normal business flow, handle them explicitly instead of using a global locator handler.
- Keep locator handlers independent and focused on resolving the specific interruption that triggered them.
- Do not introduce retries merely to hide unstable tests.
- Investigate the underlying cause of flaky behavior before adding retry mechanisms.

## Test Data

- Do not hardcode production credentials, tokens, API keys, passwords, or sensitive information.
- Reuse existing configuration and test-data utilities.
- Use existing random-data generators when appropriate instead of creating duplicate implementations.
- Keep randomly generated values concise unless the scenario specifically requires long values.
- Keep environment-dependent values in configuration rather than Java source code.

## Assertions

- Add assertions only when they verify meaningful expected behavior.
- Prefer assertions that clearly communicate the expected business or UI state.
- Do not add arbitrary assertions solely to increase the number of validations.
- Keep UI interaction responsibilities and validation responsibilities clearly separated when practical.

## Maven and Project Configuration

- Preserve compatibility with Maven and the existing `pom.xml`.
- Do not add new dependencies when the same functionality can reasonably be achieved with dependencies already available in the project.
- Before suggesting a dependency version, inspect the version currently used by the project.
- Do not unnecessarily modify Maven plugins, dependency scopes, or build configuration.
- Generated code must remain compatible with Java 17.

## Framework Architecture

Before generating or modifying code:

1. Inspect the relevant existing Page Object.
2. Inspect `BasePage` when common browser interaction behavior is involved.
3. Inspect existing reusable Components and utilities.
4. Inspect related Step Definition classes.
5. Inspect Hooks or driver management when browser lifecycle changes are involved.
6. Follow the naming and architectural patterns already established in nearby classes.

Do not create parallel implementations of functionality that the framework already provides.

## Code Modification Guidelines

When modifying existing code:

- Make the smallest reasonable change that solves the problem.
- Preserve existing behavior unless a change is explicitly requested.
- Avoid unrelated refactoring.
- Do not rename existing public methods, classes, packages, or Gherkin steps without a clear reason.
- Explain significant architectural changes before implementing them.
- If several solutions are possible, favor the solution most consistent with the existing framework.

## When Generating Tests

- Focus only on the requested scenario unless multiple scenarios are explicitly requested.
- Reuse existing Page Objects, Components, utilities, hooks, and test data.
- Do not create duplicate Page Objects for pages that already exist.
- Do not invent selectors if relevant DOM information is unavailable.
- Prefer DOM information provided by the browser or existing project locators.
- Keep the generated test concise while preserving readability and maintainability.

## Language and Naming Conventions

- Write all Java code using standard Java syntax and English language keywords.
- Use Spanish for business-domain class names, method names, variables, and test data when the existing project uses Spanish terminology.
- Preserve existing Spanish business terminology instead of translating it into English.
- Use English for generic technical concepts and framework abstractions when they are already established in the project.
- Write log messages in Spanish.
- Write Cucumber feature files and Gherkin steps in Spanish when the corresponding business scenario is defined in Spanish.
- Maintain the language and naming conventions of the surrounding code when modifying an existing class.
- Do not mix Spanish and English unnecessarily within the same naming convention.