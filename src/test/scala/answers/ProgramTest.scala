package answers

import answers.infrastructure.DependencyConfig
import cats.Id
import cats.data.OptionT
import cats.effect.IO
import common.Environment
import munit.CatsEffectSuite

class ProgramTest extends CatsEffectSuite {

  def subject(userName: String): Id[OptionT[IO, Int]] =
    Environment.compose(Application.program(userName), DependencyConfig.appTest)
      .run()

  test("get balance for existing user") {
    subject("existing").value flatMap { result =>
      IO(assertEquals(result, Some(5)))
    }
  }
  test("get balance for non existing user") {
    subject("not-existing").value flatMap { result =>
      IO(assertEquals(result, None))
    }
  }
}
