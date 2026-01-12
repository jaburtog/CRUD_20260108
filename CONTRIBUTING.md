# Contributing to Inventory Control System

Thank you for your interest in contributing to the Inventory Control System! This document provides guidelines for contributing to the project.

## Getting Started

1. Fork the repository
2. Clone your fork: `git clone https://github.com/YOUR_USERNAME/CRUD_20260108.git`
3. Create a feature branch: `git checkout -b feature/your-feature-name`
4. Make your changes
5. Test your changes thoroughly
6. Commit your changes: `git commit -m "Add feature: description"`
7. Push to your fork: `git push origin feature/your-feature-name`
8. Create a Pull Request

## Development Guidelines

### Code Style

**Java:**
- Follow standard Java naming conventions
- Use meaningful variable and method names
- Add JavaDoc comments for public methods
- Keep methods focused and concise
- Use Jakarta EE best practices

**JavaScript:**
- Use ES6+ features
- Follow consistent indentation (2 spaces)
- Use camelCase for variables and functions
- Add comments for complex logic

**CSS:**
- Use meaningful class names
- Follow BEM naming convention where appropriate
- Keep selectors specific but not overly nested

### Project Structure

```
src/
├── main/
│   ├── java/com/inventory/
│   │   ├── model/          # JPA entities
│   │   ├── repository/     # Data access layer
│   │   ├── service/        # Business logic
│   │   └── rest/           # REST endpoints
│   ├── resources/
│   │   └── META-INF/       # Configuration files
│   ├── webapp/
│   │   ├── WEB-INF/        # Web configuration
│   │   ├── css/            # Stylesheets
│   │   ├── js/             # JavaScript files
│   │   └── index.html      # Main page
│   └── liberty/config/     # Liberty configuration
```

### Testing

Before submitting a pull request:

1. **Build the project:**
   ```bash
   mvn clean package
   ```

2. **Test manually:**
   ```bash
   mvn liberty:dev
   ```
   - Test all CRUD operations through the UI
   - Test all API endpoints with curl or Postman
   - Check for console errors
   - Verify database operations

3. **Check for issues:**
   - No compilation errors
   - No runtime errors
   - Proper error handling
   - Data validation works correctly

### Commit Messages

Use clear and descriptive commit messages:

```
Add feature: description of what was added
Fix: description of what was fixed
Update: description of what was changed
Refactor: description of code improvement
Docs: description of documentation changes
```

Example:
```
Add feature: search functionality for products
Fix: quantity validation allowing negative numbers
Update: improve error messages for API responses
```

## Areas for Contribution

### Features

- [ ] User authentication and authorization
- [ ] Advanced search and filtering
- [ ] Product images upload
- [ ] Export/Import functionality (CSV, Excel)
- [ ] Barcode generation and scanning
- [ ] Low stock alerts
- [ ] Product variants (size, color, etc.)
- [ ] Order management
- [ ] Reporting and analytics
- [ ] Multi-language support

### Improvements

- [ ] Add unit tests
- [ ] Add integration tests
- [ ] Improve error handling
- [ ] Add pagination for large datasets
- [ ] Improve UI/UX design
- [ ] Add dark mode
- [ ] Optimize database queries
- [ ] Add caching layer
- [ ] Improve security
- [ ] Add API documentation with Swagger/OpenAPI

### Documentation

- [ ] Add more examples
- [ ] Create video tutorials
- [ ] Improve troubleshooting guide
- [ ] Add architecture diagrams
- [ ] Create user guide
- [ ] Add API integration examples

## Pull Request Guidelines

1. **One feature per PR** - Keep pull requests focused on a single feature or fix
2. **Update documentation** - Update relevant documentation for your changes
3. **Test thoroughly** - Ensure all existing functionality still works
4. **Clean code** - Remove debug code and console.logs
5. **Descriptive title** - Use a clear title describing what the PR does
6. **Description** - Provide details about what changed and why

### Pull Request Template

```markdown
## Description
Brief description of what this PR does

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Breaking change
- [ ] Documentation update

## Testing
Describe how you tested these changes

## Checklist
- [ ] Code builds without errors
- [ ] All existing features still work
- [ ] New code follows project style
- [ ] Documentation updated
- [ ] No sensitive data committed
```

## Bug Reports

When reporting bugs, please include:

1. **Clear title** describing the issue
2. **Steps to reproduce** the bug
3. **Expected behavior** vs actual behavior
4. **Environment details** (OS, Java version, browser, etc.)
5. **Screenshots** if applicable
6. **Error messages** or logs

### Bug Report Template

```markdown
## Bug Description
Clear description of the bug

## Steps to Reproduce
1. Step one
2. Step two
3. Step three

## Expected Behavior
What should happen

## Actual Behavior
What actually happens

## Environment
- OS: [e.g., Ubuntu 22.04]
- Java: [e.g., 17.0.5]
- Browser: [e.g., Chrome 120]
- Database: [e.g., PostgreSQL 15.2]

## Additional Context
Any other relevant information
```

## Feature Requests

We welcome feature requests! Please include:

1. **Clear description** of the feature
2. **Use case** - why is this feature needed?
3. **Proposed solution** - how should it work?
4. **Alternatives considered** - other ways to solve the problem

## Code Review Process

1. All PRs require review before merging
2. Address review comments promptly
3. Keep discussions professional and constructive
4. Update your PR based on feedback

## Questions?

If you have questions:

1. Check existing documentation
2. Search closed issues for similar questions
3. Open a new issue with the "question" label

## License

By contributing, you agree that your contributions will be licensed under the same license as the project (MIT License).

## Thank You!

Thank you for contributing to the Inventory Control System! Your help makes this project better for everyone.
