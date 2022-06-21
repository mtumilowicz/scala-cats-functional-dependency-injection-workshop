package answers

import answers.domain.ServicesEnv
import answers.gateway.UserController
import answers.infrastructure.DependencyConfig
import cats.Id
import cats.data.{Kleisli, OptionT}
import cats.effect.{ExitCode, IO, IOApp}
import common.Environment

object Application extends IOApp {

  def program(userName: String): Kleisli[OptionT[IO, *], ServicesEnv, Int] =
    UserController.getBalance(userName)

  override def run(args: List[String]): IO[ExitCode] = for {
    balance <- executable(getUserNameOrDefault(args)).run().value
    _ <- IO {
      println(balance)
    }
  } yield ExitCode.Success

  private def executable(userName: String): Kleisli[Id, Any, OptionT[IO, Int]] =
    Environment.compose(program(userName), DependencyConfig.appLive)

  private def getUserNameOrDefault(args: List[String]): String =
    args.headOption.getOrElse("existing")
}
