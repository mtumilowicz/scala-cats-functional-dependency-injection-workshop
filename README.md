# scala-cats-functional-dependency-injection-workshop

* references
    * https://github.com/kitlangton/zio-from-scatch
    * https://medium.com/@alexander.zaidel/composing-functions-with-reader-monad-f3e471958e2a
    * https://fares.codes/posts/cats-kleisli/
    * https://stackoverflow.com/questions/61899370/when-should-one-use-a-kleisli
    * https://medium.com/@supermanue/understanding-kleisli-in-scala-9c42ec1a5977
    * https://typelevel.org/cats/datatypes/kleisli.html
    * https://blog.softwaremill.com/kleisli-category-from-theory-to-cats-fbd140bf396e
    * https://blog.buildo.io/monad-transformers-for-the-working-programmer-aa7e981190e7
    * https://blog.softwaremill.com/monad-transformers-and-cats-3-tips-for-beginners-196fabe58daa
    * [Monad transformers down to earth by Gabriele Petronella](https://www.youtube.com/watch?v=jd5e71nFEZM)

## preface
* goals of this workshop:
    * showing the functional approach to dependency injection
    * practicing using effects for modelling domain
    * example of conditionally added methods to the type (based on the type)
* task: rewrite the application from `app` package using effects
    * answers: `answers` package
* it can be worthwhile to take a look at
    * type projection: https://github.com/mtumilowicz/scala213-functional-programming-functor-monoid-monad-workshop
    * conditionally adding methods (`<:<`): https://github.com/mtumilowicz/scala-cats-implicit-workshop
    * Reader: https://github.com/mtumilowicz/java11-category-theory-reader-functor
    * Kleisli
        * https://github.com/mtumilowicz/java11-category-theory-kleisli-category
        * https://github.com/mtumilowicz/scala212-category-theory-kleisli-writer-category-functor
    * heterogenous map (`Has`) and effects: https://github.com/mtumilowicz/scala-http4s-zio-fs2-doobie-workshop

## Reader
* main purpose: compose functions and delay dependency injection phase until the later moment
* drawback: combining Reader together with other monads requires to write a bit of boilerplate
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
* solution: `ReaderT` (monad transformer)
    ```
    def validateUser(name: Name, age: Age): ReaderT[Try, Env, User] =
      for {
        validatedName <- validateName(name)
        validatedAge <- validateAge(age)
      } yield User(validatedName, validatedAge)

    private def validateName(name: Name): ReaderT[Try, Env, Name]

    private def validateAge(age: Age): ReaderT[Try, Env, Age]
    ```
* can also be used for passing a "Diagnostic Context"
    * example: add `requestId` to every log message in your code base
        * make `requestId` accessible in every function
    * solution
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
## monad transformers
It turns out this is a well known fact: Monads do not compose, at least not generically.
So, yeah, Monads may not compose generically, but you only need a flatMap (and a map) that works for Future[Option[A]].
* problem
    ```
    def findUserById(id: Long): Future[Option[User]] = ???
    def findAddressByUser(user: User): Future[Option[Address]] = ???

    def findAddressByUserId(id: Long): Future[Option[Address]] =
      for {
        user    <- findUserById(id) // user has type: Option[User]
        address <- findAddressByUser(user) // error: type mismatch;
      } yield address
    ```
    or in other words
    ```

    ```
* solution
    ```
    case class FutureOpt[A](value: Future[Option[A]]) {

      def map[B](f: A => B): FutureOpt[B] =
        FutureOpt(value.map(optA => optA.map(f)))
      def flatMap[B](f: A => FutureOpt[B]): FutureOpt[B] =
        FutureOpt(value.flatMap(opt => opt match {
          case Some(a) => f(a).value
          case None => Future.successful(None)
        }))
    }

    def findAddressByUserId(id: Long): Future[Option[Address]] =
      (for {
        user    <- FutOpt(findUserById(id))
        address <- FutOpt(findAddressByUser(user))
      } yield address).value
    ```
* and suppose we want to have wrapper for `List[Future]`
    ```
    case class ListOpt[A](value: List[Option[A]]) {

     def map[B](f: A => B): ListOpt[B] =
        ListOpt(value.map(optA => optA.map(f)))
     def flatMap[B](f: A => ListOpt[B]): ListOpt[B] =
        ListOpt(value.flatMap(opt => opt match {
          case Some(a) => f(a).value
          case None => List(None)
        }))
    }
    ```
* conclusion
    * we don’t need to know anything specific about the "outer" Monad
    * On the other hand, see how we destructured the Option? That’s some specific knowledge about the “inner” Monad (Option in this case) that we need to have.
    * we’ve just accidentally invented a Monad Transformer, usually named OptionT
        * OptionT[F, A] is a flat version of F[Option[A]] which is a Monad itself
* IO[Option[A]] might describe a computation that performs side-effect returning a single value that might or might not exists

## Kleisli
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
