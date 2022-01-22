package app.domain.balance

import app.domain.user.User

trait BalanceRepository {
  def getFor(user: User): Option[Int]
}
