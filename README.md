# scala-cats-functional-dependency-injection-workshop

* https://github.com/mtumilowicz/scala213-functional-programming-functor-monoid-monad-workshop
* https://github.com/mtumilowicz/scala-cats-implicit-workshop
* https://github.com/mtumilowicz/java11-category-theory-reader-functor
* https://github.com/mtumilowicz/scala-http4s-zio-fs2-doobie-workshop
* https://github.com/kitlangton/zio-from-scatch
* https://github.com/mtumilowicz/java11-category-theory-kleisli-category
* https://github.com/mtumilowicz/scala212-category-theory-kleisli-writer-category-functor
* https://medium.com/@alexander.zaidel/composing-functions-with-reader-monad-f3e471958e2a
* https://fares.codes/posts/cats-kleisli/

* The main purpose of the Reader monad is to compose functions and delay dependency injection phase until the later moment
* Combining Reader together with other monads requires to write a bit of boilerplate
    ```
    def validateUser(name: Name, age: Age): Reader[Env, Try[User]] =
        for {
          validatedNameTry <- validateName(name)
          validatedAgeTry <- validateAge(age)
        } yield for {
            validatedName <- validatedNameTry
            validatedAge <- validatedAgeTry
          } yield User(validatedName, validatedAge)

    private def validateName(name: Name): Reader[Env, Try[Name]]

    private def validateAge(age: Age): Reader[Env, Try[Age]]
    ```
* and ReaderT
    ```
    def validateUser(name: Name, age: Age): ReaderT[Try, Env, User] =
      for {
        validatedName <- validateName(name)
        validatedAge <- validateAge(age)
      } yield User(validatedName, validatedAge)

    private def validateName(name: Name): ReaderT[Try, Env, Name]

    private def validateAge(age: Age): ReaderT[Try, Env, Age]
    ```
* Reader Monad can also be used for passing a Diagnostic Context .
    * Image that you have to add requestId to every log message in your code base, thus you are forced to pass requestId in every function.
    * example
        ```
        def saveUser(user: User): = ReaderT[Try, Context, User] { context =>
          logger.debug(s"reqId: ${context.requestId} saveUser ${user}")
          // saving logic goes here
        }

        def createUser(name: Name, age: Age): Try[User] =
          (for {
            user <- validateUser(name, age)
            savedUser <- UserRepository.saveUser(user)
          } yield savedUser).run(Context(generateReqId()))
        ```
* monad transformers
* Kleisli
    * Kleisli allows the composition of functions where the return type is a monadic value while the input to the next function is not.
    * example
        ```
        val getValueFromDB: Unit => String = ...
        val transformValue: String => String = ...
        val saveValueToDB: String => Unit = ...

        val result = saveValueToDB compose transformValue compose getValueFromDB
        result()
        ```
        