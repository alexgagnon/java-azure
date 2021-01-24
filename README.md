# java-spring-webapp-template

Includes everything needed to build a modern webapp based on Java + Spring.

Components:

- Frontend - Java + Spring + Thymeleaf
  - Unit testing: JUnit
  - System (e2e) testing: Playwright
  - Accessibility + Performance: Lighthouse
- API - Java + Spring
  - Unit testing: JUnit
  - Integration testing: newman
  - Smoke testing: k6
  - Load testing: k6
- DB
  - Integration testing: TestContainers

## Desired Environments for testing:

- CI/CD - unit + integration testing
- DEV - smoke/manual testing
- PREPROD - performance/accessibility/load testing. QA approval
- PROD

## TODO

- [ ] API
  - [ ] create openapi spec
  - [ ] generate smoke tests
  - [ ] generate load tests
- [ ] Frontend
  - [ ] create authenticated routes
  - [ ] generate e2e tests
