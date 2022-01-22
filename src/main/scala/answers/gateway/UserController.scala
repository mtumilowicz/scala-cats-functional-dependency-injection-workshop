package answers.gateway

import cats.implicits._
import app.domain._
import app.infrastructure.balance.BalanceServiceProxy
import app.infrastructure.user.UserServiceProxy

object UserController {

  def getBalance(id: String)(implicit env: ServicesEnv): Option[Int] = (for {
    user <- UserServiceProxy.getById(id)
    balance <- BalanceServiceProxy.getFor(user)
  } yield balance)
    .apply(env)
}
