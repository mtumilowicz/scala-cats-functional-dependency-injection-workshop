package app

import app.domain.balance.{BalanceRepository, BalanceService}
import app.domain.user.{UserRepository, UserService}
import common.Has

package object domain {
  type ServicesEnv = Has[BalanceService] with Has[UserService]
  type RepositoriesEnv = Has[BalanceRepository] with Has[UserRepository]
}
