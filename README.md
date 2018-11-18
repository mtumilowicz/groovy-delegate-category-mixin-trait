# groovy-delegate-mixin-trait
Overview of delegate, mixin, category and trait in groovy.

_Reference_: https://stackoverflow.com/questions/23121890/difference-between-delegate-mixin-and-traits-in-groovy  
_Reference_: http://docs.groovy-lang.org/next/html/documentation/#_differences_with_mixins    
_Reference_: http://docs.groovy-lang.org/next/html/documentation/#_implement_delegation_pattern_using_delegate_annotation  
_Reference_: http://docs.groovy-lang.org/next/html/documentation/#_class_design_annotations  
_Reference_: http://docs.groovy-lang.org/next/html/documentation/#categories  
_Reference_: http://docs.groovy-lang.org/next/html/documentation/#_compile_time_metaprogramming  


# preface

## trait
_Reference_: http://docs.groovy-lang.org/next/html/documentation/core-traits.html

Traits are a structural construct of the language which allows:
* composition of behaviors
* runtime implementation of interfaces
* behavior overriding
* compatibility with static type checking/compilation

They can be seen as interfaces carrying both default 
implementations and state. 

A trait is defined using the trait keyword:
```
trait Swimmer {
    def swim() {
        "swimming"
    }
}
```

* Traits may declare abstract methods.
* Traits may also define private methods.