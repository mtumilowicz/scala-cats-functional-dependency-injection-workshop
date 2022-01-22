package answers

import answers.domain.ServicesEnv
import answers.gateway.UserController
import answers.infrastructure.DependencyConfig
import cats.data.OptionT
import cats.effect.IO
import munit.CatsEffectSuite
import org.scalatest.GivenWhenThen

class UserControllerTest extends CatsEffectSuite {

  def getBalance(id: String): OptionT[IO, Int] = (for {
    implicit0 (env: ServicesEnv) <- DependencyConfig.appLive
  } yield UserController.getBalance(id))
    .apply(())

    test("get balance for existing user") {
      getBalance("existing").value flatMap { result =>
        IO(assertEquals(result, Some(5)))
      }
    }
    test("get balance for non existing user") {
      getBalance("not-existing").value flatMap { result =>
        IO(assertEquals(result, None))
      }
    }
}
