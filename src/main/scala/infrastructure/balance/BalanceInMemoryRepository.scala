package infrastructure.balance

import domain.balance.BalanceRepository
import domain.user.User

class BalanceInMemoryRepository extends BalanceRepository {
  override def getFor(user: User): Option[Int] = Some(5)
}
