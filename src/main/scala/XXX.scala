import cats.Id
import cats.data.Kleisli
import infrastructure.{BalanceServiceConfig, BalanceServiceProxy, UserServiceConfig, UserServiceProxy}
import cats.implicits._
import core.Has
import domain.{BalanceRepository, BalanceService, UserRepository, UserService}

object XXX extends App {

  type AppEnv = Has[BalanceService] with Has[UserService]

  val x: Kleisli[Option, AppEnv, Int] = for {
    user <- UserServiceProxy.getById("1")
    balance <- BalanceServiceProxy.getFor(user)
  } yield balance

  val appLive: Kleisli[Id, Has[BalanceRepository] with Has[UserRepository], Has[UserService]] = for {
    _ <- UserServiceConfig.live
    _ <- BalanceServiceConfig.live
  } yield ()

  val appWithDeps = appLive.app
}
