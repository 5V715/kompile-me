package dev.silas

class TestImpl : SomeInterface.Child {

    context(Context)
    override suspend fun doSomething(foo: FirstParameter, bar: SecondParameter) {
        println("hello $foo and $bar and $foobar")
    }

    override val someProperty: String
        get() = "hi"

}