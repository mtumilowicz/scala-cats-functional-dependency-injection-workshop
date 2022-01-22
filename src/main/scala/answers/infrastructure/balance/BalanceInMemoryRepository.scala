package answers.infrastructure.balance

import answers.domain.balance.BalanceRepository
import answers.domain.user.User
import cats.data.OptionT
import cats.effect.IO

class BalanceInMemoryRepository extends BalanceRepository {
  override def getFor(user: User): OptionT[IO, Int] = IO { Some(5) }
}
