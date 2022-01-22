package answers

import answers.domain.ServicesEnv
import answers.gateway.UserController
import answers.infrastructure.DependencyConfig
import cats.Id
import cats.data.OptionT
import cats.effect.{ExitCode, IO, IOApp}

object Application extends IOApp {

  val program: Id[OptionT[IO, Int]] = (for {
    implicit0(env: ServicesEnv) <- DependencyConfig.appLive
  } yield UserController.getBalance("existing")
    ).apply(())

  override def run(args: List[String]): IO[ExitCode] = for {
    balance <- program.value
    _ <- IO { println(balance) }
  } yield ExitCode.Success
}
