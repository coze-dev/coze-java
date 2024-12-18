## Environment Setup

Ensure your development environment has:
- JDK 1.8 (Java 8)
- Maven 3.x

## Building the Project

After cloning the project, run in the project root:

```shell
+mvn clean install
```

## IDE Setup

IntelliJ IDEA is recommended. When importing the project, select import as a Maven project.

## Code Style

This project follows Google Java Style guidelines. In IntelliJ IDEA:

1. Install the google-java-format plugin
2. Enable google-java-format in settings

You can run following command on the terminal to format code:

```shell
mvn spotless:apply
```

## Git Hooks

We use the Maven spotless plugin to ensure code quality. The project is configured to run checks automatically before each commit.

To manually format code, run:

```shell
mvn spotless:apply
```

## Dependency Management

This project uses Maven for dependency management. To add new dependencies, modify the `build.gradle` file.

Example:
```xml
<dependency>
    <groupId>com.example</groupId>
    <artifactId>example</artifactId>
    <version>version</version>
</dependency>
```

Make sure to run tests before committing:

```shell
mvn clean test jacoco:report jacoco:check
```