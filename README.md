# java-spring-webapp-template

Includes everything needed to build a modern webapp based on Java + Spring.

Components:

- Frontend - Java + Spring + Thymeleaf
  - Unit testing: JUnit
  - System testing (e2e/a11y/perf): Playwright + axe-core
- API - Java + Spring
  - Unit testing: JUnit
  - Integration testing: newman
  - Smoke testing: k6
  - Load testing: k6
- DB
  - Integration testing: TestContainers

**NOTE: this project installs certs so you can develop locally over HTTPS. You need to have `mkcert` installed**

## Desired Environments for testing:

- CI/CD - unit + integration testing
- DEV - smoke/manual testing
- PREPROD - performance/accessibility/load testing. QA approval
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
- [ ] get devtools working with auto restart
