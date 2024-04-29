package dev.silas

interface SomeInterface {

    val someProperty: String

    interface Child : SomeInterface {

        suspend fun doSomething(foo: FirstParameter, bar: SecondParameter)

    }

    interface Test : Child {

        context(Context)
        override suspend fun doSomething(foo: FirstParameter, bar: SecondParameter)
    }

}


interface FirstParameter {

    val foo: String

}

interface SecondParameter {

    val bar: String

}

interface Context {
    val foobar: String
}