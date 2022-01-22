package app.infrastructure.balance

import app.domain.balance.BalanceRepository
import app.domain.user.User

class BalanceInMemoryRepository extends BalanceRepository {
  override def getFor(user: User): Option[Int] = Some(5)
}
