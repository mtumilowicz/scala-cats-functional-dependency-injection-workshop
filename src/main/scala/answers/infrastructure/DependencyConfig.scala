package answers.infrastructure

import answers.domain._
import answers.infrastructure.config.balance.{BalanceRepositoryConfig, BalanceServiceConfig}
import answers.infrastructure.config.user.{UserRepositoryConfig, UserServiceConfig}
import cats.Id
import cats.data.Kleisli
import cats.implicits._

object DependencyConfig {

  private val repositories: Kleisli[Id, Any, RepositoriesEnv] = for {
    userRepo <- UserRepositoryConfig.live
    balanceRepo <- BalanceRepositoryConfig.live
  } yield userRepo ++ balanceRepo

  private val services: Kleisli[Id, RepositoriesEnv, ServicesEnv] = for {
    userService <- UserServiceConfig.live
    balanceService <- BalanceServiceConfig.live
  } yield userService ++ balanceService

  val appLive: Kleisli[Id, Any, ServicesEnv] = repositories >>> services

}
