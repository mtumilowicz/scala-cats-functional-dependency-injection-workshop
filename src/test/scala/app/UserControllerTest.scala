package app

import app.gateway.UserController
import app.infrastructure.DependencyConfig
import domain.ServicesEnv
import org.scalatest.GivenWhenThen
import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

class UserControllerTest extends AnyFeatureSpec with GivenWhenThen {

  def getBalance(id: String): Option[Int] = (for {
    implicit0 (env: ServicesEnv) <- DependencyConfig.appLive
  } yield UserController.getBalance(id))
    .apply(())

  Feature("UserController") {
    Scenario("get balance for existing user") {
      getBalance("existing") shouldBe Some(5)
    }
    Scenario("get balance for non existing user") {
      getBalance("not-existing") shouldBe None
    }
  }

}