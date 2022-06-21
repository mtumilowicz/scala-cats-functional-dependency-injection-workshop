package app

import app.domain.ServicesEnv
import app.gateway.UserController
import app.infrastructure.DependencyConfig
import cats.Id
import cats.data.Kleisli
import common.Environment

object Application extends App {

  def program(userName: String): Kleisli[Option, ServicesEnv, Int] =
    UserController.getBalance(userName)

  println(executable(getUserNameOrDefault(args)).run())

  private def executable(userName: String): Kleisli[Id, Any, Option[Int]] =
    Environment.compose[Option, ServicesEnv, Int](program(userName), DependencyConfig.appLive)

  private def getUserNameOrDefault(args: Array[String]): String =
    args.headOption.getOrElse("existing")

}
