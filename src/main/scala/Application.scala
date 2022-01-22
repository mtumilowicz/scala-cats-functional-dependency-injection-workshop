import gateway.UserController
import infrastructure._

object Application extends App {

  val balance = for {
    env <- DependencyConfig.appLive
  } yield UserController.getBalance("1").apply(env)

  println(balance.apply(()))

}
