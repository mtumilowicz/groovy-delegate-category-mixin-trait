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
_Reference_: http://docs.groovy-lang.org/next/html/documentation/#xform-Category
_Reference_: http://docs.groovy-lang.org/next/html/documentation/#categories

Category classes aren’t enabled by default. To use the methods defined in 
a category class it is necessary to apply the scoped use method that is 
provided by the GDK and available from inside every Groovy object instance.

```
@Category(Integer)
class TripleCategory {
    public Integer triple() { 3*this }
}

use (TripleCategory) {
    assert 9 == 3.triple()
}
```

Note that the mixed in class can be referenced using this instead.

It’s also worth noting that using instance fields in a category class is 
inherently unsafe: categories are not stateful (like traits).

Applying the @Category annotation has the advantage of being able to use 
instance methods without the target type as a first parameter. The target 
type class is given as an argument to the annotation instead.

`groovy.time.TimeCategory`