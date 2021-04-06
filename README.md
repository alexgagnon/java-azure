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

This is a Gradle based multiproject repository. The repo templates contains an API (/api) and a website (/site). This is to make it easy to create 3-tier systems, but if you don't need one or the other, you can just delete them.

There is a third module, DTOs (/dto), which is where you should create your inter-service models if using SSR (Java to Java). If your front-end is SPA, this isn't required (JavaScript to Java).

## Usage

`./setup.sh && ./gradlew bootRun --parallel` will get you started right away

The project has been designed using best practices so that all secrets should be passed in as environment variables or via the command line. The `bootRun` task provided by Spring Boot's gradle plugin should be used for local development, since it has been optimized to make development faster/easier. It automatically applies a Spring Boot profile called `local` (configured through `src/main/resources/application-local.properties`):

- both services are served on `localhost` (ports 8443 for website and 9443 for API if also using `https` profile, 8080 and 9080 otherwise (see below))
- Spring Boot devtools is included which allows for automatic restart/reload of the applications when the classpath changes
  - works best with LiveReload browser extension installed
  - requires changes to classpath to trigger, so either need to have the IDE recompile on save OR run `./gradlew classes --continuous` in a separate process
- the API uses an in-memory database for faster startup/restart
- more descriptive logging output

To make local development more similar to production environments, there are two other profiles configured:

- `https` - serves the website and the API over HTTPS instead of HTTP

  - must have certs installed in the Java trust store and in the browsers. There is a convenience script `/setup.sh` which does this for you, but requires having `mkcert` installed
  - Website is served at `https://localhost:8443` instead of `http://localhost:8080`
  - API is served at `https://localhost:9443` instead of `http://localhost:9080`
  - use `/api/src/main/resources/application-https.properties` to configure

- `tc` - in API, replaces the in memory DB with a containerized version, useful for integration testing and deploying to staging environment

  - must have docker engine running
  - must have TestContainer dependencies on the classpath (see `dependencies` block in `/api/build.gradle`)
  - must have database drivers on the classpath (see `dependencies` block in `/api/build.gradle`)
  - use `/api/src/main/resources/application-tc.properties` to configure

You can explicitely set the profiles using the `-Dprofiles=<profiles>` argument (i.e. `./gradlew bootRun -Dprofiles=local,https,tc`). To make it easier, both API and website modules have been pre-configured to use a default set of profiles during `bootRun` if none are explicitely provided. These are configurable in the `/api/env.properties` and `/site/env.properties`.

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
