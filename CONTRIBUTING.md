## Environment Setup

Ensure your development environment has:
- JDK 1.8 (Java 8)
- Gradle 8.x

## Building the Project

After cloning the project, run in the project root:

```shell
./gradlew build
```

## IDE Setup

IntelliJ IDEA is recommended. When importing the project, select import as a Gradle project.

## Code Style

This project follows Google Java Style guidelines. In IntelliJ IDEA:

1. Install the google-java-format plugin
2. Enable google-java-format in settings

## Git Hooks

We use the Gradle spotless plugin to ensure code quality. The project is configured to run checks automatically before each commit.

To manually format code, run:

```shell
./gradlew spotlessApply
```

## Dependency Management

This project uses Gradle for dependency management. To add new dependencies, modify the `build.gradle` file.

Example:
```groovy
dependencies {
    implementation 'com.example:library:1.0.0'
}
```

Make sure to run tests before committing:

```shell
./gradlew test
```