# groovy-delegate-mixin-trait
Overview of delegate, mixin, category and trait in groovy.

_Reference_: https://stackoverflow.com/questions/23121890/difference-between-delegate-mixin-and-traits-in-groovy     
_Reference_: http://docs.groovy-lang.org/next/html/documentation/#_implement_delegation_pattern_using_delegate_annotation  
_Reference_: http://docs.groovy-lang.org/next/html/documentation/#_class_design_annotations  
_Reference_: http://docs.groovy-lang.org/next/html/documentation/#_compile_time_metaprogramming  


# preface

## trait
They can be seen as interfaces carrying both default 
implementations and state.

```
trait Swimmer {
    def swim() {
        "swimming"
    }
}
```

For more info about traits please refer my other github
repository: https://github.com/mtumilowicz/groovy-trait

## mixins
_Reference_: http://docs.groovy-lang.org/next/html/documentation/#_differences_with_mixins 

We are talking about runtime mixins, not the 
`@Mixin` annotation which is deprecated in favour of traits.

Methods added through a mixin are only 
visible at runtime.

```
class A { String methodFromA() { 'A' } }
class B { String methodFromB() { 'B' } }
A.mixin B
def o = new A()
assert o.methodFromA() == 'A'                   
assert o.methodFromB() == 'B'
assert o instanceof A                        
assert !(o instanceof B)
```

The instances are not modified, so if you mixin some class 
into another, there isn’t a third class generated, and methods 
which respond to A will continue responding to A even if 
mixed in.

## category
Category classes aren’t enabled by default. To use the methods defined in 
a category class it is necessary to apply the scoped `use` (takes the category 
class as its first parameter and a closure code block as second parameter)
method that is provided by the GDK and available from inside every Groovy 
object instance. Inside the Closure access to the category methods is available.

It’s also worth noting that using instance fields in a category class is 
inherently unsafe: categories are not stateful (like traits).

### static convention
_Reference_: http://docs.groovy-lang.org/next/html/documentation/#categories

#### example
Example: `groovy.time.TimeCategory`
```
public class TimeCategory {

    public static Date plus(final Date date, final BaseDuration duration) {
        return duration.plus(date);
    }
    
    ...
}
```
```
use(TimeCategory)  {
    def someDate = new Date()       
    println someDate + 3.months
}
```

#### definitions
We have two requirements that must be met by category classes for its methods 
to be successfully added to a class inside the `use` code block:
* method must be static
* the first argument of the static method must define the type the method is 
attached to once being activated while the other arguments are the normal 
arguments the method will take as parameters

Take a look at exemplary `groovy.time.TimeCategory`:
* method `plus` is static
* the first argument is type `Date` and on the 
`def someDate = new Date()` we invoke method `+`

### annotation base @Category
_Reference_: http://docs.groovy-lang.org/next/html/documentation/#xform-Category

#### example
```
@Category(Integer)
class IntegerUtils {
    boolean even() {
        this % 2 == 0
    }
}
```
```
use(IntegerUtils) {
    2.even()
}
```

#### definitions
Applying the `@Category` annotation has the advantage of being able to use 
instance methods without the target type as a first parameter. The target 
type class is given as an argument to the annotation instead (
the mixed in class can be referenced using `this`)

### summary
* static approach
```
class TripleCategory {
    public static Integer triple(Integer self) {
        3*self
    }
}
use (TripleCategory) {
    assert 9 == 3.triple()
}
```
* `@Category` approach
```
@Category(Integer)
class TripleCategory {
    public Integer triple() { 3*this }
}
use (TripleCategory) {
    assert 9 == 3.triple()
}
```

## delegate
_Reference_: http://docs.groovy-lang.org/next/html/documentation/#_class_design_annotations

The `@Delegate` AST transformation aims at implementing the delegation design pattern.

* useful parameters:
    * excludes - a list of methods to be excluded from delegation
        ```
        class Delegating {
            @Delegate(excludes=['task2']) Worker worker = new Worker()
        }
        ```
    * includes - a list of methods to be included in delegation.
        ```
        class Delegating {
            @Delegate(includes=['task1']) Worker worker = new Worker()
        }
        ```

When the property `field` (type `X`) of class `Y` is annotated with `@Delegate`, 
meaning that the `Y` class will delegate calls to `X` methods to the property `field`.

# project description
We provide examples and tests to the above mentioned features.

* **category** (only annotation approach)
    ```
    @Category(Integer)
    class IntegerUtils {
        boolean even() {
            this % 2 == 0
        }
    }
    ```
    ```
    expect:
    use(IntegerUtils) {
        2.even()
    }
    ```
* **mixin**
    ```
    class StringUtils {
        static String concatWithComma(String self, String other) {
            self + "," + other
        }
    }
    ```
    ```
    def setupSpec() {
        String.mixin StringUtils
    }
    
    def "string concat with comma"() {
        expect:
        "a".concatWithComma("b") == "a,b"
    }
    ```
* trait
    ```
    trait Flyer {
        def fly() {
            "flying"
        }
    }
    ```
    ```
    trait Swimmer {
        def swim() {
            "swimming"
        }
    }
    ```
    ```
    class Cormorant implements Flyer, Swimmer
    ```
    ```
    class Penguin implements Swimmer
    ```
    * tests in `TraitTest`
        ```
        expect:
        new Cormorant() instanceof Flyer
        new Cormorant() instanceof Swimmer
        new Cormorant().swim() == "swimming"
        new Cormorant().fly() == "flying"
        ```
        ```
        expect:
        new Penguin().swim() == "swimming"
        new Penguin() instanceof Swimmer
        !(new Penguin() instanceof Flyer)
        ```
        Note that:
        ```
        when:
        new Penguin().fly()
        
        then:
        thrown(MissingMethodException)
        ```