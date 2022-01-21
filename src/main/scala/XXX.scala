import cats.Id
import cats.data.Kleisli
import infrastructure.{BalanceInMemoryRepository, BalanceServiceConfig, BalanceServiceProxy, UserInMemoryRepository, UserServiceConfig, UserServiceProxy}
import cats.implicits._
import core.Has
import domain.{BalanceRepository, BalanceService, UserRepository, UserService}

object XXX extends App {

  type AppEnv = Has[BalanceService] with Has[UserService]

  val x: Kleisli[Option, AppEnv, Int] = for {
    user <- UserServiceProxy.getById("1")
    balance <- BalanceServiceProxy.getFor(user)
  } yield balance

  val appLive: Kleisli[Id, Has[BalanceRepository] with Has[UserRepository], Has[UserService] with Has[BalanceService]] = for {
    userService <- UserServiceConfig.live
    balanceService <- BalanceServiceConfig.live
  } yield userService ++ balanceService

  val repos: Has[UserRepository] with Has[BalanceRepository] =
    Has.succeed[UserRepository](new UserInMemoryRepository()) ++
      Has.succeed[BalanceRepository](new BalanceInMemoryRepository())

  val appWithDeps = appLive.apply(repos)

  println(x.apply(appWithDeps))
}
