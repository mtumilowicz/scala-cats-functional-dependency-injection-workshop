import cats.Id
import cats.data.Kleisli
import cats.implicits._
import core.Has
import domain.{BalanceRepository, BalanceService, UserRepository, UserService}
import infrastructure._

object XXX extends App {

  type ReposEnv = Has[BalanceRepository] with Has[UserRepository]
  type ServicesEnv = Has[BalanceService] with Has[UserService]

  val x: Kleisli[Option, ServicesEnv, Int] = for {
    user <- UserServiceProxy.getById("1")
    balance <- BalanceServiceProxy.getFor(user)
  } yield balance

  val rep: Kleisli[Id, Any, ReposEnv] = for {
    userRepo <- UserRepositoryConfig.live
    balanceRepo <- BalanceRepositoryConfig.live
  } yield userRepo ++ balanceRepo

  val sers: Kleisli[Id, ReposEnv, ServicesEnv] = for {
    userService <- UserServiceConfig.live
    balanceService <- BalanceServiceConfig.live
  } yield userService ++ balanceService

  val deps: Kleisli[Id, Any, ServicesEnv] = rep.andThen(sers)

  val appWithDeps = deps.apply(())

  println(x.apply(appWithDeps))
}
