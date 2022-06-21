package app.gateway

import app.domain._
import app.infrastructure.balance.BalanceServiceProxy
import app.infrastructure.user.UserServiceProxy
import cats.data.Kleisli
import cats.implicits._

object UserController {

  def getBalance(id: String): Kleisli[Option, ServicesEnv, Int] =
    for {
      user <- UserServiceProxy.getById(id)
      balance <- BalanceServiceProxy.getFor(user)
    } yield balance
}
