package answers.gateway

import answers.domain._
import answers.infrastructure.balance.BalanceServiceProxy
import answers.infrastructure.user.UserServiceProxy
import cats.data.{Kleisli, OptionT}
import cats.effect.IO

object UserController {

  def getBalance(id: String): Kleisli[OptionT[IO, *], ServicesEnv, Int] =
    for {
      user <- UserServiceProxy.getById(id)
      balance <- BalanceServiceProxy.getFor(user)
    } yield balance
}
