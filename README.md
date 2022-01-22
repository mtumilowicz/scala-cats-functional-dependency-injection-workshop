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
* https://stackoverflow.com/questions/61899370/when-should-one-use-a-kleisli
* https://medium.com/@supermanue/understanding-kleisli-in-scala-9c42ec1a5977
* https://typelevel.org/cats/datatypes/kleisli.html
* https://blog.softwaremill.com/kleisli-category-from-theory-to-cats-fbd140bf396e

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
    * At its core, Kleisli[F[_], A, B] is just a wrapper around the function A => F[B]
    * We may also have several functions which depend on some environment and want a nice way to compose these functions to ensure they all receive the same environment
    * Kleisli allows the composition of functions where the return type is a monadic value while the input to the next function is not.
    * Kleisli can be viewed as the monad transformer for functions.
    * example
        ```
        val makeDB: Config => IO[Database]
        val makeHttp: Config => IO[HttpClient]
        val makeCache: Config => IO[RedisClient]

        def program(config: Config) = for {
          db <- makeDB(config)
          http <- makeHttp(config)
          cache <- makeCache(config)
          ...
        } yield someResult
        vs
        ```
        val makeDB: Config => IO[Database]
        val makeHttp: Config => IO[HttpClient]
        val makeCache: Config => IO[RedisClient]

        val program: Kleisli[IO, Config, Result] = for {
          db <- makeDB
          http <- makeHttp
          cache <- makeCache
          ...
        } yield someResult
        ```
        and
        ```
        val parse: String => Option[Int] =
          s => if (s.matches("-?[0-9]+")) Some(s.toInt) else None

        val reciprocal: Int => Option[Double] =
          i => if (i != 0) Some(1.0 / i) else None

        parse("7")
        parse("casa")
        reciprocal(7)
        reciprocal(0)

        //FUNCTION COMPOSITION
        def parseAndReciprocal(s: String): Option[Double] = parse(s).flatMap(reciprocal) // you cannot compose it, you have to flatMap

        val parseKleisli: Kleisli[Option,String,Int] =
          Kleisli((s: String) => if (s.matches("-?[0-9]+")) Some(s.toInt) else None)

        val reciprocalKleisli: Kleisli[Option, Int, Double] =
          Kleisli(reciprocal)

        parseKleisli("7")
        parseKleisli("casa")
        reciprocalKleisli(7)
        reciprocalKleisli(0)

        val parseAndReciprocalKleisli = parseKleisli andThen reciprocalKleisli
        ```
