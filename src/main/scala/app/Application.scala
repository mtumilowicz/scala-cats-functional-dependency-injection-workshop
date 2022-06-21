package app

import app.domain.ServicesEnv
import app.gateway.UserController
import app.infrastructure.DependencyConfig

object Application extends App {

  val balance = for {
    implicit0(env: ServicesEnv) <- DependencyConfig.appLive
  } yield UserController.getBalance("existing")

  println(balance.run())

}
