package answers.infrastructure.balance

import answers.domain.balance.BalanceRepository
import answers.domain.user.User

class BalanceInMemoryRepository extends BalanceRepository {
  override def getFor(user: User): Option[Int] = Some(5)
}
