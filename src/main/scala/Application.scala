import domain.ServicesEnv
import gateway.UserController
import infrastructure._

object Application extends App {

  val balance = for {
    implicit0 (env: ServicesEnv) <- DependencyConfig.appLive
  } yield UserController.getBalance("existing")

  println(balance.apply(()))

}
