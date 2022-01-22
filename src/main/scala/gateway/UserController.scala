package gateway

import cats.data.Kleisli
import cats.implicits._
import domain._
import infrastructure.{BalanceServiceProxy, UserServiceProxy}

object UserController {

  def getBalance(id: String): Kleisli[Option, ServicesEnv, Int] =
   for {
    user <- UserServiceProxy.getById(id)
    balance <- BalanceServiceProxy.getFor(user)
  } yield balance
}
