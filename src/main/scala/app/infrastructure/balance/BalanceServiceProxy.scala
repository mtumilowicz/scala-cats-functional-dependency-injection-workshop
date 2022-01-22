package app.infrastructure.balance

import cats.data.ReaderT
import app.core.Has
import app.domain.balance.BalanceService
import app.domain.user.User

object BalanceServiceProxy {
  def getFor(user: User): ReaderT[Option, Has[BalanceService], Int] =
    ReaderT { repo => repo.get.getFor(user) }
}
