# Instancio Fixture Builder

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=wouter-bauweraerts_instancio-fixture-builder&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=wouter-bauweraerts_instancio-fixture-builder)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=wouter-bauweraerts_instancio-fixture-builder&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=wouter-bauweraerts_instancio-fixture-builder)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=wouter-bauweraerts_instancio-fixture-builder&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=wouter-bauweraerts_instancio-fixture-builder)
## What is Instancio

> Instancio is a library for instantiating and populating objects with random data, 
> making your tests more dynamic. Each test run is against a new set of inputs.

While testing, you'll want your data to be as random as possible. 
Instancio provides you with a lot of powerful tools to achieve this, 
without losing the realism of your test data.

To learn more about instancio. [Visit their website](https://www.instancio.org)

## Keep your tests clean!

While Instancio is a very powerful tool to generate test data, 
you'll probably want to keep your tests as clean as possible.

One way to do this, is to move the instantiation of your test objects to what's often called test fixtures.
This is where instancio-fixture-builder comes in!

Very often you're working with immutable objects, so you can't change any values once your object is created.
If you require an object with some specific values (while the other attributes are initialized with random values), 
you'll find yourself creating loads of fixture methods with different sets of parameters.

## Builder pattern to the rescue!

I had the idea to solve this method overload with a simple solution. 
What if I introduced the builder pattern to my fixtures? 
It took me a few iterations to get me to the flexible solution I have now,
but in my opinion it is something very useful. My entire team is using this pattern right now!

## Installation

### Maven
Include the following dependency in your pom.xml
```xml
<dependency>
    <groupId>io.github.wouter-bauweraerts</groupId>
    <artifactId>instancio-fixture-builder</artifactId>
    <version>5.3.0.1</version>
</dependency>
```

Check [the Maven Central Repository](https://central.sonatype.com/artifact/io.github.wouter-bauweraerts/instancio-fixture-builder) for the most recent version.
There you can also find how to include it with different build systems.

## How does it work?
1. Create a class for your fixture
        
        Example: SomePojo class --> SomePojoFixtures
2. Create a static inner class that extends __AbstractFixtureBuilder__.
   
        The AbstractFixtureBuilder requires 2 generic parameters.
            - TYPEBUILDER: the (POJO) class that you want to initialize with (random) data
            - SELF: The classname of the AbstractFixtureBuilder subclass you are writing. 
              This is required to be able to return itself.

3. There are a few things you'll have to do if you have a POJO that you want to create a fixture for.
First of all, you'll have to learn how to create [Instancio Models](https://www.instancio.org/user-guide/#using-models)
(_A model in Instancio is a definition of rules that Instancio has to follow when generating the random data to initialize the object_)


4. For every parameter that you need to be able to control, add a method to set the specific value of to ignore the field (set to NULL)


5. Implement the abstract methods from AbstractFixtureBuilder
    - self method: returns the current instance of a AbstractFixtureBuilder implementation    
    ```java
     public ConcreteFixtureBuilderImplementation self() {
         return this;
     }
     ```
   - build method: triggers the actual instantiation and returns the created instance.
   ```java
   public SomePojo build() {
        return buildInternal(MODEL_CREATED_IN_STEP_3);
   }
   ```
   
__Code Generation:__ The manual implementation of the AbstractFixtureBuilder requires boilerplate code.
This can be avoided by [generating the Fixture Builder implementation.](./example-with-annotation-processing.md)

6. Provide a static method in the wrapper class that facilitates the creation of the fixture builder 
without having to instantiate the fixtures class. Return a new instance of the inner class.

Check the full example (without annotation processing) [here](./example-without-annotation-processing)
Check the full example (with annotation processing) [here](./example-with-annotation-processing)

## Releases
Initial release: 5.0.2. For simplicity, I use the same versioning as Instancio.

| Version  | Release date |
|----------|--------------|
| 5.5.0    | Sep 30 2025  |
| 5.4.1.2  | Apr 24 2025  |
| 5.4.1.1  | Apr 18 2025  |
| 5.4.1    | Mar 25 2025  |
| 5.4.0    | Mar 10 2025  |
| 5.3.0.2  | Feb 17 2025  |
| 5.3.0.1  | Jan 23 2025  |
| 5.3.0    | Jan 23 2025  |
| 5.2.1-ap | Jan 10 2025  |
| 5.2.1    | Dec 18 2024  |
| 5.0.2    | Sep 27 2024  |

## Changelog

### 5.5.0
- Bump instancio version to 5.5.0
- Bump junit version to 5.12.2
- Bump Mockito version to 5.16.2

### 5.4.1.2
- Fix issue with primitive booleans in toFixtureBuilder generation

### 5.4.1.1
- Fix issue with static fields

### 5.4.1
- Bump instancio version to 5.4.1
- Bump junit version to 5.12.1
- Bump Mockito version to 5.16.1
- 
### 5.4.0
- Bump instancio version to 5.4.0
- Bump junit version to 5.12.0
- Bump Mockito version to 5.16.0

### 5.3.0.2
- Improved support for inheritance
- Add toFixtureBuilder functionality allowing you to create a fixture builder from an existing instance

### 5.3.0.1
- Patched version of 5.3.0 where the annotation processor configuration is included

### 5.3.0
- Instancio version bump
- Restructure project
- Add GH actions
> META-INF does not contain information to register the annotation processor for code generation. Use version 5.3.0.1 instead

### 5.2.1
- Add annotation processing / FixtureBuilder code generation

### 5.2.1
- Bump instancio version to version 5.2.1

### 5.0.2
- Initial release. Expose AbstractFixtureBuilde

__Usage Notes:__ 
To use the annotation processing, make sure that your project uses at least Java 21!

Code generation has been tested in IntelliJ IDEA 2024.3.1.1 (Ultimate Edition) using both Maven and Gradle on JDK21.

## Problems / Feature Requests
If you encounter an issue or if you have a feature request, please [submit a GitHub issue!](https://github.com/wouter-bauweraerts/instancio-fixture-builder/issues)