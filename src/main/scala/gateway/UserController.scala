package gateway

import cats.implicits._
import domain._
import infrastructure.balance.BalanceServiceProxy
import infrastructure.user.UserServiceProxy

object UserController {

  def getBalance(id: String)(implicit env: ServicesEnv): Option[Int] = (for {
    user <- UserServiceProxy.getById(id)
    balance <- BalanceServiceProxy.getFor(user)
  } yield balance)
    .apply(env)
}
