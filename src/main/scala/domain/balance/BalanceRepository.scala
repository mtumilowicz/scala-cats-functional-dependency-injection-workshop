package domain.balance

import domain.user.User

trait BalanceRepository {
  def getFor(user: User): Option[Int]
}
