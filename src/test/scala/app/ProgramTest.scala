package app

import app.infrastructure.DependencyConfig
import common.Environment
import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

class ProgramTest extends AnyFeatureSpec {

  def subject(userName: String): Option[Int] =
    Environment.compose(Application.program(userName), DependencyConfig.appTest)
      .run()

  Feature("UserController") {
    Scenario("get balance for existing user") {
      subject("existing") shouldBe Some(5)
    }
    Scenario("get balance for non existing user") {
      subject("not-existing") shouldBe None
    }
  }

}
