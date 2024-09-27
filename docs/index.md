# Instancio Fixture Builder
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

## How does it work?
1. Create a class for your fixture
        
        Example: SomePojo class --> SomePojoFixtures
2. Create a static inner class that extends the ___AbstractFixtureBuilder.
   
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

6. Provide a static method in the wrapper class that facilitates the creation of the fixture builder 
without having to instantiate the fixtures class. Return a new instance of the inner class.

Check the full example [here](./example.md)