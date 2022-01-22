import core.Has

package object domain {
  type ServicesEnv = Has[BalanceService] with Has[UserService]
  type RepositoriesEnv = Has[BalanceRepository] with Has[UserRepository]
}
