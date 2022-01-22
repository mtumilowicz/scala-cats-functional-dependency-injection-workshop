package app

import app.domain.ServicesEnv
import app.domain.user.User
import app.gateway.UserController
import app.infrastructure.DependencyConfig
import cats.data.Reader

object Application extends App {

  val balance = for {
    implicit0(env: ServicesEnv) <- DependencyConfig.appLive
  } yield UserController.getBalance("existing")

  println(balance.run())

}
