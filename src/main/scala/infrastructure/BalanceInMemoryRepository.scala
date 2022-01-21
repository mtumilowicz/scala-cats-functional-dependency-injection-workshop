package infrastructure

import domain.{BalanceRepository, User}

class BalanceInMemoryRepository extends BalanceRepository {
  override def getFor(user: User): Option[Int] = Some(5)
}
