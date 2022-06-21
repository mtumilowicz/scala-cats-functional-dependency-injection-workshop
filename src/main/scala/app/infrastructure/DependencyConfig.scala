package app.infrastructure

import app.domain._
import app.infrastructure.config.balance.{BalanceRepositoryConfig, BalanceServiceConfig}
import app.infrastructure.config.user.{UserRepositoryConfig, UserServiceConfig}
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

  // usually different environment for tests: inMemory repositories, etc
  val appTest: Kleisli[Id, Any, ServicesEnv] = repositories >>> services

}
