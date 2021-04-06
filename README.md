# java-spring-webapp-template

Includes everything needed to build a modern webapp based on Java (API, website, or both).

The API is protected using Spring Security + OAuth Resource Server.

It showcases two forms of websites, both of which are protected by OpenID Connect:

- Single Page Application (SPA) - using Svelte and Microsoft Identity (resources are served by Spring MVC). Interacts with API via AJAX
- Server-Side Rendered (SSR) - using Spring MVC, Thymeleaf, Spring Security, and Microsoft Identity. Interacts with API via API client

Components:

- Frontend (SPA) - Svelte + Microsoft Identity
  - Unit testing: Jest
  - System testing (e2e/a11y/perf): Playwright + axe-core
- Frontend (SSR) - Java + Spring + Spring Security + Microsoft Identity
  - Unit testing: JUnit
  - System testing (e2e/a11y/perf): Playwright + axe-core
- API - Java/Spring
  - Unit testing: JUnit
  - Integration testing: newman
  - Smoke testing: k6
  - Load testing: k6
- DB
  - Integration testing: TestContainers

**NOTE: this project installs certs so you can develop locally over HTTPS. You need to have `mkcert` installed**

## Getting Started

This is a Gradle based multiproject repository. The repo template contains an API (/api) and a website (/site). This is to make it easy to create 3-tier systems, but if you don't need one or the other, you can just delete them.

There is a third module, DTOs (/dtos), which is where you should create your inter-service models if using SSR (Java to Java). If your front-end is SPA (JavaScript to Java) or are not using the API Client approach from the website server, this isn't required as communication is done over AJAX.

The recommended way to run Gradle is with `./gradlew <task>` on Linux/Mac, `./gradlew.bat <task>` in Windows, or using the Gradle plugins available for most modern IDEs. If not, you should make sure Gradle version is consistent across all environments. The root `/env.properties` file explicitely indicates the Gradle version that should be used and is checked whenever running a Gradle task.

## Configuration

Each module defines it's own metadata in their directories `env.properties` file, such as application name and version. Primary Spring Boot properties are configured in the `src/main/resources/application.properties`, and profile dependent ones are in `src/main/resources/application-<profile>.properties`.

NOTE: although each module can theoretically define their own Java version, it's much easier to run top-level Gradle tasks if they are the same. If you're using SDKMan as your Java SDK version manager you can define the exact Java vendor/version you want in the root `/.sdkmanrc` file, which you can set using `sdk env`. If you have SDKMans sdkman_auto_env property configured, whenever you work in the root directory it will automatically set the correct version.

## Usage

`./setup.sh && ./gradlew bootRun --parallel` will get you started right away using HTTPS, but requires `mkcert` to be installed. If you don't want to use HTTPS, change the `DEFAULT_LOCAL_PROFILES` property in both `/api/env.properties` and `/site/env.properties` files to just `local`.

The project has been designed using best practices so that all secrets should be passed in as environment variables or via the command line. The `bootRun` task provided by Spring Boot's gradle plugin should be used for local development, since it has been optimized to make development faster/easier. There are some profiles pre-configured (by default when using `bootRun`, the `local` and `https` profiles are applied)

- `local` - easier local development

  - use `src/main/resources/application-local.properties` to configure
  - both services are served on `localhost` (ports 8443 for website and 9443 for API if also using `https` profile, 8080 and 9080 otherwise (see `https` profile below))
  - Spring Boot devtools is included which allows for automatic restart/reload of the applications when the classpath changes
    - works best with LiveReload browser extension installed
    - requires changes to classpath to trigger, so either need to have the IDE recompile on save OR run `./gradlew classes --continuous` in a separate process
  - the API uses an in-memory database for faster startup/restart
  - more descriptive logging output

- `https` - serves the website and the API over HTTPS instead of HTTP

  - use `/api/src/main/resources/application-https.properties` to configure
  - must have certs installed in the Java trust store and in the browsers. There is a convenience script `/setup.sh` which does this for you, but requires having `mkcert` installed
  - Website is served at `https://localhost:8443` instead of `http://localhost:8080`
  - API is served at `https://localhost:9443` instead of `http://localhost:9080`

- `tc` - in API, replaces the in memory DB with a containerized version, useful for integration testing and deploying to staging environment

  - use `/api/src/main/resources/application-tc.properties` to configure
  - must have container engine (e.g. Docker) running
  - must have TestContainer dependencies on the classpath (see `dependencies` block in `/api/build.gradle`)
  - must have database drivers on the classpath (see `dependencies` block in `/api/build.gradle`)

You can explicitely set the profiles using the `-Dprofiles=<profiles>` argument (i.e. `./gradlew bootRun -Dprofiles=local,https,tc`). To make it easier, both API and website modules have been pre-configured to use a default set of profiles during `bootRun` if none are explicitely provided. These are configurable in the `/api/env.properties` and `/site/env.properties`.

REMEMBER THAT THE ORDER THE PROFILES ARE LISTED IS IMPORTANT, AS PROPERTIES FROM LATER FILES OVERRIDE PREVIOUS ONES (for example, the logging properties in `application.properties` vs. `application-local.properties`)

## Suggested Environment + Tasks:

- CI/CD - unit + integration testing
- DEV - smoke/manual testing
- PREPROD - performance/accessibility/load testing, QA approval
- PROD

## TODO

- [ ] API
  - [x] create openapi spec
  - [ ] generate smoke tests
  - [ ] generate load tests
- [ ] Frontend
  - [ ] create authenticated routes
  - [ ] generate e2e/smoke/a11y tests
  - [ ] automatically source endpoints from API's OpenAPI spec
- [x] get devtools working with auto restart
