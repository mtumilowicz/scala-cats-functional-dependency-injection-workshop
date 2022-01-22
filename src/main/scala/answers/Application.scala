package answers

import answers.domain.ServicesEnv
import answers.gateway.UserController
import answers.infrastructure.DependencyConfig

object Application extends App {

  val balance = for {
    implicit0(env: ServicesEnv) <- DependencyConfig.appLive
  } yield UserController.getBalance("existing")

  println(balance.apply(()))

}
