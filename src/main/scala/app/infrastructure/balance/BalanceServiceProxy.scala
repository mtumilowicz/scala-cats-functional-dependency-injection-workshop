package app.infrastructure.balance

import app.core.Has
import app.domain.balance.BalanceService
import app.domain.user.User
import cats.data.Kleisli

object BalanceServiceProxy {
  def getFor(user: User): Kleisli[Option, Has[BalanceService], Int] =
    Kleisli { _.get.getFor(user) }
}
