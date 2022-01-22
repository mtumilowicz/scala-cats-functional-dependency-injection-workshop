import core.Has
import domain.balance.{BalanceRepository, BalanceService}
import domain.user.{UserRepository, UserService}

package object domain {
  type ServicesEnv = Has[BalanceService] with Has[UserService]
  type RepositoriesEnv = Has[BalanceRepository] with Has[UserRepository]
}
