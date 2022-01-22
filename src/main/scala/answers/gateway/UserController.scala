package answers.gateway

import answers.domain._
import answers.infrastructure.balance.BalanceServiceProxy
import answers.infrastructure.user.UserServiceProxy
import cats.implicits._

object UserController {

  def getBalance(id: String)(implicit env: ServicesEnv): Option[Int] = (for {
    user <- UserServiceProxy.getById(id)
    balance <- BalanceServiceProxy.getFor(user)
  } yield balance)
    .apply(env)
}
