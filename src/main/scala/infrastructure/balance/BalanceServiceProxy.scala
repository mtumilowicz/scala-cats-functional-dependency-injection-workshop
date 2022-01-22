package infrastructure.balance

import cats.data.ReaderT
import core.Has
import domain.balance.BalanceService
import domain.user.User

object BalanceServiceProxy {
  def getFor(user: User): ReaderT[Option, Has[BalanceService], Int] =
    ReaderT { repo => repo.get.getFor(user) }
}
