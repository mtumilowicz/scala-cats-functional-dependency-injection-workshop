package answers

import common.Has
import answers.domain.balance.{BalanceRepository, BalanceService}
import answers.domain.user.{UserRepository, UserService}

package object domain {
  type ServicesEnv = Has[BalanceService] with Has[UserService]
  type RepositoriesEnv = Has[BalanceRepository] with Has[UserRepository]
}
