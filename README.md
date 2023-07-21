### Gradle Testing

```sh
docker pull gradle:7.3.1-jdk11-alpine
```
```sh
IMAGE=basic-xls-test-upstream
docker image rm $IMAGE
docker build -t $IMAGE -f Dockerfile .
```
* basic test

```sh
mkdir build
docker container run --rm -v $(pwd)/build:/work/build:rw $IMAGE
ls -l build
```
```sh
docker container run -v $(pwd)/build:/work/build:rw -it $IMAGE 
```
```text
Welcome to Gradle 7.3.1!

Here are the highlights of this release:
 - Easily declare new test suites in Java projects
 - Support for Java 17
 - Support for Scala 3

For more details see https://docs.gradle.org/7.3.1/release-notes.html

Starting a Gradle Daemon (subsequent builds will be faster)
> Task :libsProd
> Task :libsTest
> Task :compileJava
> Task :processResources NO-SOURCE
> Task :classes
> Task :compileTestJava
> Task :processTestResources
> Task :testClasses

> Task :test

com.codeborne.xlstest.CreateXLSXTest > fromFile STANDARD_ERROR
    ERROR StatusLogger Log4j2 could not find a logging implementation. Please add log4j-core to the classpath. Using SimpleLogger to log to the console...

Deprecated Gradle features were used in this build, making it incompatible with Gradle 8.0.

You can use '--warning-mode all' to show the individual deprecation warnings and determine if they come from your own scripts or plugins.

See https://docs.gradle.org/7.3.1/userguide/command_line_interface.html#sec:command_line_warnings

BUILD SUCCESSFUL in 51s
6 actionable tasks: 6 executed
```

### Troubleshooting

```sh
docker container run --rm -v $(pwd)/build:/work/build:rw -it $IMAGE sh
```

in the container run

```sh
gradle -d test
```

### NOTE: 

does not work with gradle __8.x__:

```sh
docker pull gradle:8.1-jdk11-alpine
```
update `Dockerfile`  or
```sh
IMAGE=basic-xls-test-upstream-broken
docker image rm $IMAGE
docker build --build-arg "BASE=gradle:8.1-jdk11-alpine" -t $IMAGE -f Dockerfile .
```
```text
Starting a Gradle Daemon (subsequent builds will be faster)

FAILURE: Build failed with an exception.

* Where:
Script '/work/gradle/publish.gradle' line: 11

* What went wrong:
A problem occurred evaluating script.
> Could not set unknown property 'classifier' for task ':sourcesJar' of type org.gradle.api.tasks.bundling.Jar.

```
